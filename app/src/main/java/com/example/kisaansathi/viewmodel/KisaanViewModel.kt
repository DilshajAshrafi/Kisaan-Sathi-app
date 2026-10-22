package com.example.kisaansathi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kisaansathi.data.ConsultationAttachment
import com.example.kisaansathi.data.ConsultationSubmission
import com.example.kisaansathi.data.FormSubmitService
import com.example.kisaansathi.data.KisaanRepository
import com.example.kisaansathi.data.LocalStore
import com.example.kisaansathi.data.MandiPriceService
import com.example.kisaansathi.data.WeatherService
import com.example.kisaansathi.model.ChatMessage
import com.example.kisaansathi.model.ConsultationTicket
import com.example.kisaansathi.model.CropPlan
import com.example.kisaansathi.model.CropGuide
import com.example.kisaansathi.model.FarmDiaryEntry
import com.example.kisaansathi.model.FarmExpense
import com.example.kisaansathi.model.IrrigationAdvice
import com.example.kisaansathi.model.Language
import com.example.kisaansathi.model.IndianLanguage
import com.example.kisaansathi.model.MandiPrice
import com.example.kisaansathi.model.MessageRole
import com.example.kisaansathi.model.PestCareItem
import com.example.kisaansathi.model.SchemeInfo
import com.example.kisaansathi.model.ScreenRoute
import com.example.kisaansathi.model.WeatherInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

data class UiState(
    val currentRoute: ScreenRoute = ScreenRoute.HOME,
    val selectedLanguage: Language = Language.ENGLISH,
    val chosenLanguage: IndianLanguage = IndianLanguage.ENGLISH,
    val isDarkMode: Boolean = false,
    val schemesSearchQuery: String = "",
    val selectedCropFilter: String = "All",
    val irrigationCrop: String = "Wheat",
    val irrigationSoil: String = "Loamy",
    val irrigationAreaText: String = "2.5",
    val irrigationAdvice: IrrigationAdvice? = null,
    val chatMessages: List<ChatMessage> = emptyList(),
    val isChatLoading: Boolean = false,
    val currentTicket: ConsultationTicket? = null,
    val isSubmittingConsultation: Boolean = false,
    val consultationSubmissionError: String? = null,
    val submittedTickets: List<ConsultationTicket> = emptyList(),
    val weatherInfo: WeatherInfo? = null,
    val selectedWeatherLocation: String = "Karnal, Haryana",
    val isWeatherLoading: Boolean = false,
    val showOnboarding: Boolean = false,
    val fontScale: Float = 1f,
    val farmExpenses: List<FarmExpense> = emptyList(),
    val farmDiaryEntries: List<FarmDiaryEntry> = emptyList(),
    val cropPlan: CropPlan? = null,
    val mandiPrices: List<MandiPrice> = emptyList(),
    val isMandiLoading: Boolean = false,
    val mandiError: String? = null,
    val consultationPhotoUri: String? = null,
    val consultationCrop: String? = null,
    val consultationProblem: String? = null
)

class KisaanViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    private var weatherRequestId = 0
    private var store: LocalStore? = null

    init {
        // Initialize default welcome message in AI Agri Assistant
        initDefaultChat()
        // Calculate initial irrigation plan
        calculateIrrigation()
        // Fetch initial farm weather
        fetchWeather("Karnal, Haryana")
    }

    fun fetchWeather(location: String) {
        val requestId = ++weatherRequestId
        _uiState.value = _uiState.value.copy(
            selectedWeatherLocation = location,
            weatherInfo = null,
            isWeatherLoading = true
        )
        viewModelScope.launch {
            val weather = WeatherService.fetchWeather(
                locationQuery = location,
                apiKey = com.example.BuildConfig.OPENWEATHER_API_KEY
            )
            if (requestId == weatherRequestId) {
                if (weather != null) store?.cacheWeather(weather)
                // Offline: show the last genuine reading for this place, clearly labelled as saved.
                val shown = weather ?: store?.cachedWeather(location)
                _uiState.value = _uiState.value.copy(
                    weatherInfo = shown,
                    isWeatherLoading = false
                )
            }
        }
    }

    /** Connects on-device storage and restores saved language, text size and request history. */
    fun attachStore(localStore: LocalStore) {
        if (store != null) return
        store = localStore
        _uiState.value = _uiState.value.copy(
            showOnboarding = !localStore.onboardingDone,
            fontScale = localStore.fontScale,
            submittedTickets = localStore.loadRequests(),
            farmExpenses = localStore.loadFarmExpenses(),
            farmDiaryEntries = localStore.loadFarmDiary(),
            cropPlan = localStore.loadCropPlan()
        )
        localStore.restoreCropReminder()
        if (localStore.onboardingDone) {
            setIndianLanguage(localStore.indianLanguage)
        }
    }

    fun completeOnboarding() {
        store?.onboardingDone = true
        _uiState.value = _uiState.value.copy(showOnboarding = false)
    }

    fun setFontScale(scale: Float) {
        store?.fontScale = scale
        _uiState.value = _uiState.value.copy(fontScale = scale)
    }

    fun refreshWeather() {
        fetchWeather(_uiState.value.selectedWeatherLocation)
    }

    fun saveFarmExpense(expense: FarmExpense) {
        val entries = listOf(expense) + _uiState.value.farmExpenses
        store?.saveFarmExpenses(entries)
        _uiState.value = _uiState.value.copy(farmExpenses = entries)
    }

    fun deleteFarmExpense(id: String) {
        val entries = _uiState.value.farmExpenses.filterNot { it.id == id }
        store?.saveFarmExpenses(entries)
        _uiState.value = _uiState.value.copy(farmExpenses = entries)
    }

    fun saveFarmDiaryEntry(entry: FarmDiaryEntry) {
        val entries = listOf(entry) + _uiState.value.farmDiaryEntries
        store?.saveFarmDiary(entries)
        _uiState.value = _uiState.value.copy(farmDiaryEntries = entries)
    }

    fun deleteFarmDiaryEntry(id: String) {
        val entries = _uiState.value.farmDiaryEntries.filterNot { it.id == id }
        store?.saveFarmDiary(entries)
        _uiState.value = _uiState.value.copy(farmDiaryEntries = entries)
    }

    fun saveCropPlan(plan: CropPlan?) {
        store?.saveCropPlan(plan)
        _uiState.value = _uiState.value.copy(cropPlan = plan)
    }

    fun setConsultationPrefill(uri: String?, crop: String, problem: String) {
        _uiState.value = _uiState.value.copy(
            consultationPhotoUri = uri,
            consultationCrop = crop,
            consultationProblem = problem
        )
    }

    fun fetchMandiPrices(commodity: String, state: String, district: String = "") {
        _uiState.value = _uiState.value.copy(
            mandiPrices = emptyList(),
            isMandiLoading = true,
            mandiError = null
        )
        viewModelScope.launch {
            try {
                val prices = MandiPriceService.fetchPrices(
                    commodity = commodity.trim(),
                    state = state.trim(),
                    apiKey = com.example.BuildConfig.DATA_GOV_IN_API_KEY,
                    district = district.trim()
                )
                _uiState.value = _uiState.value.copy(
                    mandiPrices = prices,
                    isMandiLoading = false,
                    mandiError = if (prices.isEmpty()) {
                        "No official market records were found for that commodity and state."
                    } else null
                )
            } catch (exception: IOException) {
                _uiState.value = _uiState.value.copy(
                    isMandiLoading = false,
                    mandiError = exception.localizedMessage
                        ?: "Could not load official mandi prices. Check your connection and try again."
                )
            }
        }
    }

    private fun initDefaultChat() {
        val welcomeMsg = ChatMessage(
            id = UUID.randomUUID().toString(),
            role = MessageRole.ASSISTANT,
            text = "Namaste Kisan Bhai! 🙏 I am your Kisaan Sathi AI Farming Assistant. How can I help you today with crops, weather advisories, irrigation planning, soil care, or government schemes like PM-KISAN?",
            timestamp = "Just now"
        )
        _uiState.value = _uiState.value.copy(chatMessages = listOf(welcomeMsg))
    }

    fun setLanguage(language: Language) {
        val welcomeText = when (language) {
            Language.HINDI -> "नमस्ते किसान भाई! 🙏 मैं आपका किसान साथी एआई सहायक हूँ। आज मैं आपकी फसल, मौसम, सिंचाई, मिट्टी या सरकारी योजनाओं में क्या मदद कर सकता हूँ?"
            Language.PUNJABI -> "ਸਤਿ ਸ਼੍ਰੀ ਅਕਾਲ ਕਿਸਾਨ ਵੀਰੋ! 🙏 ਮੈਂ ਤੁਹਾਡਾ ਕਿਸਾਨ ਸਾਥੀ ਏਆਈ ਸਹਾਇਕ ਹਾਂ। ਫ਼ਸਲਾਂ, ਮੌਸਮ, ਸਿੰਚਾਈ, ਜਾਂ ਸਰਕਾਰੀ ਸਕੀਮਾਂ ਬਾਰੇ ਕੋਈ ਵੀ ਸਵਾਲ ਪੁੱਛੋ।"
            Language.GUJARATI -> "નમસ્તે ખેડૂત મિત્ર! 🙏 હું તમારો કિસાન સાથી AI સહાયક છું. પાક, હવામાન, સિંચાઈ અથવા સરકારી યોજનાઓ અંગે પ્રશ્નો પૂછી શકો છો."
            Language.MARATHI -> "नमस्कार शेतकरी बंधूंनो! 🙏 मी तुमचा किसान साथी एआय सहाय्यक आहे. पीक, हवामान, सिंचन किंवा सरकारी योजनांविषयी विचारा."
            Language.BENGALI -> "নমস্কার কৃষক ভাই! 🙏 আমি আপনার কিষাণ সাথী এআই সহকারী। ফসল, আবহাওয়া, সেচ বা সরকারি প্রকল্প সম্পর্কে যেকোনো প্রশ্ন করুন।"
            Language.TAMIL -> "வணக்கம் விவசாய தோழரே! 🙏 நான் உங்கள் கிசான் சாதி AI உதவியாளர். பயிர்கள், வானிலை, பாசனம் அல்லது அரசு திட்டங்கள் பற்றி கேளுங்கள்."
            Language.TELUGU -> "నమస్కారం రైతు సోదరా! 🙏 నేను మీ కిసాన్ సాథి AI సహాయకుడిని. పంటలు, వాతావరణం, సాగునీరు లేదా ప్రభుత్వ పథకాల గురించి అడగండి."
            Language.ENGLISH -> "Namaste, Farmer Friend! 🙏 I am your Kisaan Sathi AI Farming Assistant. How can I help you today with crops, weather advisories, irrigation planning, soil care, or government schemes?"
        }
        val welcomeMsg = ChatMessage(
            id = UUID.randomUUID().toString(),
            role = MessageRole.ASSISTANT,
            text = welcomeText,
            timestamp = "Just now"
        )
        _uiState.value = _uiState.value.copy(
            selectedLanguage = language,
            chatMessages = listOf(welcomeMsg)
        )
    }

    fun setIndianLanguage(language: IndianLanguage) {
        store?.indianLanguage = language
        _uiState.value = _uiState.value.copy(chosenLanguage = language)
        setLanguage(language.base)
    }

    fun setDarkMode(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(isDarkMode = enabled)
    }

    fun navigateTo(route: ScreenRoute) {
        _uiState.value = _uiState.value.copy(currentRoute = route)
    }

    fun setSchemesSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(schemesSearchQuery = query)
    }

    fun setCropFilter(crop: String) {
        _uiState.value = _uiState.value.copy(selectedCropFilter = crop)
    }

    fun setIrrigationCrop(crop: String) {
        _uiState.value = _uiState.value.copy(irrigationCrop = crop)
        calculateIrrigation()
    }

    fun setIrrigationSoil(soil: String) {
        _uiState.value = _uiState.value.copy(irrigationSoil = soil)
        calculateIrrigation()
    }

    fun setIrrigationArea(area: String) {
        _uiState.value = _uiState.value.copy(irrigationAreaText = area)
        calculateIrrigation()
    }

    fun calculateIrrigation() {
        val area = _uiState.value.irrigationAreaText.toDoubleOrNull() ?: 1.0
        val advice = KisaanRepository.calculateIrrigation(
            crop = _uiState.value.irrigationCrop,
            soil = _uiState.value.irrigationSoil,
            areaAcres = area
        )
        _uiState.value = _uiState.value.copy(irrigationAdvice = advice)
    }

    fun submitConsultationRequest(
        farmerName: String,
        email: String,
        phone: String,
        financialAid: String,
        villageDistrict: String,
        crop: String,
        problemDescription: String,
        attachment: ConsultationAttachment? = null
    ) {
        if (_uiState.value.isSubmittingConsultation) return

        val ticketId = "KS-${(100000..999999).random()}"
        val submission = ConsultationSubmission(
            ticketId = ticketId,
            farmerName = farmerName.trim(),
            email = email.trim(),
            phone = phone.trim(),
            financialAid = financialAid.trim(),
            villageDistrict = villageDistrict.trim().ifBlank { "Not provided" },
            crop = crop.trim().ifBlank { "Not provided" },
            problemDescription = problemDescription.trim(),
            attachment = attachment
        )

        _uiState.value = _uiState.value.copy(
            isSubmittingConsultation = true,
            consultationSubmissionError = null
        )
        viewModelScope.launch {
            try {
                FormSubmitService.sendConsultation(submission)
                val ticket = ConsultationTicket(
                    ticketId = ticketId,
                    farmerName = submission.farmerName,
                    phone = submission.phone,
                    villageDistrict = submission.villageDistrict,
                    crop = submission.crop,
                    problemDescription = submission.problemDescription,
                    createdAt = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
                        .format(Date()),
                    status = "Sent to Group 11 IDT"
                )
                val history = listOf(ticket) + _uiState.value.submittedTickets
                store?.saveRequests(history)
                _uiState.value = _uiState.value.copy(
                    currentTicket = ticket,
                    submittedTickets = history,
                    currentRoute = ScreenRoute.REQUEST_SENT,
                    isSubmittingConsultation = false,
                    consultationPhotoUri = null,
                    consultationCrop = null,
                    consultationProblem = null
                )
            } catch (exception: IOException) {
                _uiState.value = _uiState.value.copy(
                    isSubmittingConsultation = false,
                    consultationSubmissionError = exception.localizedMessage
                        ?: "Could not send your request. Check your internet connection and try again."
                )
            }
        }
    }

    fun sendChatMessage(userText: String) {
        if (userText.isBlank()) return

        val userMsg = ChatMessage(
            id = UUID.randomUUID().toString(),
            role = MessageRole.USER,
            text = userText.trim(),
            timestamp = "Just now"
        )

        val updatedMessages = _uiState.value.chatMessages + userMsg
        _uiState.value = _uiState.value.copy(
            chatMessages = updatedMessages,
            isChatLoading = true
        )

        viewModelScope.launch {
            delay(1000) // realistic response pause
            val replyText = generateAgriReply(userText.trim(), _uiState.value.selectedLanguage)
            val assistantMsg = ChatMessage(
                id = UUID.randomUUID().toString(),
                role = MessageRole.ASSISTANT,
                text = replyText,
                timestamp = "Just now"
            )
            _uiState.value = _uiState.value.copy(
                chatMessages = _uiState.value.chatMessages + assistantMsg,
                isChatLoading = false
            )
        }
    }

    private fun generateAgriReply(query: String, lang: Language): String {
        val q = query.lowercase()
        return when {
            q.contains("pm-kisan") || q.contains("pm kisan") || q.contains("6000") || q.contains("सम्मान निधि") -> {
                if (lang == Language.HINDI) {
                    "पीएम-किसान योजना (PM-KISAN) के तहत पात्र किसान परिवारों को प्रति वर्ष ₹6,000 की प्रत्यक्ष वित्तीय सहायता दी जाती है। यह राशि ₹2,000 की तीन बराबर किस्तों में सीधे आपके बैंक खाते (DBT) में भेजी जाती है। इसके लिए आधार लिंक और eKYC अनिवार्य है। आप pmkisan.gov.in पर या नजदीकी CSC केंद्र से आवेदन कर सकते हैं।"
                } else {
                    "Under PM-KISAN (Pradhan Mantri Kisan Samman Nidhi), eligible farmer families receive ₹6,000 per year directly in their bank accounts via DBT in 3 four-monthly installments of ₹2,000. Ensure your Aadhaar is linked to your bank account and eKYC is completed on pmkisan.gov.in."
                }
            }
            q.contains("water") || q.contains("irrigation") || q.contains("सिंचाई") || q.contains("पानी") || q.contains("drip") -> {
                if (lang == Language.HINDI) {
                    "सिंचाई प्रबंधन: ड्रिप या स्प्रिंकलर (फव्वारा) सिंचाई अपनाने से 40% से 50% तक पानी की बचत होती है और फसल उत्पादन 20-30% बढ़ जाता है। गेहूं के लिए सीआरआई (CRI) अवस्था (बुवाई के 20-25 दिन बाद) पर पहली सिंचाई अत्यंत आवश्यक है। सूक्ष्म सिंचाई प्रणाली पर सरकार 45% से 55% तक सब्सिडी देती है।"
                } else {
                    "Smart Irrigation Advisory: Micro-irrigation (Drip or Sprinkler) can save up to 40-50% water while boosting yield by 20-30%. For wheat, the Crown Root Initiation (CRI) stage (20-25 days after sowing) is the most critical irrigation window. Subsidies up to 55% are available under the PMKSY Per Drop More Crop scheme."
                }
            }
            q.contains("pest") || q.contains("insect") || q.contains("कीट") || q.contains("सुंडी") || q.contains("रोग") || q.contains("rust") -> {
                if (lang == Language.HINDI) {
                    "कीट व रोग प्रबंधन: रासायनिक दवाओं के अंधाधुंध प्रयोग से बचें। शुरुआती रोकथाम के लिए 5% नीम तेल (Neem Oil 1500 ppm) का छिड़काव करें। पीले रतुए (Yellow Rust) के लक्षण दिखने पर तुरंत स्थानीय कृषि विज्ञान केंद्र (KVK) के विशेषज्ञ से संपर्क करें। किसी भी दवा के छिड़काव से पहले सुरक्षात्मक चश्मा व मास्क पहनें।"
                } else {
                    "Pest & Disease Care: Prefer Integrated Pest Management (IPM). For early stage sucking pests and caterpillars, apply 5% Neem Seed Kernel Extract (NSKE) or Neem Oil (1500 ppm). For specific chemical treatment or severe fungus like Yellow Rust or Blast, always seek diagnosis from your local Krishi Vigyan Kendra (KVK) and strictly follow label precautions."
                }
            }
            q.contains("soil") || q.contains("fertilizer") || q.contains("खाद") || q.contains("मिट्टी") || q.contains("urea") -> {
                if (lang == Language.HINDI) {
                    "मृदा स्वास्थ्य व खाद प्रबंधन: अंदाज से खाद डालने के बजाय 'मृदा स्वास्थ्य कार्ड' (Soil Health Card) के अनुसार ही उर्वरक डालें। यूरिया का अत्यधिक प्रयोग फसल में कीटों और बीमारियों को बुलावा देता है। मिट्टी में जैविक कार्बन (Organic Carbon) बढ़ाने के लिए गोबर की सड़ी खाद (FYM) या ढैंचा की हरी खाद का प्रयोग करें।"
                } else {
                    "Soil Fertility & Fertilizer Management: Avoid applying fertilizers blindly. Base your NPK and micro-nutrient dosages on your Soil Health Card report. Excessive Nitrogen/Urea leads to excessive vegetative growth, lodging, and pest susceptibility. Incorporate well-decomposed Farm Yard Manure (FYM) or green manuring to enrich organic carbon."
                }
            }
            q.contains("helpline") || q.contains("call") || q.contains("contact") || q.contains("नंबर") || q.contains("फोन") -> {
                if (lang == Language.HINDI) {
                    "ग्रुप 11 IDT से +91 8081392456 पर संपर्क करें या group11idt@outlook.com पर ईमेल भेजें। आप ऐप में परामर्श अनुरोध फ़ॉर्म भी भेज सकते हैं।"
                } else {
                    "Contact Group 11 IDT at +91 8081392456 or email group11idt@outlook.com. You can also send the consultation form from this app."
                }
            }
            else -> {
                if (lang == Language.HINDI) {
                    "धन्यवाद किसान भाई! आपके प्रश्न '$query' पर विचार किया गया है। बेहतर और सटीक मार्गदर्शन के लिए अपनी फसल का नाम, क्षेत्र/जिला और समस्या के लक्षण बताएं। आप ऐप के 'परामर्श अनुरोध' फॉर्म से सीधे हमारे प्रोजेक्ट समन्वयक डॉ. पुष्पेन्द्र त्यागी एवं ग्रुप 11 IDT टीम को अनुरोध भेज सकते हैं।"
                } else {
                    "Thank you, Farmer Brother! In response to your inquiry regarding '$query': To provide the most accurate advice, please specify your crop, sowing date, and local district conditions. You can also book a personalized consultation with Project Coordinator Dr. Pushpendra Tyagi and the Group 11 IDT team right from the app."
                }
            }
        }
    }
}

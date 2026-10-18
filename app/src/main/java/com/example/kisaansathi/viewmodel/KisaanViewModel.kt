package com.example.kisaansathi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kisaansathi.data.KisaanRepository
import com.example.kisaansathi.data.WeatherService
import com.example.kisaansathi.model.ChatMessage
import com.example.kisaansathi.model.ConsultationTicket
import com.example.kisaansathi.model.CropGuide
import com.example.kisaansathi.model.IrrigationAdvice
import com.example.kisaansathi.model.Language
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

data class UiState(
    val currentRoute: ScreenRoute = ScreenRoute.HOME,
    val selectedLanguage: Language = Language.ENGLISH,
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
    val submittedTickets: List<ConsultationTicket> = emptyList(),
    val weatherInfo: WeatherInfo? = null,
    val selectedWeatherLocation: String = "Karnal, Haryana",
    val isWeatherLoading: Boolean = false
)

class KisaanViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        // Initialize default welcome message in AI Agri Assistant
        initDefaultChat()
        // Calculate initial irrigation plan
        calculateIrrigation()
        // Fetch initial farm weather
        fetchWeather("Karnal, Haryana")
    }

    fun fetchWeather(location: String) {
        _uiState.value = _uiState.value.copy(
            selectedWeatherLocation = location,
            isWeatherLoading = true
        )
        viewModelScope.launch {
            val weather = WeatherService.fetchWeather(
                locationQuery = location,
                apiKey = com.example.BuildConfig.OPENWEATHER_API_KEY
            )
            _uiState.value = _uiState.value.copy(
                weatherInfo = weather,
                isWeatherLoading = false
            )
        }
    }

    fun refreshWeather() {
        fetchWeather(_uiState.value.selectedWeatherLocation)
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
        _uiState.value = _uiState.value.copy(selectedLanguage = language)
    }

    fun toggleDarkMode() {
        _uiState.value = _uiState.value.copy(isDarkMode = !_uiState.value.isDarkMode)
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
        phone: String,
        villageDistrict: String,
        crop: String,
        problemDescription: String
    ): Boolean {
        if (farmerName.isBlank() || phone.isBlank() || problemDescription.isBlank()) {
            return false
        }

        val dateStr = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date())
        val ticketId = "KS-${(100000..999999).random()}"

        val ticket = ConsultationTicket(
            ticketId = ticketId,
            farmerName = farmerName.trim(),
            phone = phone.trim(),
            villageDistrict = if (villageDistrict.isBlank()) "District Farm Zone" else villageDistrict.trim(),
            crop = if (crop.isBlank()) "General Crop Advisory" else crop.trim(),
            problemDescription = problemDescription.trim(),
            createdAt = dateStr,
            status = "Assigned to Agronomist"
        )

        val updatedList = listOf(ticket) + _uiState.value.submittedTickets
        _uiState.value = _uiState.value.copy(
            currentTicket = ticket,
            submittedTickets = updatedList,
            currentRoute = ScreenRoute.REQUEST_SENT
        )
        return true
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
            q.contains("helpline") || q.contains("call") || q.contains("नंबर") || q.contains("फोन") -> {
                if (lang == Language.HINDI) {
                    "किसान हेल्पलाइन: आप भारत सरकार के किसान कॉल सेंटर (Kisan Call Center) के निःशुल्क टोल-फ्री नंबर 1800-180-1551 पर प्रातः 6:00 बजे से रात 10:00 बजे तक कॉल करके अपनी क्षेत्रीय भाषा में कृषि विशेषज्ञों से सीधे सलाह ले सकते हैं।"
                } else {
                    "Agricultural Helplines: You can call the Government of India's Kisan Call Center Toll-Free number 1800-180-1551 between 6:00 AM and 10:00 PM (7 days a week) to speak directly with an agricultural officer in your native language."
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

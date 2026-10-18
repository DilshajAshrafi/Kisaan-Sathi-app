package com.example.kisaansathi.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.FilterChip
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.Grass
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import com.example.kisaansathi.ui.components.Text
import androidx.compose.material3.TextButton
import com.example.kisaansathi.data.AgriLocale
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kisaansathi.data.KisaanRepository
import com.example.kisaansathi.data.WeatherService
import com.example.kisaansathi.model.Language
import com.example.kisaansathi.model.ScreenRoute
import com.example.kisaansathi.model.SprayWindowStatus
import com.example.kisaansathi.model.WeatherInfo
import com.example.kisaansathi.ui.theme.Brown800
import com.example.kisaansathi.ui.theme.Gold100
import com.example.kisaansathi.ui.theme.Gold500
import com.example.kisaansathi.ui.theme.Green100
import com.example.kisaansathi.ui.theme.Green700
import com.example.kisaansathi.ui.theme.Green800
import com.example.kisaansathi.ui.theme.Green900

@Composable
fun HomeScreen(
    currentLanguage: Language,
    weatherInfo: WeatherInfo?,
    isWeatherLoading: Boolean = false,
    selectedLocation: String = "Karnal, Haryana",
    onLocationSelected: (String) -> Unit = {},
    onRefreshWeather: () -> Unit = {},
    onNavigate: (ScreenRoute) -> Unit,
    onEmergencyCall: () -> Unit,
    onContactPhone: () -> Unit,
    onContactEmail: () -> Unit,
    onOpenWebsite: () -> Unit = {},
    onWhatsApp: () -> Unit = {},
    fontScale: Float = 1f,
    onFontScaleChange: (Float) -> Unit = {}
) {
    val isHi = currentLanguage == Language.HINDI
    var showLocationDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("home_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Header Card
        item {
            HeroHeaderCard(
                currentLanguage = currentLanguage,
                onAskAiClick = { onNavigate(ScreenRoute.AGRI_ASSISTANT) }
            )
        }

        // Weather & Agricultural Advisory Alert (OpenWeather Live Service)
        item {
            WeatherAdvisoryCard(
                weather = weatherInfo,
                currentLanguage = currentLanguage,
                isLoading = isWeatherLoading,
                selectedLocation = selectedLocation,
                onLocationClick = { showLocationDialog = true },
                onRefresh = onRefreshWeather
            )
        }

        // Direct Group 11 IDT contact
        item {
            TeamPhoneCard(
                currentLanguage = currentLanguage,
                onCallClick = onEmergencyCall
            )
        }

        // Primary Services Section
        item {
            Text(
                text = when (currentLanguage) {
                    Language.PUNJABI -> "ਮੁੱਖ ਖੇਤੀਬਾੜੀ ਸੇਵਾਵਾਂ"
                    Language.GUJARATI -> "મુખ્ય ખેતી સેવાઓ"
                    Language.MARATHI -> "प्रमुख शेती सेवा व सल्ला"
                    Language.BENGALI -> "প্রধান কৃষি পরিষেবা"
                    Language.TAMIL -> "முக்கிய விவசாய சேவைகள்"
                    Language.TELUGU -> "ప్రధాన వ్యవసాయ సేవలు"
                    Language.HINDI -> "मुख्य सेवाएं व परामर्श"
                    Language.ENGLISH -> "Key Farming Services"
                },
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ServiceCard(
                    icon = Icons.Default.Eco,
                    iconBg = Green100,
                    iconTint = Green800,
                    title = when (currentLanguage) {
                        Language.PUNJABI -> "ਫ਼ਸਲ ਸਲਾਹ ਤੇ ਪ੍ਰਬੰਧਨ"
                        Language.GUJARATI -> "પાક સલાહ અને વ્યવસ્થાપન"
                        Language.MARATHI -> "पीक सल्ला व व्यवस्थापन"
                        Language.BENGALI -> "ফসল পরামর্শ ও পরিচর্যা"
                        Language.TAMIL -> "பயிர் ஆலோசனை"
                        Language.TELUGU -> "పంట సలహా & నిర్వహణ"
                        Language.HINDI -> "फसल परामर्श"
                        Language.ENGLISH -> "Crop Consultation"
                    },
                    subtitle = if (currentLanguage == Language.ENGLISH)
                        "Scientific advice on sowing dates, seed quality, spacing, and crop rotation."
                    else
                        "बुवाई, फसल चक्र, बीज उपचार और सही पोषण की वैज्ञानिक सलाह।",
                    tag = "service_card_crop",
                    onClick = { onNavigate(ScreenRoute.CROP_CONSULTATION) }
                )

                ServiceCard(
                    icon = Icons.Default.WaterDrop,
                    iconBg = Color(0xFFE5F3FB),
                    iconTint = Color(0xFF1976D2),
                    title = when (currentLanguage) {
                        Language.PUNJABI -> "ਸਿੰਚਾਈ ਪ੍ਰਬੰਧਨ"
                        Language.GUJARATI -> "સિંચાઈ વ્યવસ્થાપન"
                        Language.MARATHI -> "सिंचन व्यवस्थापन"
                        Language.BENGALI -> "সেচ ব্যবস্থাপনা"
                        Language.TAMIL -> "பாசன மேலாண்மை"
                        Language.TELUGU -> "నీటిపారుదల ప్రణాళిక"
                        Language.HINDI -> "सिंचाई प्रबंधन"
                        Language.ENGLISH -> "Irrigation Support"
                    },
                    subtitle = if (currentLanguage == Language.ENGLISH)
                        "Smart irrigation planner: Drip, sprinkler methods & water calculation."
                    else
                        "ड्रिप व स्प्रिंकलर प्रणाली, जल बजट कैलकुलेटर और जल बचत तकनीक।",
                    tag = "service_card_irrigation",
                    onClick = { onNavigate(ScreenRoute.IRRIGATION_SUPPORT) }
                )

                ServiceCard(
                    icon = Icons.Default.BugReport,
                    iconBg = Gold100,
                    iconTint = Brown800,
                    title = when (currentLanguage) {
                        Language.PUNJABI -> "ਮਿੱਟੀ ਤੇ ਕੀੜੇ ਸੁਰੱਖਿਆ"
                        Language.GUJARATI -> "માટી અને જીવાત સંભાળ"
                        Language.MARATHI -> "माती व कीड नियंत्रण"
                        Language.BENGALI -> "মাটি ও কীটপতঙ্গ যত্ন"
                        Language.TAMIL -> "மண் மற்றும் பூச்சி கட்டுப்பாடு"
                        Language.TELUGU -> "నేల మరియు పురుగుల నివారణ"
                        Language.HINDI -> "मृदा व कीट संरक्षण"
                        Language.ENGLISH -> "Soil & Pest Care"
                    },
                    subtitle = if (currentLanguage == Language.ENGLISH)
                        "Soil testing analysis, biological pest control, and safe dosage advice."
                    else
                        "मृदा स्वास्थ्य कार्ड परीक्षण, जैविक कीट निवारक और सुरक्षित कीटनाशक निर्देश।",
                    tag = "service_card_soil_pest",
                    onClick = { onNavigate(ScreenRoute.SOIL_PEST_CARE) }
                )

                ServiceCard(
                    icon = Icons.Default.AccountBalance,
                    iconBg = Color(0xFFEDE7F6),
                    iconTint = Color(0xFF512DA8),
                    title = when (currentLanguage) {
                        Language.PUNJABI -> "ਸਰਕਾਰੀ ਯੋਜਨਾਵਾਂ"
                        Language.GUJARATI -> "સરકારી યોજનાઓ"
                        Language.MARATHI -> "सरकारी योजना मार्गदर्शन"
                        Language.BENGALI -> "সরকারি প্রকল্প সহায়তা"
                        Language.TAMIL -> "அரசு திட்டங்கள்"
                        Language.TELUGU -> "ప్రభుత్వ పథకాలు"
                        Language.HINDI -> "सरकारी योजनाएं"
                        Language.ENGLISH -> "Government Schemes"
                    },
                    subtitle = if (currentLanguage == Language.ENGLISH)
                        "Complete guide to PM-KISAN, PMFBY insurance, KCC loan, and subsidies."
                    else
                        "पीएम-किसान, फसल बीमा, केसीसी ऋण और सब्सिडी की पूरी जानकारी।",
                    tag = "service_card_schemes",
                    onClick = { onNavigate(ScreenRoute.SCHEME_GUIDANCE) }
                )
            }
        }

        // Field-Ready Checklist Panel
        item {
            FieldReadyChecklistCard(currentLanguage = currentLanguage)
        }

        // Group 11 IDT Team Showcase
        item {
            Text(
                text = if (isHi) "ग्रुप 11 IDT" else "Group 11 IDT",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = if (isHi)
                    "प्रोजेक्ट समन्वयक, टीम लीड, एआई इंजीनियर एवं अनुसंधान टीम।"
                else
                    "Project Coordinator, Team Lead, AI Engineer & Research Team.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(KisaanRepository.experts) { expert ->
                    ExpertCard(expert = expert)
                }
            }
        }

        item {
            AppearAnimated(100) { MoreToolsCard(currentLanguage = currentLanguage, onNavigate = onNavigate) }
        }

        item {
            AppearAnimated(200) { TextSizeCard(currentLanguage, fontScale, onFontScaleChange) }
        }

        item {
            AppearAnimated(300) {
                TeamContactCard(
                    currentLanguage = currentLanguage,
                    onCall = onContactPhone,
                    onEmail = onContactEmail,
                    onWebsite = onOpenWebsite,
                    onWhatsApp = onWhatsApp
                )
            }
        }

        // Consultation Booking Call to Action
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Green800),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("request_consultation_cta_card")
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    val consultationTitle = when (currentLanguage) {
                        Language.HINDI -> "खेत की समस्या के लिए विशेषज्ञ से बात करें"
                        Language.PUNJABI -> "ਖੇਤ ਦੀ ਸਮੱਸਿਆ ਲਈ ਮਾਹਰ ਨਾਲ ਗੱਲ ਕਰੋ"
                        Language.GUJARATI -> "ખેતરની સમસ્યા માટે નિષ્ણાત સાથે વાત કરો"
                        Language.MARATHI -> "शेतीच्या समस्येसाठी तज्ज्ञांशी बोला"
                        Language.BENGALI -> "মাঠের সমস্যার জন্য বিশেষজ্ঞের সাথে কথা বলুন"
                        Language.TAMIL -> "வயல் பிரச்சனைக்கு நிபுணரிடம் ஆலோசனை பெறுக"
                        Language.TELUGU -> "పొలం సమస్య కోసం నిపుణులతో మాట్లాడండి"
                        Language.ENGLISH -> "Have a question about your field?"
                    }
                    val consultationDesc = when (currentLanguage) {
                        Language.HINDI -> "फसल की फोटो, लक्षण और खेत का विवरण भेजें। हमारे कृषि अधिकारी 24 घंटे में समाधान देंगे।"
                        Language.PUNJABI -> "ਫ਼ਸਲ ਦੀ ਫੋਟੋ, ਲੱਛਣ ਅਤੇ ਵੇਰਵੇ ਭੇਜੋ। ਸਾਡੇ ਖੇਤੀਬਾੜੀ ਅਧਿਕਾਰੀ 24 ਘੰਟਿਆਂ ਵਿੱਚ ਹੱਲ ਦੇਣਗੇ।"
                        Language.GUJARATI -> "પાકનો ફોટો, લક્ષણો અને વિગતો મોકલો. અમારા કૃષિ અધિકારીઓ 24 કલાકમાં ઉકેલ આપશે."
                        Language.MARATHI -> "पिकाचा फोटो, लक्षणे व तपशील पाठवा. आमचे कृषी अधिकारी २४ तासांत मार्गदर्शन करतील."
                        Language.BENGALI -> "ফসলের ছবি ও লক্ষণ পাঠান। আমাদের কৃষি বিশেষজ্ঞরা ২৪ ঘণ্টায় সমাধান দেবেন।"
                        Language.TAMIL -> "பயிர் புகைப்படம் மற்றும் விவரங்களை அனுப்பவும். எங்கள் விவசாய நிபுணர்கள் 24 மணி நேரத்தில் வழிகாட்டுவர்."
                        Language.TELUGU -> "పంట ఫోటో మరియు వివరాలు పంపండి. మా వ్యవసాయ నిపుణులు 24 గంటల్లో పరిష్కారం అందిస్తారు."
                        Language.ENGLISH -> "Share your location, crop, sowing date, and issue details. Our agricultural experts will respond with actionable recommendations."
                    }
                    Text(
                        text = consultationTitle,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = consultationDesc,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { onNavigate(ScreenRoute.CONSULTATION_REQUEST) },
                        colors = ButtonDefaults.buttonColors(containerColor = Gold500),
                        shape = RoundedCornerShape(999.dp),
                        modifier = Modifier.testTag("book_consultation_button")
                    ) {
                        Text(
                            text = AgriLocale.getConsultationButtonText(currentLanguage),
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    if (showLocationDialog) {
        LocationPickerDialog(
            currentLocation = selectedLocation,
            onLocationSelected = {
                onLocationSelected(it)
                showLocationDialog = false
            },
            onDismiss = { showLocationDialog = false }
        )
    }
}

@Composable
fun HeroHeaderCard(
    currentLanguage: Language,
    onAskAiClick: () -> Unit
) {
    val titleText = when (currentLanguage) {
        Language.HINDI, Language.MARATHI -> "किसान साथी"
        Language.PUNJABI -> "ਕਿਸਾਨ ਸਾਥੀ"
        Language.GUJARATI -> "કિસાન સાથી"
        Language.BENGALI -> "কিষাণ সাথী"
        Language.TAMIL -> "கிசான் சாதி"
        Language.TELUGU -> "కిసాన్ సాథి"
        Language.ENGLISH -> "Kisaan Sathi"
    }
    val subtitleText = AgriLocale.getTagline(currentLanguage)

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.verticalGradient(
                    listOf(Green900, Green700)
                )
            )
    ) {
        Column(
            modifier = Modifier.padding(22.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Gold500),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🌾", fontSize = 24.sp)
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text(
                        text = titleText,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = subtitleText,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Gold100
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = when (currentLanguage) {
                    Language.PUNJABI -> "ਫ਼ਸਲ ਸਿਹਤ, ਸਹੀ ਸਿੰਚਾਈ, ਮਿੱਟੀ ਜਾਂਚ ਅਤੇ ਸਰਕਾਰੀ ਸਕੀਮਾਂ ਦੀ ਭਰੋਸੇਯੋਗ ਜਾਣਕਾਰੀ ਸਿੱਧਾ ਤੁਹਾਡੇ ਹੱਥ ਵਿੱਚ।"
                    Language.GUJARATI -> "પાક સ્વાસ્થ્ય, સચોટ સિંચાઈ, માટી પરીક્ષણ અને સરકારી યોજનાઓની વિશ્વસનીય માહિતી સીધા તમારા હાથમાં."
                    Language.MARATHI -> "पीक आरोग्य, अचूक सिंचन, माती परीक्षण आणि सरकारी योजनांची खात्रीशीर माहिती थेट तुमच्या हातात."
                    Language.BENGALI -> "ফসল স্বাস্থ্য, সঠিক সেচ, মাটি পরীক্ষা এবং সরকারি প্রকল্পের নির্ভরযোগ্য তথ্য সরাসরি আপনার হাতে।"
                    Language.TAMIL -> "பயிர் நலம், துல்லியமான பாசனம், மண் பரிசோதனை மற்றும் அரசு திட்டங்களின் தகவல்கள் உங்கள் கைகளில்."
                    Language.TELUGU -> "పంట ఆరోగ్యం, ఖచ్చితమైన నీటిపారుదల, నేల పరీక్ష మరియు ప్రభుత్వ పథకాల నమ్మకమైన సమాచారం మీ చేతుల్లో."
                    Language.HINDI -> "फसल स्वास्थ्य, सटीक सिंचाई, मृदा जांच और सरकारी योजनाओं की विश्वसनीय जानकारी सीधे आपके हाथ में।"
                    Language.ENGLISH -> "Empowering farmers with smart crop advisory, precision irrigation, soil health diagnostics, and direct government scheme support."
                },
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.95f),
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onAskAiClick,
                colors = ButtonDefaults.buttonColors(containerColor = Gold500),
                shape = RoundedCornerShape(999.dp),
                modifier = Modifier.testTag("hero_ask_ai_button")
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = when (currentLanguage) {
                        Language.PUNJABI -> "ਏਆਈ ਖੇਤੀ ਸਹਾਇਕ ਨੂੰ ਪੁੱਛੋ"
                        Language.GUJARATI -> "AI કૃષિ સહાયકને પૂછો"
                        Language.MARATHI -> "एआय कृषी सहाय्यकाला विचारा"
                        Language.BENGALI -> "এআই কৃষি সহকারীকে জিজ্ঞাসা করুন"
                        Language.TAMIL -> "AI விவசாய உதவியாளரிடம் கேளுங்கள்"
                        Language.TELUGU -> "AI వ్యవసాయ సహాయకుడిని అడగండి"
                        Language.HINDI -> "एआई कृषि सहायक से पूछें"
                        Language.ENGLISH -> "Ask AI Agri Assistant"
                    },
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun WeatherAdvisoryCard(
    weather: WeatherInfo?,
    currentLanguage: Language,
    isLoading: Boolean,
    selectedLocation: String,
    onLocationClick: () -> Unit,
    onRefresh: () -> Unit
) {
    val isHi = currentLanguage == Language.HINDI
    val updatedLabel = when (currentLanguage) {
        Language.HINDI -> "अपडेट"
        Language.PUNJABI -> "ਅੱਪਡੇਟ"
        Language.BENGALI -> "আপডেট"
        Language.GUJARATI -> "અપડેટ"
        Language.MARATHI -> "अपडेट"
        Language.TAMIL -> "புதுப்பிப்பு"
        Language.TELUGU -> "నవీకరణ"
        Language.ENGLISH -> "Updated"
    }
    val feelsLabel = when (currentLanguage) {
        Language.HINDI -> "अनुभव"
        Language.PUNJABI -> "ਮਹਿਸੂਸ"
        Language.BENGALI -> "অনুভূত"
        Language.GUJARATI -> "અનુભવ"
        Language.MARATHI -> "जाणवते"
        Language.TAMIL -> "உணரப்படுகிறது"
        Language.TELUGU -> "అనిపిస్తుంది"
        Language.ENGLISH -> "Feels"
    }
    val humidityLabel = AgriLocale.getHumidityLabel(currentLanguage)
    val windLabel = AgriLocale.getWindLabel(currentLanguage)
    val pressureLabel = AgriLocale.getPressureLabel(currentLanguage)
    val sprayHeader = when (currentLanguage) {
        Language.HINDI -> "दवा छिड़काव स्थिति"
        Language.PUNJABI -> "ਸਪਰੇਅ ਸਥਿਤੀ"
        Language.BENGALI -> "স্প্রে অবস্থা"
        Language.GUJARATI -> "છંટકાવ સ્થિતિ"
        Language.MARATHI -> "फवारणी स्थिती"
        Language.TAMIL -> "தெளிப்பு நிலை"
        Language.TELUGU -> "పిచికారీ స్థితి"
        Language.ENGLISH -> "Field Spray Window"
    }
    val irrigationHeader = when (currentLanguage) {
        Language.HINDI -> "सिंचाई योजना"
        Language.PUNJABI -> "ਸਿੰਚਾਈ ਸਲਾਹ"
        Language.BENGALI -> "সেচ পরামর্শ"
        Language.GUJARATI -> "સિંચાઈ સલાહ"
        Language.MARATHI -> "सिंचन सल्ला"
        Language.TAMIL -> "பாசன ஆலோசனை"
        Language.TELUGU -> "నీటిపారుదల సలహా"
        Language.ENGLISH -> "Irrigation Advisory"
    }
    val fieldWorkHeader = when (currentLanguage) {
        Language.HINDI -> "खेत कार्य सलाह"
        Language.PUNJABI -> "ਖੇਤ ਕੰਮ ਸਲਾਹ"
        Language.BENGALI -> "মাঠের কাজের পরামর্শ"
        Language.GUJARATI -> "ખેતર કાર્ય સલાહ"
        Language.MARATHI -> "शेती काम सल्ला"
        Language.TAMIL -> "வயல் வேலை ஆலோசனை"
        Language.TELUGU -> "పొలం పని సలహా"
        Language.ENGLISH -> "Field Work Advisory"
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("weather_advisory_card")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // OpenWeather Service Branding & Last Updated
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(if (weather?.isLiveOpenWeather == true) Color(0xFF2E7D32) else Color(0xFF1976D2))
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = weather?.sourceLabel ?: if (isLoading) {
                            AgriLocale.getWeatherLoadingLabel(currentLanguage)
                        } else {
                            AgriLocale.getWeatherUnavailableLabel(currentLanguage)
                        },
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 11.sp
                    )
                }

                if (weather != null) {
                    Text(
                        text = "$updatedLabel: ${weather.lastUpdated}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.sp
                    )
                }
            }

            // Header: Location & Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
                        .clickable { onLocationClick() }
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Farm Location",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = weather?.locationName ?: selectedLocation,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        IconButton(
                            onClick = onRefresh,
                            modifier = Modifier.size(28.dp).testTag("refresh_weather_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Refresh Weather",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            if (weather == null) {
                if (!isLoading) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = AgriLocale.getWeatherUnavailableHint(currentLanguage),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        TextButton(onClick = onRefresh) {
                            Text(AgriLocale.getWeatherRetryLabel(currentLanguage))
                        }
                    }
                }
            } else {
            Spacer(modifier = Modifier.height(12.dp))

            // Main Weather Reading (Temp + Condition)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "${weather.temperatureC}°C",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 36.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "$feelsLabel: ${weather.feelsLikeC}°C",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }
                    Text(
                        text = weather.conditionDescription,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (weather.isRainExpected) Icons.Default.CloudQueue else Icons.Default.WbSunny,
                        contentDescription = "Weather Icon",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 3-Stat Metrics Row: Humidity, Wind, Pressure
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatPill(
                    icon = Icons.Default.WaterDrop,
                    label = humidityLabel,
                    value = "${weather.humidityPercent}%",
                    modifier = Modifier.weight(1f)
                )
                StatPill(
                    icon = Icons.Default.Air,
                    label = windLabel,
                    value = "${weather.windSpeedKmh} km/h",
                    modifier = Modifier.weight(1f)
                )
                StatPill(
                    icon = Icons.Default.Speed,
                    label = pressureLabel,
                    value = "${weather.pressureHpa} hPa",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Spray Window Status Banner
            val spray = weather.sprayStatus
            val sprayBg = when (spray) {
                SprayWindowStatus.SAFE -> Color(0xFFE8F5E9)
                SprayWindowStatus.CAUTION -> Color(0xFFFFF8E1)
                SprayWindowStatus.UNSAFE -> Color(0xFFFFEBEE)
            }
            val sprayTextColor = when (spray) {
                SprayWindowStatus.SAFE -> Color(0xFF2E7D32)
                SprayWindowStatus.CAUTION -> Color(0xFFF57F17)
                SprayWindowStatus.UNSAFE -> Color(0xFFC62828)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(sprayBg)
                    .padding(10.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(sprayTextColor)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "$sprayHeader: ${AgriLocale.getSprayWindowLabel(spray, currentLanguage)}",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = sprayTextColor
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = if (isHi) spray.advisoryHi else spray.advisoryEn,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Irrigation Scheduling Impact
            Row(verticalAlignment = Alignment.Top) {
                Text(
                    text = "💧 ",
                    fontSize = 14.sp
                )
                Text(
                    text = "$irrigationHeader: ${if (isHi) weather.irrigationAdvisoryHi else weather.irrigationAdvisoryEn}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Field Work & Tillage Advisory
            Row(verticalAlignment = Alignment.Top) {
                Text(
                    text = "🚜 ",
                    fontSize = 14.sp
                )
                Text(
                    text = "$fieldWorkHeader: ${if (isHi) weather.fieldWorkAdvisoryHi else weather.fieldWorkAdvisoryEn}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 16.sp
                )
            }
            }
        }
    }
}

@Composable
fun StatPill(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(vertical = 8.dp, horizontal = 6.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 10.sp
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun LocationPickerDialog(
    currentLocation: String,
    onLocationSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredDistricts = remember(searchQuery) {
        if (searchQuery.isBlank()) WeatherService.agriculturalDistricts
        else WeatherService.agriculturalDistricts.filter { it.contains(searchQuery, ignoreCase = true) }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Select Farm District / जिला चुनें",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search or type any district / village...") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                )

                if (searchQuery.isNotBlank() && filteredDistricts.none { it.equals(searchQuery.trim(), ignoreCase = true) }) {
                    Button(
                        onClick = {
                            onLocationSelected(searchQuery.trim())
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Green800)
                    ) {
                        Text("Use \"${searchQuery.trim()}\" for Weather")
                    }
                }

                Text(
                    text = "Agricultural District Hubs:",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 6.dp)
                )

                LazyColumn(modifier = Modifier.height(240.dp)) {
                    items(filteredDistricts) { district ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onLocationSelected(district) }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = currentLocation == district,
                                onClick = { onLocationSelected(district) }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = district,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = if (currentLocation == district) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close / बंद करें")
            }
        },
        shape = RoundedCornerShape(18.dp)
    )
}


@Composable
fun TeamPhoneCard(
    currentLanguage: Language,
    onCallClick: () -> Unit
) {
    val helplineTitle = AgriLocale.getTeamContactTitle(currentLanguage)
    val callButtonText = when (currentLanguage) {
        Language.HINDI -> "कॉल करें"
        Language.PUNJABI -> "ਕਾਲ ਕਰੋ"
        Language.GUJARATI -> "કૉલ કરો"
        Language.MARATHI -> "कॉल करा"
        Language.BENGALI -> "কল করুন"
        Language.TAMIL -> "அழைக்க"
        Language.TELUGU -> "కాల్ చేయండి"
        Language.ENGLISH -> "Call"
    }

    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("team_phone_card")
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Green800.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Call Group 11 IDT",
                    tint = Green800,
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = helplineTitle,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "+91 8081392456",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Button(
                onClick = onCallClick,
                colors = ButtonDefaults.buttonColors(containerColor = Green800),
                shape = RoundedCornerShape(999.dp),
                modifier = Modifier.testTag("call_team_button")
            ) {
                Text(
                    text = callButtonText,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ServiceCard(
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    title: String,
    subtitle: String,
    tag: String,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag(tag)
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconTint,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
fun FieldReadyChecklistCard(currentLanguage: Language) {
    val title = when (currentLanguage) {
        Language.HINDI -> "खेत के लिए सरल चेकलिस्ट"
        Language.PUNJABI -> "ਖੇਤ ਲਈ ਸੌਖੀ ਚੈੱਕਲਿਸਟ"
        Language.GUJARATI -> "ખેતર માટે સરળ ચેકલિસ્ટ"
        Language.MARATHI -> "शेतासाठी सुलभ चेकलिस्ट"
        Language.BENGALI -> "মাঠের জন্য সহজ চেকলিস্ট"
        Language.TAMIL -> "விவசாய பண்ணை சரிபார்ப்பு பட்டியல்"
        Language.TELUGU -> "పొలం కోసం సులభమైన చెక్‌లిస్ట్"
        Language.ENGLISH -> "Field-Ready Checklist"
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(10.dp))

            val items = when (currentLanguage) {
                Language.HINDI -> listOf(
                    "मिट्टी से शुरुआत करें: अंदाज के बजाय मिट्टी जांच (Soil Test) के आधार पर खाद चुनें।",
                    "समझदारी से पानी दें: फसल की अवस्था अनुसार सिंचाई करें, जलभराव से बचें।",
                    "नियमित निरीक्षण करें: पत्तियों पर धब्बे, कीट या पीलापन दिखने पर तुरंत जांच करें।"
                )
                Language.PUNJABI -> listOf(
                    "ਮਿੱਟੀ ਦੀ ਜਾਂਚ ਨਾਲ ਸ਼ੁਰੂ ਕਰੋ: ਅੰਦਾਜ਼ੇ ਦੀ ਬਜਾਏ ਮਿੱਟੀ ਟੈਸਟ ਅਨੁਸਾਰ ਖਾਦ ਪਾਓ।",
                    "ਸਮਝਦਾਰੀ ਨਾਲ ਪਾਣੀ ਦਿਓ: ਫ਼ਸਲ ਦੀ ਲੋੜ ਮੁਤਾਬਕ ਸਿੰਚਾਈ ਕਰੋ, ਪਾਣੀ ਖੜ੍ਹਾ ਨਾ ਹੋਣ ਦਿਓ।",
                    "ਨਿਯਮਿਤ ਨਿਗਰਾਨੀ ਕਰੋ: ਪੱਤਿਆਂ 'ਤੇ ਧੱਬੇ ਜਾਂ ਕੀੜੇ ਦਿਖਣ 'ਤੇ ਤੁਰੰਤ ਜਾਂਚ ਕਰੋ।"
                )
                Language.GUJARATI -> listOf(
                    "માટી પરીક્ષણથી શરૂઆત કરો: અંદાજ લગાવવાને બદલે જમીન ચકાસણી મુજબ ખાતર આપો.",
                    "સમજદારીપૂર્વક પિયત આપો: પાકની જરૂરિયાત મુજબ સિંચાઈ કરો, પાણીનો ભરાવો અટકાવો.",
                    "નિયમિત નિરીક્ષણ કરો: પાંદડા પર ડાઘ, જીવાત કે પીળાશ દેખાય તો તરત તપાસ કરો."
                )
                Language.MARATHI -> listOf(
                    "माती परीक्षणाने सुरुवात करा: अंदाजाने नव्हे, तर सॉईल टेस्टनुसार खतांचा वापर करा.",
                    "योग्य पाणी व्यवस्थापन: पिकाच्या वाढीच्या टप्प्यानुसार पाणी द्या, पाणी साचू देऊ नका.",
                    "नियमित शेती पाहणी: पानांवर ठिपके, कीड किंवा पिवळेपणा दिसल्यास लगेच उपाय करा."
                )
                Language.BENGALI -> listOf(
                    "মাটি পরীক্ষা দিয়ে শুরু করুন: অনুমানের পরিবর্তে মৃত্তিকা পরীক্ষার ভিত্তিতে সার দিন।",
                    "বুঝে-শুনে জল দিন: ফসলের বৃদ্ধির পর্যায় অনুযায়ী সেচ দিন, জল জমতে দেবেন না।",
                    "নিয়মিত পর্যবেক্ষণ করুন: পাতায় দাগ, পোকা বা হলুদ ভাব দেখা দিলে দ্রুত ব্যবস্থা নিন।"
                )
                Language.TAMIL -> listOf(
                    "மண் பரிசோதனையுடன் தொடங்குங்கள்: உரங்களை மண் பரிசோதனை பரிந்துரைப்படி இடுங்கள்.",
                    "சரியான பாசனம்: பயிர் தேவைக்கு ஏற்ப தண்ணீர் பாய்ச்சுங்கள், தேங்க விடாதீர்கள்.",
                    "தொடர் கண்காணிப்பு: இலைகளில் புள்ளிகள், பூச்சிகள் தென்பட்டால் உடனே கவனியுங்கள்."
                )
                Language.TELUGU -> listOf(
                    "నేల పరీక్షతో ప్రారంభించండి: అంచనాలతో కాకుండా నేల పరీక్ష ఆధారంగా ఎరువులు వాడండి.",
                    "సమర్థవంతమైన నీటిపారుదల: పంట అవసరానికి తగినట్లుగా నీరు అందించండి.",
                    "నిరంతర పరిశీలన: ఆకులపై మచ్చలు లేదా పురుగులు కనిపిస్తే వెంటనే చర్యలు తీసుకోండి."
                )
                Language.ENGLISH -> listOf(
                    "Start with the soil: Choose nutrients based on a soil test, not guesswork.",
                    "Plan water wisely: Match watering to crop needs to avoid water wastage or stress.",
                    "Check crops regularly: Spot unusual wilting, spots, or insects early before disease spreads."
                )
            }

            items.forEach { tip ->
                Row(
                    modifier = Modifier.padding(vertical = 4.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Green700,
                        modifier = Modifier
                            .size(18.dp)
                            .padding(top = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = tip,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
fun AppearAnimated(delayMillis: Int, content: @Composable () -> Unit) {
    var visible by remember { mutableStateOf(false) }
    androidx.compose.runtime.LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(delayMillis.toLong())
        visible = true
    }
    androidx.compose.animation.AnimatedVisibility(
        visible = visible,
        enter = androidx.compose.animation.fadeIn(androidx.compose.animation.core.tween(400)) +
            androidx.compose.animation.slideInVertically(androidx.compose.animation.core.tween(400)) { it / 6 }
    ) { content() }
}

@Composable
fun TextSizeCard(currentLanguage: Language, fontScale: Float, onChange: (Float) -> Unit) {
    val isHi = currentLanguage == Language.HINDI
    val options = listOf(
        1f to (if (isHi) "सामान्य" else "Normal"),
        1.15f to (if (isHi) "बड़ा" else "Large"),
        1.3f to (if (isHi) "बहुत बड़ा" else "Extra large")
    )
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = if (isHi) "अक्षर का आकार" else "Text size",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            options.forEach { (scale, label) ->
                FilterChip(
                    selected = kotlin.math.abs(fontScale - scale) < 0.01f,
                    onClick = { onChange(scale) },
                    label = { Text(label) }
                )
            }
        }
    }
}

@Composable
fun MoreToolsCard(currentLanguage: Language, onNavigate: (ScreenRoute) -> Unit) {
    val isHi = currentLanguage == Language.HINDI
    val tools = listOf(
        Triple(ScreenRoute.MSP_PRICES, if (isHi) "MSP भाव" else "MSP Prices", Icons.Default.CurrencyRupee),
        Triple(ScreenRoute.CROP_CALENDAR, if (isHi) "फसल कैलेंडर" else "Crop Calendar", Icons.Default.CalendarMonth),
        Triple(ScreenRoute.SOIL_HEALTH, if (isHi) "मृदा सहायक" else "Soil Helper", Icons.Default.Grass),
        Triple(ScreenRoute.MY_REQUESTS, if (isHi) "मेरे अनुरोध" else "My Requests", Icons.Default.History)
    )
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = if (isHi) "और उपयोगी टूल" else "More tools",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        tools.chunked(2).forEach { rowTools ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                rowTools.forEach { (route, label, icon) ->
                    Card(
                        onClick = { onNavigate(route) },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(2.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(icon, contentDescription = null, tint = Green800)
                            Spacer(Modifier.width(10.dp))
                            Text(label, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TeamContactCard(
    currentLanguage: Language,
    onCall: () -> Unit,
    onEmail: () -> Unit,
    onWebsite: () -> Unit = {},
    onWhatsApp: () -> Unit = {}
) {
    val isHi = currentLanguage == Language.HINDI

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = if (isHi) "Group 11 IDT से संपर्क करें" else "Contact Group 11 IDT",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            OutlinedButton(
                onClick = onCall,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Phone, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("+91 8081392456")
            }
            OutlinedButton(
                onClick = onEmail,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Email, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("group11idt@outlook.com")
            }
            OutlinedButton(
                onClick = onWhatsApp,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Chat, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(if (isHi) "WhatsApp पर चैट करें" else "Chat on WhatsApp")
            }
            OutlinedButton(
                onClick = onWebsite,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Public, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(if (isHi) "हमारी वेबसाइट खोलें" else "Open our website")
            }
        }
    }
}

@Composable
fun ExpertCard(expert: com.example.kisaansathi.model.AgriExpert) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .width(220.dp)
            .testTag("expert_card_${expert.id}")
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (expert.imageRes != null) {
                Image(
                    painter = painterResource(expert.imageRes),
                    contentDescription = expert.name,
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = expert.name,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(34.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = expert.name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = expert.designation,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = expert.specialization,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                lineHeight = 14.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = expert.experience,
                style = MaterialTheme.typography.labelSmall,
                color = Gold500,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

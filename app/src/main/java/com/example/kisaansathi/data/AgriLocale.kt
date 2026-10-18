package com.example.kisaansathi.data

import com.example.kisaansathi.model.Language
import com.example.kisaansathi.model.SprayWindowStatus

object AgriLocale {

    fun getGreeting(lang: Language): String = when (lang) {
        Language.HINDI -> "नमस्ते किसान भाई! 🙏"
        Language.PUNJABI -> "ਸਤਿ ਸ਼੍ਰੀ ਅਕਾਲ ਕਿਸਾਨ ਵੀਰੋ! 🙏"
        Language.BENGALI -> "নমস্কার কৃষক ভাই! 🙏"
        Language.GUJARATI -> "નમસ્તે ખેડૂત મિત્ર! 🙏"
        Language.MARATHI -> "नमस्कार शेतकरी बंधूंनो! 🙏"
        Language.TAMIL -> "வணக்கம் விவசாய தோழரே! 🙏"
        Language.TELUGU -> "నమస్కారం రైతు సోదరా! 🙏"
        Language.ENGLISH -> "Namaste, Farmer Friend! 🙏"
    }

    fun getTagline(lang: Language): String = when (lang) {
        Language.HINDI -> "स्मार्ट कृषि सहायता एवं वैज्ञानिक मार्गदर्शन"
        Language.PUNJABI -> "ਸਮਾਰਟ ਖੇਤੀਬਾੜੀ ਸਹਾਇਤਾ ਅਤੇ ਵਿਗਿਆਨਕ ਸੇਧ"
        Language.BENGALI -> "স্মার্ট কৃষি সহায়তা এবং বৈজ্ঞানিক নির্দেশনা"
        Language.GUJARATI -> "સ્માર્ટ કૃષિ સહાય અને વૈજ્ઞાનિક માર્ગદર્શન"
        Language.MARATHI -> "स्मार्ट कृषी सहाय्य व वैज्ञानिक मार्गदर्शन"
        Language.TAMIL -> "ஸ்மார்ட் விவசாய உதவி மற்றும் அறிவியல் வழிகாட்டுதல்"
        Language.TELUGU -> "స్మార్ట్ వ్యవసాయ మద్దతు మరియు శాస్త్రీయ మార్గదర్శకత్వం"
        Language.ENGLISH -> "Smart Farming Support & Scientific Guidance"
    }

    // Navigation Labels
    fun getNavHome(lang: Language): String = when (lang) {
        Language.HINDI, Language.MARATHI -> "होम"
        Language.PUNJABI -> "ਮੁੱਖ ਪੰਨਾ"
        Language.BENGALI -> "হোম"
        Language.GUJARATI -> "મુખ્ય"
        Language.TAMIL -> "முகப்பு"
        Language.TELUGU -> "హోమ్"
        Language.ENGLISH -> "Home"
    }

    fun getNavCrops(lang: Language): String = when (lang) {
        Language.HINDI -> "फसल"
        Language.PUNJABI -> "ਫ਼ਸਲ"
        Language.BENGALI -> "ফসল"
        Language.GUJARATI -> "પાક"
        Language.MARATHI -> "पिके"
        Language.TAMIL -> "பயிர்கள்"
        Language.TELUGU -> "పంటలు"
        Language.ENGLISH -> "Crops"
    }

    fun getNavIrrigation(lang: Language): String = when (lang) {
        Language.HINDI, Language.PUNJABI, Language.MARATHI, Language.GUJARATI -> "सिंचाई"
        Language.BENGALI -> "সেচ"
        Language.TAMIL -> "பாசனம்"
        Language.TELUGU -> "నీటిపారుదల"
        Language.ENGLISH -> "Irrigation"
    }

    fun getNavSoilPest(lang: Language): String = when (lang) {
        Language.HINDI -> "कीट/मिट्टी"
        Language.PUNJABI -> "ਕੀੜੇ/ਮਿੱਟੀ"
        Language.BENGALI -> "মাটি/কীট"
        Language.GUJARATI -> "માટી/જીવાત"
        Language.MARATHI -> "माती/कीड"
        Language.TAMIL -> "மண்/பூச்சி"
        Language.TELUGU -> "నేల/పురుగులు"
        Language.ENGLISH -> "Soil/Pest"
    }

    fun getNavSchemes(lang: Language): String = when (lang) {
        Language.HINDI, Language.MARATHI -> "योजनाएं"
        Language.PUNJABI -> "ਯੋਜਨਾਵਾਂ"
        Language.BENGALI -> "পরিকল্পনা"
        Language.GUJARATI -> "યોજનાઓ"
        Language.TAMIL -> "திட்டங்கள்"
        Language.TELUGU -> "పథకాలు"
        Language.ENGLISH -> "Schemes"
    }

    fun getNavAssistant(lang: Language): String = when (lang) {
        Language.HINDI -> "एआई सहायक"
        Language.PUNJABI -> "ਏਆਈ ਸਹਾਇਕ"
        Language.BENGALI -> "এআই সহকারী"
        Language.GUJARATI -> "AI સહાયક"
        Language.MARATHI -> "एआय सहाय्यक"
        Language.TAMIL -> "AI உதவியாளர்"
        Language.TELUGU -> "AI సహాయకుడు"
        Language.ENGLISH -> "Agri AI"
    }

    // Weather Metrics
    fun getHumidityLabel(lang: Language): String = when (lang) {
        Language.HINDI, Language.MARATHI -> "नमी"
        Language.PUNJABI -> "ਨਮੀ"
        Language.BENGALI -> "আর্দ্রতা"
        Language.GUJARATI -> "ભેજ"
        Language.TAMIL -> "ஈரப்பதம்"
        Language.TELUGU -> "తేమ"
        Language.ENGLISH -> "Humidity"
    }

    fun getWindLabel(lang: Language): String = when (lang) {
        Language.HINDI, Language.PUNJABI, Language.MARATHI, Language.GUJARATI -> "हवा गति"
        Language.BENGALI -> "বাতাস"
        Language.TAMIL -> "காற்று வேகம்"
        Language.TELUGU -> "గాలి వేగం"
        Language.ENGLISH -> "Wind Speed"
    }

    fun getPressureLabel(lang: Language): String = when (lang) {
        Language.HINDI, Language.MARATHI -> "दबाव"
        Language.PUNJABI -> "ਦਬਾਅ"
        Language.BENGALI -> "বায়ুচাপ"
        Language.GUJARATI -> "દબાણ"
        Language.TAMIL -> "அழுத்தம்"
        Language.TELUGU -> "పీడనం"
        Language.ENGLISH -> "Pressure"
    }

    fun getWeatherLoadingLabel(lang: Language): String = when (lang) {
        Language.HINDI -> "मौसम जानकारी लोड हो रही है"
        Language.PUNJABI -> "ਮੌਸਮ ਦੀ ਜਾਣਕਾਰੀ ਲੋਡ ਹੋ ਰਹੀ ਹੈ"
        Language.BENGALI -> "আবহাওয়ার তথ্য লোড হচ্ছে"
        Language.GUJARATI -> "હવામાન માહિતી લોડ થઈ રહી છે"
        Language.MARATHI -> "हवामान माहिती लोड होत आहे"
        Language.TAMIL -> "வானிலை தகவல் ஏற்றப்படுகிறது"
        Language.TELUGU -> "వాతావరణ సమాచారం లోడ్ అవుతోంది"
        Language.ENGLISH -> "Loading live weather"
    }

    fun getWeatherUnavailableLabel(lang: Language): String = when (lang) {
        Language.HINDI -> "मौसम जानकारी उपलब्ध नहीं है"
        Language.PUNJABI -> "ਮੌਸਮ ਦੀ ਜਾਣਕਾਰੀ ਉਪਲਬਧ ਨਹੀਂ ਹੈ"
        Language.BENGALI -> "আবহাওয়ার তথ্য পাওয়া যাচ্ছে না"
        Language.GUJARATI -> "હવામાન માહિતી ઉપલબ્ધ નથી"
        Language.MARATHI -> "हवामान माहिती उपलब्ध नाही"
        Language.TAMIL -> "வானிலை தகவல் கிடைக்கவில்லை"
        Language.TELUGU -> "వాతావరణ సమాచారం అందుబాటులో లేదు"
        Language.ENGLISH -> "Weather data unavailable"
    }

    fun getWeatherUnavailableHint(lang: Language): String = when (lang) {
        Language.HINDI -> "इंटरनेट कनेक्शन जांचें और फिर से कोशिश करें।"
        Language.PUNJABI -> "ਇੰਟਰਨੈੱਟ ਕਨੈਕਸ਼ਨ ਦੀ ਜਾਂਚ ਕਰੋ ਅਤੇ ਦੁਬਾਰਾ ਕੋਸ਼ਿਸ਼ ਕਰੋ।"
        Language.BENGALI -> "ইন্টারনেট সংযোগ পরীক্ষা করে আবার চেষ্টা করুন।"
        Language.GUJARATI -> "ઇન્ટરનેટ કનેક્શન તપાસો અને ફરી પ્રયાસ કરો."
        Language.MARATHI -> "इंटरनेट कनेक्शन तपासा आणि पुन्हा प्रयत्न करा."
        Language.TAMIL -> "இணைய இணைப்பை சரிபார்த்து மீண்டும் முயற்சிக்கவும்."
        Language.TELUGU -> "ఇంటర్నెట్ కనెక్షన్ తనిఖీ చేసి మళ్లీ ప్రయత్నించండి."
        Language.ENGLISH -> "Check your internet connection and try again."
    }

    fun getWeatherRetryLabel(lang: Language): String = when (lang) {
        Language.HINDI -> "फिर से कोशिश करें"
        Language.PUNJABI -> "ਦੁਬਾਰਾ ਕੋਸ਼ਿਸ਼ ਕਰੋ"
        Language.BENGALI -> "আবার চেষ্টা করুন"
        Language.GUJARATI -> "ફરી પ્રયાસ કરો"
        Language.MARATHI -> "पुन्हा प्रयत्न करा"
        Language.TAMIL -> "மீண்டும் முயற்சிக்கவும்"
        Language.TELUGU -> "మళ్లీ ప్రయత్నించండి"
        Language.ENGLISH -> "Try again"
    }

    fun getSprayWindowLabel(status: SprayWindowStatus, lang: Language): String = when (lang) {
        Language.HINDI -> when (status) {
            SprayWindowStatus.SAFE -> "🟢 छिड़काव के लिए उपयुक्त"
            SprayWindowStatus.CAUTION -> "🟡 सावधानीपूर्वक छिड़काव करें"
            SprayWindowStatus.UNSAFE -> "🔴 आज छिड़काव न करें"
        }
        Language.PUNJABI -> when (status) {
            SprayWindowStatus.SAFE -> "🟢 ਸਪਰੇਅ ਲਈ ਢੁਕਵਾਂ ਸਮਾਂ"
            SprayWindowStatus.CAUTION -> "🟡 ਸਾਵਧਾਨੀ ਨਾਲ ਸਪਰੇਅ ਕਰੋ"
            SprayWindowStatus.UNSAFE -> "🔴 ਅੱਜ ਸਪਰੇਅ ਨਾ ਕਰੋ"
        }
        Language.GUJARATI -> when (status) {
            SprayWindowStatus.SAFE -> "🟢 છંટકાવ માટે યોગ્ય સમય"
            SprayWindowStatus.CAUTION -> "🟡 સાવધાનીપૂર્વક છંટકાવ કરો"
            SprayWindowStatus.UNSAFE -> "🔴 આજે છંટકાવ ન કરો"
        }
        Language.MARATHI -> when (status) {
            SprayWindowStatus.SAFE -> "🟢 फवारणीसाठी अनुकूल वेळ"
            SprayWindowStatus.CAUTION -> "🟡 काळजीपूर्वक फवारणी करा"
            SprayWindowStatus.UNSAFE -> "🔴 आज फवारणी करू नका"
        }
        Language.BENGALI -> when (status) {
            SprayWindowStatus.SAFE -> "🟢 স্প্রে করার জন্য উপযুক্ত"
            SprayWindowStatus.CAUTION -> "🟡 সতর্কতার সাথে স্প্রে করুন"
            SprayWindowStatus.UNSAFE -> "🔴 আজ স্প্রে করবেন না"
        }
        Language.TAMIL -> when (status) {
            SprayWindowStatus.SAFE -> "🟢 தெளிக்க ஏற்ற நேரம்"
            SprayWindowStatus.CAUTION -> "🟡 எச்சரிக்கையுடன் தெளிக்கவும்"
            SprayWindowStatus.UNSAFE -> "🔴 இன்று தெளிக்க வேண்டாம்"
        }
        Language.TELUGU -> when (status) {
            SprayWindowStatus.SAFE -> "🟢 పిచికారీకి అనుకూలం"
            SprayWindowStatus.CAUTION -> "🟡 జాగ్రత్తగా పిచికారీ చేయండి"
            SprayWindowStatus.UNSAFE -> "🔴 ఈరోజు పిచికారీ చేయవద్దు"
        }
        Language.ENGLISH -> when (status) {
            SprayWindowStatus.SAFE -> "🟢 Optimal for Spraying"
            SprayWindowStatus.CAUTION -> "🟡 Spray with Caution"
            SprayWindowStatus.UNSAFE -> "🔴 Do Not Spray Today"
        }
    }

    fun getRegionDescription(lang: Language): String = when (lang) {
        Language.HINDI -> "उत्तर व मध्य भारत (UP, MP, बिहार, राजस्थान, हरियाणा)"
        Language.PUNJABI -> "ਪੰਜਾਬ ਅਤੇ ਹਰਿਆਣਾ (ਕਣਕ ਤੇ ਝੋਨਾ ਖੇਤਰ)"
        Language.GUJARATI -> "ગુજરાત (કપાસ, મગફળી, જીરું)"
        Language.MARATHI -> "महाराष्ट्र (कापूस, ऊस, सोयाबीन, कांदा)"
        Language.BENGALI -> "পশ্চিমবঙ্গ ও ত্রিপুরা (ধান, পাট, আলু)"
        Language.TAMIL -> "தமிழ்நாடு (நெல், கரும்பு, வாழை)"
        Language.TELUGU -> "ఆంధ్రప్రదేశ్ & తెలంగాణ (వరి, పత్తి, మిరప)"
        Language.ENGLISH -> "Pan-India & Universal Agricultural Standard"
    }

    fun getTeamContactTitle(lang: Language): String = when (lang) {
        Language.HINDI -> "ग्रुप 11 IDT सहायता"
        Language.PUNJABI -> "ਗਰੁੱਪ 11 IDT ਸਹਾਇਤਾ"
        Language.GUJARATI -> "ગ્રુપ 11 IDT સહાય"
        Language.MARATHI -> "ग्रुप 11 IDT मदत"
        Language.BENGALI -> "গ্রুপ 11 IDT সহায়তা"
        Language.TAMIL -> "குரூப் 11 IDT உதவி"
        Language.TELUGU -> "గ్రూప్ 11 IDT సహాయం"
        Language.ENGLISH -> "Group 11 IDT Support"
    }

    fun getConsultationButtonText(lang: Language): String = when (lang) {
        Language.HINDI -> "विशेषज्ञ परामर्श बुक करें"
        Language.PUNJABI -> "ਮਾਹਰ ਸਲਾਹ ਬੁੱਕ ਕਰੋ"
        Language.GUJARATI -> "નિષ્ણાત સલાહ મેળવો"
        Language.MARATHI -> "तज्ज्ञ सल्ला बुक करा"
        Language.BENGALI -> "বিশেষজ্ঞ পরামর্শ বুক করুন"
        Language.TAMIL -> "நிபுணர் ஆலோசனை பெறுக"
        Language.TELUGU -> "నిపుణుల సలహా బుక్ చేయండి"
        Language.ENGLISH -> "Book Expert Consultation"
    }
}

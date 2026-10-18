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

    fun getHelplineTitle(lang: Language): String = when (lang) {
        Language.HINDI -> "किसान कॉल सेंटर (निःशुल्क टोल-फ्री)"
        Language.PUNJABI -> "ਕਿਸਾਨ ਕਾਲ ਸੈਂਟਰ (ਮੁਫ਼ਤ ਟੋਲ-ਫ੍ਰੀ)"
        Language.GUJARATI -> "કિસાન કૉલ સેન્ટર (ટોલ-ફ્રી)"
        Language.MARATHI -> "किसान कॉल सेंटर (टोल-फ्री)"
        Language.BENGALI -> "কিসান কল সেন্টার (টোল-ফ্রি)"
        Language.TAMIL -> "கிசான் கால் சென்டர் (இலவசம்)"
        Language.TELUGU -> "కిసాన్ కాల్ సెంటర్ (టోల్-ఫ్రీ)"
        Language.ENGLISH -> "Kisan Call Center (Toll-Free Helpline)"
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

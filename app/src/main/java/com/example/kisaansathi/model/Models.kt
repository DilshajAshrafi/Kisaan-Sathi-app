package com.example.kisaansathi.model

import androidx.annotation.DrawableRes

enum class Language(val code: String, val displayName: String, val nativeName: String) {
    ENGLISH("en", "English", "English"),
    HINDI("hi", "Hindi", "हिंदी"),
    PUNJABI("pa", "Punjabi", "ਪੰਜਾਬੀ"),
    BENGALI("bn", "Bengali", "বাংলা"),
    GUJARATI("gu", "Gujarati", "ગુજરાતી"),
    MARATHI("mr", "Marathi", "मराठी"),
    TAMIL("ta", "Tamil", "தமிழ்"),
    TELUGU("te", "Telugu", "తెలుగు")
}

/** Every language offered in the picker; screens render with [base] until a full translation exists. */
enum class IndianLanguage(
    val code: String,
    val displayName: String,
    val nativeName: String,
    val region: String,
    val base: Language,
    val speechTag: String
) {
    ENGLISH("en", "English", "English", "All India", Language.ENGLISH, "en-IN"),
    HINDI("hi", "Hindi", "हिंदी", "Uttar Pradesh, Bihar, Haryana, Madhya Pradesh", Language.HINDI, "hi-IN"),
    PUNJABI("pa", "Punjabi", "ਪੰਜਾਬੀ", "Punjab", Language.PUNJABI, "en-IN"),
    BENGALI("bn", "Bengali", "বাংলা", "West Bengal, Tripura", Language.BENGALI, "en-IN"),
    GUJARATI("gu", "Gujarati", "ગુજરાતી", "Gujarat", Language.GUJARATI, "en-IN"),
    MARATHI("mr", "Marathi", "मराठी", "Maharashtra", Language.MARATHI, "en-IN"),
    TAMIL("ta", "Tamil", "தமிழ்", "Tamil Nadu", Language.TAMIL, "en-IN"),
    TELUGU("te", "Telugu", "తెలుగు", "Andhra Pradesh, Telangana", Language.TELUGU, "en-IN"),
    KANNADA("kn", "Kannada", "ಕನ್ನಡ", "Karnataka", Language.ENGLISH, "en-IN"),
    MALAYALAM("ml", "Malayalam", "മലയാളം", "Kerala", Language.ENGLISH, "en-IN"),
    ODIA("or", "Odia", "ଓଡ଼ିଆ", "Odisha", Language.ENGLISH, "en-IN"),
    ASSAMESE("as", "Assamese", "অসমীয়া", "Assam", Language.ENGLISH, "en-IN"),
    URDU("ur", "Urdu", "اردو", "Jammu & Kashmir, Uttar Pradesh, Telangana", Language.ENGLISH, "en-IN"),
    NEPALI("ne", "Nepali", "नेपाली", "Sikkim, Darjeeling", Language.ENGLISH, "en-IN"),
    MAITHILI("mai", "Maithili", "मैथिली", "Bihar", Language.ENGLISH, "hi-IN"),
    DOGRI("doi", "Dogri", "डोगरी", "Jammu", Language.HINDI, "hi-IN"),
    KONKANI("kok", "Konkani", "कोंकणी", "Goa, Konkan coast", Language.ENGLISH, "en-IN"),
    BODO("brx", "Bodo", "बड़ो", "Assam", Language.HINDI, "hi-IN"),
    KASHMIRI("ks", "Kashmiri", "کٲشُر", "Kashmir", Language.HINDI, "hi-IN"),
    SANSKRIT("sa", "Sanskrit", "संस्कृतम्", "All India", Language.ENGLISH, "hi-IN"),
    SANTALI("sat", "Santali", "ᱥᱟᱱᱛᱟᱲᱤ", "Jharkhand, Odisha, West Bengal", Language.HINDI, "hi-IN"),
    SINDHI("sd", "Sindhi", "سنڌي", "Gujarat, Rajasthan", Language.ENGLISH, "en-IN"),
    MANIPURI("mni", "Manipuri (Meitei)", "মৈতৈলোন্", "Manipur", Language.BENGALI, "en-IN");

    val isFullyTranslated: Boolean get() = base.code == code

    companion object {
        fun fromCode(code: String?): IndianLanguage =
            entries.firstOrNull { it.code == code } ?: ENGLISH
    }
}

enum class ScreenRoute {
    HOME,
    CROP_CONSULTATION,
    IRRIGATION_SUPPORT,
    SOIL_PEST_CARE,
    SCHEME_GUIDANCE,
    AGRI_ASSISTANT,
    CONSULTATION_REQUEST,
    REQUEST_SENT,
    MSP_PRICES,
    CROP_CALENDAR,
    SOIL_HEALTH,
    MY_REQUESTS,
    EXPENSE_TRACKER,
    WEATHER_ALERTS,
    FARM_DIARY,
    SCHEME_ELIGIBILITY,
    CROP_PHOTO_GUIDE,
    MANDI_PRICES
}

data class FarmExpense(
    val id: String,
    val crop: String,
    val description: String,
    val amount: Double,
    val isIncome: Boolean,
    val date: String
)

data class FarmProfitSummary(
    val income: Double,
    val expenses: Double,
    val net: Double,
    val netByCrop: Map<String, Double>
)

fun calculateFarmProfit(expenses: List<FarmExpense>): FarmProfitSummary {
    val income = expenses.filter { it.isIncome }.sumOf { it.amount }
    val costs = expenses.filterNot { it.isIncome }.sumOf { it.amount }
    val netByCrop = expenses.groupBy { it.crop }.mapValues { (_, cropEntries) ->
        cropEntries.filter { it.isIncome }.sumOf { it.amount } -
            cropEntries.filterNot { it.isIncome }.sumOf { it.amount }
    }
    return FarmProfitSummary(income, costs, income - costs, netByCrop)
}

data class FarmDiaryEntry(
    val id: String,
    val crop: String,
    val note: String,
    val date: String,
    val photoUri: String?
)

data class CropPlan(
    val crop: String,
    val sowingDate: String,
    val reminderDate: String,
    val reminderTask: String
)

data class MandiPrice(
    val commodity: String,
    val market: String,
    val district: String,
    val state: String,
    val arrivalDate: String,
    val minimumPrice: String,
    val maximumPrice: String,
    val modalPrice: String,
    val priceUnit: String
)

data class CropGuide(
    val id: String,
    val nameEn: String,
    val nameHi: String,
    val seasonEn: String,
    val seasonHi: String,
    val soilTypeEn: String,
    val soilTypeHi: String,
    val waterNeedEn: String,
    val waterNeedHi: String,
    val sowingPeriodEn: String,
    val sowingPeriodHi: String,
    val spacingAdviceEn: String,
    val spacingAdviceHi: String,
    val keyTipsEn: List<String>,
    val keyTipsHi: List<String>,
    val commonDiseasesEn: List<String>,
    val commonDiseasesHi: List<String>
)

data class SchemeInfo(
    val id: String,
    val titleEn: String,
    val titleHi: String,
    val ministryEn: String,
    val ministryHi: String,
    val benefitEn: String,
    val benefitHi: String,
    val eligibilityEn: List<String>,
    val eligibilityHi: List<String>,
    val requiredDocsEn: List<String>,
    val requiredDocsHi: List<String>,
    val howToApplyEn: String,
    val howToApplyHi: String,
    val portalUrl: String
)

data class AgriExpert(
    val id: String,
    val name: String,
    val designation: String,
    val specialization: String,
    val experience: String,
    @DrawableRes val imageRes: Int? = null
)

data class ConsultationTicket(
    val ticketId: String,
    val farmerName: String,
    val phone: String,
    val villageDistrict: String,
    val crop: String,
    val problemDescription: String,
    val createdAt: String,
    val status: String
)

data class IrrigationAdvice(
    val cropName: String,
    val soilType: String,
    val areaAcres: Double,
    val dailyRequirementLiters: Long,
    val weeklyFrequency: String,
    val recommendedSystem: String,
    val savingsTip: String
)

data class PestCareItem(
    val id: String,
    val cropEn: String,
    val cropHi: String,
    val pestOrDiseaseEn: String,
    val pestOrDiseaseHi: String,
    val symptomsEn: String,
    val symptomsHi: String,
    val organicRemedyEn: String,
    val organicRemedyHi: String,
    val precautionEn: String,
    val precautionHi: String
)

enum class MessageRole {
    USER,
    ASSISTANT
}

data class ChatMessage(
    val id: String,
    val role: MessageRole,
    val text: String,
    val timestamp: String = "Now"
)

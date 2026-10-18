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

enum class ScreenRoute {
    HOME,
    CROP_CONSULTATION,
    IRRIGATION_SUPPORT,
    SOIL_PEST_CARE,
    SCHEME_GUIDANCE,
    AGRI_ASSISTANT,
    CONSULTATION_REQUEST,
    REQUEST_SENT
}

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

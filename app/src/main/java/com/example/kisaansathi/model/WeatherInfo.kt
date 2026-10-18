package com.example.kisaansathi.model

enum class SprayWindowStatus(val labelEn: String, val labelHi: String, val advisoryEn: String, val advisoryHi: String) {
    SAFE(
        "Optimal for Spraying",
        "छिड़काव के लिए उपयुक्त",
        "Gentle wind (<15 km/h) & clear skies. Optimal conditions for pesticide or foliar spray.",
        "हवा की गति सामान्य (<15 किमी/घंटा) व मौसम साफ है। कीटनाशक व पोषण छिड़काव के लिए उत्तम समय।"
    ),
    CAUTION(
        "Spray with Caution",
        "सावधानीपूर्वक छिड़काव करें",
        "Moderate wind (15-25 km/h) or high humidity. Use drift-reduction nozzles and spray early.",
        "हवा की गति मध्यम (15-25 किमी/घंटा) है। केवल सुबह या शाम को सावधानी से छिड़काव करें।"
    ),
    UNSAFE(
        "Do Not Spray Today",
        "आज छिड़काव न करें",
        "High wind (>25 km/h) or rain forecasted. High risk of spray drift or chemical wash-off.",
        "तेज हवा या वर्षा की संभावना है। दवा बहने या उड़ने का भारी जोखिम है, छिड़काव टालें।"
    )
}

data class WeatherInfo(
    val locationName: String,
    val temperatureC: Int,
    val feelsLikeC: Int,
    val conditionTitle: String,
    val conditionDescription: String,
    val humidityPercent: Int,
    val windSpeedKmh: Double,
    val pressureHpa: Int,
    val isRainExpected: Boolean,
    val sprayStatus: SprayWindowStatus,
    val irrigationAdvisoryEn: String,
    val irrigationAdvisoryHi: String,
    val fieldWorkAdvisoryEn: String,
    val fieldWorkAdvisoryHi: String,
    val lastUpdated: String,
    val isLiveOpenWeather: Boolean = false,
    val sourceLabel: String = "OpenWeather API Service",
    val cloudCoverPercent: Int = 10
)

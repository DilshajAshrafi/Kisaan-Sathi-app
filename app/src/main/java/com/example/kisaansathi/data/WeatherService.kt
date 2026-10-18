package com.example.kisaansathi.data

import com.example.BuildConfig
import com.example.kisaansathi.model.SprayWindowStatus
import com.example.kisaansathi.model.WeatherInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object WeatherService {

    // Default agricultural district hubs
    val agriculturalDistricts = listOf(
        "Karnal, Haryana",
        "Ludhiana, Punjab",
        "Jaipur, Rajasthan",
        "Indore, Madhya Pradesh",
        "Lucknow, Uttar Pradesh",
        "Nagpur, Maharashtra",
        "Patna, Bihar",
        "Guntur, Andhra Pradesh",
        "Bhatinda, Punjab",
        "Rajkot, Gujarat",
        "Hisar, Haryana",
        "Varanasi, Uttar Pradesh"
    )

    suspend fun fetchWeather(
        locationQuery: String = "Karnal, Haryana",
        apiKey: String = BuildConfig.OPENWEATHER_API_KEY
    ): WeatherInfo? = withContext(Dispatchers.IO) {
        val cleanQuery = locationQuery.split(",")[0].trim()

        if (apiKey.isNotBlank() && apiKey != "YOUR_OPENWEATHER_API_KEY") {
            val openWeatherResult = queryOpenWeather(cleanQuery, locationQuery, apiKey)
            if (openWeatherResult != null) {
                return@withContext openWeatherResult
            }
        }

        queryOpenMeteoLive(cleanQuery, locationQuery)
    }

    private fun queryOpenWeather(city: String, locationLabel: String, apiKey: String): WeatherInfo? {
        try {
            val encodedCity = URLEncoder.encode(city, "UTF-8")
            // Try city name with IN country code first, then plain city
            val urls = listOf(
                "https://api.openweathermap.org/data/2.5/weather?q=$encodedCity,IN&appid=$apiKey&units=metric",
                "https://api.openweathermap.org/data/2.5/weather?q=$encodedCity&appid=$apiKey&units=metric"
            )

            for (urlString in urls) {
                val url = URL(urlString)
                val conn = url.openConnection() as HttpURLConnection
                conn.connectTimeout = 6000
                conn.readTimeout = 6000
                conn.requestMethod = "GET"

                if (conn.responseCode == 200) {
                    val reader = BufferedReader(InputStreamReader(conn.inputStream))
                    val response = reader.readText()
                    reader.close()
                    conn.disconnect()
                    return parseOpenWeatherResponse(response, locationLabel)
                }
                conn.disconnect()
            }
        } catch (_: Exception) {
            // Log or ignore and proceed to fallback
        }
        return null
    }

    private fun queryOpenMeteoLive(city: String, locationLabel: String): WeatherInfo? {
        try {
            val encodedCity = URLEncoder.encode(city, "UTF-8")
            val geoUrl = URL("https://geocoding-api.open-meteo.com/v1/search?name=$encodedCity&count=1&language=en&format=json")
            val geoConn = geoUrl.openConnection() as HttpURLConnection
            geoConn.connectTimeout = 5000
            geoConn.readTimeout = 5000
            if (geoConn.responseCode != 200) {
                geoConn.disconnect()
                return null
            }
            val geoResponse = geoConn.inputStream.bufferedReader().readText()
            geoConn.disconnect()

            val geoJson = JSONObject(geoResponse)
            val results = geoJson.optJSONArray("results") ?: return null
            if (results.length() == 0) return null
            val first = results.getJSONObject(0)
            val lat = first.getDouble("latitude")
            val lon = first.getDouble("longitude")

            val weatherUrl = URL("https://api.open-meteo.com/v1/forecast?latitude=$lat&longitude=$lon&current=temperature_2m,relative_humidity_2m,apparent_temperature,precipitation,surface_pressure,wind_speed_10m,weather_code")
            val weatherConn = weatherUrl.openConnection() as HttpURLConnection
            weatherConn.connectTimeout = 5000
            weatherConn.readTimeout = 5000
            if (weatherConn.responseCode != 200) {
                weatherConn.disconnect()
                return null
            }
            val weatherResp = weatherConn.inputStream.bufferedReader().readText()
            weatherConn.disconnect()

            val wJson = JSONObject(weatherResp)
            val current = wJson.getJSONObject("current")
            val temp = current.getDouble("temperature_2m").toInt()
            val feelsLike = current.getDouble("apparent_temperature").toInt()
            val humidity = current.getInt("relative_humidity_2m")
            val windSpeed = current.getDouble("wind_speed_10m")
            val pressure = current.getDouble("surface_pressure").toInt()
            val precip = current.getDouble("precipitation")
            val code = current.getInt("weather_code")

            val (condTitle, condDesc) = parseWmoCode(code)
            val isRain = precip > 0.0 || code in listOf(51, 53, 55, 61, 63, 65, 80, 81, 82, 95)

            val sprayStatus = when {
                isRain || windSpeed > 22.0 -> SprayWindowStatus.UNSAFE
                windSpeed > 15.0 || humidity > 80 -> SprayWindowStatus.CAUTION
                else -> SprayWindowStatus.SAFE
            }

            val (irrigEn, irrigHi) = generateIrrigationAdvisory(isRain, temp, humidity)
            val (workEn, workHi) = generateFieldWorkAdvisory(isRain, windSpeed)

            val timeStr = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())

            return WeatherInfo(
                locationName = locationLabel,
                temperatureC = temp,
                feelsLikeC = feelsLike,
                conditionTitle = condTitle,
                conditionDescription = condDesc,
                humidityPercent = humidity,
                windSpeedKmh = windSpeed,
                pressureHpa = pressure,
                isRainExpected = isRain,
                sprayStatus = sprayStatus,
                irrigationAdvisoryEn = irrigEn,
                irrigationAdvisoryHi = irrigHi,
                fieldWorkAdvisoryEn = workEn,
                fieldWorkAdvisoryHi = workHi,
                lastUpdated = timeStr,
                isLiveOpenWeather = false,
                sourceLabel = "Live Farm Weather Service"
            )
        } catch (_: Exception) {
            return null
        }
    }

    private fun parseOpenWeatherResponse(jsonStr: String, locationLabel: String): WeatherInfo {
        val json = JSONObject(jsonStr)
        val main = json.getJSONObject("main")
        val wind = json.getJSONObject("wind")
        val weatherArray = json.getJSONArray("weather")
        val weatherObj = weatherArray.getJSONObject(0)
        val clouds = json.optJSONObject("clouds")?.optInt("all", 0) ?: 0

        val temp = main.getDouble("temp").toInt()
        val feelsLike = main.getDouble("feels_like").toInt()
        val humidity = main.getInt("humidity")
        val pressure = main.getInt("pressure")
        val conditionTitle = weatherObj.getString("main")
        val conditionDesc = weatherObj.getString("description").replaceFirstChar { it.uppercase() }

        // OpenWeather wind speed is in m/s -> convert to km/h
        val windSpeedMs = wind.getDouble("speed")
        val windSpeedKmh = (windSpeedMs * 3.6 * 10).toInt() / 10.0

        val isRain = conditionTitle.lowercase().contains("rain") ||
                conditionTitle.lowercase().contains("drizzle") ||
                conditionTitle.lowercase().contains("thunderstorm") ||
                json.has("rain")

        val sprayStatus = when {
            isRain || windSpeedKmh > 22.0 -> SprayWindowStatus.UNSAFE
            windSpeedKmh > 15.0 || humidity > 80 -> SprayWindowStatus.CAUTION
            else -> SprayWindowStatus.SAFE
        }

        val (irrigationEn, irrigationHi) = generateIrrigationAdvisory(isRain, temp, humidity)
        val (fieldWorkEn, fieldWorkHi) = generateFieldWorkAdvisory(isRain, windSpeedKmh)

        val timeStr = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())

        return WeatherInfo(
            locationName = locationLabel,
            temperatureC = temp,
            feelsLikeC = feelsLike,
            conditionTitle = conditionTitle,
            conditionDescription = conditionDesc,
            humidityPercent = humidity,
            windSpeedKmh = windSpeedKmh,
            pressureHpa = pressure,
            isRainExpected = isRain,
            sprayStatus = sprayStatus,
            irrigationAdvisoryEn = irrigationEn,
            irrigationAdvisoryHi = irrigationHi,
            fieldWorkAdvisoryEn = fieldWorkEn,
            fieldWorkAdvisoryHi = fieldWorkHi,
            lastUpdated = timeStr,
            isLiveOpenWeather = true,
            sourceLabel = "OpenWeather API (Live)",
            cloudCoverPercent = clouds
        )
    }

    private fun generateIrrigationAdvisory(isRain: Boolean, temp: Int, humidity: Int): Pair<String, String> {
        val en = when {
            isRain -> "Rain forecast: Delay scheduled irrigation by 24-48 hours. Conserve energy and avoid root waterlogging."
            temp > 32 && humidity < 40 -> "High evaporation rate: Irrigate in early morning or evening hours to minimize water evaporation."
            else -> "Favorable soil evaporation conditions: Proceed with normal irrigation rotation for current crop stage."
        }
        val hi = when {
            isRain -> "वर्षा की संभावना: बिजली व भूजल बचाने हेतु सिंचाई 24-48 घंटे टालें, इससे फसल में जलभराव रुकेगा।"
            temp > 32 && humidity < 40 -> "उच्च वाष्पीकरण दर: पानी बचाने के लिए सुबह जल्दी या शाम ढलने पर ही हल्की सिंचाई करें।"
            else -> "सामान्य वाष्पीकरण स्थिति: फसल चक्र अनुसार निर्धारित समय पर नियमित सिंचाई जारी रखें।"
        }
        return Pair(en, hi)
    }

    private fun generateFieldWorkAdvisory(isRain: Boolean, windSpeedKmh: Double): Pair<String, String> {
        val en = when {
            isRain -> "Avoid deep plowing or heavy tractor operations on wet soil to prevent subsoil compaction."
            windSpeedKmh > 20 -> "Strong gusts (>20 km/h): Postpone foliar spraying; ideal for equipment upkeep & storage checks."
            else -> "Gentle winds & dry conditions: Optimal weather for weeding, intercultural operations, and field spraying."
        }
        val hi = when {
            isRain -> "गीली मिट्टी में भारी ट्रैक्टर न चलाएं, इससे जमीन सख्त हो जाती है और वायु संचार रुकता है।"
            windSpeedKmh > 20 -> "तेज हवाएं (>20 किमी/घंटा): कीटनाशक छिड़काव रोकें, उपकरण रखरखाव और खाद भंडारण का काम करें।"
            else -> "अनुकूल मौसम: निराई-गुड़ाई, जुताई, पोषण छिड़काव और कटाई कार्यों के लिए उत्तम समय है।"
        }
        return Pair(en, hi)
    }

    private fun parseWmoCode(code: Int): Pair<String, String> {
        return when (code) {
            0 -> Pair("Clear Sky", "Sunny and bright with clear skies")
            1, 2, 3 -> Pair("Partly Cloudy", "Mainly clear with scattered clouds")
            45, 48 -> Pair("Foggy", "Fog and morning mist")
            51, 53, 55 -> Pair("Drizzle", "Light intermittent drizzle")
            61, 63, 65 -> Pair("Rain", "Moderate rainfall expected")
            80, 81, 82 -> Pair("Showers", "Localized rain showers")
            95 -> Pair("Thunderstorm", "Thunderstorm with strong winds")
            else -> Pair("Fair Weather", "Pleasant farming weather")
        }
    }

}

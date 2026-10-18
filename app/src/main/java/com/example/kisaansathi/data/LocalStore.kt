package com.example.kisaansathi.data

import android.content.Context
import com.example.kisaansathi.model.ConsultationTicket
import com.example.kisaansathi.model.Language
import com.example.kisaansathi.model.IndianLanguage
import com.example.kisaansathi.model.SprayWindowStatus
import com.example.kisaansathi.model.WeatherInfo
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/** Small on-device store for preferences, request history and the last good weather reading. */
class LocalStore(context: Context) {

    private val prefs = context.getSharedPreferences("kisaan_sathi_store", Context.MODE_PRIVATE)

    var onboardingDone: Boolean
        get() = prefs.getBoolean(KEY_ONBOARDING, false)
        set(value) = prefs.edit().putBoolean(KEY_ONBOARDING, value).apply()

    var fontScale: Float
        get() = prefs.getFloat(KEY_FONT_SCALE, 1f)
        set(value) = prefs.edit().putFloat(KEY_FONT_SCALE, value).apply()

    var indianLanguage: IndianLanguage
        get() = IndianLanguage.fromCode(prefs.getString(KEY_LANGUAGE, null))
        set(value) = prefs.edit().putString(KEY_LANGUAGE, value.code).apply()

    var language: Language
        get() = Language.entries.firstOrNull { it.code == prefs.getString(KEY_LANGUAGE, null) }
            ?: Language.ENGLISH
        set(value) = prefs.edit().putString(KEY_LANGUAGE, value.code).apply()

    fun loadRequests(): List<ConsultationTicket> {
        val raw = prefs.getString(KEY_REQUESTS, null) ?: return emptyList()
        return try {
            val array = JSONArray(raw)
            (0 until array.length()).map { index ->
                val item = array.getJSONObject(index)
                ConsultationTicket(
                    ticketId = item.getString("ticketId"),
                    farmerName = item.getString("farmerName"),
                    phone = item.getString("phone"),
                    villageDistrict = item.getString("villageDistrict"),
                    crop = item.getString("crop"),
                    problemDescription = item.getString("problemDescription"),
                    createdAt = item.getString("createdAt"),
                    status = item.getString("status")
                )
            }
        } catch (_: JSONException) {
            emptyList()
        }
    }

    fun saveRequests(tickets: List<ConsultationTicket>) {
        val array = JSONArray()
        tickets.take(MAX_SAVED_REQUESTS).forEach { ticket ->
            array.put(
                JSONObject()
                    .put("ticketId", ticket.ticketId)
                    .put("farmerName", ticket.farmerName)
                    .put("phone", ticket.phone)
                    .put("villageDistrict", ticket.villageDistrict)
                    .put("crop", ticket.crop)
                    .put("problemDescription", ticket.problemDescription)
                    .put("createdAt", ticket.createdAt)
                    .put("status", ticket.status)
            )
        }
        prefs.edit().putString(KEY_REQUESTS, array.toString()).apply()
    }

    fun cacheWeather(weather: WeatherInfo) {
        val json = JSONObject()
            .put("locationName", weather.locationName)
            .put("temperatureC", weather.temperatureC)
            .put("feelsLikeC", weather.feelsLikeC)
            .put("conditionTitle", weather.conditionTitle)
            .put("conditionDescription", weather.conditionDescription)
            .put("humidityPercent", weather.humidityPercent)
            .put("windSpeedKmh", weather.windSpeedKmh)
            .put("pressureHpa", weather.pressureHpa)
            .put("isRainExpected", weather.isRainExpected)
            .put("sprayStatus", weather.sprayStatus.name)
            .put("irrigationAdvisoryEn", weather.irrigationAdvisoryEn)
            .put("irrigationAdvisoryHi", weather.irrigationAdvisoryHi)
            .put("fieldWorkAdvisoryEn", weather.fieldWorkAdvisoryEn)
            .put("fieldWorkAdvisoryHi", weather.fieldWorkAdvisoryHi)
            .put("lastUpdated", weather.lastUpdated)
            .put("cloudCoverPercent", weather.cloudCoverPercent)
        prefs.edit().putString(KEY_WEATHER, json.toString()).apply()
    }

    /** Returns the saved reading for [location] only, so one town's data is never shown for another. */
    fun cachedWeather(location: String): WeatherInfo? {
        val raw = prefs.getString(KEY_WEATHER, null) ?: return null
        return try {
            val json = JSONObject(raw)
            val saved = json.getString("locationName")
            if (!saved.equals(location, ignoreCase = true) && !location.startsWith(saved, ignoreCase = true)) {
                return null
            }
            WeatherInfo(
                locationName = saved,
                temperatureC = json.getInt("temperatureC"),
                feelsLikeC = json.getInt("feelsLikeC"),
                conditionTitle = json.getString("conditionTitle"),
                conditionDescription = json.getString("conditionDescription"),
                humidityPercent = json.getInt("humidityPercent"),
                windSpeedKmh = json.getDouble("windSpeedKmh"),
                pressureHpa = json.getInt("pressureHpa"),
                isRainExpected = json.getBoolean("isRainExpected"),
                sprayStatus = SprayWindowStatus.valueOf(json.getString("sprayStatus")),
                irrigationAdvisoryEn = json.getString("irrigationAdvisoryEn"),
                irrigationAdvisoryHi = json.getString("irrigationAdvisoryHi"),
                fieldWorkAdvisoryEn = json.getString("fieldWorkAdvisoryEn"),
                fieldWorkAdvisoryHi = json.getString("fieldWorkAdvisoryHi"),
                lastUpdated = json.getString("lastUpdated"),
                isLiveOpenWeather = false,
                sourceLabel = "Saved offline copy",
                cloudCoverPercent = json.optInt("cloudCoverPercent", 10)
            )
        } catch (_: JSONException) {
            null
        } catch (_: IllegalArgumentException) {
            null
        }
    }

    private companion object {
        const val KEY_ONBOARDING = "onboarding_done"
        const val KEY_FONT_SCALE = "font_scale"
        const val KEY_LANGUAGE = "language"
        const val KEY_REQUESTS = "requests"
        const val KEY_WEATHER = "weather_cache"
        const val MAX_SAVED_REQUESTS = 50
    }
}

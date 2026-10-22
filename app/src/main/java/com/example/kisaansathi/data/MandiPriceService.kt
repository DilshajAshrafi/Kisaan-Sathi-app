package com.example.kisaansathi.data

import com.example.kisaansathi.model.MandiPrice
import org.json.JSONException
import org.json.JSONObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

object MandiPriceService {
    private const val RESOURCE_ID = "9ef84268-d588-465a-a308-a864a43d0070"

    suspend fun fetchPrices(
        commodity: String,
        state: String,
        apiKey: String,
        district: String = ""
    ): List<MandiPrice> =
        withContext(Dispatchers.IO) {
        if (apiKey.isBlank()) {
            throw IOException("Live mandi prices need a data.gov.in API key. Add DATA_GOV_IN_API_KEY to the local .env file and rebuild.")
        }

        val filters = mutableListOf(
            "api-key" to apiKey,
            "format" to "json",
            "limit" to "50",
            "filters[commodity]" to commodity,
            "filters[state]" to state
        )
        if (district.isNotBlank()) filters += "filters[district]" to district.trim()
        val query = filters.joinToString("&") { (key, value) ->
            "${encode(key)}=${encode(value)}"
        }
        val connection = URL("https://api.data.gov.in/resource/$RESOURCE_ID?$query")
            .openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connectTimeout = 10_000
        connection.readTimeout = 10_000

        try {
            val status = connection.responseCode
            if (status !in 200..299) {
                throw IOException("The official mandi price service returned HTTP $status.")
            }
            val payload = connection.inputStream.bufferedReader().use { it.readText() }
            parseResponse(payload)
        } finally {
            connection.disconnect()
        }
    }

    internal fun parseResponse(payload: String): List<MandiPrice> {
        val response = try {
            JSONObject(payload)
        } catch (exception: JSONException) {
            throw IOException("The official mandi price service returned invalid data.", exception)
        }
        val records = response.optJSONArray("records")
            ?: throw IOException("The official mandi price service returned no records.")
        return (0 until records.length()).map { index ->
            val record = records.optJSONObject(index)
                ?: throw IOException("The official mandi price service returned an invalid market record.")
            try {
                MandiPrice(
                    commodity = record.getString("commodity"),
                    market = record.getString("market"),
                    district = record.getString("district"),
                    state = record.getString("state"),
                    arrivalDate = record.getString("arrival_date"),
                    minimumPrice = record.getString("min_price"),
                    maximumPrice = record.getString("max_price"),
                    modalPrice = record.getString("modal_price"),
                    priceUnit = record.optString("unit_of_price").ifBlank { "unit not specified" }
                )
            } catch (exception: JSONException) {
                throw IOException("The official mandi price service returned an incomplete market record.", exception)
            }
        }
    }

    private fun encode(value: String): String =
        URLEncoder.encode(value, Charsets.UTF_8.name())
}

package com.example.kisaansathi.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.UUID

class ConsultationAttachment(
    val fileName: String,
    val mimeType: String,
    val bytes: ByteArray
)

data class ConsultationSubmission(
    val ticketId: String,
    val farmerName: String,
    val email: String,
    val phone: String,
    val financialAid: String,
    val villageDistrict: String,
    val crop: String,
    val problemDescription: String,
    val attachment: ConsultationAttachment? = null
)

object FormSubmitService {

    private const val ENDPOINT = "https://formsubmit.co/ajax/group11idt@outlook.com"
    const val MAX_ATTACHMENT_BYTES = 4 * 1024 * 1024

    suspend fun sendConsultation(submission: ConsultationSubmission) = withContext(Dispatchers.IO) {
        val boundary = "KisaanSathi${UUID.randomUUID().toString().replace("-", "")}"
        val fields = linkedMapOf(
            "_subject" to "Kisaan Sathi app support request ${submission.ticketId}",
            "_template" to "table",
            "_captcha" to "false",
            "Ticket ID" to submission.ticketId,
            "name" to submission.farmerName,
            "phone" to submission.phone,
            "email" to submission.email.ifBlank { "Not provided" },
            "financial-aid" to submission.financialAid.ifBlank { "Not provided" },
            "address" to submission.villageDistrict,
            "crop" to submission.crop,
            "message" to submission.problemDescription,
            "source" to "Kisaan Sathi Android app"
        )
        if (submission.email.isNotBlank()) {
            fields["_replyto"] = submission.email
        }

        val body = ByteArrayOutputStream()
        fun write(text: String) = body.write(text.toByteArray(Charsets.UTF_8))
        fields.forEach { (name, value) ->
            write("--$boundary\r\nContent-Disposition: form-data; name=\"$name\"\r\n\r\n$value\r\n")
        }
        submission.attachment?.let { file ->
            val safeName = file.fileName.replace("\"", "").replace("\r", "").replace("\n", "")
            write(
                "--$boundary\r\nContent-Disposition: form-data; name=\"attachment\"; " +
                    "filename=\"$safeName\"\r\nContent-Type: ${file.mimeType}\r\n\r\n"
            )
            body.write(file.bytes)
            write("\r\n")
        }
        write("--$boundary--\r\n")

        val connection = URL(ENDPOINT).openConnection() as HttpURLConnection
        try {
            connection.requestMethod = "POST"
            connection.connectTimeout = 15_000
            connection.readTimeout = 45_000
            connection.doOutput = true
            connection.setRequestProperty("Accept", "application/json")
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=$boundary")
            connection.outputStream.use { it.write(body.toByteArray()) }

            val statusCode = connection.responseCode
            val responseStream = if (statusCode in 200..299) {
                connection.inputStream
            } else {
                connection.errorStream
            }
            val responseBody = responseStream?.bufferedReader()?.use { it.readText() }.orEmpty()

            if (statusCode !in 200..299) {
                throw IOException("FormSubmit returned HTTP $statusCode.")
            }

            val response = try {
                JSONObject(responseBody)
            } catch (exception: JSONException) {
                throw IOException("FormSubmit returned an unreadable response.", exception)
            }
            val success = response.optString("success").equals("true", ignoreCase = true) ||
                response.optBoolean("success")
            if (!success) {
                throw IOException(
                    response.optString("message").ifBlank {
                        "FormSubmit could not send the consultation request."
                    }
                )
            }
        } finally {
            connection.disconnect()
        }
    }
}

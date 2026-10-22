package com.example.kisaansathi.ui.screens

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import android.net.Uri
import com.example.kisaansathi.data.ConsultationAttachment
import com.example.kisaansathi.data.FormSubmitService
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import com.example.kisaansathi.ui.components.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.kisaansathi.model.Language
import com.example.kisaansathi.ui.theme.Gold100
import com.example.kisaansathi.ui.theme.Gold500
import com.example.kisaansathi.ui.theme.Green800
import com.example.kisaansathi.ui.theme.Green900

@Composable
fun ConsultationRequestScreen(
    currentLanguage: Language,
    isSubmitting: Boolean,
    submissionError: String?,
    initialPhotoUri: String? = null,
    initialCrop: String? = null,
    initialProblem: String? = null,
    onSubmit: (String, String, String, String, String, String, String, ConsultationAttachment?) -> Unit
) {
    val isHi = currentLanguage == Language.HINDI
    val context = LocalContext.current

    var farmerName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var financialAid by remember { mutableStateOf("") }
    var villageDistrict by remember { mutableStateOf("") }
    var crop by remember(initialCrop) { mutableStateOf(initialCrop.orEmpty()) }
    var problemDescription by remember(initialProblem) { mutableStateOf(initialProblem.orEmpty()) }
    var attachment by remember(initialPhotoUri) {
        mutableStateOf(initialPhotoUri?.let { readAttachment(context, Uri.parse(it)) })
    }
    var formError by remember(initialPhotoUri) {
        mutableStateOf(
            if (initialPhotoUri != null && attachment == null) {
                if (isHi) "चुनी गई तस्वीर पढ़ी नहीं जा सकी। कृपया इसे फिर से जोड़ें।"
                else "The selected photo could not be read. Please attach it again."
            } else null
        )
    }

    val mediaPicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            val picked = readAttachment(context, uri)
            if (picked == null) {
                attachment = null
                formError = if (isHi)
                    "फ़ाइल पढ़ी नहीं जा सकी या 4 MB से बड़ी है।"
                else
                    "That file couldn't be read or is larger than 4 MB."
            } else {
                attachment = picked
                formError = null
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("consultation_request_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Assignment,
                            contentDescription = null,
                            tint = Green800,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = if (isHi) "कृषि वैज्ञानिक परामर्श फॉर्म" else "Agronomist Consultation Request",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = if (isHi)
                                    "डॉ. पुष्पेन्द्र त्यागी (प्रोजेक्ट समन्वयक) एवं ग्रुप 11 IDT टीम से समाधान पाएं।"
                                else
                                    "Get guidance from Dr. Pushpendra Tyagi (Project Coordinator) & Group 11 IDT.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = farmerName,
                        onValueChange = { farmerName = it },
                        label = { Text(if (isHi) "किसान का नाम *" else "Farmer Name *") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_farmer_name"),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(if (isHi) "ईमेल (वैकल्पिक)" else "Email (optional)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_farmer_email"),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text(if (isHi) "मोबाइल नंबर *" else "Mobile Number *") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_farmer_phone"),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = financialAid,
                        onValueChange = { financialAid = it },
                        label = { Text(if (isHi) "वित्तीय सहायता (वैकल्पिक)" else "Financial aid (optional)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_financial_aid"),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = villageDistrict,
                        onValueChange = { villageDistrict = it },
                        label = { Text(if (isHi) "गांव / जिला / राज्य" else "Village / District / State") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_farmer_location"),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = crop,
                        onValueChange = { crop = it },
                        label = { Text(if (isHi) "फसल का नाम (जैसे गेहूं, धान, कपास)" else "Crop Name (e.g. Wheat, Cotton, Mustard)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_farmer_crop"),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = problemDescription,
                        onValueChange = { problemDescription = it },
                        label = { Text(if (isHi) "समस्या का विवरण (लक्षण, बुवाई तिथि) *" else "Describe Issue / Symptoms *") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_problem_desc"),
                        minLines = 4,
                        maxLines = 6
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedButton(
                        onClick = { mediaPicker.launch("*/*") },
                        enabled = !isSubmitting,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("attach_media_button")
                    ) {
                        Icon(Icons.Default.AttachFile, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            attachment?.fileName
                                ?: if (isHi) "समस्या की फोटो/वीडियो जोड़ें (वैकल्पिक)"
                                else "Attach photo or video (optional)"
                        )
                    }

                    (formError ?: submissionError)?.let { error ->
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            val normalizedPhone = phone.filter { it.isDigit() }
                            val validPhone = normalizedPhone.length == 10 ||
                                (normalizedPhone.length == 12 && normalizedPhone.startsWith("91"))
                            val validEmail = email.isBlank() ||
                                android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
                            when {
                                farmerName.isBlank() || !validPhone || problemDescription.isBlank() -> {
                                    formError = if (isHi)
                                        "कृपया नाम, सही मोबाइल नंबर और समस्या का विवरण भरें।"
                                    else
                                        "Enter your name, a valid mobile number, and the issue details."
                                }
                                !validEmail -> {
                                    formError = if (isHi)
                                        "कृपया सही ईमेल पता दर्ज करें या खाली छोड़ें।"
                                    else
                                        "Enter a valid email address or leave it blank."
                                }
                                else -> {
                                    formError = null
                                    onSubmit(
                                        farmerName,
                                        email,
                                        phone,
                                        financialAid,
                                        villageDistrict,
                                        crop,
                                        problemDescription,
                                        attachment
                                    )
                                }
                            }
                        },
                        enabled = !isSubmitting,
                        colors = ButtonDefaults.buttonColors(containerColor = Green800),
                        shape = RoundedCornerShape(999.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("submit_consultation_button")
                    ) {
                        if (isSubmitting) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                        Text(
                            text = when {
                                isSubmitting && isHi -> "भेजा जा रहा है..."
                                isSubmitting -> "Sending request..."
                                isHi -> "ईमेल द्वारा अनुरोध भेजें"
                                else -> "Send consultation request"
                            },
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = Gold500,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = if (isHi)
                                "अनुरोध प्राप्त होने पर हमारी कृषि टीम 24 घंटे के भीतर आपसे संपर्क करेगी।"
                            else
                                "Our agricultural extension team will contact you within 24 hours.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Phone: +91 8081392456",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Email: group11idt@outlook.com",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

private fun readAttachment(context: android.content.Context, uri: android.net.Uri): ConsultationAttachment? {
    val resolver = context.contentResolver
    val limit = FormSubmitService.MAX_ATTACHMENT_BYTES
    return try {
        val bytes = resolver.openInputStream(uri)?.use { input ->
            val output = java.io.ByteArrayOutputStream()
            val buffer = ByteArray(8192)
            while (true) {
                val read = input.read(buffer)
                if (read < 0) break
                output.write(buffer, 0, read)
                if (output.size() > limit) return null
            }
            output.toByteArray()
        } ?: return null
        val name = resolver.query(uri, arrayOf(android.provider.OpenableColumns.DISPLAY_NAME), null, null, null)
            ?.use { if (it.moveToFirst()) it.getString(0) else null }
            ?: "attachment"
        ConsultationAttachment(name, resolver.getType(uri) ?: "application/octet-stream", bytes)
    } catch (_: java.io.IOException) {
        null
    } catch (_: SecurityException) {
        null
    }
}

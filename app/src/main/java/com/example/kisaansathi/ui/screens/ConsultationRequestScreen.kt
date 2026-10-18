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
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
    onSubmit: (String, String, String, String, String) -> Boolean
) {
    val isHi = currentLanguage == Language.HINDI

    var farmerName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var villageDistrict by remember { mutableStateOf("") }
    var crop by remember { mutableStateOf("") }
    var problemDescription by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

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
                            imageVector = Icons.Default.Assignment,
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
                                color = Green900
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
                        onValueChange = {
                            farmerName = it
                            errorMessage = null
                        },
                        label = { Text(if (isHi) "किसान का नाम *" else "Farmer Name *") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_farmer_name"),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = phone,
                        onValueChange = {
                            phone = it
                            errorMessage = null
                        },
                        label = { Text(if (isHi) "मोबाइल नंबर *" else "Mobile Number *") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_farmer_phone"),
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
                        onValueChange = {
                            problemDescription = it
                            errorMessage = null
                        },
                        label = { Text(if (isHi) "समस्या का विवरण (लक्षण, बुवाई तिथि) *" else "Describe Issue / Symptoms *") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_problem_desc"),
                        minLines = 4,
                        maxLines = 6
                    )

                    errorMessage?.let { error ->
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
                            val success = onSubmit(farmerName, phone, villageDistrict, crop, problemDescription)
                            if (!success) {
                                errorMessage = if (isHi)
                                    "कृपया नाम, मोबाइल नंबर और समस्या का विवरण अवश्य भरें।"
                                else
                                    "Please fill required fields (Name, Mobile, and Description)."
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Green800),
                        shape = RoundedCornerShape(999.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("submit_consultation_button")
                    ) {
                        Text(
                            text = if (isHi) "परामर्श अनुरोध भेजें" else "Submit Consultation Request",
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
                colors = CardDefaults.cardColors(containerColor = Gold100.copy(alpha = 0.5f)),
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
                    Text(
                        text = if (isHi)
                            "अनुरोध प्राप्त होने पर हमारे कृषि अधिकारी 24 घंटे के भीतर फोन या व्हाट्सएप द्वारा संपर्क करेंगे।"
                        else
                            "Once submitted, our agricultural extension team will contact you via Phone/WhatsApp within 24 hours.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF5D4037)
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

package com.example.kisaansathi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.SpaceDashboard
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kisaansathi.data.KisaanRepository
import com.example.kisaansathi.model.CropGuide
import com.example.kisaansathi.model.Language
import com.example.kisaansathi.model.ScreenRoute
import com.example.kisaansathi.ui.theme.Gold500
import com.example.kisaansathi.ui.theme.Green100
import com.example.kisaansathi.ui.theme.Green700
import com.example.kisaansathi.ui.theme.Green800
import com.example.kisaansathi.ui.theme.Green900

@Composable
fun CropConsultationScreen(
    currentLanguage: Language,
    onNavigate: (ScreenRoute) -> Unit
) {
    val isHi = currentLanguage == Language.HINDI
    var selectedCropId by remember { mutableStateOf("all") }

    val filteredCrops = remember(selectedCropId) {
        if (selectedCropId == "all") {
            KisaanRepository.crops
        } else {
            KisaanRepository.crops.filter { it.id == selectedCropId }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("crop_consultation_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Green100),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(Green800),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Eco,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = if (isHi) "फसल परामर्श एवं योजना" else "Crop Consultation & Planning",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Green900
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (isHi)
                                "बुवाई, बीज चयन, फसल चक्र और संतुलित पोषण के लिए वैज्ञानिक सलाह।"
                            else
                                "Scientific guidelines for sowing windows, soil fit, spacing, and nutrition.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Green700
                        )
                    }
                }
            }
        }

        // Crop Selection Filter Chips
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    FilterChip(
                        selected = selectedCropId == "all",
                        onClick = { selectedCropId = "all" },
                        label = { Text(if (isHi) "सभी फसलें" else "All Crops") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Green800,
                            selectedLabelColor = Color.White
                        ),
                        modifier = Modifier.testTag("chip_all_crops")
                    )
                }
                items(KisaanRepository.crops) { crop ->
                    FilterChip(
                        selected = selectedCropId == crop.id,
                        onClick = { selectedCropId = crop.id },
                        label = { Text(if (isHi) crop.nameHi else crop.nameEn) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Green800,
                            selectedLabelColor = Color.White
                        ),
                        modifier = Modifier.testTag("chip_crop_${crop.id}")
                    )
                }
            }
        }

        // Crop Detail Cards
        items(filteredCrops) { crop ->
            CropGuideCard(crop = crop, isHi = isHi)
        }

        // Consult Agronomist Action Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = if (isHi) "विशेषज्ञ से अपनी फसल पर व्यक्तिगत सलाह लें" else "Need personalized advice for your field?",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Green900
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = if (isHi)
                            "खेत का स्थान, बुवाई की तारीख और फसल में दिख रही समस्या का विवरण भेजें।"
                        else
                            "Submit your crop issue with photos and details to get verified recommendations from our agronomists.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = { onNavigate(ScreenRoute.CONSULTATION_REQUEST) },
                        colors = ButtonDefaults.buttonColors(containerColor = Green800),
                        shape = RoundedCornerShape(999.dp),
                        modifier = Modifier.testTag("crop_consult_button")
                    ) {
                        Text(if (isHi) "परामर्श फॉर्म भरें" else "Book Expert Consultation")
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun CropGuideCard(crop: CropGuide, isHi: Boolean) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("crop_guide_card_${crop.id}")
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (isHi) crop.nameHi else crop.nameEn,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Green900
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Green100)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (isHi) crop.seasonHi else crop.seasonEn,
                        style = MaterialTheme.typography.labelSmall,
                        color = Green800,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Spec Grid
            SpecRow(
                icon = Icons.Default.Landscape,
                label = if (isHi) "मिट्टी" else "Soil",
                value = if (isHi) crop.soilTypeHi else crop.soilTypeEn
            )
            SpecRow(
                icon = Icons.Default.CalendarMonth,
                label = if (isHi) "बुवाई का समय" else "Sowing Time",
                value = if (isHi) crop.sowingPeriodHi else crop.sowingPeriodEn
            )
            SpecRow(
                icon = Icons.Default.SpaceDashboard,
                label = if (isHi) "दूरी व गहराई" else "Spacing & Depth",
                value = if (isHi) crop.spacingAdviceHi else crop.spacingAdviceEn
            )
            SpecRow(
                icon = Icons.Default.WaterDrop,
                label = if (isHi) "पानी की आवश्यकता" else "Water Need",
                value = if (isHi) crop.waterNeedHi else crop.waterNeedEn
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Key Tips
            Text(
                text = if (isHi) "मुख्य वैज्ञानिक सुझाव" else "Key Agronomic Tips",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = Green800
            )
            Spacer(modifier = Modifier.height(6.dp))
            val tips = if (isHi) crop.keyTipsHi else crop.keyTipsEn
            tips.forEach { tip ->
                Row(modifier = Modifier.padding(vertical = 2.dp)) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Green700,
                        modifier = Modifier
                            .size(16.dp)
                            .padding(top = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = tip,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Common Diseases / Pest Alert
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = Gold500,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                val diseases = if (isHi) crop.commonDiseasesHi else crop.commonDiseasesEn
                Text(
                    text = "${if (isHi) "प्रमुख बीमारियां" else "Watch out for"}: ${diseases.joinToString(", ")}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun SpecRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Green700,
            modifier = Modifier
                .size(18.dp)
                .padding(top = 2.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

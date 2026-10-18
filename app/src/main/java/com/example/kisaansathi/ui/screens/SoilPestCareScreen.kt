package com.example.kisaansathi.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Healing
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import com.example.kisaansathi.ui.components.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kisaansathi.data.KisaanRepository
import com.example.kisaansathi.model.Language
import com.example.kisaansathi.model.PestCareItem
import com.example.kisaansathi.ui.theme.Brown800
import com.example.kisaansathi.ui.theme.Gold100
import com.example.kisaansathi.ui.theme.Gold500
import com.example.kisaansathi.ui.theme.Green100
import com.example.kisaansathi.ui.theme.Green700
import com.example.kisaansathi.ui.theme.Green800
import com.example.kisaansathi.ui.theme.Green900

@Composable
fun SoilPestCareScreen(currentLanguage: Language) {
    val isHi = currentLanguage == Language.HINDI

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("soil_pest_care_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
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
                            .background(Brown800),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = if (isHi) "मृदा पोषण एवं एकीकृत कीट नियंत्रण" else "Soil Health & Integrated Pest Care",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (isHi)
                                "मृदा स्वास्थ्य कार्ड आधारित संतुलित पोषण और जैविक कीट नियंत्रण।"
                            else
                                "Soil testing parameters, organic bio-remedies, and disease management.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Soil Health Card 12 Vital Parameters
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Eco,
                            contentDescription = null,
                            tint = Green800,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isHi) "मृदा स्वास्थ्य कार्ड के 12 प्रमुख मापदंड" else "12 Vital Soil Health Parameters",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = if (isHi)
                            "1. मुख्य पोषक तत्व: नाइट्रोजन (N), फास्फोरस (P), पोटाश (K)\n2. द्वितीयक पोषक तत्व: सल्फर (S)\n3. सूक्ष्म पोषक तत्व: जिंक (Zn), आयरन (Fe), कॉपर (Cu), मैंगनीज (Mn), बोरान (B)\n4. भौतिक गुण: पीएच (pH), विद्युत चालकता (EC), जैविक कार्बन (OC)"
                        else
                            "1. Primary Nutrients: Nitrogen (N), Phosphorus (P), Potassium (K)\n2. Secondary Nutrients: Sulphur (S)\n3. Micro-nutrients: Zinc (Zn), Iron (Fe), Copper (Cu), Manganese (Mn), Boron (B)\n4. Physical Parameters: pH, Electrical Conductivity (EC), Organic Carbon (OC)",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 20.sp
                    )
                }
            }
        }

        // Pest and Disease Diagnosis Cards
        item {
            Text(
                text = if (isHi) "प्रमुख कीट एवं रोग निवारण गाइड" else "Pest & Disease Advisory Catalog",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        items(KisaanRepository.pestCareItems) { item ->
            PestCareItemCard(item = item, isHi = isHi)
        }

        // Organic Bio-Pesticide Recipes
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Healing,
                            contentDescription = null,
                            tint = Green800,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isHi) "देसी जैविक कीटनाशक बनाने की विधि" else "Natural Bio-Pesticide Recipes",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = if (isHi)
                            "• नीम गिरी अर्क (NSKE 5%): 5 किग्रा नीम के बीजों को कूटकर 100 लीटर पानी में 24 घंटे भिगोएं। रस चूसक कीटों और छोटी इल्लियों के लिए रामबाण।\n\n• खट्टी छाछ (Sour Buttermilk): 5 लीटर खट्टी छाछ को 100 लीटर पानी में मिलाकर गेहूं में पीले रतुआ और सब्जियों में फफूंद रोकने के लिए छिड़कें।\n\n• दशपर्णी अर्क: नीम, धतूरा, करंज, आक व मदार के पत्तों को गोमूत्र में सड़ाकर बनाया गया प्राकृतिक कीटनाशक।"
                        else
                            "• Neem Seed Kernel Extract (NSKE 5%): Crush 5 kg neem seeds in 100 L water for 24 hours. Highly effective against aphids, thrips, and young caterpillars.\n\n• Fermented Sour Buttermilk: Mix 5 L sour buttermilk in 100 L water as a protective bio-fungicide against rust and powdery mildew.\n\n• Dashparni Ark: Fermented extract of 10 indigenous bitter plant leaves in cow urine as a powerful broad-spectrum organic insect repellent.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // Safety Precaution Notice
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onTertiaryContainer,
                        modifier = Modifier
                            .size(22.dp)
                            .padding(top = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = if (isHi) "दवा छिड़काव सुरक्षा नियम" else "Safe Chemical Application Rules",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (isHi)
                                "हमेशा हवा की दिशा में छिड़काव करें, तेज धूप या दोपहर में न करें। मास्क, दस्ताने और चश्मा अवश्य पहनें। खाली डिब्बों को खेत में न फेंकें।"
                            else
                                "Always spray in the direction of wind during early morning or evening. Wear protective mask, goggles and gloves. Never reuse empty chemical containers.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onTertiaryContainer
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

@Composable
fun PestCareItemCard(item: PestCareItem, isHi: Boolean) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("pest_card_${item.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (isHi) item.pestOrDiseaseHi else item.pestOrDiseaseEn,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (isHi) item.cropHi else item.cropEn,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${if (isHi) "लक्षण" else "Symptoms"}: ${if (isHi) item.symptomsHi else item.symptomsEn}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "${if (isHi) "जैविक उपचार" else "Organic Remedy"}: ${if (isHi) item.organicRemedyHi else item.organicRemedyEn}",
                style = MaterialTheme.typography.bodySmall,
                color = Green800,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${if (isHi) "सावधानी" else "Precaution"}: ${if (isHi) item.precautionHi else item.precautionEn}",
                style = MaterialTheme.typography.labelSmall,
                color = Gold500
            )
        }
    }
}

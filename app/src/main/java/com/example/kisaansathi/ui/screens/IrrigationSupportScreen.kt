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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kisaansathi.model.IrrigationAdvice
import com.example.kisaansathi.model.Language
import com.example.kisaansathi.model.WeatherInfo
import com.example.kisaansathi.ui.theme.Blue100
import com.example.kisaansathi.ui.theme.Blue700
import com.example.kisaansathi.ui.theme.Gold100
import com.example.kisaansathi.ui.theme.Gold500
import com.example.kisaansathi.ui.theme.Green100
import com.example.kisaansathi.ui.theme.Green800
import com.example.kisaansathi.ui.theme.Green900

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IrrigationSupportScreen(
    currentLanguage: Language,
    selectedCrop: String,
    selectedSoil: String,
    areaText: String,
    irrigationAdvice: IrrigationAdvice?,
    weatherInfo: WeatherInfo? = null,
    onCropChange: (String) -> Unit,
    onSoilChange: (String) -> Unit,
    onAreaChange: (String) -> Unit,
    onCalculate: () -> Unit
) {
    val isHi = currentLanguage == Language.HINDI

    val cropOptions = listOf("Wheat", "Paddy / Rice", "Cotton", "Mustard", "Sugarcane", "Vegetables")
    val soilOptions = listOf("Loamy", "Sandy", "Clayey")

    var cropExpanded by remember { mutableStateOf(false) }
    var soilExpanded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("irrigation_support_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
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
                            .background(Blue700),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.WaterDrop,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = if (isHi) "स्मार्ट सिंचाई योजना" else "Smart Irrigation Planner",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (isHi)
                                "प्रति बूंद अधिक फसल: सूक्ष्म सिंचाई, जल बजट और वैज्ञानिक समय-सारणी।"
                            else
                                "Per Drop More Crop: Precision scheduling, water budget, and drip efficiency.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Live OpenWeather Impact Card
        if (weatherInfo != null) {
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.fillMaxWidth().testTag("weather_irrigation_planning_card")
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(if (weatherInfo.isLiveOpenWeather) Color(0xFF2E7D32) else Color(0xFF0284C7))
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = weatherInfo.sourceLabel,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Text(
                                text = weatherInfo.locationName,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "${weatherInfo.temperatureC}°C • ${weatherInfo.conditionTitle}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "${if (isHi) "हवा" else "Wind"}: ${weatherInfo.windSpeedKmh} km/h | ${if (isHi) "नमी" else "Humidity"}: ${weatherInfo.humidityPercent}%",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            if (weatherInfo.isRainExpected) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = if (isHi) "🌧️ वर्षा संभावित" else "🌧️ Rain Expected",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onTertiaryContainer
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.Top) {
                                Text(text = "💧 ", fontSize = 14.sp)
                                Text(
                                    text = if (isHi) weatherInfo.irrigationAdvisoryHi else weatherInfo.irrigationAdvisoryEn,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Interactive Water Requirement Calculator
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("irrigation_calculator_card")
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Calculate,
                            contentDescription = null,
                            tint = Blue700,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isHi) "खेत जल आवश्यकता कैलकुलेटर" else "Farm Water Requirement Calculator",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Crop Selection Dropdown
                    ExposedDropdownMenuBox(
                        expanded = cropExpanded,
                        onExpandedChange = { cropExpanded = !cropExpanded }
                    ) {
                        OutlinedTextField(
                            value = selectedCrop,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text(if (isHi) "फसल चुनें" else "Select Crop") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = cropExpanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                                .testTag("calculator_crop_dropdown")
                        )
                        ExposedDropdownMenu(
                            expanded = cropExpanded,
                            onDismissRequest = { cropExpanded = false }
                        ) {
                            cropOptions.forEach { crop ->
                                DropdownMenuItem(
                                    text = { Text(crop) },
                                    onClick = {
                                        onCropChange(crop)
                                        cropExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Soil Type Selection Dropdown
                    ExposedDropdownMenuBox(
                        expanded = soilExpanded,
                        onExpandedChange = { soilExpanded = !soilExpanded }
                    ) {
                        OutlinedTextField(
                            value = selectedSoil,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text(if (isHi) "मिट्टी का प्रकार" else "Soil Type") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = soilExpanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                                .testTag("calculator_soil_dropdown")
                        )
                        ExposedDropdownMenu(
                            expanded = soilExpanded,
                            onDismissRequest = { soilExpanded = false }
                        ) {
                            soilOptions.forEach { soil ->
                                DropdownMenuItem(
                                    text = { Text(soil) },
                                    onClick = {
                                        onSoilChange(soil)
                                        soilExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Farm Area in Acres Input
                    OutlinedTextField(
                        value = areaText,
                        onValueChange = onAreaChange,
                        label = { Text(if (isHi) "खेत का क्षेत्रफल (एकड़ में)" else "Field Area (in Acres)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("calculator_area_input")
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onCalculate,
                        colors = ButtonDefaults.buttonColors(containerColor = Blue700),
                        shape = RoundedCornerShape(999.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("calculate_irrigation_button")
                    ) {
                        Text(
                            text = if (isHi) "जल बजट व सिंचाई योजना निकालें" else "Calculate Water Budget",
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Calculation Results
                    irrigationAdvice?.let { advice ->
                        Spacer(modifier = Modifier.height(18.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .padding(16.dp)
                        ) {
                            Column {
                                Text(
                                    text = if (isHi) "सिंचाई परिणाम एवं सिफ़ारिश" else "Recommended Irrigation Plan",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "${if (isHi) "दैनिक जल आवश्यकता" else "Daily Water Requirement"}: ${advice.dailyRequirementLiters} Liters",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Text(
                                    text = "${if (isHi) "सिंचाई अंतराल" else "Frequency"}: ${advice.weeklyFrequency}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "${if (isHi) "अनुशंसित प्रणाली" else "System"}: ${advice.recommendedSystem}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Lightbulb,
                                        contentDescription = null,
                                        tint = Gold500,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = advice.savingsTip,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Green800
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Irrigation Systems Comparison Section
        item {
            Text(
                text = if (isHi) "सिंचाई पद्धतियों की तुलना" else "Irrigation Systems Comparison",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ComparisonCard(
                    title = if (isHi) "ड्रिप (टपक) सिंचाई" else "Drip Irrigation",
                    efficiency = "90% - 95% Water Efficiency",
                    benefit = if (isHi)
                        "सीधे जड़ क्षेत्र में पानी व उर्वरक (फर्टिगेशन), 50% पानी की बचत, खरपतवार में भारी कमी।"
                    else
                        "Water & fertilizer delivered directly to root zone; saves 50-60% water, minimizes weeds.",
                    subsidy = "Up to 55% PMKSY Subsidy",
                    accentColor = Color(0xFF2E7D32)
                )

                ComparisonCard(
                    title = if (isHi) "स्प्रिंकलर (फव्वारा) सिंचाई" else "Sprinkler Irrigation",
                    efficiency = "75% - 85% Water Efficiency",
                    benefit = if (isHi)
                        "बारिश की तरह फुहार, गेहूं व सरसों के लिए आदर्श, ऊबड़-खाबड़ खेतों में बिना समतल किए प्रभावी।"
                    else
                        "Artificial rain simulation, ideal for wheat, mustard & pulses; highly effective on undulating fields.",
                    subsidy = "Up to 45% PMKSY Subsidy",
                    accentColor = Color(0xFF1565C0)
                )

                ComparisonCard(
                    title = if (isHi) "पारंपरिक खुला बहाव (Flood)" else "Traditional Flood Irrigation",
                    efficiency = "35% - 45% Efficiency Only",
                    benefit = if (isHi)
                        "अत्यधिक पानी की बर्बादी, पोषक तत्वों का बह जाना और जलभराव से जड़ सड़न का खतरा।"
                    else
                        "High runoff & evaporation loss; risks nutrient leaching, salinity, and waterlogging rot.",
                    subsidy = "Upgrade to Micro-irrigation recommended",
                    accentColor = Color(0xFFC62828)
                )
            }
        }

        // Practical Moisture Testing Tip
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = Gold500,
                        modifier = Modifier
                            .size(22.dp)
                            .padding(top = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = if (isHi) "खेत में नमी जांचने की आसान देसी विधि" else "Hand-Feel Soil Moisture Test",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (isHi)
                                "पौधे के पास 6 इंच गहराई से मुट्ठी भर मिट्टी लें। यदि दबाने पर मिट्टी का लड्डू बन जाए और छूने पर गीला महसूस हो तो पानी न दें। यदि बिखर जाए तो तुरंत हल्की सिंचाई करें।"
                            else
                                "Take a handful of soil from 6 inches root depth. If it forms a firm ball and leaves moisture on your palm, delay irrigation. If it crumbles, schedule watering promptly.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
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
fun ComparisonCard(
    title: String,
    efficiency: String,
    benefit: String,
    subsidy: String,
    accentColor: Color
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(accentColor.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = efficiency,
                        style = MaterialTheme.typography.labelSmall,
                        color = accentColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = benefit,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = subsidy,
                    style = MaterialTheme.typography.labelSmall,
                    color = accentColor,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

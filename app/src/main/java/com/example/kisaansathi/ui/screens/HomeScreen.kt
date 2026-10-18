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
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kisaansathi.data.KisaanRepository
import com.example.kisaansathi.data.WeatherService
import com.example.kisaansathi.model.Language
import com.example.kisaansathi.model.ScreenRoute
import com.example.kisaansathi.model.SprayWindowStatus
import com.example.kisaansathi.model.WeatherInfo
import com.example.kisaansathi.ui.theme.Brown800
import com.example.kisaansathi.ui.theme.Gold100
import com.example.kisaansathi.ui.theme.Gold500
import com.example.kisaansathi.ui.theme.Green100
import com.example.kisaansathi.ui.theme.Green700
import com.example.kisaansathi.ui.theme.Green800
import com.example.kisaansathi.ui.theme.Green900

@Composable
fun HomeScreen(
    currentLanguage: Language,
    weatherInfo: WeatherInfo?,
    isWeatherLoading: Boolean = false,
    selectedLocation: String = "Karnal, Haryana",
    onLocationSelected: (String) -> Unit = {},
    onRefreshWeather: () -> Unit = {},
    onNavigate: (ScreenRoute) -> Unit,
    onEmergencyCall: () -> Unit
) {
    val isHi = currentLanguage == Language.HINDI
    var showLocationDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("home_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Header Card
        item {
            HeroHeaderCard(
                isHi = isHi,
                onAskAiClick = { onNavigate(ScreenRoute.AGRI_ASSISTANT) }
            )
        }

        // Weather & Agricultural Advisory Alert (OpenWeather Live Service)
        item {
            WeatherAdvisoryCard(
                weather = weatherInfo,
                isHi = isHi,
                isLoading = isWeatherLoading,
                onLocationClick = { showLocationDialog = true },
                onRefresh = onRefreshWeather
            )
        }

        // Emergency Helpline Card
        item {
            EmergencyHelplineCard(
                isHi = isHi,
                onCallClick = onEmergencyCall
            )
        }

        // Primary Services Section
        item {
            Text(
                text = if (isHi) "मुख्य सेवाएं व परामर्श" else "Key Farming Services",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Green900
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ServiceCard(
                    icon = Icons.Default.Eco,
                    iconBg = Green100,
                    iconTint = Green800,
                    title = if (isHi) "फसल परामर्श" else "Crop Consultation",
                    subtitle = if (isHi)
                        "बुवाई, फसल चक्र, बीज उपचार और सही पोषण की वैज्ञानिक सलाह।"
                    else
                        "Scientific advice on sowing dates, seed quality, spacing, and crop rotation.",
                    tag = "service_card_crop",
                    onClick = { onNavigate(ScreenRoute.CROP_CONSULTATION) }
                )

                ServiceCard(
                    icon = Icons.Default.WaterDrop,
                    iconBg = Color(0xFFE5F3FB),
                    iconTint = Color(0xFF1976D2),
                    title = if (isHi) "सिंचाई प्रबंधन" else "Irrigation Support",
                    subtitle = if (isHi)
                        "ड्रिप व स्प्रिंकलर प्रणाली, जल बजट कैलकुलेटर और जल बचत तकनीक।"
                    else
                        "Smart irrigation planner: Drip, sprinkler methods & water calculation.",
                    tag = "service_card_irrigation",
                    onClick = { onNavigate(ScreenRoute.IRRIGATION_SUPPORT) }
                )

                ServiceCard(
                    icon = Icons.Default.BugReport,
                    iconBg = Gold100,
                    iconTint = Brown800,
                    title = if (isHi) "मृदा व कीट संरक्षण" else "Soil & Pest Care",
                    subtitle = if (isHi)
                        "मृदा स्वास्थ्य कार्ड परीक्षण, जैविक कीट निवारक और सुरक्षित कीटनाशक निर्देश।"
                    else
                        "Soil testing analysis, biological pest control, and safe dosage advice.",
                    tag = "service_card_soil_pest",
                    onClick = { onNavigate(ScreenRoute.SOIL_PEST_CARE) }
                )

                ServiceCard(
                    icon = Icons.Default.AccountBalance,
                    iconBg = Color(0xFFEDE7F6),
                    iconTint = Color(0xFF512DA8),
                    title = if (isHi) "सरकारी योजनाएं" else "Government Schemes",
                    subtitle = if (isHi)
                        "पीएम-किसान, फसल बीमा, केसीसी ऋण और सब्सिडी की पूरी जानकारी।"
                    else
                        "Complete guide to PM-KISAN, PMFBY insurance, KCC loan, and subsidies.",
                    tag = "service_card_schemes",
                    onClick = { onNavigate(ScreenRoute.SCHEME_GUIDANCE) }
                )
            }
        }

        // Field-Ready Checklist Panel
        item {
            FieldReadyChecklistCard(isHi = isHi)
        }

        // Group 11 IDT Team Showcase
        item {
            Text(
                text = if (isHi) "ग्रुप 11 IDT" else "Group 11 IDT",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Green900
            )
            Text(
                text = if (isHi)
                    "प्रोजेक्ट समन्वयक, टीम लीड, एआई इंजीनियर एवं अनुसंधान टीम।"
                else
                    "Project Coordinator, Team Lead, AI Engineer & Research Team.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(KisaanRepository.experts) { expert ->
                    ExpertCard(expert = expert)
                }
            }
        }

        // Consultation Booking Call to Action
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Green800),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("request_consultation_cta_card")
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = if (isHi) "खेत की समस्या के लिए विशेषज्ञ से बात करें" else "Have a question about your field?",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = if (isHi)
                            "फसल की फोटो, लक्षण और खेत का विवरण भेजें। हमारे कृषि अधिकारी 24 घंटे में समाधान देंगे।"
                        else
                            "Share your location, crop, sowing date, and issue details. Our agricultural experts will respond with actionable recommendations.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { onNavigate(ScreenRoute.CONSULTATION_REQUEST) },
                        colors = ButtonDefaults.buttonColors(containerColor = Gold500),
                        shape = RoundedCornerShape(999.dp),
                        modifier = Modifier.testTag("book_consultation_button")
                    ) {
                        Text(
                            text = if (isHi) "परामर्श का अनुरोध करें" else "Request Consultation",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    if (showLocationDialog) {
        LocationPickerDialog(
            currentLocation = selectedLocation,
            onLocationSelected = {
                onLocationSelected(it)
                showLocationDialog = false
            },
            onDismiss = { showLocationDialog = false }
        )
    }
}

@Composable
fun HeroHeaderCard(
    isHi: Boolean,
    onAskAiClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.verticalGradient(
                    listOf(Green900, Green700)
                )
            )
    ) {
        Column(
            modifier = Modifier.padding(22.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Gold500),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🌾", fontSize = 24.sp)
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text(
                        text = if (isHi) "किसान साथी" else "Kisaan Sathi",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = if (isHi) "स्मार्ट खेती, खुशहाल किसान" else "Smart Farming Support & Advisory",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Gold100
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = if (isHi)
                    "फसल स्वास्थ्य, सटीक सिंचाई, मृदा जांच और सरकारी योजनाओं की विश्वसनीय जानकारी सीधे आपके हाथ में।"
                else
                    "Empowering farmers with smart crop advisory, precision irrigation, soil health diagnostics, and direct government scheme support.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.95f),
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onAskAiClick,
                colors = ButtonDefaults.buttonColors(containerColor = Gold500),
                shape = RoundedCornerShape(999.dp),
                modifier = Modifier.testTag("hero_ask_ai_button")
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isHi) "एआई कृषि सहायक से पूछें" else "Ask AI Agri Assistant",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun WeatherAdvisoryCard(
    weather: WeatherInfo?,
    isHi: Boolean,
    isLoading: Boolean,
    onLocationClick: () -> Unit,
    onRefresh: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE5F3FB)),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("weather_advisory_card")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // OpenWeather Service Branding & Last Updated
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(if (weather?.isLiveOpenWeather == true) Color(0xFF2E7D32) else Color(0xFF1976D2))
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = weather?.sourceLabel ?: "OpenWeather API Service",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F3E5D),
                        fontSize = 11.sp
                    )
                }

                Text(
                    text = "${if (isHi) "अपडेट" else "Updated"}: ${weather?.lastUpdated ?: "Live"}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF546E7A),
                    fontSize = 11.sp
                )
            }

            // Header: Location & Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(Color.White.copy(alpha = 0.7f))
                        .clickable { onLocationClick() }
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Farm Location",
                        tint = Color(0xFF154360),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = weather?.locationName ?: "Select District",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF154360)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = Color(0xFF154360)
                        )
                    } else {
                        IconButton(
                            onClick = onRefresh,
                            modifier = Modifier.size(28.dp).testTag("refresh_weather_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Refresh Weather",
                                tint = Color(0xFF154360),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Main Weather Reading (Temp + Condition)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "${weather?.temperatureC ?: 26}°C",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF0D47A1),
                            fontSize = 36.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${if (isHi) "अनुभव" else "Feels"}: ${weather?.feelsLikeC ?: 27}°C",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF2874A6),
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }
                    Text(
                        text = weather?.conditionDescription ?: "Clear sky with abundant sunshine",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF154360)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.8f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (weather?.isRainExpected == true) Icons.Default.CloudQueue else Icons.Default.WbSunny,
                        contentDescription = "Weather Icon",
                        tint = if (weather?.isRainExpected == true) Color(0xFF1565C0) else Color(0xFFF57F17),
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 3-Stat Metrics Row: Humidity, Wind, Pressure
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatPill(
                    icon = Icons.Default.WaterDrop,
                    label = if (isHi) "नमी" else "Humidity",
                    value = "${weather?.humidityPercent ?: 54}%",
                    modifier = Modifier.weight(1f)
                )
                StatPill(
                    icon = Icons.Default.Air,
                    label = if (isHi) "हवा गति" else "Wind",
                    value = "${weather?.windSpeedKmh ?: 11.2} km/h",
                    modifier = Modifier.weight(1f)
                )
                StatPill(
                    icon = Icons.Default.Speed,
                    label = if (isHi) "दबाव" else "Pressure",
                    value = "${weather?.pressureHpa ?: 1013} hPa",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Spray Window Status Banner
            val spray = weather?.sprayStatus ?: SprayWindowStatus.SAFE
            val sprayBg = when (spray) {
                SprayWindowStatus.SAFE -> Color(0xFFE8F5E9)
                SprayWindowStatus.CAUTION -> Color(0xFFFFF8E1)
                SprayWindowStatus.UNSAFE -> Color(0xFFFFEBEE)
            }
            val sprayTextColor = when (spray) {
                SprayWindowStatus.SAFE -> Color(0xFF2E7D32)
                SprayWindowStatus.CAUTION -> Color(0xFFF57F17)
                SprayWindowStatus.UNSAFE -> Color(0xFFC62828)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(sprayBg)
                    .padding(10.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(sprayTextColor)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${if (isHi) "दवा छिड़काव स्थिति" else "Field Spray Window"}: ${if (isHi) spray.labelHi else spray.labelEn}",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = sprayTextColor
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = if (isHi) spray.advisoryHi else spray.advisoryEn,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF37474F),
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Irrigation Scheduling Impact
            Row(verticalAlignment = Alignment.Top) {
                Text(
                    text = "💧 ",
                    fontSize = 14.sp
                )
                Text(
                    text = "${if (isHi) "सिंचाई योजना" else "Irrigation Advisory"}: ${if (isHi) (weather?.irrigationAdvisoryHi ?: "") else (weather?.irrigationAdvisoryEn ?: "")}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF154360),
                    lineHeight = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Field Work & Tillage Advisory
            Row(verticalAlignment = Alignment.Top) {
                Text(
                    text = "🚜 ",
                    fontSize = 14.sp
                )
                Text(
                    text = "${if (isHi) "खेत कार्य सलाह" else "Field Work Advisory"}: ${if (isHi) (weather?.fieldWorkAdvisoryHi ?: "") else (weather?.fieldWorkAdvisoryEn ?: "")}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF154360),
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
fun StatPill(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.6f))
            .padding(vertical = 8.dp, horizontal = 6.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF2874A6),
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF2874A6),
                    fontSize = 10.sp
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D47A1)
            )
        }
    }
}

@Composable
fun LocationPickerDialog(
    currentLocation: String,
    onLocationSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredDistricts = remember(searchQuery) {
        if (searchQuery.isBlank()) WeatherService.agriculturalDistricts
        else WeatherService.agriculturalDistricts.filter { it.contains(searchQuery, ignoreCase = true) }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Select Farm District / जिला चुनें",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search or type any district / village...") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                )

                if (searchQuery.isNotBlank() && filteredDistricts.none { it.equals(searchQuery.trim(), ignoreCase = true) }) {
                    Button(
                        onClick = {
                            onLocationSelected(searchQuery.trim())
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Green800)
                    ) {
                        Text("Use \"${searchQuery.trim()}\" for Weather")
                    }
                }

                Text(
                    text = "Agricultural District Hubs:",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 6.dp)
                )

                LazyColumn(modifier = Modifier.height(240.dp)) {
                    items(filteredDistricts) { district ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onLocationSelected(district) }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = currentLocation == district,
                                onClick = { onLocationSelected(district) }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = district,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = if (currentLocation == district) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close / बंद करें")
            }
        },
        shape = RoundedCornerShape(18.dp)
    )
}


@Composable
fun EmergencyHelplineCard(
    isHi: Boolean,
    onCallClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("emergency_helpline_card")
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE53935).copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Emergency",
                    tint = Color(0xFFD32F2F),
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (isHi) "किसान कॉल सेंटर (निःशुल्क)" else "Kisan Call Center (Toll Free)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFB71C1C)
                )
                Text(
                    text = "1800-180-1551 (6:00 AM - 10:00 PM)",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFC62828)
                )
            }
            Button(
                onClick = onCallClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                shape = RoundedCornerShape(999.dp),
                modifier = Modifier.testTag("call_kcc_button")
            ) {
                Text(
                    text = if (isHi) "कॉल करें" else "Call",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ServiceCard(
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    title: String,
    subtitle: String,
    tag: String,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag(tag)
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconTint,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
fun FieldReadyChecklistCard(isHi: Boolean) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Green100.copy(alpha = 0.5f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = if (isHi) "खेत के लिए सरल चेकलिस्ट" else "Field-Ready Checklist",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Green900
            )
            Spacer(modifier = Modifier.height(10.dp))

            val items = if (isHi) listOf(
                "मिट्टी से शुरुआत करें: अंदाज के बजाय मिट्टी जांच (Soil Test) के आधार पर खाद चुनें।",
                "समझदारी से पानी दें: फसल की अवस्था अनुसार सिंचाई करें, जलभराव से बचें।",
                "नियमित निरीक्षण करें: पत्तियों पर धब्बे, कीट या पीलापन दिखने पर तुरंत जांच करें।"
            ) else listOf(
                "Start with the soil: Choose nutrients based on a soil test, not guesswork.",
                "Plan water wisely: Match watering to crop needs to avoid water wastage or stress.",
                "Check crops regularly: Spot unusual wilting, spots, or insects early before disease spreads."
            )

            items.forEach { tip ->
                Row(
                    modifier = Modifier.padding(vertical = 4.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Green700,
                        modifier = Modifier
                            .size(18.dp)
                            .padding(top = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = tip,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
fun ExpertCard(expert: com.example.kisaansathi.model.AgriExpert) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .width(220.dp)
            .testTag("expert_card_${expert.id}")
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Green100),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = expert.name,
                    tint = Green800,
                    modifier = Modifier.size(34.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = expert.name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = Green900
            )
            Text(
                text = expert.designation,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = expert.specialization,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                lineHeight = 14.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = expert.experience,
                style = MaterialTheme.typography.labelSmall,
                color = Gold500,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

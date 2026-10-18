package com.example.kisaansathi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Support
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import com.example.kisaansathi.ui.components.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.kisaansathi.model.ConsultationTicket
import com.example.kisaansathi.model.Language
import com.example.kisaansathi.ui.theme.Gold100
import com.example.kisaansathi.ui.theme.Gold500
import com.example.kisaansathi.ui.theme.Green700
import com.example.kisaansathi.ui.theme.Green800
import com.example.kisaansathi.ui.theme.Green900
import kotlinx.coroutines.launch

private class IntroPage(val icon: ImageVector, val titleEn: String, val titleHi: String, val bodyEn: String, val bodyHi: String)

private val introPages = listOf(
    IntroPage(
        Icons.Default.Agriculture,
        "Your farming companion", "आपका खेती का साथी",
        "Crop care, irrigation planning, soil health and government schemes, all in one place.",
        "फसल देखभाल, सिंचाई योजना, मिट्टी की सेहत और सरकारी योजनाएं, सब एक ही जगह।"
    ),
    IntroPage(
        Icons.Default.Cloud,
        "Weather that guides you", "मौसम जो राह दिखाए",
        "Live weather, spray windows and farm advisories. Your last reading stays available offline.",
        "लाइव मौसम, छिड़काव का सही समय और खेती सलाह। आख़िरी जानकारी ऑफ़लाइन भी उपलब्ध रहती है।"
    ),
    IntroPage(
        Icons.Default.AutoAwesome,
        "Ask in your voice", "अपनी आवाज़ में पूछें",
        "Speak your question to the AI assistant and listen to the answer in your language.",
        "एआई सहायक से बोलकर सवाल पूछें और जवाब अपनी भाषा में सुनें।"
    ),
    IntroPage(
        Icons.Default.Support,
        "Experts are one tap away", "विशेषज्ञ बस एक टैप दूर",
        "Send your problem with a photo to the Group 11 IDT team and track every request.",
        "फोटो के साथ अपनी समस्या ग्रुप 11 IDT टीम को भेजें और हर अनुरोध की स्थिति देखें।"
    )
)

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    currentLanguage: Language,
    onLanguageClick: () -> Unit,
    onFinish: () -> Unit,
    languageLabel: String = currentLanguage.nativeName
) {
    val isHi = currentLanguage == Language.HINDI
    val pagerState = rememberPagerState(pageCount = { introPages.size })
    val scope = rememberCoroutineScope()
    val isLast = pagerState.currentPage == introPages.lastIndex

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Green900, Green800, Green700)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = onLanguageClick) {
                    Text("🌐 $languageLabel", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(8.dp))
            androidx.compose.foundation.Image(
                painter = painterResource(com.example.R.drawable.kisaan_sathi_icon),
                contentDescription = "Kisaan Sathi",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(24.dp))
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = if (isHi) "किसान साथी" else "Kisaan Sathi",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
            Text(
                text = if (isHi) "स्मार्ट खेती, खुशहाल किसान" else "Smart Farming, Happy Farmers",
                style = MaterialTheme.typography.bodyMedium,
                color = Gold100
            )

            HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { index ->
                val page = introPages[index]
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(96.dp)
                            .background(Color.White.copy(alpha = 0.16f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(page.icon, contentDescription = null, tint = Gold500, modifier = Modifier.size(48.dp))
                    }
                    Spacer(Modifier.height(24.dp))
                    Text(
                        text = if (isHi) page.titleHi else page.titleEn,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = if (isHi) page.bodyHi else page.bodyEn,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.9f),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(introPages.size) { index ->
                    Box(
                        modifier = Modifier
                            .size(if (index == pagerState.currentPage) 24.dp else 8.dp, 8.dp)
                            .background(
                                if (index == pagerState.currentPage) Gold500 else Color.White.copy(alpha = 0.4f),
                                RoundedCornerShape(999.dp)
                            )
                    )
                }
            }
            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    if (isLast) onFinish() else scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Gold500),
                shape = RoundedCornerShape(999.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(
                    text = when {
                        isLast && isHi -> "शुरू करें"
                        isLast -> "Get started"
                        isHi -> "आगे"
                        else -> "Next"
                    },
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            if (!isLast) {
                TextButton(onClick = onFinish) {
                    Text(if (isHi) "छोड़ें" else "Skip", color = Color.White.copy(alpha = 0.85f))
                }
            }
        }
    }
}

@Composable
private fun ScreenHeader(title: String, subtitle: String) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Green800),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(18.dp)) {
            Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(Modifier.height(4.dp))
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = Gold100)
        }
    }
}

private class MspRow(val nameEn: String, val nameHi: String, val season: String, val price: Int)

// Cabinet-approved MSP, Rs per quintal, marketing season 2026-27.
private val mspRows = listOf(
    MspRow("Wheat", "गेहूं", "Rabi", 2585),
    MspRow("Barley", "जौ", "Rabi", 2150),
    MspRow("Gram (Chana)", "चना", "Rabi", 5875),
    MspRow("Lentil (Masur)", "मसूर", "Rabi", 7000),
    MspRow("Mustard", "सरसों", "Rabi", 6200),
    MspRow("Safflower", "कुसुम", "Rabi", 6540),
    MspRow("Paddy (Common)", "धान (सामान्य)", "Kharif", 2441),
    MspRow("Maize", "मक्का", "Kharif", 2410),
    MspRow("Cotton (Medium staple)", "कपास (मध्यम रेशा)", "Kharif", 8267),
    MspRow("Cotton (Long staple)", "कपास (लंबा रेशा)", "Kharif", 8667),
    MspRow("Tur (Arhar)", "तुअर (अरहर)", "Kharif", 8450),
    MspRow("Moong", "मूंग", "Kharif", 8780)
)

@Composable
fun MspPricesScreen(currentLanguage: Language) {
    val isHi = currentLanguage == Language.HINDI
    var query by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf<MspRow?>(null) }
    val rows = mspRows.filter {
        query.isBlank() || it.nameEn.contains(query, true) || it.nameHi.contains(query)
    }
    val qty = quantity.toDoubleOrNull()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            ScreenHeader(
                if (isHi) "न्यूनतम समर्थन मूल्य (MSP)" else "Minimum Support Prices (MSP)",
                if (isHi) "सरकार द्वारा घोषित 2026-27 के भाव, ₹ प्रति क्विंटल। ये बाज़ार भाव नहीं हैं।"
                else "Government-declared 2026-27 rates, Rs per quintal. These are not live market prices."
            )
        }
        item {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text(if (isHi) "फसल खोजें" else "Search crop") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            OutlinedTextField(
                value = quantity,
                onValueChange = { quantity = it },
                label = { Text(if (isHi) "अनुमानित उपज (क्विंटल) - कमाई देखने के लिए" else "Expected yield (quintal) to estimate earnings") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
        items(rows) { row ->
            Card(
                onClick = { selected = if (selected == row) null else row },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text(if (isHi) row.nameHi else row.nameEn, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                            Text(
                                if (row.season == "Rabi") (if (isHi) "रबी" else "Rabi") else (if (isHi) "खरीफ" else "Kharif"),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text("₹${"%,d".format(row.price)}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.ExtraBold, color = Green700)
                    }
                    if (qty != null && qty > 0) {
                        Spacer(Modifier.height(6.dp))
                        Text(
                            (if (isHi) "अनुमानित कमाई: ₹" else "Estimated earnings: ₹") + "%,.0f".format(row.price * qty),
                            style = MaterialTheme.typography.bodySmall,
                            color = Gold500,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
        item {
            Text(
                if (isHi) "ताज़ा मंडी भाव के लिए अपनी नज़दीकी मंडी या e-NAM से जांचें।"
                else "For today's mandi rates, check your nearest mandi or e-NAM.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private class CalendarStep(val monthEn: String, val monthHi: String, val taskEn: String, val taskHi: String)
private class CropCalendar(val nameEn: String, val nameHi: String, val steps: List<CalendarStep>)

private val cropCalendars = listOf(
    CropCalendar("Wheat", "गेहूं", listOf(
        CalendarStep("Late Oct – Nov", "अंत अक्टूबर – नवंबर", "Land preparation and sowing; treat seed before sowing.", "खेत की तैयारी व बुवाई; बुवाई से पहले बीज उपचार करें।"),
        CalendarStep("20-25 days after sowing", "बुवाई के 20-25 दिन बाद", "First irrigation at Crown Root Initiation (CRI) stage.", "ताज रूट (CRI) अवस्था पर पहली सिंचाई करें।"),
        CalendarStep("Dec – Jan", "दिसंबर – जनवरी", "Top-dress nitrogen, weed control, watch for Yellow Rust.", "यूरिया की टॉप ड्रेसिंग, खरपतवार नियंत्रण, पीले रतुआ पर नज़र रखें।"),
        CalendarStep("Feb – Mar", "फरवरी – मार्च", "Irrigate at flowering and grain filling; avoid water stress.", "फूल व दाना भरने पर सिंचाई करें; पानी की कमी न होने दें।"),
        CalendarStep("Apr", "अप्रैल", "Harvest when grains are hard and straw is golden.", "दाने सख्त और भूसा सुनहरा होने पर कटाई करें।")
    )),
    CropCalendar("Paddy", "धान", listOf(
        CalendarStep("May – Jun", "मई – जून", "Nursery sowing; prepare puddled field.", "नर्सरी डालें; खेत में कीचड़ (पडलिंग) तैयार करें।"),
        CalendarStep("Jun – Jul", "जून – जुलाई", "Transplant 25-30 day seedlings.", "25-30 दिन की पौध की रोपाई करें।"),
        CalendarStep("Jul – Aug", "जुलाई – अगस्त", "Weeding, nitrogen split doses, use Alternate Wetting & Drying.", "निराई, यूरिया की किश्तें, बारी-बारी से सिंचाई (AWD) अपनाएं।"),
        CalendarStep("Sep", "सितंबर", "Watch for blast and stem borer; keep thin water layer at flowering.", "ब्लास्ट व तना छेदक पर नज़र रखें; फूल अवस्था में हल्का पानी रखें।"),
        CalendarStep("Oct – Nov", "अक्टूबर – नवंबर", "Drain field 10 days before harvest; harvest at 80% golden grains.", "कटाई से 10 दिन पहले पानी निकालें; 80% दाने सुनहरे होने पर कटाई करें।")
    )),
    CropCalendar("Mustard", "सरसों", listOf(
        CalendarStep("Oct", "अक्टूबर", "Sow in rows with fine seedbed; thin plants at 15-20 days.", "बारीक तैयार खेत में कतारों में बुवाई; 15-20 दिन पर पौधे विरल करें।"),
        CalendarStep("Nov", "नवंबर", "First irrigation at 30-35 days; weed control.", "30-35 दिन पर पहली सिंचाई; खरपतवार नियंत्रण।"),
        CalendarStep("Dec – Jan", "दिसंबर – जनवरी", "Watch for aphids and frost; second irrigation at flowering.", "माहू व पाले पर नज़र रखें; फूल अवस्था में दूसरी सिंचाई।"),
        CalendarStep("Feb – Mar", "फरवरी – मार्च", "Harvest when 75% pods turn yellow-brown.", "75% फलियां पीली-भूरी होने पर कटाई करें।")
    )),
    CropCalendar("Cotton", "कपास", listOf(
        CalendarStep("Apr – May", "अप्रैल – मई", "Sow after pre-sowing irrigation; use treated seed.", "पलेवा के बाद उपचारित बीज से बुवाई करें।"),
        CalendarStep("Jun – Jul", "जून – जुलाई", "Thinning, weeding and first fertilizer dose.", "विरलीकरण, निराई व खाद की पहली मात्रा।"),
        CalendarStep("Aug – Sep", "अगस्त – सितंबर", "Scout weekly for Pink Bollworm and whitefly; install pheromone traps.", "गुलाबी सुंडी व सफेद मक्खी की साप्ताहिक जांच; फेरोमोन ट्रैप लगाएं।"),
        CalendarStep("Oct – Dec", "अक्टूबर – दिसंबर", "Pick mature bolls in 2-3 rounds; keep picked cotton dry.", "पके टिंडे 2-3 बार में चुनें; चुनी कपास सूखी रखें।")
    )),
    CropCalendar("Gram (Chana)", "चना", listOf(
        CalendarStep("Oct – Nov", "अक्टूबर – नवंबर", "Sow with Rhizobium-treated seed on well-drained soil.", "राइजोबियम उपचारित बीज से अच्छे जल निकास वाली भूमि में बुवाई करें।"),
        CalendarStep("Dec", "दिसंबर", "One light irrigation at branching if no rain; nip tops for branching.", "वर्षा न हो तो शाखा अवस्था पर एक हल्की सिंचाई; शाखाओं के लिए शीर्ष तोड़ें।"),
        CalendarStep("Jan – Feb", "जनवरी – फरवरी", "Monitor Pod Borer; use pheromone traps and neem spray.", "फली छेदक पर नज़र रखें; फेरोमोन ट्रैप व नीम स्प्रे अपनाएं।"),
        CalendarStep("Mar – Apr", "मार्च – अप्रैल", "Harvest when leaves dry and pods turn brown.", "पत्ते सूखने व फलियां भूरी होने पर कटाई करें।")
    ))
)

@Composable
fun CropCalendarScreen(currentLanguage: Language) {
    val isHi = currentLanguage == Language.HINDI
    var selectedIndex by remember { mutableStateOf(0) }
    val calendar = cropCalendars[selectedIndex]

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            ScreenHeader(
                if (isHi) "फसल कैलेंडर" else "Crop Calendar",
                if (isHi) "उत्तर भारत के सामान्य मौसम के अनुसार चरण-दर-चरण कार्य। अपने क्षेत्र के अनुसार तारीखें बदल सकती हैं।"
                else "Step-by-step tasks for typical North India seasons. Dates vary by region."
            )
        }
        item {
            androidx.compose.foundation.lazy.LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(cropCalendars.indices.toList()) { index ->
                    androidx.compose.material3.FilterChip(
                        selected = index == selectedIndex,
                        onClick = { selectedIndex = index },
                        label = { Text(if (isHi) cropCalendars[index].nameHi else cropCalendars[index].nameEn) }
                    )
                }
            }
        }
        items(calendar.steps) { step ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(Modifier.padding(16.dp)) {
                    Box(Modifier.width(4.dp).height(44.dp).background(Gold500, RoundedCornerShape(2.dp)))
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(if (isHi) step.monthHi else step.monthEn, fontWeight = FontWeight.Bold, color = Green700)
                        Spacer(Modifier.height(2.dp))
                        Text(if (isHi) step.taskHi else step.taskEn, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
        }
    }
}

@Composable
fun SoilHealthScreen(currentLanguage: Language) {
    val isHi = currentLanguage == Language.HINDI
    var ph by remember { mutableStateOf("") }
    var nitrogen by remember { mutableStateOf("") }
    var phosphorus by remember { mutableStateOf("") }
    var potassium by remember { mutableStateOf("") }
    var organic by remember { mutableStateOf("") }

    val advice = buildList {
        ph.toDoubleOrNull()?.let {
            add(
                when {
                    it < 6.0 -> if (isHi) "pH ${it}: मिट्टी अम्लीय है। चूना (लाइम) डालने की सलाह लें।" else "pH $it: acidic soil. Apply agricultural lime as advised locally."
                    it > 8.0 -> if (isHi) "pH ${it}: मिट्टी क्षारीय है। जिप्सम व जैविक खाद डालें।" else "pH $it: alkaline soil. Apply gypsum and organic manure."
                    else -> if (isHi) "pH ${it}: सही सीमा (6.0-8.0) में है।" else "pH $it is in the healthy range (6.0-8.0)."
                }
            )
        }
        nitrogen.toDoubleOrNull()?.let {
            add(
                when {
                    it < 280 -> if (isHi) "नाइट्रोजन कम (${it.toInt()} किग्रा/हे.): यूरिया बढ़ाएं, हरी खाद या दलहन फसल चक्र अपनाएं।" else "Nitrogen low (${it.toInt()} kg/ha): raise urea dose, use green manure or a pulse in rotation."
                    it <= 560 -> if (isHi) "नाइट्रोजन मध्यम है: सिफारिश की मात्रा रखें।" else "Nitrogen medium: keep the recommended dose."
                    else -> if (isHi) "नाइट्रोजन अधिक है: यूरिया कम करें।" else "Nitrogen high: reduce urea."
                }
            )
        }
        phosphorus.toDoubleOrNull()?.let {
            add(
                when {
                    it < 10 -> if (isHi) "फॉस्फोरस कम (${it.toInt()} किग्रा/हे.): DAP या SSP बढ़ाएं।" else "Phosphorus low (${it.toInt()} kg/ha): increase DAP or SSP."
                    it <= 25 -> if (isHi) "फॉस्फोरस मध्यम है: सिफारिश की मात्रा रखें।" else "Phosphorus medium: keep the recommended dose."
                    else -> if (isHi) "फॉस्फोरस पर्याप्त है: DAP कम करें।" else "Phosphorus high: cut back on DAP."
                }
            )
        }
        potassium.toDoubleOrNull()?.let {
            add(
                when {
                    it < 108 -> if (isHi) "पोटाश कम (${it.toInt()} किग्रा/हे.): MOP डालें।" else "Potassium low (${it.toInt()} kg/ha): apply MOP."
                    it <= 280 -> if (isHi) "पोटाश मध्यम है: सिफारिश की मात्रा रखें।" else "Potassium medium: keep the recommended dose."
                    else -> if (isHi) "पोटाश पर्याप्त है।" else "Potassium is sufficient."
                }
            )
        }
        organic.toDoubleOrNull()?.let {
            add(
                when {
                    it < 0.5 -> if (isHi) "जैविक कार्बन ${it}%: कम है। गोबर खाद/कम्पोस्ट डालें।" else "Organic carbon $it%: low. Add FYM or compost."
                    it <= 0.75 -> if (isHi) "जैविक कार्बन ${it}%: मध्यम है। कम्पोस्ट जारी रखें।" else "Organic carbon $it%: medium. Keep adding compost."
                    else -> if (isHi) "जैविक कार्बन ${it}%: अच्छा है।" else "Organic carbon $it%: good."
                }
            )
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            ScreenHeader(
                if (isHi) "मृदा स्वास्थ्य सहायक" else "Soil Health Helper",
                if (isHi) "अपने मृदा स्वास्थ्य कार्ड की वैल्यू भरें और सामान्य सलाह पाएं।"
                else "Enter the values from your Soil Health Card to get general guidance."
            )
        }
        item { SoilField(ph, { ph = it }, if (isHi) "pH" else "pH") }
        item { SoilField(nitrogen, { nitrogen = it }, if (isHi) "नाइट्रोजन (किग्रा/हे.)" else "Nitrogen (kg/ha)") }
        item { SoilField(phosphorus, { phosphorus = it }, if (isHi) "फॉस्फोरस (किग्रा/हे.)" else "Phosphorus (kg/ha)") }
        item { SoilField(potassium, { potassium = it }, if (isHi) "पोटाश (किग्रा/हे.)" else "Potassium (kg/ha)") }
        item { SoilField(organic, { organic = it }, if (isHi) "जैविक कार्बन (%)" else "Organic carbon (%)") }
        if (advice.isNotEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(if (isHi) "सलाह" else "Guidance", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        advice.forEach {
                            Text("• $it", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            }
        }
        item {
            Text(
                if (isHi) "यह सामान्य मार्गदर्शन है। खाद की सटीक मात्रा के लिए अपने कृषि विज्ञान केंद्र से सलाह लें।"
                else "This is general guidance. For exact fertiliser doses, consult your Krishi Vigyan Kendra.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SoilField(value: String, onChange: (String) -> Unit, label: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun MyRequestsScreen(
    currentLanguage: Language,
    tickets: List<ConsultationTicket>,
    onNewRequest: () -> Unit
) {
    val isHi = currentLanguage == Language.HINDI

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            ScreenHeader(
                if (isHi) "मेरे अनुरोध" else "My Requests",
                if (isHi) "आपके भेजे गए परामर्श अनुरोध।" else "Consultation requests you have sent."
            )
        }
        if (tickets.isEmpty()) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(if (isHi) "अभी कोई अनुरोध नहीं है।" else "No requests yet.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.height(12.dp))
                    Button(onClick = onNewRequest, colors = ButtonDefaults.buttonColors(containerColor = Green800)) {
                        Text(if (isHi) "नया अनुरोध भेजें" else "Send a request")
                    }
                }
            }
        }
        items(tickets) { ticket ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row {
                        Text(ticket.ticketId, fontWeight = FontWeight.ExtraBold, color = Green700, modifier = Modifier.weight(1f))
                        Text(ticket.status, style = MaterialTheme.typography.labelSmall, color = Gold500, fontWeight = FontWeight.Bold)
                    }
                    Text("${ticket.crop} • ${ticket.villageDistrict}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(ticket.problemDescription, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface, maxLines = 3)
                    Text(ticket.createdAt, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

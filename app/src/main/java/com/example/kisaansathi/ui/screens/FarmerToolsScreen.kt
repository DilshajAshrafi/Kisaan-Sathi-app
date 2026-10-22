package com.example.kisaansathi.ui.screens

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.example.kisaansathi.model.CropPlan
import com.example.kisaansathi.model.FarmDiaryEntry
import com.example.kisaansathi.model.FarmExpense
import com.example.kisaansathi.model.Language
import com.example.kisaansathi.model.MandiPrice
import com.example.kisaansathi.model.WeatherInfo
import com.example.kisaansathi.model.calculateFarmProfit
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

private val toolCrops = listOf("Wheat", "Paddy", "Mustard", "Cotton", "Maize", "Gram")

private fun todayIsoDate(): String =
    SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())

private fun isIsoDate(value: String): Boolean =
    runCatching {
        SimpleDateFormat("yyyy-MM-dd", Locale.US).apply { isLenient = false }
            .parse(value)?.let { SimpleDateFormat("yyyy-MM-dd", Locale.US).format(it) == value } == true
    }.getOrDefault(false)

@Composable
private fun ToolHeader(title: String, description: String) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(18.dp)) {
            androidx.compose.material3.Text(
                title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(Modifier.height(5.dp))
            androidx.compose.material3.Text(
                description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.88f)
            )
        }
    }
}

@Composable
private fun ToolCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp), content = content)
    }
}

@Composable
private fun CropPicker(selected: String, onSelected: (String) -> Unit) {
    androidx.compose.foundation.lazy.LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(toolCrops) { crop ->
            FilterChip(
                selected = selected == crop,
                onClick = { onSelected(crop) },
                label = { androidx.compose.material3.Text(crop) }
            )
        }
    }
}

@Composable
fun ExpenseTrackerScreen(
    currentLanguage: Language,
    entries: List<FarmExpense>,
    onSave: (FarmExpense) -> Unit,
    onDelete: (String) -> Unit
) {
    val isHi = currentLanguage == Language.HINDI
    var crop by remember { mutableStateOf(toolCrops.first()) }
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(todayIsoDate()) }
    var isIncome by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    val profit = calculateFarmProfit(entries)

    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            ToolHeader(
                if (isHi) "खेत का खर्च और मुनाफ़ा" else "Farm expense & profit tracker",
                if (isHi) "आमदनी और खर्च स्थानीय रूप से सहेजे जाते हैं।" else "Track costs and sales locally on this device."
            )
        }
        item {
            ToolCard {
                androidx.compose.material3.Text(
                    "${if (isHi) "आमदनी" else "Income"} ₹${"%.2f".format(profit.income)}  •  " +
                        "${if (isHi) "खर्च" else "Costs"} ₹${"%.2f".format(profit.expenses)}",
                    fontWeight = FontWeight.Bold
                )
                androidx.compose.material3.Text(
                    "${if (isHi) "शुद्ध" else "Net"}: ₹${"%.2f".format(profit.net)}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (profit.net >= 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                )
                profit.netByCrop.forEach { (cropName, cropNet) ->
                    androidx.compose.material3.Text(
                        "$cropName: ${if (isHi) "शुद्ध" else "net"} ₹${"%.2f".format(cropNet)}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
        item {
            ToolCard {
                androidx.compose.material3.Text(if (isHi) "फसल" else "Crop", fontWeight = FontWeight.SemiBold)
                CropPicker(crop, { crop = it })
                OutlinedTextField(
                    description,
                    { description = it },
                    label = { androidx.compose.material3.Text(if (isHi) "खर्च/बिक्री का विवरण" else "Cost or sale description") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    amount,
                    { amount = it },
                    label = { androidx.compose.material3.Text(if (isHi) "राशि (₹)" else "Amount (₹)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true
                )
                OutlinedTextField(
                    date,
                    { date = it },
                    label = { androidx.compose.material3.Text(if (isHi) "तारीख (YYYY-MM-DD)" else "Date (YYYY-MM-DD)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Switch(checked = isIncome, onCheckedChange = { isIncome = it })
                    androidx.compose.material3.Text(
                        if (isIncome) {
                            if (isHi) "बिक्री/आमदनी" else "Sale / income"
                        } else {
                            if (isHi) "खर्च" else "Expense"
                        }
                    )
                }
                error?.let { androidx.compose.material3.Text(it, color = MaterialTheme.colorScheme.error) }
                Button(
                    onClick = {
                        val parsed = amount.toDoubleOrNull()
                        if (description.isBlank() || parsed == null || parsed <= 0 || !isIsoDate(date)) {
                            error = if (isHi) "विवरण, सही राशि और तारीख भरें।" else "Enter a description, positive amount, and valid date."
                        } else {
                            onSave(FarmExpense(UUID.randomUUID().toString(), crop, description.trim(), parsed, isIncome, date))
                            description = ""
                            amount = ""
                            error = null
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { androidx.compose.material3.Text(if (isHi) "रिकॉर्ड जोड़ें" else "Add record") }
            }
        }
        if (entries.isEmpty()) {
            item { androidx.compose.material3.Text(if (isHi) "अभी कोई रिकॉर्ड नहीं है।" else "No records yet.") }
        }
        items(entries, key = { it.id }) { entry ->
            ToolCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        androidx.compose.material3.Text(entry.description, fontWeight = FontWeight.Bold)
                        androidx.compose.material3.Text("${entry.crop} • ${entry.date}", style = MaterialTheme.typography.bodySmall)
                    }
                    androidx.compose.material3.Text(
                        "${if (entry.isIncome) "+" else "−"}₹${"%.2f".format(entry.amount)}",
                        fontWeight = FontWeight.Bold
                    )
                    androidx.compose.material3.TextButton(onClick = { onDelete(entry.id) }) {
                        androidx.compose.material3.Text(if (isHi) "हटाएं" else "Delete")
                    }
                }
            }
        }
    }
}

@Composable
fun MandiPricesScreen(
    currentLanguage: Language,
    prices: List<MandiPrice>,
    isLoading: Boolean,
    error: String?,
    onSearch: (String, String, String) -> Unit
) {
    val isHi = currentLanguage == Language.HINDI
    var commodity by remember { mutableStateOf("Wheat") }
    var state by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("") }
    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            ToolHeader(
                if (isHi) "लाइव मंडी भाव" else "Live mandi prices",
                if (isHi) "राज्य, जिला और फसल से सरकारी AGMARKNET रिकॉर्ड खोजें।" else "Search official AGMARKNET records by state, district, and commodity."
            )
        }
        item {
            ToolCard {
                androidx.compose.material3.Text(
                    if (isHi) "केवल आधिकारिक लाइव रिकॉर्ड दिखाए जाते हैं; नमूना भाव नहीं।" else
                        "Only official live records are shown; sample prices are never presented as current.",
                    style = MaterialTheme.typography.bodySmall
                )
                OutlinedTextField(
                    commodity,
                    { commodity = it },
                    label = { androidx.compose.material3.Text(if (isHi) "फसल (अंग्रेज़ी नाम)" else "Commodity (English name)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    state,
                    { state = it },
                    label = { androidx.compose.material3.Text(if (isHi) "राज्य" else "State") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    district,
                    { district = it },
                    label = { androidx.compose.material3.Text(if (isHi) "जिला (वैकल्पिक)" else "District (optional)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Button(
                    onClick = { onSearch(commodity, state, district) },
                    enabled = !isLoading && commodity.isNotBlank() && state.isNotBlank(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    androidx.compose.material3.Text(if (isLoading) "Loading…" else if (isHi) "सरकारी भाव खोजें" else "Fetch official prices")
                }
                error?.let { androidx.compose.material3.Text(it, color = MaterialTheme.colorScheme.error) }
            }
        }
        items(prices) { price ->
            ToolCard {
                androidx.compose.material3.Text(
                    "${price.commodity} • ${price.market}, ${price.district}, ${price.state}",
                    fontWeight = FontWeight.Bold
                )
                androidx.compose.material3.Text("${if (isHi) "तारीख" else "Date"}: ${price.arrivalDate}")
                androidx.compose.material3.Text(
                    "${if (isHi) "न्यूनतम" else "Min"} ₹${price.minimumPrice}  •  " +
                        "${if (isHi) "अधिकतम" else "Max"} ₹${price.maximumPrice}  •  " +
                    "${if (isHi) "मॉडल" else "Modal"} ₹${price.modalPrice} / ${price.priceUnit}"
                )
                androidx.compose.material3.Text("Source: data.gov.in • AGMARKNET", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Composable
fun WeatherAlertsScreen(
    currentLanguage: Language,
    weather: WeatherInfo?,
    isLoading: Boolean,
    onRefresh: () -> Unit
) {
    val isHi = currentLanguage == Language.HINDI
    val alerts = buildList {
        if (weather != null) {
            if (weather.temperatureC >= 38) {
                add(if (isHi) "तेज़ गर्मी: सिंचाई और खेत में काम का समय स्थानीय सलाह के अनुसार रखें।" else "High heat: plan irrigation and outdoor work carefully; follow local advice.")
            }
            if (weather.windSpeedKmh >= 30) {
                add(if (isHi) "तेज़ हवा: छिड़काव टालें और हल्की फसलों/ढांचों की जांच करें।" else "Strong wind: postpone spraying and check vulnerable crops and structures.")
            }
            if (weather.isRainExpected) {
                add(if (isHi) "बारिश की संभावना: जलभराव रोकें और सिंचाई से पहले खेत की स्थिति देखें।" else "Rain is indicated: check drainage and avoid unnecessary irrigation.")
            }
            if (weather.humidityPercent >= 85) {
                add(if (isHi) "नमी अधिक है: फफूंद के लक्षण देखें और खेत का निरीक्षण करें।" else "High humidity: scout for fungal symptoms and inspect crops.")
            }
        }
    }
    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            ToolHeader(
                if (isHi) "मौसम और खेत अलर्ट" else "Weather & field alerts",
                if (isHi) "यह उपलब्ध मौसम रीडिंग पर आधारित है, पूर्वानुमान नहीं।" else "Alerts use the latest available reading, not a forecast."
            )
        }
        item {
            ToolCard {
                androidx.compose.material3.Text(
                    weather?.let { "${it.locationName} • ${it.temperatureC}°C • ${it.lastUpdated} • ${it.sourceLabel}" }
                        ?: if (isLoading) "Loading current weather…" else "Weather is unavailable for this location."
                )
                OutlinedButton(onClick = onRefresh, modifier = Modifier.fillMaxWidth()) {
                    androidx.compose.material3.Text(if (isHi) "मौसम ताज़ा करें" else "Refresh weather")
                }
            }
        }
        if (alerts.isEmpty()) {
            item {
                ToolCard {
                    androidx.compose.material3.Text(
                        if (weather == null) {
                            if (isHi) "अभी अलर्ट बनाने के लिए मौसम डेटा उपलब्ध नहीं है।" else "No weather reading is available to assess alerts."
                        } else {
                            if (isHi) "मौजूदा रीडिंग पर कोई विशेष अलर्ट नहीं।" else "No threshold-based alerts for the current reading."
                        }
                    )
                }
            }
        } else {
            items(alerts) { alert ->
                ToolCard { androidx.compose.material3.Text("⚠ $alert") }
            }
        }
    }
}

@Composable
fun FarmDiaryScreen(
    currentLanguage: Language,
    entries: List<FarmDiaryEntry>,
    onSave: (FarmDiaryEntry) -> Unit,
    onDelete: (String) -> Unit
) {
    val isHi = currentLanguage == Language.HINDI
    val context = LocalContext.current
    var crop by remember { mutableStateOf(toolCrops.first()) }
    var note by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(todayIsoDate()) }
    var photoUri by remember { mutableStateOf<String?>(null) }
    var error by remember { mutableStateOf<String?>(null) }
    val photoPicker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri != null) {
            try {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                photoUri = uri.toString()
                error = null
            } catch (exception: SecurityException) {
                error = exception.localizedMessage ?: "Photo access was not granted."
            }
        }
    }
    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            ToolHeader(
                if (isHi) "खेत डायरी" else "Farm diary",
                if (isHi) "फसल की प्रगति, नोट्स और तस्वीरें इस डिवाइस पर सहेजें।" else "Keep dated crop notes and photos on this device."
            )
        }
        item {
            ToolCard {
                CropPicker(crop, { crop = it })
                OutlinedTextField(
                    date,
                    { date = it },
                    label = { androidx.compose.material3.Text("Date (YYYY-MM-DD)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    note,
                    { note = it },
                    label = { androidx.compose.material3.Text(if (isHi) "निरीक्षण / खेत का नोट" else "Observation / field note") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
                OutlinedButton(
                    onClick = { photoPicker.launch(arrayOf("image/*")) },
                    modifier = Modifier.fillMaxWidth()
                ) { androidx.compose.material3.Text(if (isHi) "तस्वीर जोड़ें" else "Attach a photo") }
                photoUri?.let {
                    AsyncImage(model = Uri.parse(it), contentDescription = "Selected field photo", modifier = Modifier.fillMaxWidth().height(180.dp))
                }
                error?.let { androidx.compose.material3.Text(it, color = MaterialTheme.colorScheme.error) }
                Button(
                    onClick = {
                        if (note.isBlank() || !isIsoDate(date)) {
                            error = if (isHi) "नोट और सही तारीख भरें।" else "Enter a note and valid date."
                        } else {
                            onSave(FarmDiaryEntry(UUID.randomUUID().toString(), crop, note.trim(), date, photoUri))
                            note = ""
                            photoUri = null
                            error = null
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { androidx.compose.material3.Text(if (isHi) "डायरी में जोड़ें" else "Save diary entry") }
            }
        }
        if (entries.isEmpty()) {
            item { androidx.compose.material3.Text(if (isHi) "अभी कोई डायरी नोट नहीं है।" else "No diary entries yet.") }
        }
        items(entries, key = { it.id }) { entry ->
            ToolCard {
                androidx.compose.material3.Text("${entry.crop} • ${entry.date}", fontWeight = FontWeight.Bold)
                androidx.compose.material3.Text(entry.note)
                entry.photoUri?.let {
                    AsyncImage(model = Uri.parse(it), contentDescription = "Farm diary photo", modifier = Modifier.fillMaxWidth().height(180.dp))
                }
                androidx.compose.material3.TextButton(onClick = { onDelete(entry.id) }) {
                    androidx.compose.material3.Text(if (isHi) "हटाएं" else "Delete")
                }
            }
        }
    }
}

@Composable
fun SchemeEligibilityScreen(currentLanguage: Language) {
    val isHi = currentLanguage == Language.HINDI
    val context = LocalContext.current
    var ownsLand by remember { mutableStateOf(false) }
    var growsCrops by remember { mutableStateOf(false) }
    var wantsIrrigation by remember { mutableStateOf(false) }
    var wantsInsurance by remember { mutableStateOf(false) }
    val matches = buildList {
        if (ownsLand) add("PM-KISAN" to "https://pmkisan.gov.in/")
        if (growsCrops) add("Kisan Credit Card (KCC)" to "https://www.myscheme.gov.in/schemes/kcc")
        if (growsCrops && wantsInsurance) add("PM Fasal Bima Yojana" to "https://pmfby.gov.in/")
        if (wantsIrrigation) add("PM Krishi Sinchayee Yojana" to "https://pmksy.gov.in/")
        add("Soil Health Card" to "https://soilhealth.dac.gov.in/")
    }
    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            ToolHeader(
                if (isHi) "योजना पात्रता सहायक" else "Scheme eligibility helper",
                if (isHi) "आपके जवाब से संभावित योजनाएं चुनें। अंतिम पात्रता सरकारी नियम तय करते हैं।" else "Find schemes that may fit your answers. Official rules determine final eligibility."
            )
        }
        item {
            ToolCard {
                EligibilityChoice(if (isHi) "क्या आप खेती की जमीन के मालिक हैं?" else "Do you own agricultural land?", ownsLand) { ownsLand = it }
                EligibilityChoice(if (isHi) "क्या आप फसल उगाते हैं?" else "Do you grow crops?", growsCrops) { growsCrops = it }
                EligibilityChoice(if (isHi) "क्या सिंचाई सहायता चाहिए?" else "Are you seeking irrigation support?", wantsIrrigation) { wantsIrrigation = it }
                EligibilityChoice(if (isHi) "क्या फसल बीमा देखना चाहते हैं?" else "Are you interested in crop insurance?", wantsInsurance) { wantsInsurance = it }
            }
        }
        item { androidx.compose.material3.Text(if (isHi) "जांचने योग्य योजनाएं" else "Schemes to check", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold) }
        items(matches) { (scheme, url) ->
            ToolCard {
                androidx.compose.material3.Text(scheme, fontWeight = FontWeight.Bold)
                androidx.compose.material3.Text(
                    if (isHi) "संभावित मिलान — आवेदन से पहले आधिकारिक पात्रता और दस्तावेज जांचें।"
                    else "Potential match — verify current eligibility and required documents on the official portal."
                )
                OutlinedButton(
                    onClick = { openOfficialPortal(context, url) },
                    modifier = Modifier.fillMaxWidth()
                ) { androidx.compose.material3.Text(if (isHi) "आधिकारिक पोर्टल खोलें" else "Open official portal") }
            }
        }
    }
}

@Composable
private fun EligibilityChoice(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
        androidx.compose.material3.Text(label)
    }
}

private fun openOfficialPortal(context: Context, url: String) {
    try {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    } catch (_: ActivityNotFoundException) {
        Toast.makeText(context, "No browser is available to open this official portal.", Toast.LENGTH_LONG).show()
    }
}

@Composable
fun CropPhotoGuideScreen(
    currentLanguage: Language,
    onRequestExpertReview: (String?, String, String) -> Unit
) {
    val isHi = currentLanguage == Language.HINDI
    val context = LocalContext.current
    var crop by remember { mutableStateOf(toolCrops.first()) }
    var selectedSymptoms by remember { mutableStateOf(setOf<String>()) }
    var photoUri by remember { mutableStateOf<String?>(null) }
    var photoError by remember { mutableStateOf<String?>(null) }
    val photoPicker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri != null) {
            try {
                context.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                photoUri = uri.toString()
                photoError = null
            } catch (exception: SecurityException) {
                photoUri = null
                photoError = exception.localizedMessage ?: "Photo access was not granted."
            }
        }
    }
    val symptoms = listOf(
        "Yellowing / spots" to "Yellowing and leaf spots can have several causes; check drainage and compare nearby plants.",
        "Wilting" to "Check soil moisture and roots. Wilting may be caused by water stress, root damage, or disease.",
        "Holes / chewed leaves" to "Inspect both sides of leaves and look for larvae or fresh feeding before choosing control.",
        "White or orange powder" to "Do not handle heavily affected plants; ask a local KVK to confirm a possible fungal issue.",
        "Damaged fruit / pods" to "Inspect the whole plant for pests and record affected parts before seeking advice."
    )
    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            ToolHeader(
                if (isHi) "फसल फोटो और लक्षण सहायक" else "Crop photo & symptom guide",
                if (isHi) "तस्वीर को यहां AI द्वारा जांचा नहीं जाता। लक्षणों से सामान्य सुरक्षित कदम देखें।" else
                    "Photos are not AI-diagnosed here. Use symptoms for general, safer next steps."
            )
        }
        item {
            ToolCard {
                CropPicker(crop, { crop = it })
                OutlinedButton(onClick = { photoPicker.launch(arrayOf("image/*")) }, modifier = Modifier.fillMaxWidth()) {
                    androidx.compose.material3.Text(if (isHi) "फसल की तस्वीर चुनें" else "Choose crop photo")
                }
                photoError?.let { androidx.compose.material3.Text(it, color = MaterialTheme.colorScheme.error) }
                photoUri?.let { AsyncImage(Uri.parse(it), contentDescription = "Selected crop photo", modifier = Modifier.fillMaxWidth().height(200.dp)) }
                androidx.compose.material3.Text(if (isHi) "दिख रहे लक्षण चुनें:" else "Select visible symptoms:")
                symptoms.forEach { (label, _) ->
                    FilterChip(
                        selected = label in selectedSymptoms,
                        onClick = {
                            selectedSymptoms = if (label in selectedSymptoms) selectedSymptoms - label else selectedSymptoms + label
                        },
                        label = { androidx.compose.material3.Text(label) }
                    )
                }
            }
        }
        if (selectedSymptoms.isNotEmpty()) {
            item {
                ToolCard {
                    androidx.compose.material3.Text(if (isHi) "संभावित कारण और सुरक्षित कदम" else "Possibilities & safer next steps", fontWeight = FontWeight.Bold)
                    symptoms.filter { it.first in selectedSymptoms }.forEach { (_, guidance) ->
                        androidx.compose.material3.Text("• $guidance")
                    }
                    androidx.compose.material3.Text(
                        if (isHi) "यह निदान नहीं है। दवा डालने से पहले स्थानीय कृषि विशेषज्ञ से पुष्टि करें।"
                        else "This is not a diagnosis. Confirm with a local agricultural expert before applying treatments.",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
        item {
            Button(
                onClick = {
                    onRequestExpertReview(
                        photoUri,
                        crop,
                        selectedSymptoms.joinToString(", ").ifBlank {
                            if (isHi) "तस्वीर जोड़ी गई; लक्षण अभी नहीं चुने।" else "Photo attached; no symptoms selected yet."
                        }
                    )
                },
                enabled = photoUri != null,
                modifier = Modifier.fillMaxWidth()
            ) {
                androidx.compose.material3.Text(if (isHi) "फोटो विशेषज्ञ को भेजें" else "Send photo for expert review")
            }
        }
    }
}

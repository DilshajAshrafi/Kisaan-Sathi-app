package com.example.kisaansathi

import android.content.Intent
import android.content.ActivityNotFoundException
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.kisaansathi.model.ScreenRoute
import com.example.kisaansathi.ui.components.KisaanBottomBar
import com.example.kisaansathi.ui.components.KisaanTopBar
import com.example.kisaansathi.ui.components.LanguageDialog
import com.example.kisaansathi.ui.screens.AgriAssistantScreen
import com.example.kisaansathi.ui.screens.ConsultationRequestScreen
import com.example.kisaansathi.ui.screens.CropConsultationScreen
import com.example.kisaansathi.ui.screens.HomeScreen
import com.example.kisaansathi.ui.screens.IrrigationSupportScreen
import com.example.kisaansathi.ui.screens.RequestSentScreen
import com.example.kisaansathi.ui.screens.SchemeGuidanceScreen
import com.example.kisaansathi.ui.screens.SoilPestCareScreen
import com.example.kisaansathi.ui.theme.KisaanSathiTheme
import com.example.kisaansathi.viewmodel.KisaanViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: KisaanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModel.setDarkMode(
            getPreferences(MODE_PRIVATE).getBoolean(PREFERENCE_DARK_MODE, false)
        )

        setContent {
            val uiState by viewModel.uiState.collectAsState()
            var showLanguageDialog by remember { mutableStateOf(false) }

            // Handle back navigation
            BackHandler(enabled = uiState.currentRoute != ScreenRoute.HOME) {
                viewModel.navigateTo(ScreenRoute.HOME)
            }

            KisaanSathiTheme(darkTheme = uiState.isDarkMode) {
                val view = LocalView.current
                SideEffect {
                    val controller = WindowCompat.getInsetsController(window, view)
                    controller.isAppearanceLightStatusBars = false
                    controller.isAppearanceLightNavigationBars = !uiState.isDarkMode
                }
                Scaffold(
                    containerColor = MaterialTheme.colorScheme.background,
                    topBar = {
                        KisaanTopBar(
                            currentRoute = uiState.currentRoute,
                            currentLanguage = uiState.selectedLanguage,
                            isDarkMode = uiState.isDarkMode,
                            onBackClick = { viewModel.navigateTo(ScreenRoute.HOME) },
                            onLanguageClick = { showLanguageDialog = true },
                            onThemeToggle = {
                                val isDarkMode = !uiState.isDarkMode
                                viewModel.setDarkMode(isDarkMode)
                                getPreferences(MODE_PRIVATE).edit()
                                    .putBoolean(PREFERENCE_DARK_MODE, isDarkMode)
                                    .apply()
                            },
                            onEmergencyCallClick = { dialTeamContact() }
                        )
                    },
                    bottomBar = {
                        KisaanBottomBar(
                            currentRoute = uiState.currentRoute,
                            currentLanguage = uiState.selectedLanguage,
                            onNavigate = { route -> viewModel.navigateTo(route) }
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(innerPadding)
                    ) {
                        when (uiState.currentRoute) {
                            ScreenRoute.HOME -> HomeScreen(
                                currentLanguage = uiState.selectedLanguage,
                                weatherInfo = uiState.weatherInfo,
                                isWeatherLoading = uiState.isWeatherLoading,
                                selectedLocation = uiState.selectedWeatherLocation,
                                onLocationSelected = { viewModel.fetchWeather(it) },
                                onRefreshWeather = { viewModel.refreshWeather() },
                                onNavigate = { route -> viewModel.navigateTo(route) },
                                onEmergencyCall = { dialTeamContact() },
                                onContactPhone = { openTeamContact("tel:+918081392456") },
                                onContactEmail = { openTeamContact("mailto:group11idt@outlook.com") },
                                onOpenWebsite = { openTeamContact("https://kissansathiindia.vercel.app/") }
                            )
                            ScreenRoute.CROP_CONSULTATION -> CropConsultationScreen(
                                currentLanguage = uiState.selectedLanguage,
                                onNavigate = { route -> viewModel.navigateTo(route) }
                            )
                            ScreenRoute.IRRIGATION_SUPPORT -> IrrigationSupportScreen(
                                currentLanguage = uiState.selectedLanguage,
                                selectedCrop = uiState.irrigationCrop,
                                selectedSoil = uiState.irrigationSoil,
                                areaText = uiState.irrigationAreaText,
                                irrigationAdvice = uiState.irrigationAdvice,
                                weatherInfo = uiState.weatherInfo,
                                onCropChange = { viewModel.setIrrigationCrop(it) },
                                onSoilChange = { viewModel.setIrrigationSoil(it) },
                                onAreaChange = { viewModel.setIrrigationArea(it) },
                                onCalculate = { viewModel.calculateIrrigation() }
                            )
                            ScreenRoute.SOIL_PEST_CARE -> SoilPestCareScreen(
                                currentLanguage = uiState.selectedLanguage
                            )
                            ScreenRoute.SCHEME_GUIDANCE -> SchemeGuidanceScreen(
                                currentLanguage = uiState.selectedLanguage,
                                searchQuery = uiState.schemesSearchQuery,
                                onSearchQueryChange = { viewModel.setSchemesSearchQuery(it) }
                            )
                            ScreenRoute.AGRI_ASSISTANT -> AgriAssistantScreen(
                                currentLanguage = uiState.selectedLanguage,
                                chatMessages = uiState.chatMessages,
                                isLoading = uiState.isChatLoading,
                                onSendMessage = { text -> viewModel.sendChatMessage(text) }
                            )
                            ScreenRoute.CONSULTATION_REQUEST -> ConsultationRequestScreen(
                                currentLanguage = uiState.selectedLanguage,
                                isSubmitting = uiState.isSubmittingConsultation,
                                submissionError = uiState.consultationSubmissionError,
                                onSubmit = { name, email, phone, aid, loc, crop, desc, file ->
                                    viewModel.submitConsultationRequest(name, email, phone, aid, loc, crop, desc, file)
                                }
                            )
                            ScreenRoute.REQUEST_SENT -> RequestSentScreen(
                                currentLanguage = uiState.selectedLanguage,
                                ticket = uiState.currentTicket,
                                onBackHome = { viewModel.navigateTo(ScreenRoute.HOME) },
                                onCallHelpline = { dialTeamContact() }
                            )
                        }

                        if (showLanguageDialog) {
                            LanguageDialog(
                                currentLanguage = uiState.selectedLanguage,
                                onLanguageSelected = { lang -> viewModel.setLanguage(lang) },
                                onDismiss = { showLanguageDialog = false }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun dialTeamContact() {
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:+918081392456")
            }
            startActivity(intent)
        } catch (_: Exception) {
            // Gracefully ignore if device doesn't support dialer intent
        }
    }

    private fun openTeamContact(uri: String) {
        try {
            val action = when {
                uri.startsWith("tel:") -> Intent.ACTION_DIAL
                uri.startsWith("mailto:") -> Intent.ACTION_SENDTO
                else -> Intent.ACTION_VIEW
            }
            startActivity(Intent(action, Uri.parse(uri)))
        } catch (_: ActivityNotFoundException) {
            Toast.makeText(this, "No app is available to open this contact.", Toast.LENGTH_LONG).show()
        }
    }

    private companion object {
        const val PREFERENCE_DARK_MODE = "dark_mode"
    }
}

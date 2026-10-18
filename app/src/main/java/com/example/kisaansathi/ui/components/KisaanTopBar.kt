package com.example.kisaansathi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kisaansathi.model.Language
import com.example.kisaansathi.model.ScreenRoute
import com.example.kisaansathi.ui.theme.Gold500
import com.example.kisaansathi.ui.theme.Green800
import com.example.kisaansathi.ui.theme.Green900

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KisaanTopBar(
    currentRoute: ScreenRoute,
    currentLanguage: Language,
    isDarkMode: Boolean,
    onBackClick: () -> Unit,
    onLanguageClick: () -> Unit,
    onThemeToggle: () -> Unit,
    onEmergencyCallClick: () -> Unit
) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            Brush.linearGradient(listOf(Gold500, Green800))
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Spa,
                        contentDescription = "Kisaan Sathi Emblem",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = if (currentLanguage == Language.HINDI) "किसान साथी" else "Kisaan Sathi",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.5.sp
                    ),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        navigationIcon = {
            if (currentRoute != ScreenRoute.HOME) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.testTag("top_bar_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        actions = {
            // Language selector button with current language label
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(Color.White.copy(alpha = 0.22f))
                    .clickable { onLanguageClick() }
                    .padding(horizontal = 8.dp, vertical = 6.dp)
                    .testTag("top_bar_language_button"),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Language,
                    contentDescription = "Select Language",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = currentLanguage.nativeName,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 11.sp
                )
            }

            // Theme toggle button
            IconButton(
                onClick = onThemeToggle,
                modifier = Modifier.testTag("top_bar_theme_toggle_button")
            ) {
                Icon(
                    imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                    contentDescription = "Toggle Dark/Light Mode",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            // Emergency call button
            IconButton(
                onClick = onEmergencyCallClick,
                modifier = Modifier
                    .padding(end = 4.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE53935))
                    .size(36.dp)
                    .testTag("top_bar_emergency_call_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Emergency Kisan Call Center",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = if (isDarkMode) Green900 else Green800,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}

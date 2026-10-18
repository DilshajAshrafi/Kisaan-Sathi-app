package com.example.kisaansathi.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import com.example.kisaansathi.model.Language
import com.example.kisaansathi.model.ScreenRoute
import com.example.kisaansathi.ui.theme.Gold500
import com.example.kisaansathi.ui.theme.Green800

sealed class BottomNavItem(
    val route: ScreenRoute,
    val titleEn: String,
    val titleHi: String,
    val icon: ImageVector,
    val tag: String
) {
    data object Home : BottomNavItem(ScreenRoute.HOME, "Home", "होम", Icons.Default.Home, "nav_home")
    data object Crops : BottomNavItem(ScreenRoute.CROP_CONSULTATION, "Crops", "फसल", Icons.Default.Eco, "nav_crops")
    data object Irrigation : BottomNavItem(ScreenRoute.IRRIGATION_SUPPORT, "Irrigation", "सिंचाई", Icons.Default.WaterDrop, "nav_irrigation")
    data object SoilPest : BottomNavItem(ScreenRoute.SOIL_PEST_CARE, "Soil/Pest", "कीट/मिट्टी", Icons.Default.BugReport, "nav_soil_pest")
    data object Schemes : BottomNavItem(ScreenRoute.SCHEME_GUIDANCE, "Schemes", "योजनाएं", Icons.Default.AccountBalance, "nav_schemes")
    data object Assistant : BottomNavItem(ScreenRoute.AGRI_ASSISTANT, "Agri AI", "एआई सहायक", Icons.Default.AutoAwesome, "nav_assistant")
}

@Composable
fun KisaanBottomBar(
    currentRoute: ScreenRoute,
    currentLanguage: Language,
    onNavigate: (ScreenRoute) -> Unit
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Crops,
        BottomNavItem.Irrigation,
        BottomNavItem.SoilPest,
        BottomNavItem.Schemes,
        BottomNavItem.Assistant
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.route) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.titleEn
                    )
                },
                label = {
                    val labelText = when (item) {
                        BottomNavItem.Home -> com.example.kisaansathi.data.AgriLocale.getNavHome(currentLanguage)
                        BottomNavItem.Crops -> com.example.kisaansathi.data.AgriLocale.getNavCrops(currentLanguage)
                        BottomNavItem.Irrigation -> com.example.kisaansathi.data.AgriLocale.getNavIrrigation(currentLanguage)
                        BottomNavItem.SoilPest -> com.example.kisaansathi.data.AgriLocale.getNavSoilPest(currentLanguage)
                        BottomNavItem.Schemes -> com.example.kisaansathi.data.AgriLocale.getNavSchemes(currentLanguage)
                        BottomNavItem.Assistant -> com.example.kisaansathi.data.AgriLocale.getNavAssistant(currentLanguage)
                    }
                    Text(
                        text = labelText,
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                modifier = Modifier.testTag(item.tag),
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Green800,
                    selectedTextColor = Green800,
                    indicatorColor = Gold500.copy(alpha = 0.25f),
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

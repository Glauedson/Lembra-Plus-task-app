package com.progmobile.lembraplus.ui.components.NavBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController

data class NavBarIconState(
    val icon: ImageVector,
    val tint: Color,
    val isSelected: Boolean
)

data class NavProps(
    val navController: NavHostController,
    val currentScreen: String
)

@Composable
fun createNavBarProps(
    currentScreen: String,
    navController: NavHostController
): Pair<NavBarIconState, NavBarIconState> {
    val homeIconState = getIconState(
        screen = "home",
        currentScreen = currentScreen,
        defaultIcon = Icons.Default.Home,
        outlinedIcon = Icons.Outlined.Home,
        selectedColor = MaterialTheme.colorScheme.primary,
        unselectedColor = MaterialTheme.colorScheme.tertiary
    )

    val aboutIconState = getIconState(
        screen = "about",
        currentScreen = currentScreen,
        defaultIcon = Icons.Default.Info,
        outlinedIcon = Icons.Outlined.Info,
        selectedColor = MaterialTheme.colorScheme.primary,
        unselectedColor = MaterialTheme.colorScheme.tertiary
    )

    return Pair(homeIconState, aboutIconState)
}

@Composable
private fun getIconState(
    screen: String,
    currentScreen: String,
    defaultIcon: ImageVector,
    outlinedIcon: ImageVector,
    selectedColor: Color,
    unselectedColor: Color
): NavBarIconState {
    val isSelected = currentScreen.lowercase() == screen.lowercase()
    val isNewTaskScreen = currentScreen.lowercase() == "newtask"

    return NavBarIconState(
        icon = if (isSelected && !isNewTaskScreen) defaultIcon else outlinedIcon,
        tint = if (isSelected && !isNewTaskScreen) selectedColor else unselectedColor,
        isSelected = isSelected
    )
}
package com.progmobile.lembraplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.progmobile.lembraplus.ui.screens.AboutScreen
import com.progmobile.lembraplus.ui.theme.LembraPlusTheme
import com.progmobile.lembraplus.ui.screens.HomeScreen
import com.progmobile.lembraplus.utils.Routes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LembraPlusTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    val systemUiController = rememberSystemUiController()
    val navController = rememberNavController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = true
        )
    }

    Scaffold { pad ->
        NavHost(
            navController = navController,
            startDestination = Routes.Home.route,
            modifier = Modifier.padding(pad).background(color = MaterialTheme.colorScheme.background)
        ) {
            composable(Routes.Home.route) {
                HomeScreen(navController)
            }
            composable(Routes.About.route) {
                AboutScreen(navController)
            }
//            composable(Routes.CreateNote.route) {
//                CreateNewTaskScreen(navController)
//            }
        }

    }

}
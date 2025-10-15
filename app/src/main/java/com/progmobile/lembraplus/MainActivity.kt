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
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.progmobile.lembraplus.data.db.AppDatabase
import com.progmobile.lembraplus.data.repository.TaskRepository
import com.progmobile.lembraplus.ui.screens.AboutScreen
import com.progmobile.lembraplus.ui.screens.CreateNewTaskScreen
import com.progmobile.lembraplus.ui.theme.LembraPlusTheme
import com.progmobile.lembraplus.ui.screens.HomeScreen
import com.progmobile.lembraplus.ui.vms.TaskViewModel
import com.progmobile.lembraplus.ui.vms.TaskViewModelFactory
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

    val context = LocalContext.current
    val db = AppDatabase.getInstance(context)
    val taskRepository = TaskRepository(db.taskDao())
    val taskViewModelFactory = TaskViewModelFactory(taskRepository)

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
            composable(Routes.CreateNote.route) {
                CreateNewTaskScreen(
                    viewModel = viewModel(factory = taskViewModelFactory),
                    navController
                )
            }
        }

    }

}
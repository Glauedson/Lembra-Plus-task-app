package com.progmobile.lembraplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.progmobile.lembraplus.data.db.AppDatabase
import com.progmobile.lembraplus.data.repository.NoteRepository
import com.progmobile.lembraplus.ui.screens.AboutScreen
import com.progmobile.lembraplus.ui.screens.CreateNewNoteScreen
import com.progmobile.lembraplus.ui.screens.HomeScreen
import com.progmobile.lembraplus.ui.screens.SeeAllScreen
import com.progmobile.lembraplus.ui.theme.LembraPlusTheme
import com.progmobile.lembraplus.ui.vms.NoteViewModel
import com.progmobile.lembraplus.ui.vms.NoteViewModelFactory
import com.progmobile.lembraplus.utils.Routes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LembraPlusTheme {
                SetStatusBarColor(Color.White)

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val context = LocalContext.current
                    val noteDao = AppDatabase.getInstance(context).noteDao()
                    val noteRepository = remember { NoteRepository(noteDao) }
                    val noteViewModel: NoteViewModel =
                        viewModel(factory = NoteViewModelFactory(noteRepository))

                    // Adiciona o Scaffold como layout principal
                    Scaffold(
                        modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = Routes.Home.route,
                            modifier = Modifier.padding(innerPadding) // Aplica o padding do Scaffold
                        ) {
                            composable(Routes.Home.route) {
                                HomeScreen(navController = navController)
                            }
                            composable(Routes.About.route) {
                                AboutScreen(navController = navController)
                            }
                            composable(
                                route = "createNote?noteId={noteId}",
                                arguments = listOf(navArgument("noteId") {
                                    type = NavType.StringType; nullable = true
                                })
                            ) {
                                val noteId = it.arguments?.getString("noteId")
                                CreateNewNoteScreen(
                                    viewModel = noteViewModel,
                                    navController = navController,
                                    noteId = noteId
                                )
                            }
                            composable(
                                route = Routes.SeeAll.route,
                                arguments = listOf(navArgument("type") { type = NavType.StringType })
                            ) { backStackEntry ->
                                val type = backStackEntry.arguments?.getString("type")
                                SeeAllScreen(navController = navController, type = type ?: "")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SetStatusBarColor(color: Color) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = color
    )
}

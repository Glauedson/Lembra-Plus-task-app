package com.progmobile.lembraplus.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.progmobile.lembraplus.ui.components.HeaderTitle.HeaderTitle
import com.progmobile.lembraplus.ui.components.HeaderTitle.HeaderTitleProps
import com.progmobile.lembraplus.utils.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeeAllScreen(
    navController: NavController,
    type: String
) {
    Scaffold() { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(start = 25.dp, end = 25.dp)
        ) {
            HeaderTitle(
                props = HeaderTitleProps(
                    title = "All ${type}",
                    onClick = {
                        navController.navigate(Routes.Home.route)
                    }
                )
            )
        }
    }
}
package com.progmobile.lembraplus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.progmobile.lembraplus.ui.components.HeaderTitle.HeaderTitle
import com.progmobile.lembraplus.ui.components.HeaderTitle.HeaderTitleProps
import com.progmobile.lembraplus.utils.Routes

@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold { pad ->
        Column (
            modifier = Modifier
                .padding(pad)
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize(),
        ) {

            HeaderTitle(
                props = HeaderTitleProps(
                    title = "Home",
                    onClick = {
                        navController.navigate(Routes.About.route)
                    }
                )
            )


        }
    }
}


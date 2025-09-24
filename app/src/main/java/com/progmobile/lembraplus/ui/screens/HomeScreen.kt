package com.progmobile.lembraplus.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen () {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Text(text = "Home Screen")
        }
}

@Composable
@Preview(showBackground = true)
fun previewHomeScreen(){
    HomeScreen()
}
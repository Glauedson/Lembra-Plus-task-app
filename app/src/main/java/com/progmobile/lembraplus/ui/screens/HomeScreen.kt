package com.progmobile.lembraplus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.progmobile.lembraplus.ui.components.HeaderTitle.HeaderTitle

@Composable
fun HomeScreen () {
    Scaffold { pad ->
        Column (
            modifier = Modifier
                .padding(pad)
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize(),
        ) {
            HeaderTitle(
                title = "Create New Note",
                onBackClick = { /* TO DO */ }
            )


        }
    }
}

@Composable
@Preview(showBackground = true)
fun previewHomeScreen(){
    HomeScreen()
}
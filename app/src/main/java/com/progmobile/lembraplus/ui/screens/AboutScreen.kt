package com.progmobile.lembraplus.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.progmobile.lembraplus.ui.components.HeaderTitle.HeaderTitle

@Composable
fun AboutScreen( ) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        HeaderTitle(
            title = "Create New Note",
            onBackClick = { /* TO DO */ }
        )

    }

}

@Composable
@Preview(showBackground = true)
fun previewAboutScreen(){
    AboutScreen()
}
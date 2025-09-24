package com.progmobile.lembraplus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CreateNewTaskScreen(){
    Scaffold { pad ->
        Column (
            modifier = Modifier
                .padding(pad)
                .fillMaxSize()
        ){
            Row (
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth()
                    .background(Color.Gray)
            ){

            }
            Column (
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(25.dp)
            ){
                Column (
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ){
                    Text(
                        "Note Title",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 20.sp
                    )
                    TextField(
                        value = "",
                        onValueChange = {},
                        shape = RoundedCornerShape(30),
                        modifier = Modifier
                            .fillMaxWidth(),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun CreateNewTaskScreenPreview(){
    CreateNewTaskScreen()
}
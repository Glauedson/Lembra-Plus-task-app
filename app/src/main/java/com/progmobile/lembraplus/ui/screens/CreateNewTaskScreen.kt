package com.progmobile.lembraplus.ui.screens

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.progmobile.lembraplus.ui.components.HeaderTitle.HeaderTitle
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNewTaskScreen(){

    var notetitle by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Scaffold { pad ->
        Column (
            modifier = Modifier
                .padding(pad)
                .padding(25.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ){
            HeaderTitle(
                title = "Create New Note",
                onBackClick = { /* TODO */ }
            )
            Column (
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ){
                Column (
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ){
                    Text(
                        "Note Title",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleMedium
                    )

                    TextField(
                        value = notetitle,
                        onValueChange = { digited -> notetitle = digited },
                        shape = RoundedCornerShape(10.dp),
                        placeholder = { Text(
                            "Grocery list",
                            color = MaterialTheme.colorScheme.outline
                        ) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(10.dp)),
                    )
                }
                Column (
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ){
                    Text(
                        "Description",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleMedium
                    )
                    TextField(
                        value = description,
                        onValueChange = { digited -> description = digited },
                        shape = RoundedCornerShape(10.dp),
                        placeholder = { Text(
                            "Milk, Bread, Eggs, Cheese, Chicken, Tomatoes, Onions, Pasta",
                            color = MaterialTheme.colorScheme.outline
                        ) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        minLines = 5,
                        maxLines = 10,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(10.dp)),
                    )
                }
                Column (
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ){
                    Text(
                        "Date & Time",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Row (){
                        CustomDatePicker()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(modifier: Modifier = Modifier) {
    var isDatePickerOpen by rememberSaveable { mutableStateOf(false) }

    val state = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis(),
        initialDisplayMode = DisplayMode.Picker
    )

    if (isDatePickerOpen) {
        DatePickerDialog(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            onDismissRequest = { isDatePickerOpen = false },
            confirmButton = {
                TextButton(onClick = { isDatePickerOpen = false }) {
                    Text("Select")
                }
            },
            dismissButton = {
                TextButton(onClick = { isDatePickerOpen = false }) {
                    Text("Cancel")
                }
            },
            content = {
                DatePicker(
                    state = state,
                    showModeToggle = true,
                    title = {
                        Text(
                            text = "Select a day",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(20.dp)
                        )
                    }
                )
            }
        )
    }
    Row (
        modifier = Modifier
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(10.dp))
            .padding(10.dp)
            .clickable(onClick = { isDatePickerOpen = true }),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            state.selectedDateMillis.toFormattedDateString(),
            color = MaterialTheme.colorScheme.onSurface
        )
        Box() {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

fun Long?.toFormattedDateString(): String {
    val millis = this ?: System.currentTimeMillis()
    val instant = java.time.Instant.ofEpochMilli(millis)
    val localDate = instant.atZone(java.time.ZoneOffset.UTC).toLocalDate()
    return localDate.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))
}

@Preview(showBackground = false)
@Composable
fun CreateNewTaskScreenPreview(){
    CreateNewTaskScreen()
}
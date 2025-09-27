package com.progmobile.lembraplus.ui.screens

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.progmobile.lembraplus.data.db.AppDatabase
import com.progmobile.lembraplus.data.repository.CategoryRepository
import com.progmobile.lembraplus.ui.components.HeaderTitle.HeaderTitle
import com.progmobile.lembraplus.ui.components.NewCategoryModal.NewCategoryModal
import com.progmobile.lembraplus.ui.vms.CategoryViewModel
import com.progmobile.lembraplus.ui.vms.CategoryViewModelFactory
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNewTaskScreen(){

    var notetitle by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    val context = LocalContext.current
    val categoryDao = AppDatabase.getInstance(context).categoryDao()
    val repository = remember { CategoryRepository(categoryDao) }
    val factory = remember { CategoryViewModelFactory(repository) }
    val categoryViewModel: CategoryViewModel = viewModel(factory = factory)

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
                            color = MaterialTheme.colorScheme.outline,
                            style = MaterialTheme.typography.labelMedium
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
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.outline,
                                RoundedCornerShape(10.dp)
                            ),
                    )
                }
                Column {
                    Text(
                        "Categories",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(scrollState)
                            .clip(RoundedCornerShape(10.dp)),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ){
                        NewCategoryModal(
                            modifier = Modifier,
                            viewModel = categoryViewModel
                        )
                        OutlinedButton (
                            onClick = {},
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = Color(0xFFFF6200).copy(alpha = 1f),
                                contentColor = Color.White,
                                disabledContainerColor = Color(0xFFFF6200).copy(alpha = 0.15f),
                                disabledContentColor = Color(0xFFFF6200).copy(alpha = 1f),
                            ),
                            border = BorderStroke(1.dp, Color(0xFFFF6200)),
                            shape = RoundedCornerShape(30.dp)
                        ) {
                            Text("Casa")
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(start = 5.dp)
                            )
                        }
                        repeat(10){
                            OutlinedButton (
                                onClick = {},
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = Color(0xFFFF6200).copy(alpha = 0.15f),
                                    contentColor = Color(0xFFFF6200).copy(alpha = 1f),
                                    disabledContainerColor = Color(0xFFFF6200).copy(alpha = 0.15f),
                                    disabledContentColor = Color(0xFFFF6200).copy(alpha = 1f),
                                ),
                                border = BorderStroke(1.dp, Color(0xFFFF6200)),
                                shape = RoundedCornerShape(30.dp)
                            ) {
                                Text("Casa")
                            }
                        }
                    }

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
                            color = MaterialTheme.colorScheme.outline,
                            style = MaterialTheme.typography.labelMedium
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
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.outline,
                                RoundedCornerShape(10.dp)
                            ),
                    )
                }
                Column (
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ){
                    Text(
                        "Date & Time",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ){
                        CustomDatePicker(modifier = Modifier.weight(1f))
                        TimePickerWithDialog(modifier = Modifier.weight(1f))
                    }
                }
                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ){
                    Button(
                        onClick = {},
                        shape = RoundedCornerShape(18.dp),
                        modifier = Modifier
                            .height(50.dp)
                            .weight(1f)
                    ) {
                        Text(
                            "Create Note",
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                    Button(
                        onClick = {},
                        shape = RoundedCornerShape(18.dp),
                        modifier = Modifier
                            .height(50.dp)
                    ){
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color.White
                        )
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
        initialSelectedDateMillis = null,
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
            text = state.selectedDateMillis?.toFormattedDateString() ?: "Select a date",
            color = MaterialTheme.colorScheme.onSurface
        )
        Box {
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
    val instant = Instant.ofEpochMilli(millis)
    val localDate = instant.atZone(ZoneOffset.UTC).toLocalDate()
    return localDate.format(DateTimeFormatter.ofPattern("dd / MMM / yyyy"))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerWithDialog(modifier: Modifier = Modifier) {
    val timeState = rememberTimePickerState(
        is24Hour = true
    )

    var timeSelected by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    Row (
        modifier = modifier
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(10.dp))
            .padding(10.dp)
            .clickable(onClick = {
                showDialog = true
            }),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = timeSelected.ifEmpty { "Select time" }
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        if (showDialog) {
            Dialog(
                onDismissRequest = { showDialog = false },
                properties = DialogProperties(usePlatformDefaultWidth = true)
            ) {
                ElevatedCard(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.extraLarge),
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                    shape = MaterialTheme.shapes.extraLarge
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp),
                            text = "Select time",
                            style = MaterialTheme.typography.titleLarge
                        )
                        TimePicker(
                            state = timeState,
                            layoutType = TimePickerLayoutType.Vertical,
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button (
                                modifier = Modifier.padding(end = 8.dp),
                                onClick = { showDialog = false }
                            ) {
                                Text(
                                    text = "Cancel"
                                )
                            }
                            Button(
                                modifier = Modifier.padding(start = 8.dp),
                                onClick = {
                                    timeSelected = formattedTime(timeState.hour, timeState.minute)
                                    showDialog = false
                                }
                            ) {
                                Text(text = "Select")
                            }
                        }
                    }
                }
            }
        }
    }
}

fun formattedTime(hour: Int, minute: Int): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val time = LocalTime.of(hour, minute).format(formatter)
    return time
}

@Preview(showBackground = false)
@Composable
fun CreateNewTaskScreenPreview(){
    CreateNewTaskScreen()
}
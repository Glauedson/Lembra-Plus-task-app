package com.progmobile.lembraplus.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Star
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.progmobile.lembraplus.data.db.AppDatabase
import com.progmobile.lembraplus.data.db.model.Task
import com.progmobile.lembraplus.data.repository.CategoryRepository
import com.progmobile.lembraplus.data.repository.TaskRepository
import com.progmobile.lembraplus.ui.components.HeaderTitle.HeaderTitle
import com.progmobile.lembraplus.ui.components.HeaderTitle.HeaderTitleProps
import com.progmobile.lembraplus.ui.components.NewCategoryModal.NewCategoryModal
import com.progmobile.lembraplus.ui.vms.CategoryViewModel
import com.progmobile.lembraplus.ui.vms.CategoryViewModelFactory
import com.progmobile.lembraplus.ui.vms.TaskViewModel
import com.progmobile.lembraplus.ui.vms.TaskViewModelFactory
import com.progmobile.lembraplus.utils.ColorUtils.safeParseColor
import com.progmobile.lembraplus.utils.Formatters
import com.progmobile.lembraplus.utils.Formatters.toFormattedDateString
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNewTaskScreen(
    viewModel: TaskViewModel,
    navController: NavHostController,
    taskId: String?
) {
    var notetitle by remember { mutableStateOf("") }
    val maxTitle = 50

    var description by remember { mutableStateOf("") }
    val maxDescription = 300

    var isFixed by remember { mutableStateOf(false) }

    var date by remember { mutableStateOf<LocalDate?>(null) }
    var time by remember { mutableStateOf<LocalTime?>(null) }

    var error by remember { mutableStateOf<String?>(null) }

    val scrollState = rememberScrollState()
    val mainContentScrollState = rememberScrollState()

    val context = LocalContext.current

    val categoryDao = AppDatabase.getInstance(context).categoryDao()
    val categoryRepository = remember { CategoryRepository(categoryDao) }
    val categoryViewModel: CategoryViewModel =
        viewModel(factory = CategoryViewModelFactory(categoryRepository))

    val categories by categoryViewModel.categories.collectAsState()
    var selectedCategoryId by remember { mutableStateOf<Int?>(null) }
    var createdAt by remember { mutableStateOf(System.currentTimeMillis()) }

    val isEditing = taskId != null

    if (isEditing) {
        LaunchedEffect (key1 = taskId) {
            viewModel.getTaskById(taskId.toInt())?.let { taskWithCategory ->
                notetitle = taskWithCategory.task.title
                description = taskWithCategory.task.description ?: ""
                isFixed = taskWithCategory.task.isFixed
                selectedCategoryId = taskWithCategory.task.categoryId
                date = taskWithCategory.task.date
                time = taskWithCategory.task.time
                createdAt = taskWithCategory.task.createdAt
            }
        }
    }

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 25.dp, start = 25.dp, end = 25.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = {
                        val titleTrimmed = notetitle.trim()
                        if (titleTrimmed.isEmpty()) {
                            error = "Inform a title!"
                            return@Button
                        }
                        val descriptionTrimmed = description.trim()

                        val task = Task(
                            id = taskId?.toInt() ?: 0,
                            title = titleTrimmed,
                            description = descriptionTrimmed.ifEmpty { null },
                            categoryId = selectedCategoryId,
                            date = date,
                            time = time,
                            isFixed = isFixed,
                            createdAt = createdAt
                        )
                        if (isEditing) {
                            viewModel.updateTask(task)
                        } else {
                            viewModel.addTask(task)
                        }
                        navController.navigate("home") { popUpTo("home") { inclusive = true } }
                    },
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier
                        .height(50.dp)
                        .weight(1f)
                ) {
                    Text(
                        if (isEditing) "Update Note" else "Create Note",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                Button(
                    onClick = { isFixed = !isFixed },
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier.height(50.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Star,
                        contentDescription = null,
                        tint = if (isFixed) Color.Yellow else MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }) { pad ->
        Column(
            modifier = Modifier
                .padding(pad)
                .padding(25.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            HeaderTitle(
                props = HeaderTitleProps(
                    title = if (isEditing) "Edit Note" else "Create New Note",
                    backButton = { navController.popBackStack() }
                )
            )
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(mainContentScrollState)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        "Note Title",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleMedium
                    )
                    OutlinedTextField(
                        value = notetitle,
                        onValueChange = { digited ->
                            if (digited.length <= maxTitle) {
                                notetitle = digited
                            }
                            if (notetitle.isNotBlank()) error = null
                        },
                        shape = RoundedCornerShape(10.dp),
                        placeholder = {
                            Text(
                                if (error == null) "Grocery list" else "$error",
                                color = if (error == null) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.labelMedium
                            )
                        },
                        maxLines = 1,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
                    ) {
                        if (error != null) {
                            Text(
                                "This field is mandatory.  ",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                        Text(
                            "${notetitle.length} / $maxTitle",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        "Categories",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .clip(RoundedCornerShape(10.dp))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(scrollState)
                                .clip(RoundedCornerShape(10.dp)),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            NewCategoryModal(
                                modifier = Modifier, viewModel = categoryViewModel
                            )
                            categories.forEach { cat ->
                                key(cat.category.id) {
                                    val buttonColor = safeParseColor(cat.category.colorHex)
                                    val isSelected = cat.category.id == selectedCategoryId
                                    OutlinedButton(
                                        onClick = {
                                            selectedCategoryId = if (isSelected) null else cat.category.id
                                        },
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            containerColor = if (isSelected) buttonColor else buttonColor.copy(
                                                alpha = 0.15f
                                            ),
                                            contentColor = if (isSelected) Color.White else buttonColor
                                        ),
                                        border = BorderStroke(1.dp, buttonColor),
                                        shape = RoundedCornerShape(30.dp)
                                    ) {
                                        Text(cat.category.name)
                                    }
                                }
                            }
                        }
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .fadingEdgeOverlay(
                                    scrollState = scrollState,
                                    fadeWidth = 32.dp,
                                    overlayColor = MaterialTheme.colorScheme.background
                                )
                        )
                    }

                }
                Column(
                    modifier = Modifier.padding(top = 15.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        "Description",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleMedium
                    )
                    OutlinedTextField(
                        value = description, onValueChange = { digited ->
                            if (digited.length <= maxDescription) {
                                description = digited
                            }
                        }, shape = RoundedCornerShape(10.dp), placeholder = {
                            Text(
                                "Milk, Bread, Eggs, Cheese, Chicken, Tomatoes, Onions, Pasta",
                                color = MaterialTheme.colorScheme.outline,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }, minLines = 5, maxLines = 7, modifier = Modifier.fillMaxWidth()

                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            "${description.length} / $maxDescription",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.outline,
                            textAlign = TextAlign.End,
                        )
                    }
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        "Date & Time",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {

                        val dateInMillis = Formatters.dateInMillis(date)

                        CustomDatePicker(
                            selectedDateMillis = dateInMillis,
                            onDateSelected = { millis ->
                                date = millis?.let {
                                    Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault())
                                        .toLocalDate()
                                }
                            })
                        TimePickerWithDialog(
                            modifier = Modifier.weight(1f),
                            onTimeSelected = { selected -> time = selected }
                        )
                    }
                }
            }
        }
    }
}

fun Modifier.fadingEdgeOverlay(
    scrollState: ScrollState, fadeWidth: Dp, overlayColor: Color = Color.White
): Modifier = this.then(
    Modifier.drawWithContent {

        drawContent()

        if (scrollState.maxValue <= 0) return@drawWithContent

        val fadePx = fadeWidth.toPx()
        val heightPx = size.height
        val widthPx = size.width

        if (scrollState.value > 0) {
            drawRect(
                brush = Brush.horizontalGradient(
                    colors = listOf(overlayColor, Color.Transparent), startX = 0f, endX = fadePx
                ), topLeft = Offset(0f, 0f), size = Size(fadePx, heightPx)
            )
        }

        if (scrollState.value < scrollState.maxValue) {
            drawRect(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color.Transparent, overlayColor),
                    startX = widthPx - fadePx,
                    endX = widthPx
                ), topLeft = Offset(widthPx - fadePx, 0f), size = Size(fadePx, heightPx)
            )
        }
    })

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(
    modifier: Modifier = Modifier, selectedDateMillis: Long?, onDateSelected: (Long?) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val state = rememberDatePickerState(
        initialSelectedDateMillis = selectedDateMillis, initialDisplayMode = DisplayMode.Picker
    )

    if (showDatePicker) {
        DatePickerDialog(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDateSelected(state.selectedDateMillis)
                        showDatePicker = false
                    }) {
                    Text("Select")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            },
            content = {
                DatePicker(
                    state = state, showModeToggle = true, title = {
                        Text(
                            text = "Select a day",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(20.dp)
                        )
                    })
            })
    }
    Row(
        modifier = Modifier
            .border(
                1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(10.dp)
            )
            .padding(10.dp)
            .clickable(onClick = { showDatePicker = true }),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = state.selectedDateMillis?.toFormattedDateString() ?: "Select a date",
            color = if (state.selectedDateMillis == null) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.onSurface,
            style = if (state.selectedDateMillis == null) MaterialTheme.typography.labelMedium else MaterialTheme.typography.labelLarge
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerWithDialog(
    modifier: Modifier = Modifier,
    onTimeSelected: (LocalTime) -> Unit
) {

    val timeState = rememberTimePickerState(
        is24Hour = true
    )

    var timeSelected by remember { mutableStateOf("") }
    var showTimePicker by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .border(
                1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(10.dp)
            )
            .padding(10.dp)
            .clickable(onClick = {
                showTimePicker = true
            }),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = timeSelected.ifEmpty { "Select time" },
            color = if (timeSelected.isEmpty()) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.onSurface,
            style = if (timeSelected.isEmpty()) MaterialTheme.typography.labelMedium else MaterialTheme.typography.labelLarge
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        if (showTimePicker) {
            Dialog(
                onDismissRequest = { showTimePicker = false },
                properties = DialogProperties(usePlatformDefaultWidth = true)
            ) {
                ElevatedCard(
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.extraLarge
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                    shape = MaterialTheme.shapes.extraLarge
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
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
                            Button(
                                modifier = Modifier.padding(end = 8.dp),
                                onClick = { showTimePicker = false }) {
                                Text(
                                    text = "Cancel"
                                )
                            }
                            Button(
                                modifier = Modifier.padding(start = 8.dp),
                                onClick = {
                                    val selectedTime =
                                        LocalTime.of(timeState.hour, timeState.minute)
                                    timeSelected =
                                        Formatters.formattedTime(selectedTime.hour, selectedTime.minute)
                                    onTimeSelected(selectedTime)
                                    showTimePicker = false
                                }) {
                                Text(text = "Select")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun CreateNewTaskScreenPreview() {
    CreateNewTaskScreen(
        viewModel = viewModel(),
        navController = rememberNavController(),
        taskId = null
    )
}
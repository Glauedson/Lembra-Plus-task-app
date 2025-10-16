package com.progmobile.lembraplus.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import com.progmobile.lembraplus.data.db.AppDatabase
import com.progmobile.lembraplus.data.repository.TaskRepository
import com.progmobile.lembraplus.ui.vms.TaskViewModel
import com.progmobile.lembraplus.ui.vms.TaskViewModelFactory

data class TaskModalData(
    val id: String,
    val title: String,
    val description: String?,
    val categoryName: String?,
    val categoryColorHex: String?,
    val isPinned: Boolean,
    val date: String?,
    val time: String?,
    val createdAt: String?
)

@Composable
fun TaskViewModal(
    task: TaskModalData,
    onDismiss: () -> Unit,
    onEdit: (TaskModalData) -> Unit = {},
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    val safeColorHex = task.categoryColorHex ?: "#9D9D9D"
    val categoryColor = Color(safeColorHex.toColorInt())

    val context = LocalContext.current
    val taskDao = AppDatabase.getInstance(context).taskDao()
    val taskRepository = remember { TaskRepository(taskDao) }
    val taskViewModel: TaskViewModel =
        viewModel(factory = TaskViewModelFactory(taskRepository))


    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(
                    onClick = onDismiss,
                    indication = null,
                    interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
                )
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier.clickable(
                    onClick = {},
                    indication = null,
                    interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
                ),

                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Badge
                        Box(
                            modifier = Modifier
                                .background(
                                    color = categoryColor.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = categoryColor,
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = task.categoryName ?: "Without Category",
                                color = categoryColor,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                            )
                        }

                        // TO DO: Create Date
                    }

                    // Título
                    Text(
                        text = task.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        lineHeight = 28.sp,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )


                    // Description
                    Text(
                        text = task.description ?: "",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    if (task.date != null && task.time != null) {
                        // Date & Time Section
                        Text(
                            text = "Date & Time",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Date
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .background(
                                        color = Color(0xFFF5F5F5),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = task.date,
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                            }

                            // hour
                            Box(
                                modifier = Modifier
                                    .weight(0.6f)
                                    .background(
                                        color = Color(0xFFF5F5F5),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = task.time,
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Button Delete
                        Button(
                            onClick = { showDeleteDialog = true },
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .height(45.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(15.dp)
                                )
                        ) {
                            Text("Delete")
                        }

                        // Button edit
                        Button(
                            onClick = { onEdit(task) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(15.dp)
                                )
                        ) {
                            Text("Edit")
                        }
                    }
                }
            }
        }
    }

    // Dialog confirmation
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Text(
                    text = "Delete note?",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text("Are you sure you want to delete this task? This action cannot be undone.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onDismiss()

                        taskViewModel.deleteTask(task.id.toInt())
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.Red
                    )
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}
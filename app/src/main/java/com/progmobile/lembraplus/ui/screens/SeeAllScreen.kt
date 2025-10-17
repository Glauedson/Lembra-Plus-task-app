package com.progmobile.lembraplus.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.progmobile.lembraplus.data.db.AppDatabase
import com.progmobile.lembraplus.data.repository.CategoryRepository
import com.progmobile.lembraplus.data.repository.TaskRepository
import com.progmobile.lembraplus.ui.components.CategoryCard.CategoryCard
import com.progmobile.lembraplus.ui.components.CategoryCard.CategoryCardProps
import com.progmobile.lembraplus.ui.components.HeaderTitle.HeaderTitle
import com.progmobile.lembraplus.ui.components.HeaderTitle.HeaderTitleProps
import com.progmobile.lembraplus.ui.components.TaskCard
import com.progmobile.lembraplus.ui.components.TaskCardProps
import com.progmobile.lembraplus.ui.vms.CategoryViewModel
import com.progmobile.lembraplus.ui.vms.CategoryViewModelFactory
import com.progmobile.lembraplus.ui.vms.TaskViewModel
import com.progmobile.lembraplus.ui.vms.TaskViewModelFactory
import com.progmobile.lembraplus.utils.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeeAllScreen(
    navController: NavHostController,
    type: String
) {

    var showDeleteDialog by remember { mutableStateOf(false) }
    
    val context = LocalContext.current

    val taskDao = AppDatabase.getInstance(context).taskDao()
    val taskRepository = remember { TaskRepository(taskDao) }
    val taskViewModel: TaskViewModel =
        viewModel(factory = TaskViewModelFactory(taskRepository))

    val allTasks by taskViewModel.allTasks.collectAsState()
    val tasksFixed by taskViewModel.tasksFixed.collectAsState()

    val categoryDao = AppDatabase.getInstance(context).categoryDao()
    val categoryRepository = remember { CategoryRepository(categoryDao) }
    val categoryViewModel: CategoryViewModel =
        viewModel(factory = CategoryViewModelFactory(categoryRepository))

    val categories by categoryViewModel.categories.collectAsState()

    Scaffold() { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(start = 25.dp, end = 25.dp, top = 15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            HeaderTitle(
                props = HeaderTitleProps(
                    title = "All ${type}",
                    backButton = {
                        navController.navigate(Routes.Home.route)
                    }
                ),
                onDeleteClick = { showDeleteDialog = true }
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
                    .padding(top = 2.dp, bottom = 25.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                when (type) {
                    "favorites" -> {
                        tasksFixed.forEach { card ->
                            TaskCard(
                                TaskCardProps(
                                    id = card.task.id.toString(),
                                    title = card.task.title,
                                    description = card.task.description,
                                    categoryName = card.category?.name,
                                    categoryColorHex = card.category?.colorHex,
                                    createdAt = card.task.createdAt,
                                    date = card.task.date,
                                    time = card.task.time,
                                    isPinned = true
                                ),
                                navController = navController
                            )
                        }
                    }

                    "categories" -> {
                        categories.sortedByDescending { it.taskCount }.forEach { card ->
                            CategoryCard(
                                CategoryCardProps(
                                    id = card.category.id,
                                    name = card.category.name,
                                    colorHex = card.category.colorHex,
                                    quant = card.taskCount
                                ),
                                navController = navController,
                                viewModel = categoryViewModel
                            )
                        }
                    }

                    "tasks" -> {
                        allTasks.forEach { card ->
                            TaskCard(
                                TaskCardProps(
                                    id = card.task.id.toString(),
                                    title = card.task.title,
                                    description = card.task.description,
                                    categoryName = card.category?.name,
                                    categoryColorHex = card.category?.colorHex,
                                    createdAt = card.task.createdAt,
                                    date = card.task.date,
                                    time = card.task.time,
                                    isPinned = card.task.isFixed
                                ),
                                navController = navController
                            )
                        }
                    }
                }
            }
            
            if (showDeleteDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    title = {
                        Text(
                            text = "Delete all $type?",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    text = {
                        Text("Are you sure you want to delete all $type? This action cannot be undone.")
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                when(type){
                                    "favorites" -> {
                                        tasksFixed.forEach { task ->
                                            taskViewModel.deleteTask(task.task.id)
                                        }
                                    }
                                    "categories" -> {
                                        categories.forEach { category ->
                                            categoryViewModel.deleteCategory(category.category.id)
                                        }
                                    }
                                    "tasks" -> {
                                        allTasks.forEach { task ->
                                            taskViewModel.deleteTask(task.task.id)
                                        }
                                    }
                                }
                                showDeleteDialog = false
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
    }
}

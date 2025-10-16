package com.progmobile.lembraplus.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
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
    navController: NavController,
    type: String
) {

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
                .padding(start = 25.dp, end = 25.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            HeaderTitle(
                props = HeaderTitleProps(
                    title = "All ${type}",
                    onClick = {
                        navController.navigate(Routes.Home.route)
                    }
                )
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                when (type) {
                    "favorites" -> {
                        tasksFixed.forEach { card ->
                            TaskCard(
                                TaskCardProps(
                                    title = card.task.title,
                                    description = card.task.description,
                                    categoryName = card.category?.name,
                                    categoryColorHex = card.category?.colorHex,
                                    isPinned = true
                                )
                            )
                        }
                    }

                    "categories" -> {
                        categories.sortedByDescending { it.taskCount }.forEach { card ->
                            CategoryCard(
                                CategoryCardProps(
                                    name = card.category.name,
                                    colorHex = card.category.colorHex,
                                    quant = card.taskCount
                                )
                            )
                        }
                    }

                    "notes" -> {
                        allTasks.forEach { card ->
                            TaskCard(
                                TaskCardProps(
                                    title = card.task.title,
                                    description = card.task.description,
                                    categoryName = card.category?.name,
                                    categoryColorHex = card.category?.colorHex,
                                    isPinned = card.task.isFixed
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
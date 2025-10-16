package com.progmobile.lembraplus.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.progmobile.lembraplus.R
import com.progmobile.lembraplus.data.db.AppDatabase
import com.progmobile.lembraplus.data.repository.CategoryRepository
import com.progmobile.lembraplus.data.repository.TaskRepository
import com.progmobile.lembraplus.ui.components.CategoryCard.CategoryCard
import com.progmobile.lembraplus.ui.components.CategoryCard.CategoryCardProps
import com.progmobile.lembraplus.ui.components.NavBar.NavBar
import com.progmobile.lembraplus.ui.components.NavBar.NavProps
import com.progmobile.lembraplus.ui.components.SearchTaskModal.SearchTaskModal
import com.progmobile.lembraplus.ui.components.TaskCard
import com.progmobile.lembraplus.ui.components.TaskCardProps
import com.progmobile.lembraplus.ui.vms.CategoryViewModel
import com.progmobile.lembraplus.ui.vms.CategoryViewModelFactory
import com.progmobile.lembraplus.ui.vms.TaskViewModel
import com.progmobile.lembraplus.ui.vms.TaskViewModelFactory
import com.progmobile.lembraplus.utils.Routes

@Composable
fun HomeScreen(navController: NavHostController) {

    var showModal by remember { mutableStateOf(false) }

    val scrollVertical = rememberScrollState()
    val scrollHorizontal = rememberScrollState()

    val context = LocalContext.current
    val taskDao = AppDatabase.getInstance(context).taskDao()
    val taskRepository = remember { TaskRepository(taskDao) }
    val taskViewModel: TaskViewModel =
        viewModel(factory = TaskViewModelFactory(taskRepository))

    val allTasks by taskViewModel.allTasks.collectAsState()
    val tasksFixed by taskViewModel.tasksFixed.collectAsState()

    if (showModal) {
        SearchTaskModal(
            onDismiss = { showModal = false },
            navController = navController
        )
    }

    Scaffold(
        bottomBar = {
            NavBar(props = NavProps(navController, "home"))
        }
    ) { pad ->

        Column(
            modifier = Modifier
                .padding(pad)
                .padding(start = 25.dp, end = 25.dp, top = 15.dp)
                .verticalScroll(scrollHorizontal)
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            // Header
            Box (
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                IconButton(
                    onClick = {
                        showModal = true
                    },
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .background(
                            color = MaterialTheme.colorScheme.tertiary,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .width(45.dp)
                        .height(45.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logo App",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(90.dp)
                )
            }

            // Favorited
            Column(
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Favorited",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium

                    )


                    Text(
                        "See All",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable {
                            navController.navigate(Routes.SeeAll.createRoute("favorites"))
                        }
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            //.height(130.dp)
                            .horizontalScroll(scrollVertical)
                            .clip(RoundedCornerShape(10.dp))
                            .padding(2.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        tasksFixed.forEach { task ->
                            TaskCard(
                                props = TaskCardProps(
                                    id = task.task.id.toString(),
                                    title = task.task.title,
                                    description = task.task.description,
                                    categoryName = task.category?.name,
                                    categoryColorHex = task.category?.colorHex,
                                    createdAt = task.task.createdAt,
                                    date = task.task.date,
                                    time = task.task.time,
                                    width = 250,
                                    isPinned = true
                                ),
                                navController = navController
                            )
                        }

                        if (tasksFixed.isEmpty()){
                            Text(
                                "no favorite notes",
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    }
                }
            }

            // Categories
            val categoryDao = AppDatabase.getInstance(context).categoryDao()
            val categoryRepository = remember { CategoryRepository(categoryDao) }
            val categoryViewModel: CategoryViewModel =
                viewModel(factory = CategoryViewModelFactory(categoryRepository))

            val categories by categoryViewModel.categories.collectAsState()

            Column(
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Categories",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium

                    )

                    Text(
                        "See All",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable {
                            navController.navigate(Routes.SeeAll.createRoute("categories"))
                        }
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    categories.sortedByDescending { it.taskCount }.take(3).forEach { cat ->
                        CategoryCard(
                            props = CategoryCardProps(
                                name = cat.category.name,
                                colorHex = cat.category.colorHex,
                                quant = cat.taskCount
                            )
                        )
                    }

                    if (categories.isEmpty()){
                        Text(
                            "no categories notes",
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
            }

            // Latest tasks added

            Column(
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Latest tasks added",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium

                    )

                    Text(
                        "See All",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable {
                            navController.navigate(Routes.SeeAll.createRoute("tasks"))
                        }
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    allTasks.take(3).forEach { taskWithCategory ->
                        TaskCard(
                            props = TaskCardProps(
                                id = taskWithCategory.task.id.toString(),
                                title = taskWithCategory.task.title,
                                description = taskWithCategory.task.description,
                                categoryName = taskWithCategory.category?.name,
                                categoryColorHex = taskWithCategory.category?.colorHex,
                                isPinned = taskWithCategory.task.isFixed,
                                createdAt = taskWithCategory.task.createdAt,
                                date = taskWithCategory.task.date,
                                time = taskWithCategory.task.time
                            ),
                            navController = navController
                        )
                    }

                    if (allTasks.isEmpty()){
                        Text(
                            "no notes",
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
                Spacer(modifier = Modifier.height(25.dp))
            }
        }
    }
}

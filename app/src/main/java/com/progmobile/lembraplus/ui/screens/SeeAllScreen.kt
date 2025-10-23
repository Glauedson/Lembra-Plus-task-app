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
import androidx.navigation.NavHostController
import com.progmobile.lembraplus.data.db.AppDatabase
import com.progmobile.lembraplus.data.repository.CategoryRepository
import com.progmobile.lembraplus.data.repository.NoteRepository
import com.progmobile.lembraplus.ui.components.CategoryCard.CategoryCard
import com.progmobile.lembraplus.ui.components.CategoryCard.CategoryCardProps
import com.progmobile.lembraplus.ui.components.HeaderTitle.HeaderTitle
import com.progmobile.lembraplus.ui.components.HeaderTitle.HeaderTitleProps
import com.progmobile.lembraplus.ui.components.NoteCard
import com.progmobile.lembraplus.ui.components.NoteCardProp
import com.progmobile.lembraplus.ui.vms.CategoryViewModel
import com.progmobile.lembraplus.ui.vms.CategoryViewModelFactory
import com.progmobile.lembraplus.ui.vms.NoteViewModel
import com.progmobile.lembraplus.ui.vms.NoteViewModelFactory
import com.progmobile.lembraplus.utils.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeeAllScreen(
    navController: NavHostController,
    type: String
) {

    var showDeleteDialog by remember { mutableStateOf(false) }
    
    val context = LocalContext.current

    val taskDao = AppDatabase.getInstance(context).noteDao()
    val noteRepository = remember { NoteRepository(taskDao) }
    val noteViewModel: NoteViewModel =
        viewModel(factory = NoteViewModelFactory(noteRepository))

    val allTasks by noteViewModel.allNotes.collectAsState()
    val tasksFixed by noteViewModel.pinnedNotes.collectAsState()

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
                            NoteCard(
                                NoteCardProp(
                                    id = card.note.id.toString(),
                                    title = card.note.title,
                                    description = card.note.description,
                                    categoryName = card.category?.name,
                                    categoryColorHex = card.category?.colorHex,
                                    createdAt = card.note.createdAt,
                                    date = card.note.date,
                                    time = card.note.time,
                                    isPinned = true
                                ),
                                navController = navController
                            )
                        }
                    }

                    "categories" -> {
                        categories.sortedByDescending { it.noteCount }.forEach { card ->
                            CategoryCard(
                                CategoryCardProps(
                                    id = card.category.id,
                                    name = card.category.name,
                                    colorHex = card.category.colorHex,
                                    quant = card.noteCount
                                ),
                                navController = navController,
                                viewModel = categoryViewModel
                            )
                        }
                    }

                    "tasks" -> {
                        allTasks.forEach { card ->
                            NoteCard(
                                NoteCardProp(
                                    id = card.note.id.toString(),
                                    title = card.note.title,
                                    description = card.note.description,
                                    categoryName = card.category?.name,
                                    categoryColorHex = card.category?.colorHex,
                                    createdAt = card.note.createdAt,
                                    date = card.note.date,
                                    time = card.note.time,
                                    isPinned = card.note.isPinned
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
                                            noteViewModel.deleteNote(task.note.id)
                                        }
                                    }
                                    "categories" -> {
                                        categories.forEach { category ->
                                            categoryViewModel.deleteCategory(category.category.id)
                                        }
                                    }
                                    "tasks" -> {
                                        allTasks.forEach { task ->
                                            noteViewModel.deleteNote(task.note.id)
                                        }
                                    }
                                    "category" -> {

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

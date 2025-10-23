package com.progmobile.lembraplus.ui.components.SearchNoteModal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.progmobile.lembraplus.data.db.AppDatabase
import com.progmobile.lembraplus.data.repository.NoteRepository
import com.progmobile.lembraplus.ui.components.NoteCard
import com.progmobile.lembraplus.ui.components.NoteCardProp
import com.progmobile.lembraplus.ui.vms.NoteViewModel
import com.progmobile.lembraplus.ui.vms.NoteViewModelFactory

@Composable
fun SearchNoteModal(
    onDismiss: () -> Unit,
    navController: NavController
) {

    val context = LocalContext.current
    val noteDao = AppDatabase.getInstance(context).noteDao()
    val noteRepository = remember { NoteRepository(noteDao) }
    val noteViewModel: NoteViewModel = viewModel(factory = NoteViewModelFactory(noteRepository))
    val searchResults by noteViewModel.searchResults.collectAsState()

    var isTextFieldFocused by remember { mutableStateOf(false) }

    var search by rememberSaveable { mutableStateOf("") }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .zIndex(1f)
                .fillMaxSize()
                .clickable(
                    onClick = onDismiss,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                )
                .padding(top = if (isTextFieldFocused) 25.dp else 0.dp),
            contentAlignment = if (isTextFieldFocused) Alignment.TopCenter else Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(25.dp)
                    .heightIn(max = 500.dp)
                    .width(300.dp)
                    .clickable(enabled = false) {},
                verticalArrangement = Arrangement.spacedBy(if (searchResults.isEmpty()) 0.dp else 15.dp)
            ) {
                OutlinedTextField(
                    value = search,
                    onValueChange = {
                        search = it
                        noteViewModel.searchNotes(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged {
                            isTextFieldFocused = it.isFocused
                        },
                    placeholder = {
                        Text(
                            "Search a Note",
                            color = MaterialTheme.colorScheme.outline,
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    shape = RoundedCornerShape(20.dp),
                    singleLine = true,
                )
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .padding(if (searchResults.isEmpty()) 0.dp else 5.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                )
                {
                    searchResults.forEach { card ->
                        NoteCard(
                            NoteCardProp(
                                title = card.note.title,
                                description = card.note.description,
                                categoryName = card.category?.name,
                                categoryColorHex = card.category?.colorHex,
                                isPinned = card.note.isPinned,
                                id = card.note.id.toString(),
                                createdAt = card.note.createdAt,
                                date = card.note.date,
                                time = card.note.time
                            ),
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}
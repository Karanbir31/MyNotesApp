package com.example.mynotesapp.notes.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mynotesapp.R
import com.example.mynotesapp.navigation.NavigationRoutes
import com.example.mynotesapp.notes.domain.NotesItem
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllNotesScreen(
    navController: NavController,
    notesViewModel: NotesViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        notesViewModel.readAllNotes()
    }

    val allNotesState = notesViewModel.allNotes

    Scaffold(

        topBar = {
            TopAppBar(
                title = {
                    Text("My Notes")
                }
            )
        },


        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Text(text = "Add note", fontFamily = FontFamily.Cursive)
                },
                icon = {
                    Icon(
                        painterResource(R.drawable.outline_add_notes_24),
                        contentDescription = "Add new note"
                    )
                },
                onClick = {
                    navController.navigate(NavigationRoutes.NotesDetails.createRoute(-1))
                }
            )
        }
    ) {
        AllNotesScreenUi(
            allNotesState = allNotesState,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(it),
            onNoteUpdate = { updatedNote ->
                notesViewModel.updateNoteOnDataBase(updatedNote)
            },
            onClickNote = { noteId ->
                navController.navigate(NavigationRoutes.NotesDetails.createRoute(noteId))
            },
            onDeleteNote = {note->
                notesViewModel.deleteNote(note)
            }
        )
    }


}

@Composable
fun AllNotesScreenUi(
    allNotesState: StateFlow<List<NotesItem>>,
    modifier: Modifier,
    onNoteUpdate: (NotesItem) -> Unit,
    onClickNote: (Long) -> Unit,
    onDeleteNote: (NotesItem) -> Unit
) {


    val allNotes by allNotesState.collectAsState()

    if (allNotes.isEmpty()) {
        EmptyUi()
    } else {
        LazyColumn(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                // filter sort
            }

            items(allNotes) { note ->
                NoteItemUi(
                    note = note,
                    onNoteUpdate = {
                        onNoteUpdate.invoke(it)
                    },
                    onClickNote = {
                        onClickNote.invoke(it)
                    },
                    onDeleteNote = {
                        onDeleteNote.invoke(it)
                    }
                )

                Spacer(Modifier.padding(top = 8.dp))
            }

        }
    }

}

@Composable
fun EmptyUi() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Data not available",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontFamily = FontFamily.Cursive,
            textAlign = TextAlign.Center
        )
    }


}


@Composable
fun NoteItemUi(note: NotesItem, onNoteUpdate: (NotesItem) -> Unit, onClickNote: (Long) -> Unit, onDeleteNote : (NotesItem) -> Unit) {
    var pinButtonState by remember { mutableStateOf(note.isPinned) }
    val icon = if (pinButtonState) R.drawable.ic_pin_selected else R.drawable.ic_pin_unselected

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp))
            .clickable {
                onClickNote.invoke(note.id)
            }
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {

        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                Text(
                    text = note.title,
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    maxLines = 2
                )

                HorizontalDivider()

                Text(
                    text = note.content,
                    style = TextStyle(fontSize = 18.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    maxLines = 4,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    overflow = TextOverflow.Ellipsis
                )

            }

            IconButton(
                onClick = {
                    pinButtonState = !pinButtonState
                    onNoteUpdate.invoke(note.copy(isPinned = pinButtonState))
                },
                content = {
                    Icon(
                        painterResource(icon),
                        contentDescription = "pin",
                        modifier = Modifier
                            .padding(8.dp)
                            .size(24.dp)
                    )
                },
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.CenterVertically)
            )
        }

        HorizontalDivider()

        Row(
            modifier = Modifier.padding(bottom = 4.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "last update ${note.getLocalDateTimeAsString()}",
                style = TextStyle(fontSize = 18.sp),
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f),
                modifier = Modifier
                    .weight(1f),
                maxLines = 1
            )

            IconButton(
                onClick = {
                    onDeleteNote.invoke(note)
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_delete_24),
                    "Delete"
                )
            }
        }
    }
}
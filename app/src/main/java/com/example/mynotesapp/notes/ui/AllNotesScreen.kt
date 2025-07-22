package com.example.mynotesapp.notes.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mynotesapp.R
import com.example.mynotesapp.navigation.NavigationRoutes
import com.example.mynotesapp.notes.domain.NotesItem
import kotlinx.coroutines.flow.StateFlow

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
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Text(text= "Add note", fontFamily = FontFamily.Cursive)
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
                .padding(it),
            onNoteUpdate = {updatedNote->
                notesViewModel.updateNoteOnDataBase(updatedNote)
            },
            onClickNote = {noteId->
                navController.navigate(NavigationRoutes.NotesDetails.createRoute(noteId))
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
) {



    val allNotes by allNotesState.collectAsState()

    if (allNotes.isEmpty()) {
        EmptyUi()
    }else{
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
    ){
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
fun NoteItemUi(note: NotesItem, onNoteUpdate: (NotesItem) -> Unit, onClickNote: (Long) -> Unit) {
    var pinButtonState by remember { mutableStateOf(note.isPinned) }
    val icon = if (pinButtonState) R.drawable.ic_pin_selected else R.drawable.ic_pin_unselected

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClickNote.invoke(note.id)
            }
            .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
    ) {

        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    fontFamily = FontFamily.Cursive
                )

                Text(
                    text = note.content,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 4,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = FontFamily.Cursive
                )
                Text(
                    text = note.getLocalDateTimeAsString(),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    fontFamily = FontFamily.Cursive
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
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                },
                modifier = Modifier.padding(8.dp)
            )
        }

    }
}
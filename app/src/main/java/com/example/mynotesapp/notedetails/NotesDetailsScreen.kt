package com.example.mynotesapp.notedetails

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mynotesapp.R

@Composable
fun NotesDetailsScreen(
    noteId: Long,
    navController: NavController,
    noteDetailsViewModel: NoteDetailsViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(noteId) {
        if (noteId >= 0) {
            noteDetailsViewModel.readNoteWithId(noteId)
        }
    }

    Scaffold(
        topBar = {
            NotesDetailsScreenAppBar(onClickSaveButton = {
                // save data in room data base
                if (noteId >= 0) {
                    noteDetailsViewModel.updateNoteOnDataBase()
                }else{
                    noteDetailsViewModel.saveNewNote()
                }

                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
            })
        }

    ) {
        val modifier = Modifier
            .fillMaxSize()
            .padding(it)

        NotesDetailsScreenUi(
            noteDetailsViewModel = noteDetailsViewModel,
            modifier = modifier
        )
    }

}

@Composable
fun NotesDetailsScreenUi(
    noteDetailsViewModel: NoteDetailsViewModel,
    modifier: Modifier = Modifier
) {
    val note = noteDetailsViewModel.note.collectAsState().value
    Column(
        modifier
    ) {
        TextField(
            value = note.title,
            onValueChange = {
                noteDetailsViewModel.updateLocalTitle(it)
            },
            label = {
                Text("Note title")
            },
            maxLines = 2,
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = note.content,
            onValueChange = {
                noteDetailsViewModel.updateLocalContent(it)
            },
            label = {
                Text("Note content")
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesDetailsScreenAppBar(onClickSaveButton: () -> Unit) {
    TopAppBar(
        title = {

        },
        navigationIcon = {
            Icon(
                painter = painterResource(R.drawable.baseline_arrow_back_24),
                "back button"
            )
        },
        actions = {
            Button(
                onClick = {
                    onClickSaveButton.invoke()
                }
            ) {
                Text("Save")
            }
        }

    )
}


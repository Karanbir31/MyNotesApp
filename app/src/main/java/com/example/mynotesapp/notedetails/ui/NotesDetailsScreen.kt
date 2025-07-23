package com.example.mynotesapp.notedetails.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognizerIntent
import android.speech.tts.UtteranceProgressListener
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mynotesapp.R
import com.example.mynotesapp.notedetails.TTSHelper
import com.example.mynotesapp.notedetails.TTSListener

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


    val requestRecordAudioLuncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            noteDetailsViewModel.onClickMicButton()
        } else {
            Toast.makeText(
                context,
                "Allow microphone permission in device setting",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    val speechLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val resultList =
                result.data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)

            val spokenText = resultList?.get(0) ?: "No result"
            Toast.makeText(context, spokenText, Toast.LENGTH_LONG).show()
            noteDetailsViewModel.updateNoteContentWithSpokenText(spokenText)
        }
    }

    LaunchedEffect(Unit) {
        noteDetailsViewModel.triggerSpeechRecognizer.collect {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-In")
                putExtra(RecognizerIntent.EXTRA_PROMPT, "start")
            }

            speechLauncher.launch(intent)
        }
    }

    Scaffold(
        topBar = {
            NotesDetailsScreenAppBar(
                onClickNavigationIcon = {
                    navController.popBackStack()
                },

                onClickSaveButton = {
                    if (noteId >= 0) {
                        noteDetailsViewModel.updateNoteOnDataBase()
                    } else {
                        noteDetailsViewModel.saveNewNote()
                    }
                    navController.popBackStack()
                }
            )
        },

        floatingActionButton = {
            FloatingMicButton {
                if (checkRecordAudioPermission(context)) {
                    noteDetailsViewModel.onClickMicButton()
                } else {
                    requestRecordAudioLuncher.launch(Manifest.permission.RECORD_AUDIO)
                }
            }
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
    val context = LocalContext.current
    val note = noteDetailsViewModel.note.collectAsState().value
    val shouldPlayTextAudio = remember { mutableStateOf(false) }

    val tts = remember {
        TTSHelper(
            context = context,
            object : TTSListener{
                 override fun onStart() {   }
                 override fun onDone() { shouldPlayTextAudio.value = false }
                 override fun onError() {  }
            })
    }


    val playAudioIcon = if (shouldPlayTextAudio.value) {
        // start audio
        tts.speakOut(note.content)
        painterResource(R.drawable.outline_pause_circle_24)

    } else {
        painterResource(R.drawable.outline_play_circle_24)
    }


    DisposableEffect(Unit) {
        onDispose {
            tts.shutdown()
        }
    }



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
            modifier = Modifier.fillMaxWidth(),

            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        IconButton(
            onClick = {
                shouldPlayTextAudio.value = true
            }
        ) {
            Icon(
                painter = playAudioIcon,
                "play audio"
            )
        }

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
                .weight(1f),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesDetailsScreenAppBar(onClickSaveButton: () -> Unit, onClickNavigationIcon: () -> Unit) {
    TopAppBar(
        title = {

        },
        navigationIcon = {
            IconButton(
                onClick = { onClickNavigationIcon.invoke() }
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_back_24),
                    "back button"
                )
            }
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

@Composable
fun FloatingMicButton(onClickMicButton: () -> Unit) {
    FloatingActionButton(
        content = {
            Icon(
                painterResource(R.drawable.baseline_mic_24),
                contentDescription = "Add new note"
            )
        },
        onClick = {
            onClickMicButton.invoke()
        }
    )
}

fun checkRecordAudioPermission(context: Context): Boolean {

    return (
            ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)

                    == PackageManager.PERMISSION_GRANTED
            )

}
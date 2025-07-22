package com.example.mynotesapp.notedetails

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mynotesapp.R

@Composable
fun NotesDetailsScreen(noteId: Long, navController: NavController) {
    var pinButtonState by remember { mutableStateOf(false) }
    val icon = if (pinButtonState) R.drawable.ic_pin_selected else R.drawable.ic_pin_unselected

    var noteTitle by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Row {
                TextField(
                    value = noteTitle,
                    onValueChange = {
                        noteTitle = it
                    },
                    label = {
                        Text("Note title")
                    },
                    maxLines = 2,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = {
                        pinButtonState = !pinButtonState
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

    ) {
        var noteContent by remember { mutableStateOf("") }




        TextField(
            value = noteContent,
            onValueChange = {
                noteContent = it
            },
            label = {
                Text("Note content")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
        )
    }

}


package com.example.mynotesapp.notedetails.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat


@Composable
fun LaunchSpeechRecognizer(onRecivedSpokenText : (String) -> Unit) {
    val context = LocalContext.current
    var isPermissionGrantedState by remember { mutableStateOf(false) }
    var shouldStartRecognizer by remember { mutableStateOf(false) }


    val requestRecordAudioLuncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->

        isPermissionGrantedState = isGranted
        if (isGranted) shouldStartRecognizer = true
    }

    val speechLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val resultList =
                result.data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)

            val spokenText = resultList?.get(0) ?: "No result"

            onRecivedSpokenText.invoke(spokenText)
        }
    }

    LaunchedEffect(shouldStartRecognizer) {
        if (shouldStartRecognizer) {
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

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            isPermissionGrantedState = true
            shouldStartRecognizer = true
        } else {
            requestRecordAudioLuncher.launch(Manifest.permission.RECORD_AUDIO)
        }

    }
}

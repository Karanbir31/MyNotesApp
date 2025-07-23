package com.example.mynotesapp.notedetails

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.widget.Toast
import java.util.Locale

interface TTSListener{
    fun onDone()

    fun onError()

    fun onStart()
}

class TTSHelper(
    private val context: Context,
    private val ttsListener : TTSListener? = null
) : TextToSpeech.OnInitListener {

    private val tts: TextToSpeech = TextToSpeech(context, this)

    private var isReady = false

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.ENGLISH)
            isReady =
                result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED

            if (isReady){
                tts.setOnUtteranceProgressListener(object : UtteranceProgressListener(){
                    override fun onDone(utteranceId: String?) {
                        ttsListener?.onDone()
                    }

                    override fun onError(utteranceId: String?) {
                       ttsListener?.onError()
                    }


                    override fun onStart(utteranceId: String?) {
                        ttsListener?.onStart()
                    }

                })
            }

        } else {
            Toast.makeText(context, "Text to speech not supported", Toast.LENGTH_SHORT).show()
        }
    }

    fun speakOut(text: String) {
        if (isReady) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    fun shutdown() {
        tts.stop()
        tts.shutdown()
    }
}
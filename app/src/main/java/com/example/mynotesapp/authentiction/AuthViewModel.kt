package com.example.mynotesapp.authentiction

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepo
) : ViewModel() {
    private val tag = "AuthViewModel"

    fun  createUser(email : String, password : String){
        try {
            viewModelScope.launch {
                repo.createUserWithEmailPassword(email, password)
            }
        }catch (e: Exception){
            Log.e(tag, "createUser: ${e.message}", e)
        }
    }

    fun  signUser(email : String, password : String, onComplete : () -> Unit){
        try {
            viewModelScope.launch {
                repo.signInUserWithEmailPassword(email, password)
                onComplete.invoke()
            }
        }catch (e: Exception){
            Log.e(tag, "signUser: ${e.message}", e)
        }
    }
}
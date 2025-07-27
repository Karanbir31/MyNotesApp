package com.example.mynotesapp.authentiction

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebaseAuth : FirebaseAuthHelper
) : ViewModel() {
    private val tag = "AuthViewModel"
    

    fun  createUser(email : String, password : String, onComplete : (Boolean) -> Unit){
        try {
            viewModelScope.launch {
                firebaseAuth.createUserInWithEmailPassword(email, password){
                    // on complete
                    onComplete.invoke(it)
                }
            }
        }catch (e: Exception){
            Log.e(tag, "createUser: ${e.message}", e)
        }
    }

    fun  signUser(email : String, password : String,  onComplete : (Boolean) -> Unit){
        try {
            viewModelScope.launch {
                firebaseAuth.signInWithEmailPassword(email, password){
                    // on complete
                    onComplete.invoke(it)

                }
            }
        }catch (e: Exception){
            Log.e(tag, "signUser: ${e.message}", e)
        }
    }
    
    
    
}
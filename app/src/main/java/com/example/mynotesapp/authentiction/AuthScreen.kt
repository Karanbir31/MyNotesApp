package com.example.mynotesapp.authentiction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mynotesapp.R
import com.example.mynotesapp.navigation.NavigationRoutes


@Composable
fun AuthScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    AuthScreenUiInit(
        onClickSubmit = { email, password, isSignUp ->
            if (isSignUp) {
                authViewModel.createUser(email, password)
            } else {
                authViewModel.signUser(email, password) {
                    navController.navigate(NavigationRoutes.AllNotesScreen.routes) {
                        // navController.popBackStack()
                    }
                }
            }


        }
    )

}

@Composable
fun AuthScreenUiInit(
    onClickSubmit: (email: String, password: String, isSignUp: Boolean) -> Unit
) {
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var isSignUp by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Row(Modifier.fillMaxWidth()) {
            AuthScreenButton(
                btnText = "Sign UP",
                buttonColorState = isSignUp,
                modifier = Modifier.weight(1f).padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                isSignUp = true
            }

            AuthScreenButton(
                btnText = "Sign In",
                buttonColorState = !isSignUp,
                modifier = Modifier.weight(1f).padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                isSignUp = false
            }

        }


        // Email TextField (common for both)
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = showError && !validateEmail(email)
        )
        if (showError && !validateEmail(email)) {
            Text(
                "Enter a valid email address.",
                color = Color.Red,
                style = MaterialTheme.typography.labelMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Password TextField (common for both)
        PasswordTextField(
            password = password,
            onPasswordChange = { password = it },
            showError = showError
        )

        Spacer(modifier = Modifier.height(16.dp))

       AuthScreenButton(
           btnText = if (isSignUp) "SignUp" else "Sign In",
           buttonColorState = true,
           modifier = Modifier.fillMaxWidth().padding(16.dp)
       ) {

           if (validateEmail(email) && validatePassword(password)){
               onClickSubmit.invoke(email, password, isSignUp)
           }else{
               showError = true
           }

       }

    }

}

@Composable
fun PasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
    showError: Boolean,
    modifier: Modifier = Modifier
) {
    var passwordVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        modifier = modifier.fillMaxWidth(),
        label = { Text("Password") },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    painter = if (passwordVisible) painterResource(id = R.drawable.ic_password_visibel)
                    else painterResource(id = R.drawable.ic_password_visibel),
                    contentDescription = if (passwordVisible) "Hide password" else "Show password"
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        isError = showError && !validatePassword(password)
    )
    if (showError && !validatePassword(password)) {
        Text(
            "Password must be at least 8 characters, with letters, numbers, and special characters.",
            color = Color.Red, style = MaterialTheme.typography.labelMedium
        )
    }
}

// Validation Functions
fun validatePassword(input: String): Boolean {
    return input.length >= 4 &&
            input.any { it.isDigit() }
            &&
            input.any { it.isLetter() } &&
            input.any { !it.isLetterOrDigit() }
}

fun validateEmail(input: String): Boolean {
    return input.contains("@") && input.endsWith(".com")
}

fun validatePhoneNumber(input: String): Boolean {
    return input.length == 10 && input.all { it.isDigit() }
}


@Composable
fun AuthScreenButton(btnText : String, buttonColorState : Boolean, modifier: Modifier , onButtonClick : () -> Unit) {

    val buttonColors = ButtonDefaults.outlinedButtonColors(
        containerColor = if (buttonColorState) MaterialTheme.colorScheme.primary.copy(
            alpha = 0.7f
        ) else MaterialTheme.colorScheme.secondaryContainer,

        contentColor = if (buttonColorState) MaterialTheme.colorScheme.onPrimaryContainer
        else MaterialTheme.colorScheme.onSecondaryContainer
    )


    OutlinedButton(
        onClick = { onButtonClick.invoke() },
        modifier = modifier,
        colors = buttonColors
    ) {
        Text(
            text = btnText,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
        )
    }


}


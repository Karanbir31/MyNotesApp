package com.example.mynotesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mynotesapp.navigation.AppNavGraph
import com.example.mynotesapp.navigation.NavigationRoutes
import com.example.mynotesapp.ui.theme.MyNotesAppTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            MyNotesAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ApplicationUI(
                        navController = navController,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun ApplicationUI(navController: NavHostController, modifier: Modifier = Modifier) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val startDestination =
        if (currentUser != null) NavigationRoutes.AllNotesScreen.routes
        else NavigationRoutes.AuthScreen.routes

    AppNavGraph(navController, startDestination ,modifier)
}

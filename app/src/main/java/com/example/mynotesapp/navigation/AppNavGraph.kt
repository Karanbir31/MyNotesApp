package com.example.mynotesapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mynotesapp.authentiction.AuthScreen
import com.example.mynotesapp.notedetails.ui.NotesDetailsScreen
import com.example.mynotesapp.notes.ui.AllNotesScreen

@Composable
fun AppNavGraph(navController: NavHostController, startDestination : String ,modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.AllNotesScreen.routes
    ) {

        composable(
            route = NavigationRoutes.AuthScreen.routes
        ) {
            AuthScreen(navController)
        }
        composable(
            route = NavigationRoutes.AllNotesScreen.routes
        ) {
            AllNotesScreen(navController)
        }

        composable(
            route = NavigationRoutes.NotesDetails.routes,
            arguments = listOf(navArgument("noteId") { type = NavType.LongType })
        ) { navBackStackEntry ->
            val noteId = navBackStackEntry.arguments?.getLong("noteId") ?: 1L
            NotesDetailsScreen(noteId, navController)
        }


    }

}
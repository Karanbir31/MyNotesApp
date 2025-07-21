package com.example.mynotesapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mynotesapp.notedetails.NotesDetails
import com.example.mynotesapp.notes.ui.AllocationNotesScreen

@Composable
fun AppNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.AllNotesScreen.routes
    ) {

        composable(
            route = NavigationRoutes.AllNotesScreen.routes
        ) {
            AllocationNotesScreen(navController)
        }

        composable(
            route = NavigationRoutes.NotesDetails.routes,
            arguments = listOf(navArgument("noteId") { type = NavType.LongType })
        ) { navBackStackEntry ->
            val noteId = navBackStackEntry.arguments?.getLong("noteId") ?: 1L
            NotesDetails(noteId, navController)
        }


    }

}
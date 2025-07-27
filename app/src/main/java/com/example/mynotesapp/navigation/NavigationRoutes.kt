package com.example.mynotesapp.navigation

sealed class NavigationRoutes(val routes : String) {

    data object AllNotesScreen : NavigationRoutes("all_notes_screen")

    data object AuthScreen : NavigationRoutes("auth_screen")

    data object NotesDetails : NavigationRoutes("notes_details/{noteId}"){
        fun createRoute(noteId : Long) : String = "notes_details/$noteId"
    }

}
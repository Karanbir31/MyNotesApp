package com.example.mynotesapp.navigation

sealed class NavigationRoutes(val routes : String) {

    object AllNotesScreen : NavigationRoutes("all_notes_screen")

    object NotesDetails : NavigationRoutes("notes_details/{noteId}"){
        fun createRoute(noteId : Long) : String = "notes_details/$noteId"
    }

}
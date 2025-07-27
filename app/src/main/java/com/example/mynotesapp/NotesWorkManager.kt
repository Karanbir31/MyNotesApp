package com.example.mynotesapp

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.mynotesapp.notes.data.NotesRepoImp
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotesWorkManager @Inject constructor(
    private val workerParams: WorkerParameters,
    private val repoImp: NotesRepoImp,
    @ApplicationContext private val context: Context
) : Worker(context, workerParams) {


    override fun doWork(): Result {

        return Result.success()
    }


}
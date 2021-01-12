package com.example.anatomieapp.Quizzes

import android.content.Context
import androidx.lifecycle.LiveData

class ResultsRepository(context: Context) {

    private var resultsDao: ResultsDao

    init {
        val quizRoomDatabase = ResultsRoomDatabase.getDatabase(context)
        resultsDao = quizRoomDatabase!!.quizDao()
    }

     fun getAllResults(): LiveData<List<Results>> {
        return resultsDao.getAllResults()

    }

    suspend fun insertQuiz(quiz: Results) {
        resultsDao.insertQuiz(quiz)
    }

    suspend fun deleteQuiz(quiz: Results) {
        resultsDao.deleteQuiz(quiz)
    }


    suspend fun updateQuiz(quiz: Results) {
        resultsDao.updateQuiz(quiz)
    }

    suspend fun deleteAllResults() {
        resultsDao.deleteAllResults()
    }


}
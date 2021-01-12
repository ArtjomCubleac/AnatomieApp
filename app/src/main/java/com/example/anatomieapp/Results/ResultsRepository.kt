package com.example.anatomieapp.Quizzes

import android.content.Context



class ResultsRepository(context: Context) {

    private var resultsDao: ResultsDao

    init {
        val quizRoomDatabase = ResultsRoomDatabase.getDatabase(context)
        resultsDao = quizRoomDatabase!!.quizDao()
    }

    suspend fun getAllQuizzes(): List<Results> {
        return resultsDao.getAllQuizzes()
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

    suspend fun deleteAllQuizzes() {
        resultsDao.deleteAllQuizzes()
    }


}
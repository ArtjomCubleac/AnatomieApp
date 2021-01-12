package com.example.anatomieapp.Quizzes

import androidx.room.*

@Dao
interface ResultsDao {

    @Query("SELECT * FROM quizTable")
    suspend fun getAllQuizzes(): List<Results>

    @Insert
    suspend fun insertQuiz(quiz: Results)

    @Delete
    suspend fun deleteQuiz(quiz: Results)

    @Update
    suspend fun updateQuiz(quiz: Results)

    @Query("DELETE FROM quizTable")
    suspend fun deleteAllQuizzes()

}

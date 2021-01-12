package com.example.anatomieapp.Quizzes

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ResultsDao {

    @Query("SELECT * FROM quizTable")
     fun getAllResults(): LiveData<List<Results>>


    @Insert
    suspend fun insertQuiz(quiz: Results)

    @Delete
    suspend fun deleteQuiz(quiz: Results)

    @Update
    suspend fun updateQuiz(quiz: Results)
    

    @Query("DELETE FROM quizTable")
    suspend fun deleteAllResults()

}

package com.example.anatomieapp.Quizzes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "QuizTable")
data class Results(

    @ColumnInfo(name = "quizNumber")
    var quizNumber: Int,
    @ColumnInfo(name = "quizProgress")
    var quizProgress: Boolean,


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "quizId")
    var id: Long? = null
)

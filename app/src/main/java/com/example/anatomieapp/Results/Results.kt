package com.example.anatomieapp.Quizzes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "QuizTable")
data class Results(

    @ColumnInfo(name = "questionText")
    var questionText: String,
    @ColumnInfo(name = "questionAnswer")
    var questionAnswer: String,


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "quizId")
    var id: Long? = null
)
//Questions for the quiz
//companion object {
//    val LEVEL1QUESTIONS = arrayOf(
//        "A 'val' and 'var' are the same",
//        "Mobile Application Development grants 12 ETCS. ",
//        "A Unit in Kotlin corresponds to a void in Java",
//        "In Kotlin 'when' replaces the 'the switch' operator in Java.",
//    )
//
//    //Answers for the quiz
//    val LEVEL1ANSWERS =  arrayOf(
//        "A 'val' and 'var' are the same",
//        "Mobile Application Development grants 12 ETCS. ",
//        "A Unit in Kotlin corresponds to a void in Java",
//        "In Kotlin 'when' replaces the 'the switch' operator in Java.",
//    )
//}
//
//}
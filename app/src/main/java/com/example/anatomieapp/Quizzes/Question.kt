package com.example.anatomieapp.Quizzes

import com.google.firebase.database.DataSnapshot
import java.io.Serializable
import java.lang.Exception

class Question  (snapshot: DataSnapshot?) : Serializable {
    var id = ""
    var question: Long = 0
    var answer = ""
    var done = false


    init {
        try {
            val data:HashMap<String, Any> = snapshot!!.value as HashMap<String, Any>
            question = data["question"] as Long
            answer = data["answer"] as String
            done = data["done"] as Boolean
         } catch (e:Exception){

        }
    }
}



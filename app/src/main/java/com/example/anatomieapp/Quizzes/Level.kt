package com.example.anatomieapp.Quizzes

import android.util.Log
import com.google.firebase.database.DataSnapshot
import java.io.Serializable
import java.lang.Exception
import java.security.Key

class Level  (snapshot: DataSnapshot?) : Serializable {
    var id = ""
    var questions = ArrayList<Question>()
    var progress: Long = 0

    init {
        try {
            val data:HashMap<String, Any> = snapshot!!.value as HashMap<String, Any>
            id = snapshot.key?: ""
            val qs = data["Questions"] as HashMap<String, Any>
            for (key in qs.keys){
                val q = qs[key] as HashMap<String, Any>
                val question= Question(snapshot = null)
                question.id = key
                question.question = q["question"] as Long
                question.answer = q["answer"] as String
                question.done = q["done"] as Boolean
                questions.add(question)
            }
            progress = data["progress"] as Long
        } catch (e:Exception){

        }
    }
}
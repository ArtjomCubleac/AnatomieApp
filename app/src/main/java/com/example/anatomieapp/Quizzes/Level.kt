package com.example.anatomieapp.Quizzes

import com.google.firebase.database.DataSnapshot
import java.io.Serializable

//In this class the Firebase Realtime Database data for a Level  gets retrieved
class Level  (snapshot: DataSnapshot?) : Serializable {
    //FireBase does not store Integer only Longs and Strings
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
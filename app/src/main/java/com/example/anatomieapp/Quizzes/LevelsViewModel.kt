package com.example.anatomieapp.Quizzes

import android.util.Log
import com.google.firebase.database.*
import java.util.*

object LevelsViewModel: Observable() {

    private var levels: List<Level> = emptyList()

    private fun getDatabaseRef(): DatabaseReference? {
        return FirebaseDatabase.getInstance().reference.child("Levels")
    }

    init {
        getDatabaseRef()?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val data: ArrayList<Level> = ArrayList()
                    for (runData: DataSnapshot in snapshot.children) {
                        try {
                            data.add(Level(runData))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    levels = data
                    setChanged()
                    notifyObservers()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            override fun onCancelled(p0: DatabaseError) {
                Log.i("LevelsModel", p0.message)
            }
        })

    }

    fun getLevel(id: String) : Level? {
        if(levels.isNullOrEmpty())
            getAllLevels()
        try {
            for (run in levels) {
                if (run.id == id)
                    return run
            }
        } catch (e: NullPointerException) {
            Log.e("LevelsModel", "Level with id $id not found!")
        }
        return null
    }

    fun getQuestion(idLevel: String, idQuestion: String) : Question? {
        if(levels.isNullOrEmpty())
            getAllLevels()
        try {
            for (run in levels) {
                if (run.id == idLevel)

                    for(question in run.questions)
                        if(question.id == idQuestion) {
                            return question
                        }
            }
        } catch (e: NullPointerException) {
            Log.e("LevelsModel", "Level with id $idLevel not found!")
        }
        return null
    }

    fun getAllLevels(): List<Level> {
        return levels
    }



    fun updateQuestion(question: Question, levelId: String) {
        val updatedUser = getDatabaseRef()!!.child(levelId).child("Questions").child(question.id)
        updatedUser.child("question").setValue(question.question)
        updatedUser.child("answer").setValue(question.answer)
        updatedUser.child("done").setValue(question.done)
    }

    fun updateLevel(level: Level) {
        val updatedLevel = getDatabaseRef()!!.child(level.id)
        updatedLevel.child("progress").setValue(level.progress)
    }


}
package com.example.anatomieapp.Quizzes

import android.util.Log
import com.google.firebase.database.*
import java.util.*

object LevelsViewModel: Observable() {

    private var levels: List<Level> = emptyList()

    //Make connection with Realtime Database
    private fun getDatabaseRef(): DatabaseReference? {
        return FirebaseDatabase.getInstance().reference.child("Levels")
    }

    init {
        getDatabaseRef()?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val data: ArrayList<Level> = ArrayList()
                    for (levelData: DataSnapshot in snapshot.children) {
                        try {
                            data.add(Level(levelData))
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

    //Gets specific level from levels list
    fun getLevel(id: String) : Level? {
        if(levels.isNullOrEmpty())
            getAllLevels()
        try {
            for (level in levels) {
                if (level.id == id)
                    return level
            }
        } catch (e: NullPointerException) {
            Log.e("LevelsModel", "Level with id $id not found!")
        }
        return null
    }

    //Returns all levels
    fun getAllLevels(): List<Level> {
        return levels
    }

    //Updates question, I have used this to set the value of done to true
    fun updateQuestion(question: Question, levelId: String) {
        val updatedLevel = getDatabaseRef()!!.child(levelId).child("Questions").child(question.id)
        updatedLevel.child("question").setValue(question.question)
        updatedLevel.child("answer").setValue(question.answer)
        updatedLevel.child("done").setValue(question.done)
    }

    //Updates Level progress
    fun updateLevel(level: Level) {
        val updatedLevel = getDatabaseRef()!!.child(level.id)
        updatedLevel.child("progress").setValue(level.progress)
    }


}
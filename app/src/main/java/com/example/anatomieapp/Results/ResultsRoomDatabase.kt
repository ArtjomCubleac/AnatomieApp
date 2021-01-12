package com.example.anatomieapp.Quizzes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Results::class], version = 1, exportSchema = false)
abstract class ResultsRoomDatabase : RoomDatabase() {

    abstract fun quizDao(): ResultsDao

    companion object {
        private const val DATABASE_NAME = "QUIZZES_DATABASE"

        @Volatile
        private var resultsRoomDatabaseInstance: ResultsRoomDatabase? = null

        fun getDatabase(context: Context): ResultsRoomDatabase? {
            if (resultsRoomDatabaseInstance == null) {
                synchronized(ResultsRoomDatabase::class.java) {
                    if (resultsRoomDatabaseInstance == null) {
                        resultsRoomDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            ResultsRoomDatabase::class.java, DATABASE_NAME
                        )
                            .build()
                    }
                }
            }
            return resultsRoomDatabaseInstance
        }
    }

}
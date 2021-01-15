package com.example.anatomieapp.Quizzes

import android.content.Context
import android.provider.ContactsContract
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
                            .fallbackToDestructiveMigration()
                            .addCallback(object : RoomDatabase.Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    resultsRoomDatabaseInstance?.let { database ->
                                        CoroutineScope(Dispatchers.IO).launch {
                                            for (i in 1..15) {
                                                database.quizDao().insertQuiz(
                                                    Results(i, false),
                                                    )
                                            }
                                        }
                                    }
                                }
                            })

                            .build()
                    }
                }
            }
            return resultsRoomDatabaseInstance
        }
    }
}

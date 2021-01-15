package com.example.anatomieapp.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.anatomieapp.Quizzes.Results
import com.example.anatomieapp.Quizzes.ResultsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(application: Application) : AndroidViewModel(application){

    private val mainScope = CoroutineScope(Dispatchers.Main)
    private val repository = ResultsRepository(application.applicationContext)

    val results: LiveData<List<Results>> = repository.getAllResults()

    fun updateMotivation(toString: String): String {
        return toString
    }
    fun updateResult(number: Int, result: Boolean) {
        val newResult = Results(
                quizNumber = number + 1,
                quizProgress = result,
                id = number.toLong() + 1,
        )
        mainScope.launch {
            withContext(Dispatchers.IO) {
                repository.updateQuiz(newResult)
            }
        }
    }

}

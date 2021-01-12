package com.example.anatomieapp.ui.home

import android.app.Application
import android.provider.ContactsContract
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.anatomieapp.Quizzes.Results
import com.example.anatomieapp.Quizzes.ResultsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(application: Application) : AndroidViewModel(application){

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val repository = ResultsRepository(application.applicationContext)

    val results: LiveData<List<Results>> = repository.getAllResults()


    fun updateResult(number: Int, result: Boolean) {
        val newResult = Results(
            quizNumber = number,
            quizProgress = result
        )

        ioScope.launch {
            withContext(Dispatchers.IO) {
                repository.updateQuiz(newResult)
            }
        }
    }



}

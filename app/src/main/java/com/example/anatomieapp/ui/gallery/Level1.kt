package com.example.anatomieapp.ui.gallery


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.anatomieapp.Quizzes.Quizzes
import com.example.anatomieapp.R
import kotlinx.android.synthetic.main.fragment_level1.*


private val quizzes = arrayListOf<Quizzes>()

private val questionCounter = 0
private var questionCountTotal = 0
private var currentQuestion = 0

class GalleryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_level1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        QuizLogic()

    }


    private fun QuizLogic(){
        questionCountTotal = Quizzes.LEVEL1QUESTIONS.size;
        Quizzes.LEVEL1QUESTIONS.shuffle()
    }



}
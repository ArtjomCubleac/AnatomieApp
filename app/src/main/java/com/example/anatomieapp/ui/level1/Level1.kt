package com.example.anatomieapp.ui.level1


import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anatomieapp.Quizzes.Quiz
import com.example.anatomieapp.Quizzes.Quizzes
import com.example.anatomieapp.Quizzes.QuizzesAdapter

import com.example.anatomieapp.databinding.FragmentLevel1Binding
import com.example.anatomieapp.ui.home.HomeViewModel
import com.google.android.material.snackbar.Snackbar

import kotlinx.android.synthetic.main.fragment_level1.*
import kotlin.random.Random

class Level1 : Fragment() {

    private val quizzes = arrayListOf<Quiz>()
    private val quizzesAdapter = QuizzesAdapter(quizzes) { quiz : Quiz-> partItemClicked(quiz) }
    private lateinit var binding: FragmentLevel1Binding
    private val viewModel: HomeViewModel by viewModels()
    private var quizIndex = 0
    private val quizDone = arrayListOf<Quiz>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLevel1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }


    private fun initViews() {
        //Initialize the recycler view with a linear layout manager, adapter
        binding.rvAnswers.layoutManager =
            LinearLayoutManager(
                this.context, RecyclerView.VERTICAL, false
            )
        binding.rvAnswers.adapter = quizzesAdapter
        binding.rvAnswers.addItemDecoration(
            DividerItemDecoration(
                this.context,
                DividerItemDecoration.VERTICAL
            )
        )

        for (i in Quizzes.LEVEL1QUESTIONS.indices) {
            quizzes.add(
                Quiz(
                    Quizzes.LEVEL1QUESTIONS[i],
                    Quizzes.LEVEL1ANSWERS[i]
                )
            )
        }
        Answer().attachToRecyclerView(rvAnswers)
        setRandomQuestion()
    }

     private fun Answer(): ItemTouchHelper {
            //Creates touchhelper which enables swiping left or right.
            val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)  {
                // Enables or Disables the ability to move items up and down.
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }
                // Callback triggered when a user swiped an item.
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    if (position == quizIndex) {
                        setRandomQuestion()
                        viewModel.updateResultLevel1(position  ,true)

                        Snackbar.make(questionNumber, "Goedzo! Ga zo door!" + quizzes.get(position).toString(), Snackbar.LENGTH_LONG)
                            .show()
                        vibratePhone()
                    } else {
                    Snackbar.make(questionNumber, "Fout! Probeer het nog eens!", Snackbar.LENGTH_SHORT)
                         .show()
                    }
                    quizzesAdapter.notifyDataSetChanged()
                }
            }
            return ItemTouchHelper(callback)
        }



private fun setRandomQuestion() {
      val quizInt = (Random.nextInt(0,quizzes.size))
        if(quizDone.contains(quizzes[quizInt])){
            setRandomQuestion()
        } else {
            quizIndex = quizInt
            binding.questionNumber.text = quizzes.get(quizIndex).questionText.toString()
            quizDone.add(quizzes.get(quizIndex))
        }
    }

    fun Fragment.vibratePhone() {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(400, 255))
        } else {
            vibrator.vibrate(200)
        }
    }

    private fun partItemClicked(quiz: Quiz) {
        Snackbar.make(questionNumber, "Swipe naar links of naar rechts, als je denkt dat het antwoord goed is!", Snackbar.LENGTH_SHORT)
            .show()
    }
}




package com.example.anatomieapp.ui.level1


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anatomieapp.Quizzes.*
import com.example.anatomieapp.R

import com.example.anatomieapp.databinding.FragmentLevel1Binding
import com.google.android.material.snackbar.Snackbar

import kotlinx.android.synthetic.main.fragment_level1.*
import kotlin.math.ceil
import kotlin.random.Random

class Level1 : Fragment() {

    private val quizzes = arrayListOf<Question>()
    private lateinit var binding: FragmentLevel1Binding
    private var quizIndex = 0
    private val quizDone = arrayListOf<Question>()
    private var quizzesAdapter = QuizzesAdapter(quizDone) { question : Question-> partItemClicked(question) }

    private var currentLevel : Level = Level(snapshot = null)

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
        currentLevel = LevelsViewModel.getLevel("1")!!
        quizzesAdapter = QuizzesAdapter(currentLevel.questions) { question : Question-> partItemClicked(question) }
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

        if (LevelsViewModel.getLevel("1") == null){
            Log.d("tag", "IK BEN NUL")
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
                        currentLevel.questions.get(position).done = true
                        LevelsViewModel.updateQuestion(currentLevel.questions.get(position), currentLevel.id)
                        currentLevel.progress += ceil( 100.0 /currentLevel.questions.size).toInt()
                        LevelsViewModel.updateLevel(currentLevel)
                        Snackbar.make(questionNumber, "Goedzo! Ga zo door!" + currentLevel.questions.get(position).toString(), Snackbar.LENGTH_LONG)
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
      val quizInt = (Random.nextInt(0,currentLevel.questions.size))
        if(quizDone.contains(currentLevel.questions[quizInt])) {
            if (quizDone.size != currentLevel.questions.size) {
                setRandomQuestion()
            } else {
                QuizDoneAlert()
            }
        } else if (currentLevel.questions[quizInt].done){
            quizDone.add(currentLevel.questions[quizInt])
            setRandomQuestion()
        } else {
            quizIndex = quizInt
            binding.questionNumber.text =  currentLevel.questions.get(quizIndex).question.toString()
            quizDone.add(currentLevel.questions.get(quizIndex))
        }
    }

    fun Fragment.vibratePhone() {
        val vibrator = this.context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(400, 255))
        } else {
            vibrator.vibrate(200)
        }
    }

    private fun partItemClicked(quiz: Question) {
        Snackbar.make(questionNumber, "Swipe naar links of naar rechts, als je denkt dat het antwoord goed is!", Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun QuizDoneAlert(){
        // build alert dialog
        val dialogBuilder = AlertDialog.Builder(this.context)

        // set message of alert dialog
        dialogBuilder.setMessage("Weet je zeker dat je je vooruitgang wilt verwijderen?")
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("Ga naar volgende level", DialogInterface.OnClickListener {
                    dialog, id -> findNavController().navigate(R.id.nav_slideshow)
                })

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Begin opnieuw")
        // show alert dialog
        alert.show()
    }
}




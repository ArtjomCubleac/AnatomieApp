package com.example.anatomieapp.ui.home

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import com.example.anatomieapp.Quizzes.*
import com.example.anatomieapp.R
import com.example.anatomieapp.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class HomeFragment() : Fragment(), Observer {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.setBackgroundColor(Color.WHITE);
        initViews()
    }

    private fun initViews() {

        val fab: View = binding.floatingActionButton
        fab.setOnClickListener { view ->
            // build alert dialog
            val dialogBuilder = AlertDialog.Builder(this.context)

            // set message of alert dialog
            dialogBuilder.setMessage("Weet je zeker dat je je vooruitgang wilt verwijderen?")
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("Ja, ik weet het zeker!", DialogInterface.OnClickListener {
                        dialog, id -> accepted()
                })
                // negative button text and action
                .setNegativeButton("Annuleer", DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                })

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle("Begin opnieuw")
            // show alert dialog
            alert.show()
        }
        LevelsViewModel.addObserver(this)
    }

    private fun accepted(){
        for(level in LevelsViewModel.getAllLevels()){
            for(question in level.questions){
                question.done = false
                LevelsViewModel.updateQuestion(question, level.id)
            }
            level.progress = 0
            LevelsViewModel.updateLevel(level)
        }
    }

    private fun loadProgressBar(){
        progressBarLevel1.max= 100
        progressBarLevel2.max = 100

        for (level in LevelsViewModel.getAllLevels()){

            when (level.id){
                "1" ->   ObjectAnimator.ofInt(progressBarLevel1,"progress", level.progress.toInt()).setDuration(700).start()
                "2" ->   ObjectAnimator.ofInt(progressBarLevel2,"progres1", level.progress.toInt()).setDuration(700).start()
            }
        }
    }
    override fun update(o: Observable?, arg: Any?) {
       loadProgressBar()
    }

    override fun onStart() {
        super.onStart()
        loadProgressBar()
    }

    override fun onResume() {
        super.onResume()
        loadProgressBar()
    }


}


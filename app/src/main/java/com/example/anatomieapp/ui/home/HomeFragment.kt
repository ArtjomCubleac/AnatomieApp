package com.example.anatomieapp.ui.home

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.anatomieapp.Quizzes.LevelsViewModel
import com.example.anatomieapp.R
import com.example.anatomieapp.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class HomeFragment() : Fragment(), Observer {

    private lateinit var binding: FragmentHomeBinding
    //Used for tracking progress
    private var MAX = 100;
    //Used for tracking progress
    private var ZERO = 0;

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
    //Used for fab onclick handling adding an observer for the LevelsViewModel
    private fun initViews() {
        fabClickListener()
        LevelsViewModel.addObserver(this)
    }

    private fun fabClickListener(){
        val fab: View = binding.deleteAll
        fab.setOnClickListener { view ->
            // build alert dialog
            val dialogBuilder = AlertDialog.Builder(this.context)
            // set message of alert dialog
            dialogBuilder.setMessage(R.string.sure_to_delete_progress)
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton(R.string.confirmation, DialogInterface.OnClickListener {
                        dialog, id -> accepted()
                })
                // negative button text and action
                .setNegativeButton(R.string.cancel, DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                })
            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle(R.string.start_again)
            // show alert dialog
            alert.show()
        }
    }

    //If the user accpets the fab (deleting al progress), all the questions progress will be set to false and the level progress to 0
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

    //This function sets the progress bar on the home screen
    private fun loadProgressBar(){
        //Variables used from above
        progressBarLevel1.max = MAX
        progressBarLevel2.max = MAX

        //This function sets the motivational quote of the progress bar
        for (level in LevelsViewModel.getAllLevels()){
            when (level.id){
                "1" ->   {ObjectAnimator.ofInt(progressBarLevel1,"progress", level.progress.toInt()).setDuration(700).start();
                    if (level.progress.toInt() == ZERO){
                        progress_text.text = getString(R.string.motivation_0)
                    } else if (level.progress.toInt() in 51..99){
                        progress_text.text = getString(R.string.motivation_mid)
                    } else if (level.progress.toInt() > MAX){
                        progress_text.text = getString(R.string.motivation_100)
                    }
                }
                "2" ->   {ObjectAnimator.ofInt(progressBarLevel2,"progress", level.progress.toInt()).setDuration(700).start()
                if (level.progress.toInt() == ZERO){
                    progress_text2.text = getString(R.string.motivation_0)
                } else if (level.progress.toInt() in 51..99){
                    progress_text2.text = getString(R.string.motivation_mid)
                } else if (level.progress.toInt() > MAX){
                    progress_text2.text = getString(R.string.motivation_100)
                }
            }}
        }
    }
    //Functions that are for the observable
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


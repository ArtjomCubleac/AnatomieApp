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
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anatomieapp.Quizzes.*
import com.example.anatomieapp.R
import com.example.anatomieapp.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment() : Fragment() {

    private  val homeViewModel: HomeViewModel by viewModels()
    private val results = arrayListOf<Results>()
    private lateinit var binding: FragmentHomeBinding
    private val resultsAdapter = ResultsAdapter(results)


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
        observeResults()
    }

    private fun initViews() {
        binding.rvResults.layoutManager =
            LinearLayoutManager(
                this.context, RecyclerView.VERTICAL, false
            )
        binding.rvResults.adapter = resultsAdapter
        binding.rvResults.addItemDecoration(
            DividerItemDecoration(
                this.context,
                DividerItemDecoration.VERTICAL
            )
        )

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
    }

    private fun accepted(){
    }

    private fun observeResults() {
        homeViewModel.results.observe(viewLifecycleOwner, { results ->
            this.results.clear()
            this.results.addAll(results)
            loadProgressBar()
            resultsAdapter.notifyDataSetChanged()
        })
    }

    private fun loadProgressBar(){
        progressBarLevel1.max= 15
        var currentGoed = 0;
        for (item in this.results){
            if (item.quizProgress == true){
                currentGoed++;
            }
        }
        when(currentGoed){
            0 -> binding.updateMotivation.text = homeViewModel.updateMotivation(R.string.motivation1.toString());
            1,2,3,4,5,6,7 -> binding.updateMotivation.text = homeViewModel.updateMotivation(R.string.motivation2.toString());
        }

        ObjectAnimator.ofInt(progressBarLevel1,"progress", currentGoed).setDuration(700).start()


    }
}


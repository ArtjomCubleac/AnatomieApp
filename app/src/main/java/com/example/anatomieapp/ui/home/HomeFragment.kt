package com.example.anatomieapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anatomieapp.Quizzes.*
import com.example.anatomieapp.R
import com.example.anatomieapp.databinding.ActivityMainBinding.inflate
import com.example.anatomieapp.databinding.FragmentHomeBinding
import com.example.anatomieapp.databinding.FragmentLevel1Binding


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private val results = arrayListOf<Results>()
    private lateinit var binding: FragmentHomeBinding
    private val resultsAdapter = ResultsAdapter(results)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val textView: TextView = binding.root.findViewById(R.id.text_home)
        binding = FragmentHomeBinding.inflate(layoutInflater)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        //Initialize the recycler view with a linear layout manager, adapter
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

//        for (i in Quizzes.LEVEL1QUESTIONS.indices) {
//            quizzes.add(
//                Quiz(
//                    Quizzes.LEVEL1QUESTIONS[i],
//                    Quizzes.LEVEL1ANSWERS[i]
//                )
//            )
//        }

    }
}
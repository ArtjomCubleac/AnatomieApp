package com.example.anatomieapp.ui.home

import android.graphics.Color
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anatomieapp.Quizzes.*
import com.example.anatomieapp.databinding.FragmentHomeBinding



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

    }

    private fun observeResults() {
        homeViewModel.results.observe(viewLifecycleOwner, { results ->
            this.results.clear()
            this.results.addAll(results)
            resultsAdapter.notifyDataSetChanged()
        })
    }

}

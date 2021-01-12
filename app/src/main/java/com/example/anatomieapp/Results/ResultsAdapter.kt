package com.example.anatomieapp.Quizzes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anatomieapp.R

import com.example.anatomieapp.databinding.ItemResultBinding



class ResultsAdapter(private val results: List<Results>) :
        RecyclerView.Adapter<ResultsAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemResultBinding.bind(itemView)

        fun databind(result: Results) {
            binding.itemAnswer.text = result.quizNumber.toString()
            binding.itemTrueorFalse.text = result.quizProgress.toString()
        }

    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout called simple_list_item_1.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsAdapter.ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_result, parent, false)
        )
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return results.size
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(results[position])
    }


}
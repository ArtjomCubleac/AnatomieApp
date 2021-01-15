package com.example.anatomieapp.Quizzes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anatomieapp.R
import com.example.anatomieapp.databinding.ItemAnswerBinding

class QuizzesAdapter(private val quizzes: List<Quiz>, private val clickListener: (Quiz) -> Unit) :
        RecyclerView.Adapter<QuizzesAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = ItemAnswerBinding.bind(itemView)

        fun databind(quiz: Quiz, clickListener: (Quiz) -> Unit) {
                binding.itemAnswer.text = quiz.questionAnswer
                itemView.setOnClickListener {clickListener(quiz)}
        }
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout called simple_list_item_1.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizzesAdapter.ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_answer, parent, false)
        )
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return quizzes.size
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(quizzes[position],clickListener)
    }


}


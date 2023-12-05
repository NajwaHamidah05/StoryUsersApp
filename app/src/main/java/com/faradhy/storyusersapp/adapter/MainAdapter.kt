package com.faradhy.storyusersapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.faradhy.storyusersapp.data.response.RowItemStory
import com.faradhy.storyusersapp.databinding.RowItemStoryBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainAdapter: PagingDataAdapter<RowItemStory, MainAdapter.MyViewHolder>(DIFF_CALLBACK){

    private var onItemClickCallback: ((RowItemStory) -> Unit)? = null

    fun setOnItemClickCallback(callback: (RowItemStory) -> Unit) {
        this.onItemClickCallback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RowItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        if (user != null) {
            holder.bind(user)
        }
    }

    @Suppress("DEPRECATION")
    inner class MyViewHolder(private val binding: RowItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val user = getItem(position)
                    if (user != null) {
                        onItemClickCallback?.invoke(user)
                    }
                }
            }
        }
        private fun formatDate(dateString: String?): String {
            if (dateString == null) {
                return ""
            }

            try {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                val outputFormat = SimpleDateFormat("dd MMMM yyyy' - 'HH:mm", Locale.getDefault())

                val date: Date = inputFormat.parse(dateString)!!
                return outputFormat.format(date)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return ""
        }

        fun bind(storyItem: RowItemStory) {
            val date = formatDate(storyItem.createdAt)
            binding.usernameStory.text = "${storyItem.name}"
            binding.dateUplStory.text = "${date}"
            binding.descStoryRow.text = "${storyItem.description}"
            Glide.with(itemView)
                .load(storyItem.photoUrl)
                .into(binding.imgStory)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RowItemStory>() {
            override fun areItemsTheSame(oldItem: RowItemStory, newItem: RowItemStory): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: RowItemStory, newItem: RowItemStory): Boolean {
                return oldItem == newItem
            }
        }
    }
}
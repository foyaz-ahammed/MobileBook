package com.play2pay.bookapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.play2pay.bookapp.databinding.BookItemBinding
import com.play2pay.bookapp.models.BookItem


/**
 * [ListAdapter] to show list of book items
 */
class BookListAdapter: ListAdapter<BookItem, BookListAdapter.ViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    /**
     * [DiffUtil.ItemCallback] for [BookItem]
     */
    object DiffCallback: DiffUtil.ItemCallback<BookItem>() {
        override fun areItemsTheSame(oldItem: BookItem, newItem: BookItem) =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: BookItem, newItem: BookItem) =
            oldItem.image == newItem.image
    }

    /**
     * [ViewHolder] for [BookListAdapter]
     */
    class ViewHolder(private val binding: BookItemBinding): RecyclerView.ViewHolder(binding.root) {
        /**
         * Bind function to update UI
         */
        fun bind(item: BookItem) {
            //Set title
            binding.title.text = item.title

            //Set image
            Glide.with(binding.root.context)
                .load(item.image)
                .into(binding.image)

            binding.authorViews.isVisible = item.author.isNotEmpty()
            binding.author.text = item.author
        }
    }
}
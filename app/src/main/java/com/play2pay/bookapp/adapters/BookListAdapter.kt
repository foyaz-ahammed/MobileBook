package com.play2pay.bookapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.play2pay.bookapp.R
import com.play2pay.bookapp.activities.DetailActivity
import com.play2pay.bookapp.databinding.BookItemBinding
import com.play2pay.bookapp.helper.GlideApp
import com.play2pay.bookapp.repository.entities.BookItem

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

    override fun onViewRecycled(holder: ViewHolder) {
        holder.recycle()
    }

    /**
     * [DiffUtil.ItemCallback] for [BookItem]
     */
    object DiffCallback: DiffUtil.ItemCallback<BookItem>() {
        override fun areItemsTheSame(oldItem: BookItem, newItem: BookItem) =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: BookItem, newItem: BookItem) =
            oldItem.imageUrl == newItem.imageUrl
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

            //Set image (show unavailable image if url is empty)
            GlideApp.with(binding.root.context)
                .load(
                    if(item.imageUrl.isNotEmpty()) item.imageUrl
                    else R.drawable.ic_unavailable
                )
                .into(binding.image)

            binding.authorViews.isVisible = item.author.isNotEmpty()
            binding.author.text = item.author

            binding.rootView.setOnClickListener {
                binding.rootView.context.let {
                    val intent = Intent(it, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.EXTRA_BOOK_ITEM, item)
                    }

                    it.startActivity(intent)
                }
            }
        }

        /**
         * Called when the view is recycled in the RecyclerView
         */
        fun recycle() {
            GlideApp.with(binding.root.context).clear(binding.image)
        }
    }
}
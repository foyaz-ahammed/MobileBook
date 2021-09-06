package com.play2pay.bookapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.play2pay.bookapp.R
import com.play2pay.bookapp.databinding.ActivityDetailBinding
import com.play2pay.bookapp.helper.GlideApp
import com.play2pay.bookapp.repository.entities.BookItem

/**
 * Activity for detail screen
 */
class DetailActivity: AppCompatActivity() {
    companion object {
        const val EXTRA_BOOK_ITEM = "extra_book_item"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set content view
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = intent.getParcelableExtra<BookItem>(EXTRA_BOOK_ITEM) as BookItem
        updateViews(item)
    }

    /**
     * Update views from [BookItem]
     */
    private fun updateViews(item: BookItem) {
        binding.title.text = item.title

        //Set image (show unavailable image if url is empty)
        GlideApp.with(binding.root.context)
            .load(
                if(item.imageUrl.isNotEmpty()) item.imageUrl
                else R.drawable.ic_unavailable)
            .into(binding.image)

        binding.authorViews.isVisible = item.author.isNotEmpty()
        binding.author.text = item.author
    }
}
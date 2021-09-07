package com.play2pay.bookapp.view

import android.content.Intent
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.play2pay.bookapp.R
import com.play2pay.bookapp.activities.DetailActivity
import com.play2pay.bookapp.extensions.hasText
import com.play2pay.bookapp.extensions.isDisplayed
import com.play2pay.bookapp.extensions.isNotDisplayed
import com.play2pay.bookapp.repository.entities.BookItem
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class DetailActivityTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun when_full_book_item_input_check_views_display() {
        val item = BookItem("title", "", "someone")
        runActivity(item)

        R.id.title.isDisplayed()
        R.id.image.isDisplayed()
        R.id.author_label.isDisplayed()
        R.id.author.isDisplayed()
    }

    @Test
    fun when_author_missing_book_item_input_check_views_display() {
        val item = BookItem("title", "", "")
        runActivity(item)

        R.id.title.isDisplayed()
        R.id.image.isDisplayed()
        R.id.author_label.isNotDisplayed()
        R.id.author.isNotDisplayed()
    }

    @Test
    fun when_input_check_text() {
        val item = BookItem("my_title", "http://t0.gstatic.com/licensed-image?q=tbn:ANd9GcRHGBlLyVChpyhs_uVroGrYPamsOuq54-czY8LjC9TlzEFP6grZYQ3hdVC2dT6-", "margaret mitchell")
        runActivity(item)

        R.id.title.isDisplayed()
        R.id.image.isDisplayed()
        R.id.author_label.isDisplayed()
        R.id.author.isDisplayed()

        //Check text
        R.id.title.hasText(item.title)
        R.id.author.hasText(item.author)
    }

    private fun runActivity(item: BookItem) {
        ActivityScenario.launch<DetailActivity>(
            Intent(ApplicationProvider.getApplicationContext(), DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_BOOK_ITEM, item)
            }
        )
    }
}
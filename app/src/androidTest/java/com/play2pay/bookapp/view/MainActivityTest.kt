package com.play2pay.bookapp.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.play2pay.bookapp.R
import com.play2pay.bookapp.activities.MainActivity
import com.play2pay.bookapp.extensions.isDisplayed
import com.play2pay.bookapp.extensions.isNotDisplayed
import com.play2pay.bookapp.repository.BookRepository
import com.play2pay.bookapp.repository.entities.BookItem
import com.play2pay.bookapp.repository.entities.DataResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    private val bookRepository = mockk<BookRepository>(relaxed = true)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        loadKoinModules(module {
            single { bookRepository }
        })
    }

    @Test
    fun when_launch_loadingViewDisplayed_And_errorViewNotDisplayed() {
        ActivityScenario.launch(MainActivity::class.java)

        R.id.progressBar.isDisplayed()
        R.id.book_recyclerview.isNotDisplayed()

        //Error views
        R.id.error_poke.isNotDisplayed()
        R.id.retry.isNotDisplayed()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun when_loading_failed_ErrorViews_Displayed() {
        generateErrorAPI()

        ActivityScenario.launch(MainActivity::class.java)

        R.id.progressBar.isNotDisplayed()
        R.id.book_recyclerview.isNotDisplayed()

        //Error views
        R.id.error_poke.isDisplayed()
        R.id.retry.isDisplayed()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun when_loading_succeed_RecyclerView_Displayed() {
        generateWorkingAPI()

        ActivityScenario.launch(MainActivity::class.java)

        R.id.progressBar.isNotDisplayed()
        R.id.book_recyclerview.isDisplayed()

        //Error views
        R.id.error_poke.isNotDisplayed()
        R.id.retry.isNotDisplayed()
    }

    @ExperimentalCoroutinesApi
    private fun generateErrorAPI() {
        coEvery { bookRepository.getBookItems() } returns DataResult.Failure(Exception("error"))
    }

    @ExperimentalCoroutinesApi
    private fun generateWorkingAPI() {
        coEvery { bookRepository.getBookItems() } returns DataResult.Success(
            listOf(
                BookItem("title", "", "someone")
            )
        )
    }
}
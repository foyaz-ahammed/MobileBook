package com.play2pay.bookapp.repository

import android.util.Log
import com.play2pay.bookapp.repository.api.BookAPI
import com.play2pay.bookapp.repository.entities.BookItem
import com.play2pay.bookapp.repository.entities.DataResult
import java.lang.Exception

/**
 * Repository class to load data from network service
 */
class BookRepository(private val api: BookAPI) {
    companion object {
        const val TAG = "BookRepository"
    }

    suspend fun getBookItems(): DataResult<List<BookItem>> =
        try {
            val bookData = api.getBookItems()
            DataResult.Success(bookData)
        } catch (e: Exception) {
            Log.w(TAG, "error: $e")
            DataResult.Failure(e)
        }
}
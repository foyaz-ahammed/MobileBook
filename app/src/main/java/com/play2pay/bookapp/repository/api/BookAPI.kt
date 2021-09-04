package com.play2pay.bookapp.repository.api

import com.play2pay.bookapp.repository.entities.BookItem
import retrofit2.http.GET

/**
 * API interface to load book items from the endpoint
 */
interface BookAPI {
    @GET("books.json")
    suspend fun getBookItems(): List<BookItem>
}
package com.play2pay.bookapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.play2pay.bookapp.repository.BookRepository
import com.play2pay.bookapp.repository.entities.BookItem
import com.play2pay.bookapp.repository.entities.DataResult
import kotlinx.coroutines.launch

class MainViewModel(private val repository: BookRepository): ViewModel() {
    private val _bookItems = MutableLiveData<List<BookItem>>()

    val bookItems: LiveData<List<BookItem>>
        get() = _bookItems

    fun fetchData() {
        viewModelScope.launch {
            when(val result = repository.getBookItems()) {
                is DataResult.Success -> {
                    _bookItems.value = result.data
                }
                is DataResult.Failure -> {
                }
            }
        }
    }
}
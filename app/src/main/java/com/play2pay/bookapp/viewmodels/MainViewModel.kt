package com.play2pay.bookapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.play2pay.bookapp.repository.BookRepository
import com.play2pay.bookapp.repository.entities.BookItem
import com.play2pay.bookapp.repository.entities.DataResult
import com.play2pay.bookapp.repository.entities.LoadResult
import kotlinx.coroutines.launch

/**
 * [ViewModel] for main screen
 */
class MainViewModel(private val repository: BookRepository): ViewModel() {
    private val _bookItems = MutableLiveData<List<BookItem>>()
    private val _loading = MutableLiveData<LoadResult>()

    val bookItems: LiveData<List<BookItem>>
        get() = _bookItems
    val loading: LiveData<LoadResult>
        get() = _loading

    /**
     * Load data through API
     */
    fun fetchData() {
        _loading.value = LoadResult.LOADING
        viewModelScope.launch {
            when(val result = repository.getBookItems()) {
                is DataResult.Success -> {
                    _loading.value = LoadResult.SUCCESS
                    _bookItems.value = result.data
                }
                is DataResult.Failure -> {
                    _loading.value = LoadResult.FAIL
                }
            }
        }
    }
}
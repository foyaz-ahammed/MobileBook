package com.play2pay.bookapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.play2pay.bookapp.adapters.BookListAdapter
import com.play2pay.bookapp.databinding.ActivityMainBinding
import com.play2pay.bookapp.repository.entities.BookItem
import com.play2pay.bookapp.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Activity for main screen
 *
 * @see ActivityMainBinding
 */
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainViewModel>()

    //Binding variable
    lateinit var binding: ActivityMainBinding

    //Adapter variable to connect to the recycler view
    private val adapter = BookListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set content view using view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Set adapter to book list recyclerview
        binding.bookRecyclerview.adapter = adapter

        //Add observer to book items livedata, and update UI
        viewModel.bookItems.observe(this) {
            adapter.submitList(it)
        }

        viewModel.fetchData()
    }
}
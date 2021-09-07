package com.play2pay.bookapp.activities

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import com.play2pay.bookapp.R
import com.play2pay.bookapp.adapters.BookListAdapter
import com.play2pay.bookapp.databinding.ActivityMainBinding
import com.play2pay.bookapp.repository.entities.LoadResult
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
    private lateinit var binding: ActivityMainBinding

    //Adapter variable to connect to the recycler view
    private val adapter = BookListAdapter()

    private lateinit var searchAdapter: ArrayAdapter<String>
    private var query = ""

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

        //Add observer to load status
        viewModel.loading.observe(this) {
            binding.bookRecyclerview.isVisible = it == LoadResult.SUCCESS
            binding.progressBar.isVisible = it == LoadResult.LOADING
            binding.errorViews.isVisible = it == LoadResult.FAIL
        }

        if(savedInstanceState == null) viewModel.fetchData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchItem = menu!!.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = getString(R.string.search)

        //Get text view of Search View, and add click listener
        val textView = searchView.findViewById<SearchView.SearchAutoComplete>(R.id.search_src_text)
        textView.setOnItemClickListener { _, _, i, _ -> textView.setText(searchAdapter.getItem(i).toString()) }

        //Set adapter to add suggestion queries
        searchAdapter = ArrayAdapter(this, R.layout.dropdown_item_line, ArrayList<String>())
        searchAdapter.setNotifyOnChange(true)
        textView.setAdapter(searchAdapter)

        //Add Query text change listener
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newText: String?): Boolean {
                if(query != newText) {
                    query = newText?:""
                    viewModel.search(query)

                    if(query != "") searchAdapter.add(query)
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return true
    }

    /**
     * Called when clicking retry button
     */
    fun onRetry(view: View) {
        viewModel.fetchData()
    }
}
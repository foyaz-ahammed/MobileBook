package com.play2pay.bookapp.activities

import android.content.SharedPreferences
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
import com.play2pay.bookapp.helper.SEARCH_HISTORY_SIZE
import com.play2pay.bookapp.repository.entities.LoadResult
import com.play2pay.bookapp.viewmodels.MainViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Activity for main screen
 *
 * @see ActivityMainBinding
 */
class MainActivity : AppCompatActivity()/*, KoinComponent*/ {
    companion object {
        const val PREF_SEARCH_QUERIES = "pref_search_queries"
    }

    private val viewModel by viewModel<MainViewModel>()

    //Binding variable
    private lateinit var binding: ActivityMainBinding

    //Adapter variable to connect to the recycler view
    private val adapter = BookListAdapter()

    //Shared Preference variable
    private val prefs: SharedPreferences by inject()

    //Adapter to manage search history
    private var searchQueryList = ArrayList<String>()
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
        textView.setOnItemClickListener { _, _, i, _ ->
            searchView.setQuery(searchAdapter.getItem(i).toString(), true)
        }

        //Set adapter to add suggestion queries
        searchQueryList = getSearchQueries()
        searchAdapter = ArrayAdapter(this, R.layout.dropdown_item_line, searchQueryList)
        textView.setAdapter(searchAdapter)

        //Add Query text change listener
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newText: String?): Boolean {
                if(query != "" && !searchQueryList.contains(query)) {
                    searchAdapter.add(query)
                    searchQueryList.add(query)
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(query != newText) {
                    query = newText?:""
                    viewModel.search(query)
                }

                return true
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

    override fun onStop() {
        //Get 10 size sublist
        val startIndex = searchQueryList.size - SEARCH_HISTORY_SIZE
        saveSearchQueries(
            if(startIndex > 0) searchQueryList.subList(startIndex, searchQueryList.size - 1)
            else searchQueryList
        )

        super.onStop()
    }

    /**
     * Get queries from search adapter
     */
    private fun getSearchQueries(): ArrayList<String> {
        val queryList = ArrayList<String>()
        prefs.getStringSet(PREF_SEARCH_QUERIES, emptySet())?.forEach {
            queryList.add(it)
        }

        return queryList
    }

    /**
     *
     */
    private fun saveSearchQueries(queryList: List<String>) {
        val editor = prefs.edit()
        editor.putStringSet(PREF_SEARCH_QUERIES, queryList.toSet())
        editor.apply()
    }
}
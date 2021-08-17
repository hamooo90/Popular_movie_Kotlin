package com.gmail.hamedvakhide.popularmovies.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.hamedvakhide.popularmovies.R
import com.gmail.hamedvakhide.popularmovies.ui.home.MovieFooterStateAdapter
import com.gmail.hamedvakhide.popularmovies.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.search_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.search_fragment) {

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: SearchAdapter
//
//    override fun onDestroy() {
//        super.onDestroy()
//        KeyboardUtil().hideKeyboard(activity as MainActivity)
//        Log.d("DAGDAG", "onDestroy: ")
//    }
//
//    override fun onStop() {
//        super.onStop()
//        KeyboardUtil().hideKeyboard(activity as MainActivity)
//        Log.d("DAGDAG", "onStop: ")
//    }

//    override fun onPause() {
//        super.onPause()
//        KeyboardUtil().hideKeyboard(activity as MainActivity)
//        Log.d("DAGDAG", "onPause: ")
//    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        search_view.requestFocus()
//        KeyboardUtil().showKeyboard(activity as MainActivity)


        searchAdapter = SearchAdapter()
        recycler_view_search.layoutManager = LinearLayoutManager(requireContext())

        // add loading footer to movieAdapter
        recycler_view_search.adapter = searchAdapter.withLoadStateFooter(
            MovieFooterStateAdapter {
                // retry failed operation if footer retry button clicked
                searchAdapter.retry()
            }
        )

        swipe_refresh_search.isEnabled = false
        searchAdapter.addLoadStateListener { state ->
            swipe_refresh_search.isRefreshing = state.source.refresh is LoadState.Loading
            error_container.isVisible = state.source.refresh is LoadState.Error
            recycler_view_search.isVisible = !error_container.isVisible

            if (state.source.refresh is LoadState.Error) {
                btn_retry_error.setOnClickListener {
                    searchAdapter.retry()
                }

                val errorMessage = (state.source.refresh as LoadState.Error).error.message
                text_view_error_message_error.text = errorMessage
            }
        }



        tool_bar_search.setNavigationOnClickListener {
            it.findNavController().popBackStack()
        }

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText != "" ) {
                    viewModel.onSearchMovie(newText)
                    recycler_view_search.visibility = View.VISIBLE
                } else {
                    recycler_view_search.visibility = View.GONE
                }
                return false

            }
        })

        viewModel.searchMovies.observe(viewLifecycleOwner, {
            searchAdapter.submitData(lifecycle, it)
        })

    }


}


package com.gmail.hamedvakhide.popularmovies.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.hamedvakhide.popularmovies.R
import com.gmail.hamedvakhide.popularmovies.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.home_fragment) {

    private lateinit var movieAdapter: MovieAdapter

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tool_bar_home.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.toolbar_search_menu -> {
                    view.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
                    true
                }
                else -> {
                    false
                }
            }
        }


        movieAdapter = MovieAdapter()
        recycler_view_movies.layoutManager = LinearLayoutManager(requireContext())

        // add loading footer to movieAdapter
        recycler_view_movies.adapter = movieAdapter.withLoadStateFooter(
            MovieFooterStateAdapter {
                movieAdapter.retry()
            }
        )

        movieAdapter.addLoadStateListener { state ->
            swipe_refresh_home.isRefreshing = state.source.refresh is LoadState.Loading
            error_container.isVisible = state.source.refresh is LoadState.Error
            recycler_view_movies.isVisible = !error_container.isVisible

            if (state.source.refresh is LoadState.Error) {
                btn_retry_error.setOnClickListener {
                    // retry failed operation if footer retry button clicked
                    movieAdapter.retry()
                }

                val errorMessage = (state.source.refresh as LoadState.Error).error.message
                text_view_error_message_error.text = errorMessage
            }
        }

        // refresh on swipe
        swipe_refresh_home.setOnRefreshListener {
            viewModel.onRefresh()
        }

        viewModel.trendingMovies.observe(viewLifecycleOwner, {
            movieAdapter.submitData(lifecycle,it)
        })

    }



}
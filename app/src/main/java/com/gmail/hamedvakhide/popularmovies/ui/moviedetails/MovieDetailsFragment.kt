package com.gmail.hamedvakhide.popularmovies.ui.moviedetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.hamedvakhide.popularmovies.R
import com.gmail.hamedvakhide.popularmovies.glide.GlideApp
import com.gmail.hamedvakhide.popularmovies.model.Status
import com.gmail.hamedvakhide.popularmovies.viewmodel.MovieDetailsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.loading_layout.*
import kotlinx.android.synthetic.main.movie_details_fragment.*

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {
    private val viewModel: MovieDetailsViewModel by viewModels()

    private lateinit var castAdapter: CastAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        btn_back_detail.setOnClickListener {
            it.findNavController().popBackStack()
        }

        castAdapter = CastAdapter()
        recycler_view_cast_detail.isNestedScrollingEnabled = false
        recycler_view_cast_detail.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recycler_view_cast_detail.adapter = castAdapter

        viewModel.movie.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    showLoading(false)

                    val movie = it.data

                    GlideApp.with(image_view_backdrop_detail)
                        .load("https://image.tmdb.org/t/p/original${movie?.backdropPath}")
                        .placeholder(R.drawable.backdrop_placeholder)
                        .into(image_view_backdrop_detail)

                    GlideApp.with(image_view_poster_detail)
                        .load("https://image.tmdb.org/t/p/w500${movie?.posterPath}")
                        .placeholder(R.drawable.ic_image)
                        .into(image_view_poster_detail)

                    text_view_title_detail.text = movie?.title
                    if (movie?.genres != null && movie.genres.isNotEmpty()) {
                        val genres = movie.genres.joinToString(
                            separator = "  ",
                            transform = { genre -> genre.name }
                        )

                        text_view_genres_detail.text = genres
                    } else {
                        text_view_genres_detail.visibility = View.GONE
                    }

                    if (movie?.runtime != null) {
                        text_view_runtime_detail.text =
                            getString(R.string.minutes_placeholder, movie.runtime.toString())
//                        text_view_runtime_detail.text = movie.runtime.toString()
                    } else {
                        text_view_runtime_detail.visibility = View.GONE
                    }

                    if (movie?.releaseDate != null && movie.releaseDate.isNotBlank()) {
                        text_view_release_date_detail.text = movie.releaseDate
                    } else {
                        text_view_release_date_detail.visibility = View.GONE
                    }

                    movie?.voteAverage?.let { rate ->
                        rating_bar_detail.rating = (rate / 2).toFloat()
                    }

                    text_view_vote_count_detail.text = movie?.voteCount.toString()
                    text_view_overview_detail.text = movie?.overview

                    val casts = movie?.credits?.cast
                    if (casts != null && casts.isNotEmpty()) {

                        //////// only show 10 casts ////////
//                        val castsCount = if (casts.size < 10) casts.size else 10
//                        castAdapter.submitList(casts.take(castsCount))

                        castAdapter.submitList(casts)
                    }
                }
                Status.ERROR -> {
                    showLoading(false)
                    Snackbar.make(requireView(), it.message!!, Snackbar.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    showLoading(true)
                }
            }
        })
    }

    private fun showLoading(show: Boolean) {
        if (show) {
            load_container.visibility = View.VISIBLE
        } else {
            load_container.visibility = View.GONE
        }
    }

}
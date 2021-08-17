package com.gmail.hamedvakhide.popularmovies.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gmail.hamedvakhide.popularmovies.R
import com.gmail.hamedvakhide.popularmovies.glide.GlideApp
import com.gmail.hamedvakhide.popularmovies.model.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter constructor(
//    private val movies: List<Movie>
) : PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(COMPARATOR) {


    class MovieViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            itemView.setOnClickListener {
                val direction =
                    HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(movie.id!!)
                it.findNavController().navigate(direction)
            }

            itemView.txt_title_item.text = movie.title
            itemView.txt_overview_item.text = movie.overview
            itemView.txt_release_date_item.text = movie.releaseDate

            GlideApp.with(itemView.image_view_poster_item)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .placeholder(R.drawable.ic_image)
                .into(itemView.image_view_poster_item)
        }

        companion object {
            fun from(parent: ViewGroup): MovieViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.item_movie, parent, false)
                return MovieViewHolder(itemView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let { holder.bind(it) }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }
}
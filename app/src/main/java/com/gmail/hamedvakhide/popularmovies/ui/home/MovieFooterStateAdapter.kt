package com.gmail.hamedvakhide.popularmovies.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gmail.hamedvakhide.popularmovies.R
import kotlinx.android.synthetic.main.iterm_movie_footer_state.view.*

class MovieFooterStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<MovieFooterStateAdapter.MovieFooterStateViewHolder>() {

    override fun onBindViewHolder(holder: MovieFooterStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MovieFooterStateViewHolder {
        return MovieFooterStateViewHolder.create(parent, retry)
    }

    class MovieFooterStateViewHolder private constructor(
        itemView: View,
        retry: () -> Unit
    ) :
        RecyclerView.ViewHolder(itemView) {

        init {
            itemView.btn_retry_footer.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            itemView.apply {
                if (loadState is LoadState.Error) {
                    text_view_error_footer.text = loadState.error.message
                }

                progress_bar_footer.isVisible = loadState is LoadState.Loading
                btn_retry_footer.isVisible = loadState !is LoadState.Loading
                text_view_error_footer.isVisible = loadState !is LoadState.Loading
            }
        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): MovieFooterStateViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.iterm_movie_footer_state, parent, false)

                return MovieFooterStateViewHolder(view, retry)
            }
        }
    }
}
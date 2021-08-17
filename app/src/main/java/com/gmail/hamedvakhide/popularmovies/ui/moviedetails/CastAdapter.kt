package com.gmail.hamedvakhide.popularmovies.ui.moviedetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gmail.hamedvakhide.popularmovies.R
import com.gmail.hamedvakhide.popularmovies.glide.GlideApp
import com.gmail.hamedvakhide.popularmovies.model.Cast
import kotlinx.android.synthetic.main.item_cast.view.*

class CastAdapter : ListAdapter<Cast, CastAdapter.CastViewHolder>(COMPARATOR) {

    class CastViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cast: Cast) {

            itemView.setOnClickListener {
                val direction =
                    MovieDetailsFragmentDirections.actionMovieDetailsFragmentToCastFragment(cast)
                it.findNavController().navigate(direction)
            }

            GlideApp.with(itemView.image_view_cast_photo_item)
                .load("https://image.tmdb.org/t/p/w500${cast.profilePath}")
                .placeholder(R.drawable.ic_avatar)
                .into(itemView.image_view_cast_photo_item)

            itemView.text_view_cast_name_item.text = cast.name
        }

        companion object {
            fun from(parent: ViewGroup): CastViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.item_cast, parent, false)
                return CastViewHolder(itemView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        return CastViewHolder.from(parent)

    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val cast = getItem(position)
        cast?.let { holder.bind(it) }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Cast>() {
            override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
                return oldItem == newItem
            }
        }
    }
}
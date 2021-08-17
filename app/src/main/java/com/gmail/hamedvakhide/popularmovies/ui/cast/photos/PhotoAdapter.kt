package com.gmail.hamedvakhide.popularmovies.ui.cast.photos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gmail.hamedvakhide.popularmovies.R
import com.gmail.hamedvakhide.popularmovies.glide.GlideApp
import com.gmail.hamedvakhide.popularmovies.model.Photo
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotoAdapter: ListAdapter<Photo, PhotoAdapter.PhotoViewHolder>(COMPARATOR) {

    class PhotoViewHolder private constructor(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(it: Photo) {
            GlideApp.with(itemView.image_view_photo_item)
                .load("https://image.tmdb.org/t/p/w500${it.filePath}")
                .placeholder(R.drawable.ic_avatar)
                .into(itemView.image_view_photo_item)
        }

        companion object {
            fun from(parent: ViewGroup): PhotoViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.item_photo, parent, false)
                return PhotoViewHolder(itemView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = getItem(position)
        photo?.let {
            holder.bind(it)
        }
    }

    companion object{
        private val COMPARATOR = object : DiffUtil.ItemCallback<Photo>(){
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem.filePath == newItem.filePath
            }
            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem == newItem
            }
        }
    }
}
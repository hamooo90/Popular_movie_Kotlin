package com.gmail.hamedvakhide.popularmovies.ui.cast.photos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.gmail.hamedvakhide.popularmovies.R
import com.gmail.hamedvakhide.popularmovies.model.Status
import com.gmail.hamedvakhide.popularmovies.viewmodel.PhotoViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_photo.*

private const val ARG_PARAM1 = "castId"

@AndroidEntryPoint
class PhotoFragment : Fragment(R.layout.fragment_photo) {
    private var castId: Long? = null

    private val viewModel: PhotoViewModel by viewModels()
    private lateinit var photoAdapter: PhotoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // get castId passed from CastFragment ViewPager
        arguments?.let {
            castId = it.getLong(ARG_PARAM1)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photoAdapter = PhotoAdapter()
        recycler_view_photo.layoutManager =
            GridLayoutManager(requireContext(),3)
        recycler_view_photo.adapter = photoAdapter

        swipe_refresh_photo.isEnabled = false
        viewModel.getPersonPhotos(castId!!)

        viewModel.photos.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    photoAdapter.submitList(it.data)
                    showLoading(false)
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

    private fun showLoading(isLoading: Boolean) {
        swipe_refresh_photo.isRefreshing = isLoading
    }

    companion object {
        @JvmStatic
        fun newInstance(id: Long) =
            PhotoFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_PARAM1, id)
                }
            }
    }
}
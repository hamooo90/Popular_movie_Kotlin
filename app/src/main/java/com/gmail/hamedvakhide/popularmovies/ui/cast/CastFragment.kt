package com.gmail.hamedvakhide.popularmovies.ui.cast

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.gmail.hamedvakhide.popularmovies.R
import com.gmail.hamedvakhide.popularmovies.glide.GlideApp
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.cast_fragment.*

@AndroidEntryPoint
class CastFragment : Fragment(R.layout.cast_fragment) {

    private val args: CastFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get navigation argument passed from MovieDetailFragment
        val cast = args.cast

        btn_back_cast.setOnClickListener {
            it.findNavController().popBackStack()
        }

        GlideApp.with(image_view_profile_image_cast)
            .load("https://image.tmdb.org/t/p/w500${cast.profilePath}")
            .placeholder(R.drawable.ic_avatar)
            .into(image_view_profile_image_cast)

        text_view_name_cast.text = cast.name



        view_pager_cast.adapter = ViewPagerAdapter(this,cast.id!!)
        TabLayoutMediator(tab_layout_cast,view_pager_cast){tab,position ->
            when (position) {
                0 -> {
                    tab.text = "About"
                }
                else -> {
                    tab.text = "Photo"
                }
            }
        }.attach()
    }

}


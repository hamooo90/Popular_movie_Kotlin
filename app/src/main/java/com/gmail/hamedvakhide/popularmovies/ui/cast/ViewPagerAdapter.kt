package com.gmail.hamedvakhide.popularmovies.ui.cast

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gmail.hamedvakhide.popularmovies.ui.cast.about.AboutFragment
import com.gmail.hamedvakhide.popularmovies.ui.cast.photos.PhotoFragment

class ViewPagerAdapter(fm: Fragment, private val castId: Long): FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                AboutFragment.newInstance(castId)
            }
            else -> {
                PhotoFragment.newInstance(castId)

            }
        }
    }
}
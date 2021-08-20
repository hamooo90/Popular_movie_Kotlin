package com.gmail.hamedvakhide.popularmovies.ui.cast.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.gmail.hamedvakhide.popularmovies.R
import com.gmail.hamedvakhide.popularmovies.model.Status
import com.gmail.hamedvakhide.popularmovies.util.DateToAge
import com.gmail.hamedvakhide.popularmovies.viewmodel.AboutViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_about.*


private const val ARG_PARAM1 = "castId"

@AndroidEntryPoint
class AboutFragment : Fragment(R.layout.fragment_about) {
    private var castId: Long? = null

    private val viewModel: AboutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // get castId passed from CastFragment ViewPager
        arguments?.let {
            castId = it.getLong(ARG_PARAM1)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe_refresh_about.isEnabled = false
        viewModel.getCastDetail(castId!!)

        viewModel.castDetail.observe(viewLifecycleOwner, {
            when (it.status) {
                (Status.SUCCESS) -> {
                    showLoading(false)

                    val cast = it.data
                    text_view_biography_about.text = cast?.biography
                    text_view_birthday_about.text = cast?.birthday
                    text_view_birth_place_about.text = cast?.placeOfBirth
                    text_view_department_about.text = cast?.knownForDepartment
                    text_view_age_about.text =
                        cast?.birthday?.let { it1 -> DateToAge.getAge(it1) }
                    text_view_known_as_about.text = cast?.alsoKnownAs?.joinToString(", ")
                    cast?.deathday.let { deathDay ->
                        text_view_deathday_about.text = deathDay
                        if(!deathDay.isNullOrEmpty()) {
                            linear_layout_death_cast.visibility = View.VISIBLE
                        }
                    }
                }
                (Status.LOADING) -> {
                    showLoading(true)
                }
                (Status.ERROR) -> {
                    showLoading(false)
                    Snackbar.make(requireView(), it.message!!, Snackbar.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        swipe_refresh_about.isRefreshing = isLoading

        if (!isLoading) {
            nested_scroll_about.visibility = View.VISIBLE
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(id: Long) =
            AboutFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_PARAM1, id)
                }
            }
    }
}
package com.gmail.hamedvakhide.popularmovies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.gmail.hamedvakhide.popularmovies.model.Movie
import com.gmail.hamedvakhide.popularmovies.model.Resource
import com.gmail.hamedvakhide.popularmovies.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _movie = MutableLiveData<Resource<Movie>>()
    val movie: LiveData<Resource<Movie>>
        get() = _movie

    init {
        if (savedStateHandle.contains("movieId")) {
            val movieId = savedStateHandle.get<Long>("movieId")

            compositeDisposable.add(
                repository.getSingleMovie(movieId!!)
                    .doOnSubscribe { _movie.value = Resource.Loading() }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            _movie.value = Resource.Success(it)
                        },
                        {
                            _movie.value = Resource.Error(it.message!!)
                        }
                    )
            )

        } else {
            _movie.value = Resource.Error("Error! How did you get here?")
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}
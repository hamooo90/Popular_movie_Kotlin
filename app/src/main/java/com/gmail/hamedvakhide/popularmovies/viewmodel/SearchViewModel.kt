package com.gmail.hamedvakhide.popularmovies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.gmail.hamedvakhide.popularmovies.model.Movie
import com.gmail.hamedvakhide.popularmovies.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _searchMovies = MutableLiveData<PagingData<Movie>>()
    val searchMovies: LiveData<PagingData<Movie>>
        get() {
            return _searchMovies
        }

    override fun onCleared() {
        super.onCleared()
        // fix memory leak
        compositeDisposable.clear()
    }

    @ExperimentalCoroutinesApi
    fun onSearchMovie(query: String) {
        compositeDisposable.add(
            repository.getSearchMovies( query )
                .cachedIn(viewModelScope)
                .subscribe {
                    _searchMovies.value = it
                }
        )
    }
}
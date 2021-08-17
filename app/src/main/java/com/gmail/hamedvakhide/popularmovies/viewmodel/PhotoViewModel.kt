package com.gmail.hamedvakhide.popularmovies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.hamedvakhide.popularmovies.model.Photo
import com.gmail.hamedvakhide.popularmovies.model.Resource
import com.gmail.hamedvakhide.popularmovies.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _photos = MutableLiveData<Resource<List<Photo>>>()
    val photos: LiveData<Resource<List<Photo>>>
        get() = _photos


    fun getPersonPhotos(castId: Long) {
        compositeDisposable.add(
            repository.getPersonPhotos(castId)
                .doOnSubscribe { _photos.value = Resource.Loading() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _photos.value = Resource.Success(it)
                    },
                    {
                        _photos.value = Resource.Error(it.message!!)
                    }
                )
        )
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
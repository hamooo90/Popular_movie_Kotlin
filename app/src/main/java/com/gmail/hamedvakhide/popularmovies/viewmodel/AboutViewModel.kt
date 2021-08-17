package com.gmail.hamedvakhide.popularmovies.viewmodel

import androidx.lifecycle.*
import com.gmail.hamedvakhide.popularmovies.model.Cast
import com.gmail.hamedvakhide.popularmovies.model.CastDetail
import com.gmail.hamedvakhide.popularmovies.model.Resource
import com.gmail.hamedvakhide.popularmovies.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _castDetail = MutableLiveData<Resource<CastDetail>>()
    val castDetail: LiveData<Resource<CastDetail>>
        get() = _castDetail

    fun getCastDetail(id: Long) {
        compositeDisposable.add(
            repository.getCastDetail(id)
                .doOnSubscribe { _castDetail.value = Resource.Loading() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _castDetail.value = Resource.Success(it)
                    },
                    {
                        _castDetail.value = Resource.Error(it.message!!)
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}
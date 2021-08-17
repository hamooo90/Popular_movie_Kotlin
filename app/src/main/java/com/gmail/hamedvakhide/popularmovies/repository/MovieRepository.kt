package com.gmail.hamedvakhide.popularmovies.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.gmail.hamedvakhide.popularmovies.model.*
import com.gmail.hamedvakhide.popularmovies.network.MovieService
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieService: MovieService,
    private val moviePagingSource: MoviePagingSource
) {
    @ExperimentalCoroutinesApi
    fun getTrendingMovie(): Flowable<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 1
            ),
            pagingSourceFactory = { moviePagingSource }
        ).flowable
    }

    @ExperimentalCoroutinesApi
    fun getSearchMovies(query: String): Flowable<PagingData<Movie>> {

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 1
            ),
            pagingSourceFactory = { SearchPagingSource(movieService, query) }
        ).flowable
    }

    fun getSingleMovie(movieId: Long): Single<Movie>{
        return movieService.getSingleMovie(movieId)
            .subscribeOn(Schedulers.io())
    }

    fun getCastDetail(castId: Long): Single<CastDetail>{
        return movieService.getCastDetail(castId)
            .subscribeOn(Schedulers.io())
    }

    fun getPersonPhotos(castId: Long): Single<List<Photo>>{
        return movieService.getPersonPhotos(castId)
            .subscribeOn(Schedulers.io())
            .map {
                it.photos
            }
    }
}
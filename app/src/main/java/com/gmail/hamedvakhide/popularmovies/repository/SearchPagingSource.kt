package com.gmail.hamedvakhide.popularmovies.repository

import android.app.DownloadManager
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.gmail.hamedvakhide.popularmovies.model.Movie
import com.gmail.hamedvakhide.popularmovies.network.MovieService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SearchPagingSource constructor(
    private val movieService: MovieService,
    private val query: String
) : RxPagingSource<Int, Movie>() {
    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Movie>> {
        // starting page
        val page = params.key ?: 1
        return movieService.getSearchedMovies(query, page)
            .subscribeOn(Schedulers.io())
            .map {
                LoadResult.Page(
                    data = it.results,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (page == it.totalPages.toInt() || it.totalPages.toInt()==0) null else page + 1
                ) as LoadResult<Int, Movie>
            }
            .onErrorReturn {
                LoadResult.Error(it)
            }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }
}
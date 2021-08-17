package com.gmail.hamedvakhide.popularmovies.network

import com.gmail.hamedvakhide.popularmovies.model.CastDetail
import com.gmail.hamedvakhide.popularmovies.model.PersonPhotos
import com.gmail.hamedvakhide.popularmovies.model.Movie
import com.gmail.hamedvakhide.popularmovies.model.Movies
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("trending/movie/day")
    fun getTrendingMovies(@Query("page") page: Int): Single<Movies>

    @GET("movie/{movieId}?append_to_response=credits")
    fun getSingleMovie(@Path("movieId") movieId: Long): Single<Movie>

    @GET("person/{castId}")
    fun getCastDetail(@Path("castId") castId: Long): Single<CastDetail>

    @GET("person/{castId}/images")
    fun getPersonPhotos(@Path("castId") castId: Long): Single<PersonPhotos>

    @GET("search/movie?include_adult=false")
    fun getSearchedMovies(@Query("query") query: String, @Query("page") page: Int): Single<Movies>

}
package com.gmail.hamedvakhide.popularmovies.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie(
//    val adult: Boolean?,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "first_air_date")
    val firstAirDate: String?,
    @Json(name = "genre_ids")
    val genreIds: List<Int>?,
    val id: Long?,
//    @Json(name = "media_type")
//    val mediaType: String?,
    val name: String?,
//    @Json(name = "origin_country")
//    val originCountry: List<String>?,
//    @Json(name = "original_language")
//    val originalLanguage: String?,
    @Json(name = "original_name")
    val originalName: String?,
    @Json(name = "original_title")
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "release_date")
    val releaseDate: String?,
    val title: String?,
//    val video: Boolean?,
    @Json(name = "vote_average")
    val voteAverage: Double?,
    @Json(name = "vote_count")
    val voteCount: Long?,

    // for movie details
    val genres: List<Genre>?,
    val runtime: Long?,

    // for cast
    val credits: Credit?
)


// for movie details
@JsonClass(generateAdapter = true)
data class Genre(
    val id: Long,
    val name: String
)
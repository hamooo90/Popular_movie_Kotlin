package com.gmail.hamedvakhide.popularmovies.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PersonPhotos(
    val id: Int?,
    @Json(name = "profiles")
    val photos: List<Photo>?
)

@JsonClass(generateAdapter = true)
data class Photo(
//    @Json(name = "aspect_ratio")
//    val aspectRatio: Double?,
    @Json(name = "file_path")
    val filePath: String?,
//    val height: Int?,
//    @Json(name = "iso_639_1")
//    val iso6391: Any?,
//    @Json(name = "vote_average")
//    val voteAverage: Double?,
//    @Json(name = "vote_count")
//    val voteCount: Int?,
//    val width: Int?
)
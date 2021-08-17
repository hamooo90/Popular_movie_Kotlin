package com.gmail.hamedvakhide.popularmovies.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CastDetail(
//    val adult: Boolean?,
    @Json(name = "also_known_as")
    val alsoKnownAs: List<String>?,
    val biography: String?,
    val birthday: String?,
    val deathday: String?,
//    val gender: Int?,
//    val homepage: Any,
    val id: Int?,
//    @Json(name = "imdb_id")
//    val imdbId: String,
    @Json(name = "known_for_department")
    val knownForDepartment: String?,
    val name: String?,
    @Json(name = "place_of_birth")
    val placeOfBirth: String?,
//    val popularity: Double?,
    @Json(name = "profile_path")
    val profilePath: String?
)
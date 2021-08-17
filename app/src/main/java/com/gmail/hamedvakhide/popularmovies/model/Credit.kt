package com.gmail.hamedvakhide.popularmovies.model


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
data class Credit(
    val cast: List<Cast>,
//    val crew: List<Crew>
)

@Parcelize
@JsonClass(generateAdapter = true)
data class Cast(
//    val adult: Boolean?,
    @Json(name = "cast_id")
    val castId: Int?,
    val character: String?,
    @Json(name = "credit_id")
    val creditId: String?,
//    val gender: Int?,
    val id: Long?,
    @Json(name = "known_for_department")
    val knownForDepartment: String?,
    val name: String?,
//    val order: Int?,
    @Json(name = "original_name")
    val originalName: String?,
//    val popularity: Double?,
    @Json(name = "profile_path")
    val profilePath: String?
): Parcelable

//@JsonClass(generateAdapter = true)
//data class Crew(
//    val adult: Boolean?,
//    @Json(name = "credit_id")
//    val creditId: String?,
//    val department: String?,
//    val gender: Int?,
//    val id: Int?,
//    val job: String?,
//    @Json(name = "known_for_department")
//    val knownForDepartment: String?,
//    val name: String?,
//    @Json(name = "original_name")
//    val originalName: String?,
//    val popularity: Double?,
//    @Json(name = "profile_path")
//    val profilePath: String?
//)
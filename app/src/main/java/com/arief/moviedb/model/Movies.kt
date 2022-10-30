package com.arief.moviedb.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

/**
 *
 * Model Class for movies and movies table
 *
 * @property adult
 * @property backdrop_path
 * @property genre_ids
 * @property id
 * @property original_language
 * @property original_title
 * @property overview
 * @property popularity
 * @property poster_path
 * @property release_date
 * @property title
 * @property vote_average
 * @property vote_count
 */
@Parcelize
@Entity(tableName = "movies", primaryKeys = ["id"])
data class Movies(
    var adult:Boolean = false,
    var backdrop_path:String? = "",
    var genre_ids:List<Int> = listOf(),
    var id:Int = 0,
    var original_language:String = "",
    var original_title:String = "",
    var overview:String = "",
    var popularity:Float = 0F,
    var poster_path:String? = "",
    var release_date:String = "",
    var title:String = "",
    var vote_average:Float = 0F,
    var vote_count:Int = 0,
) : Parcelable
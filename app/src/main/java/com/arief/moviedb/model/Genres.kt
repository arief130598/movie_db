package com.arief.moviedb.model

import androidx.room.Entity

/**
 *
 * Model Class for genres movie and genres table
 *
 * @property id
 * @property name
 */
@Entity(tableName = "genres", primaryKeys = ["id"])
data class Genres(
    var id:Int = 0,
    var name:String = "",
)

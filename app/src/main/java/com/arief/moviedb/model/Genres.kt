package com.arief.moviedb.model

import androidx.room.Entity

@Entity(tableName = "genres", primaryKeys = ["id"])
data class Genres(
    var id:Int = 0,
    var name:String = "",
)

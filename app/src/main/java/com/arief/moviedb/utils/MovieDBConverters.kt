package com.arief.moviedb.utils

import androidx.room.TypeConverter

class MovieDBConverters {

    @TypeConverter
    fun fromListInttoString(data: List<Int>): String{
        return data.joinToString(",")
    }

    @TypeConverter
    fun fromStringtoListInt(data: String): List<Int>{
        return data.split(",").map { it -> it.toInt() }
    }

}
package com.arief.moviedb.core.utils

import androidx.room.TypeConverter

/**
 * Converters for Room Database <br/>
 * For each type that not included in room create function here and reverse the return <br/>
 * Doc : https://developer.android.com/training/data-storage/room/referencing-data
 */
class MovieDBConverters {

    /**
     * Convert from List<Int> to String
     *
     * @param data
     * @return
     */
    @TypeConverter
    fun fromListInttoString(data: List<Int>): String{
        return data.joinToString(",")
    }

    /**
     * Convert from String to List<Int>
     *
     * @param data
     * @return
     */
    @TypeConverter
    fun fromStringtoListInt(data: String): List<Int>{
        return data.split(",").map { it -> it.toInt() }
    }

}
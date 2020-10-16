package com.example.madlevel4task2

import androidx.room.TypeConverter
import java.util.*

/* All of this is found on StackOverflow after getting an error when building the application for the first time*/

class Converter {
    @TypeConverter
    fun dateToTime(date: Date?): Long? {
        return date?.time?.toLong() //Redundant yet when I remove it wont work... I am lost here
    }

    @TypeConverter
    fun toTimeStamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }
}
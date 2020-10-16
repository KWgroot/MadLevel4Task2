package com.example.madlevel4task2

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "gameTable")
data class Game (
    @DrawableRes
    @ColumnInfo(name = "player")
    var user: Int,

    @DrawableRes
    @ColumnInfo(name = "computer")
    var computer: Int,

    @ColumnInfo(name = "date")
    var date: Date,

    @ColumnInfo(name = "result")
    var result: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
)
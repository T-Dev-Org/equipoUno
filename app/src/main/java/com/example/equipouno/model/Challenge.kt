package com.example.equipouno.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.equipouno.utils.Constants.challengeTable
import com.example.equipouno.utils.DateConverter
import java.io.Serializable
import java.util.Date

@Entity(tableName = challengeTable.TABLE_NAME)
data class Challenge(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = challengeTable.COLUMN_ID)
    val id: Int = 0,

    @ColumnInfo(name = challengeTable.COLUMN_DESCRIPTION, defaultValue = "")
    var description: String,

    @ColumnInfo(name = challengeTable.COLUMN_MODIFICATION_DATE)
    @TypeConverters(DateConverter::class)
    var modificationDate: Date = Date(),

    @ColumnInfo(name = challengeTable.COLUMN_CREATION_DATE)
    @TypeConverters(DateConverter::class)
    val creationDate: Date = Date()

): Serializable

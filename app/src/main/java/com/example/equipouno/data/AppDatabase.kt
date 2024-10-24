package com.example.equipouno.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.equipouno.model.Challenge
import com.example.equipouno.utils.Constants.NAME_BD
import com.example.equipouno.utils.DateConverter

@Database(entities = [Challenge::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun challengeDao(): ChallengeDao

    companion object {
        fun getDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                NAME_BD
            ).build()
        }
    }
}
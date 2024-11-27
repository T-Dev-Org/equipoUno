package com.example.equipouno.di

import android.content.Context
import androidx.room.Room
import com.example.equipouno.data.AppDatabase
import com.example.equipouno.data.ChallengeDao
import com.example.equipouno.repository.ChallengeRepository
import com.example.equipouno.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            Constants.NAME_BD
        ).build()
    }


    @Singleton
    @Provides
    fun provideChallengeDao(appDatabase: AppDatabase): ChallengeDao {
        return appDatabase.challengeDao()
    }

    @Singleton
    @Provides
    fun provideChallengeRepository(challengeDao: ChallengeDao): ChallengeRepository {
        return ChallengeRepository(challengeDao)
    }

}
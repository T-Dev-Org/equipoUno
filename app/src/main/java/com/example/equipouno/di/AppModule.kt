package com.example.equipouno.di

import com.example.equipouno.repository.ChallengeRepository
import com.example.equipouno.repository.PokemonRepository
import com.example.equipouno.utils.Constants.BASE_URL
import com.example.equipouno.utils.Constants.challengeTable.TABLE_NAME
import com.example.equipouno.webservice.ApiService
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideChallengeCollection(db: FirebaseFirestore): CollectionReference {
        return db.collection(TABLE_NAME)
    }

    @Provides
    @Singleton
    fun provideChallengeRepository(collection: CollectionReference): ChallengeRepository {
        return ChallengeRepository(collection)
    }

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePokemonRepository(apiService: ApiService): PokemonRepository {
        return PokemonRepository(apiService)
    }
}
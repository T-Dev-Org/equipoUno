package com.example.equipouno.repository

import android.content.Context
import com.example.equipouno.data.AppDatabase
import com.example.equipouno.data.ChallengeDao
import com.example.equipouno.model.Challenge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date

class ChallengeRepository(context: Context) {
    private val challengeDao: ChallengeDao = AppDatabase.getDatabase(context).challengeDao()

    suspend fun insertChallenge(challenge: Challenge) {
        withContext(Dispatchers.IO) {
            // Asignar la fecha actual para creación y modificación a la actual
            val currentDate = Date()
            val newChallenge = challenge.copy(
                creationDate = currentDate,
                modificationDate = currentDate
            )
            challengeDao.insertChallenge(newChallenge)
        }
    }

    suspend fun getListChallenge(): MutableList<Challenge> {
        return withContext(Dispatchers.IO) {
            challengeDao.getListChallenge()
        }
    }

    suspend fun getChallengeById(id: Int): Challenge? {
        return withContext(Dispatchers.IO) {
            challengeDao.getChallengeById(id)
        }
    }

    suspend fun updateChallenge(challenge: Challenge) {
        withContext(Dispatchers.IO) {
            // Asignar la fecha de modificación a la actual
            val updatedChallenge = challenge.copy(
                modificationDate = Date()
            )
            challengeDao.updateChallenge(updatedChallenge)
        }
    }

    suspend fun deleteChallenge(challenge: Challenge) {
        withContext(Dispatchers.IO) {
            challengeDao.deleteChallenge(challenge)
        }
    }
}
package com.example.equipouno.repository

import com.example.equipouno.model.Challenge
import com.example.equipouno.utils.Constants.challengeTable.COLUMN_CREATION_DATE
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ChallengeRepository @Inject constructor(
    private val collection: CollectionReference
) {

    suspend fun insertChallenge(challenge: Challenge): Boolean {
        return try {
            val challengeWithTimestamps = challenge.copy(
                id = challenge.id.ifEmpty { collection.document().id },
                creationDate = Timestamp.now(),
                modificationDate = Timestamp.now()
            )

            collection.document(challengeWithTimestamps.id).set(challengeWithTimestamps).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun getListChallenge(callback: (List<Challenge>) -> Unit) {
        collection.orderBy(COLUMN_CREATION_DATE, com.google.firebase.firestore.Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { snapshot ->
                val challenges = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(Challenge::class.java)?.copy(id = doc.id)
                }
                callback(challenges)
            }
            .addOnFailureListener {
                callback(emptyList())
            }
    }

    suspend fun getRandomChallenge(): Challenge? {
        return try {
            val snapshot = collection.get().await()
            val challenges = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Challenge::class.java)?.copy(id = doc.id)
            }

            if (challenges.isNotEmpty()) {
                challenges.random()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun updateChallenge(id: String, updatedFields: Map<String, Any>): Boolean {
        return try {
            collection.document(id).update(updatedFields).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteChallenge(id: String): Boolean {
        return try {
            collection.document(id).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }
}

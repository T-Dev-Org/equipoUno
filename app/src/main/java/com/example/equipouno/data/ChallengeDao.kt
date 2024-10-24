package com.example.equipouno.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.equipouno.model.Challenge

@Dao
interface ChallengeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChallenge(challenge: Challenge)

    @Query("SELECT * FROM Challenge")
    fun getListChallenge(): MutableList<Challenge>

    @Query("SELECT * FROM Challenge WHERE id = :id")
    fun getChallengeById(id: Int): Challenge?

    @Update
    fun updateChallenge(challenge: Challenge)

    @Delete
    fun deleteChallenge(challenge: Challenge)
}
package com.example.equipouno.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.equipouno.model.Challenge
import com.example.equipouno.repository.ChallengeRepository
import com.example.equipouno.utils.Constants.challengeTable.COLUMN_DESCRIPTION
import com.example.equipouno.utils.Constants.challengeTable.COLUMN_MODIFICATION_DATE
import kotlinx.coroutines.launch

class ChallengeViewModel(application: Application) : AndroidViewModel(application) {

    val context = getApplication<Application>()
    private val challengeRepository = ChallengeRepository()

    private val _listChallenge = MutableLiveData<List<Challenge>>()
    val listChallenge: LiveData<List<Challenge>> get() = _listChallenge

    private val _progressState = MutableLiveData(false)
    val progressState: LiveData<Boolean> = _progressState

    fun saveChallenge(challenge: Challenge) {
        viewModelScope.launch {
            _progressState.value = true
            try {
                val success = challengeRepository.insertChallenge(challenge)
                if (success) getListChallenge()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _progressState.value = false
            }
        }
    }

    fun updateChallenge(challenge: Challenge) {
        viewModelScope.launch {
            _progressState.value = true
            try {
                val updatedFields = mapOf(
                    COLUMN_DESCRIPTION to challenge.description,
                    COLUMN_MODIFICATION_DATE to challenge.modificationDate
                )
                val success = challengeRepository.updateChallenge(challenge.id, updatedFields)
                if (success) getListChallenge()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _progressState.value = false
            }
        }
    }

    fun getListChallenge() {
        viewModelScope.launch {
            _progressState.value = true
            try {
                challengeRepository.getListChallenge { challenges ->
                    _listChallenge.postValue(challenges)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _progressState.value = false
            }
        }
    }

    fun deleteChallenge(challenge: Challenge) {
        viewModelScope.launch {
            _progressState.value = true
            try {
                val success = challengeRepository.deleteChallenge(challenge.id)
                if (success) getListChallenge()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _progressState.value = false
            }
        }
    }
}
package com.example.equipouno.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.equipouno.model.Challenge
import com.example.equipouno.repository.ChallengeRepository
import kotlinx.coroutines.launch

class ChallengeViewModel(application: Application) : AndroidViewModel(application) {
    
    val context = getApplication<Application>()
    private val challengeRepository = ChallengeRepository(context)

    private val _listChallenge = MutableLiveData<MutableList<Challenge>>()
    val listChallenge: LiveData<MutableList<Challenge>> get() = _listChallenge

    private val _progressState = MutableLiveData(false)
    val progressState: LiveData<Boolean> = _progressState

    fun saveChallenge(challenge: Challenge) {
        viewModelScope.launch {
            _progressState.value = true
            try {
                challengeRepository.insertChallenge(challenge)
                _progressState.value = false
            } catch (e: Exception) {
                _progressState.value = false
            }
        }
    }

    fun updateChallenge(challenge: Challenge) {
        viewModelScope.launch {
            _progressState.value = true
            try {
                challengeRepository.updateChallenge(challenge)
                _progressState.value = false
            } catch (e: Exception) {
                _progressState.value = false
            }
        }
    }

    fun getListChallenge() {
        viewModelScope.launch {
            _progressState.value = true
            try {
                _listChallenge.value = challengeRepository.getListChallenge()
                _progressState.value = false
            } catch (e: Exception) {
                _progressState.value = false
            }
        }
    }

    fun deleteChallenge(challenge: Challenge) {
        viewModelScope.launch {
            _progressState.value = true
            try {
                challengeRepository.deleteChallenge(challenge)
                _progressState.value = false
            } catch (e: Exception) {
                _progressState.value = false
            }
        }
    }
}

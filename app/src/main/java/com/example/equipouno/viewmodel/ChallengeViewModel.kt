package com.example.equipouno.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.equipouno.model.Challenge
import com.example.equipouno.repository.ChallengeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class  ChallengeViewModel @Inject constructor(
    private val challengeRepository: ChallengeRepository
): ViewModel() {

    private val _listChallenge = MutableLiveData<List<Challenge>>()
    val listChallenge: LiveData<List<Challenge>> get() = _listChallenge

    private val _progressState = MutableLiveData(false)
    val progressState: LiveData<Boolean> = _progressState

    fun saveChallenge(challenge: Challenge) {
        viewModelScope.launch {
            _progressState.value = true
            try {
                challengeRepository.insertChallenge(challenge)
                getListChallenge()
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
                getListChallenge()
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
                getListChallenge()
                _progressState.value = false
            } catch (e: Exception) {
                _progressState.value = false
            }
        }
    }
}

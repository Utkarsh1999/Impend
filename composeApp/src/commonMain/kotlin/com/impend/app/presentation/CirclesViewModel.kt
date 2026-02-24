package com.impend.app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.impend.shared.domain.model.Circle
import com.impend.shared.domain.repository.CircleRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CirclesViewModel(
    private val circleRepository: CircleRepository
) : ViewModel() {

    val allCircles: StateFlow<List<Circle>> = circleRepository.getAllCircles()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val joinedCircles: StateFlow<List<Circle>> = circleRepository.getJoinedCircles()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun joinCircle(id: String) {
        viewModelScope.launch {
            circleRepository.joinCircle(id)
        }
    }

    fun leaveCircle(id: String) {
        viewModelScope.launch {
            circleRepository.leaveCircle(id)
        }
    }

    fun syncStats(id: String) {
        viewModelScope.launch {
            circleRepository.syncCircleStats(id)
        }
    }
}

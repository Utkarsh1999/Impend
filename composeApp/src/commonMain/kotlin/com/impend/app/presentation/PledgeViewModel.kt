package com.impend.app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.impend.shared.domain.model.Pledge
import com.impend.shared.domain.repository.PledgeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PledgeViewModel(
    private val pledgeRepository: PledgeRepository
) : ViewModel() {

    val pledges: StateFlow<List<Pledge>> = pledgeRepository.getAllPledges()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun createPledge(category: String, durationHours: Int) {
        viewModelScope.launch {
            val durationMillis = durationHours.toLong() * 60 * 60 * 1000
            pledgeRepository.createPledge(category, durationMillis)
        }
    }
}

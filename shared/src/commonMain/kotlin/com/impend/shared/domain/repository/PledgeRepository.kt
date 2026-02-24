package com.impend.shared.domain.repository

import com.impend.shared.domain.model.Pledge
import kotlinx.coroutines.flow.Flow

interface PledgeRepository {
    suspend fun createPledge(category: String, durationMillis: Long)
    suspend fun getActivePledge(category: String): Pledge?
    fun getAllPledges(): Flow<List<Pledge>>
    suspend fun markPledgeBroken(id: Long)
    suspend fun markPledgeCompleted(id: Long)
    suspend fun getPledgeSummaryStats(): Map<String, Long>
}

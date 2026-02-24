package com.impend.shared.domain.repository

import com.impend.shared.domain.model.Circle
import kotlinx.coroutines.flow.Flow

interface CircleRepository {
    fun getAllCircles(): Flow<List<Circle>>
    fun getJoinedCircles(): Flow<List<Circle>>
    suspend fun joinCircle(id: String)
    suspend fun leaveCircle(id: String)
    suspend fun syncCircleStats(id: String)
    suspend fun reportPledgeSuccess(circleId: String)
}

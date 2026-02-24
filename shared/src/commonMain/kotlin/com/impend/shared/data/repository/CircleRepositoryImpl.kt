package com.impend.shared.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.impend.shared.data.local.ImpendDatabase
import com.impend.shared.domain.model.Circle
import com.impend.shared.domain.repository.CircleRepository
import com.impend.shared.social.SocialRelay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CircleRepositoryImpl(
    database: ImpendDatabase,
    private val socialRelay: SocialRelay
) : CircleRepository {

    private val queries = database.circleEntityQueries

    override fun getAllCircles(): Flow<List<Circle>> {
        return queries.getAllCircles()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { list ->
                list.map { entity ->
                    Circle(
                        id = entity.id,
                        name = entity.name,
                        description = entity.description,
                        memberCount = entity.memberCount?.toInt() ?: 0,
                        groupSuccessRate = entity.groupSuccessRate?.toFloat() ?: 0f,
                        isJoined = entity.isJoined == 1L
                    )
                }
            }
    }

    override fun getJoinedCircles(): Flow<List<Circle>> {
        return queries.getJoinedCircles()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { list ->
                list.map { entity ->
                    Circle(
                        id = entity.id,
                        name = entity.name,
                        description = entity.description,
                        memberCount = entity.memberCount?.toInt() ?: 0,
                        groupSuccessRate = entity.groupSuccessRate?.toFloat() ?: 0f,
                        isJoined = true
                    )
                }
            }
    }

    override suspend fun joinCircle(id: String) {
        queries.joinCircle(id)
        syncCircleStats(id)
    }

    override suspend fun leaveCircle(id: String) {
        queries.leaveCircle(id)
    }

    override suspend fun syncCircleStats(id: String) {
        val stats = socialRelay.getCircleStats(id)
        if (stats != null) {
            queries.updateCircleStats(stats.first.toLong(), stats.second.toDouble(), id)
        }
    }

    override suspend fun reportPledgeSuccess(circleId: String) {
        socialRelay.submitSuccessToken(circleId)
        syncCircleStats(circleId)
    }
}

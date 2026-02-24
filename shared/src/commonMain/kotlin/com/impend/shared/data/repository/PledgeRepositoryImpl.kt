package com.impend.shared.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.impend.shared.data.local.ImpendDatabase
import com.impend.shared.domain.model.Pledge
import com.impend.shared.domain.model.PledgeStatus
import com.impend.shared.domain.repository.PledgeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class PledgeRepositoryImpl(
    db: ImpendDatabase
) : PledgeRepository {
    private val queries = db.expenseEntityQueries

    override suspend fun createPledge(category: String, durationMillis: Long) {
        val endTime = Clock.System.now().toEpochMilliseconds() + durationMillis
        queries.insertPledge(
            category = category,
            endTime = endTime,
            status = PledgeStatus.ACTIVE.name
        )
    }

    override suspend fun getActivePledge(category: String): Pledge? {
        val now = Clock.System.now().toEpochMilliseconds()
        return queries.getActivePledgeForCategory(category, now)
            .executeAsOneOrNull()
            ?.let {
                Pledge(
                    id = it.id,
                    category = it.category,
                    endTimeMillis = it.endTime,
                    status = PledgeStatus.valueOf(it.status)
                )
            }
    }

    override fun getAllPledges(): Flow<List<Pledge>> {
        return queries.getAllPledges()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { list ->
                list.map {
                    Pledge(
                        id = it.id,
                        category = it.category,
                        endTimeMillis = it.endTime,
                        status = PledgeStatus.valueOf(it.status)
                    )
                }
            }
    }

    override suspend fun markPledgeBroken(id: Long) {
        queries.updatePledgeStatus(PledgeStatus.BROKEN.name, id)
    }

    override suspend fun markPledgeCompleted(id: Long) {
        queries.updatePledgeStatus(PledgeStatus.COMPLETED.name, id)
    }

    override suspend fun getPledgeSummaryStats(): Map<String, Long> {
        return queries.getPledgeSummaryStats().executeAsList().associate { 
            it.status to it.pledgeCount
        }
    }
}

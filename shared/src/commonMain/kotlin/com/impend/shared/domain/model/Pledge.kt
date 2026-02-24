package com.impend.shared.domain.model

data class Pledge(
    val id: Long = 0,
    val category: String,
    val endTimeMillis: Long,
    val status: PledgeStatus
)

enum class PledgeStatus {
    ACTIVE,
    COMPLETED,
    BROKEN
}

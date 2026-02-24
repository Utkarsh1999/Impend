package com.impend.shared.domain.model

data class Circle(
    val id: String,
    val name: String,
    val description: String?,
    val memberCount: Int,
    val groupSuccessRate: Float,
    val isJoined: Boolean
)

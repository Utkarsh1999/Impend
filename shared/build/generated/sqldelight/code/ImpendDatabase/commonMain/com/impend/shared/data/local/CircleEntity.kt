package com.impend.shared.`data`.local

import kotlin.Double
import kotlin.Long
import kotlin.String

public data class CircleEntity(
  public val id: String,
  public val name: String,
  public val description: String?,
  public val memberCount: Long?,
  public val groupSuccessRate: Double?,
  public val isJoined: Long?,
)

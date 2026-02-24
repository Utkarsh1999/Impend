package com.impend.shared.`data`.local

import kotlin.Long
import kotlin.String

public data class PledgeEntity(
  public val id: Long,
  public val category: String,
  public val endTime: Long,
  public val status: String,
)

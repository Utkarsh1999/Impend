package com.impend.shared.`data`.local

import kotlin.Double
import kotlin.Long
import kotlin.String

public data class ExpenseEntity(
  public val id: Long,
  public val amount: Double,
  public val category: String,
  public val dateMillis: Long,
  public val customTag: String?,
  public val moodScore: Long?,
)

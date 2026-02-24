package com.impend.shared.`data`.local

import app.cash.sqldelight.Transacter
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import com.impend.shared.`data`.local.shared.newInstance
import com.impend.shared.`data`.local.shared.schema
import kotlin.Unit

public interface ImpendDatabase : Transacter {
  public val circleEntityQueries: CircleEntityQueries

  public val expenseEntityQueries: ExpenseEntityQueries

  public companion object {
    public val Schema: SqlSchema<QueryResult.Value<Unit>>
      get() = ImpendDatabase::class.schema

    public operator fun invoke(driver: SqlDriver): ImpendDatabase =
        ImpendDatabase::class.newInstance(driver)
  }
}

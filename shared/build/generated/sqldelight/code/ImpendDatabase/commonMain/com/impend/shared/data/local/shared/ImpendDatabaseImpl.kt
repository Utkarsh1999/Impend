package com.impend.shared.`data`.local.shared

import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.AfterVersion
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import com.impend.shared.`data`.local.CircleEntityQueries
import com.impend.shared.`data`.local.ExpenseEntityQueries
import com.impend.shared.`data`.local.ImpendDatabase
import kotlin.Long
import kotlin.Unit
import kotlin.reflect.KClass

internal val KClass<ImpendDatabase>.schema: SqlSchema<QueryResult.Value<Unit>>
  get() = ImpendDatabaseImpl.Schema

internal fun KClass<ImpendDatabase>.newInstance(driver: SqlDriver): ImpendDatabase =
    ImpendDatabaseImpl(driver)

private class ImpendDatabaseImpl(
  driver: SqlDriver,
) : TransacterImpl(driver), ImpendDatabase {
  override val circleEntityQueries: CircleEntityQueries = CircleEntityQueries(driver)

  override val expenseEntityQueries: ExpenseEntityQueries = ExpenseEntityQueries(driver)

  public object Schema : SqlSchema<QueryResult.Value<Unit>> {
    override val version: Long
      get() = 4

    override fun create(driver: SqlDriver): QueryResult.Value<Unit> {
      driver.execute(null, """
          |CREATE TABLE CircleEntity (
          |    id TEXT PRIMARY KEY,
          |    name TEXT NOT NULL,
          |    description TEXT,
          |    memberCount INTEGER DEFAULT 0,
          |    groupSuccessRate REAL DEFAULT 0.0,
          |    isJoined INTEGER DEFAULT 0
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE CircleMemberEntity (
          |    circleId TEXT NOT NULL,
          |    anonymousId TEXT NOT NULL,
          |    PRIMARY KEY (circleId, anonymousId)
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE ExpenseEntity (
          |    id INTEGER PRIMARY KEY AUTOINCREMENT,
          |    amount REAL NOT NULL,
          |    category TEXT NOT NULL,
          |    dateMillis INTEGER NOT NULL,
          |    customTag TEXT,
          |    moodScore INTEGER
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE PledgeEntity (
          |    id INTEGER PRIMARY KEY AUTOINCREMENT,
          |    category TEXT NOT NULL,
          |    endTime INTEGER NOT NULL, -- Epoch millis
          |    status TEXT NOT NULL -- 'ACTIVE', 'COMPLETED', 'BROKEN'
          |)
          """.trimMargin(), 0)
      return QueryResult.Unit
    }

    private fun migrateInternal(
      driver: SqlDriver,
      oldVersion: Long,
      newVersion: Long,
    ): QueryResult.Value<Unit> {
      if (oldVersion <= 1 && newVersion > 1) {
        driver.execute(null, """
            |CREATE TABLE ExpenseEntity_new (
            |    id INTEGER PRIMARY KEY AUTOINCREMENT,
            |    amount REAL NOT NULL,
            |    category TEXT NOT NULL,
            |    dateMillis INTEGER NOT NULL,
            |    customTag TEXT,
            |    moodScore INTEGER
            |)
            """.trimMargin(), 0)
        driver.execute(null, """
            |INSERT INTO ExpenseEntity_new (amount, category, dateMillis, customTag, moodScore)
            |SELECT amount, category, dateMillis, customTag, moodScore FROM ExpenseEntity
            """.trimMargin(), 0)
        driver.execute(null, "DROP TABLE ExpenseEntity", 0)
        driver.execute(null, "ALTER TABLE ExpenseEntity_new RENAME TO ExpenseEntity", 0)
      }
      if (oldVersion <= 2 && newVersion > 2) {
        driver.execute(null, """
            |CREATE TABLE PledgeEntity (
            |    id INTEGER PRIMARY KEY AUTOINCREMENT,
            |    category TEXT NOT NULL,
            |    endTime INTEGER NOT NULL,
            |    status TEXT NOT NULL
            |)
            """.trimMargin(), 0)
      }
      if (oldVersion <= 3 && newVersion > 3) {
        driver.execute(null, """
            |CREATE TABLE CircleEntity (
            |    id TEXT PRIMARY KEY,
            |    name TEXT NOT NULL,
            |    description TEXT,
            |    memberCount INTEGER DEFAULT 0,
            |    groupSuccessRate REAL DEFAULT 0.0,
            |    isJoined INTEGER DEFAULT 0
            |)
            """.trimMargin(), 0)
        driver.execute(null, """
            |CREATE TABLE CircleMemberEntity (
            |    circleId TEXT NOT NULL,
            |    anonymousId TEXT NOT NULL,
            |    PRIMARY KEY (circleId, anonymousId)
            |)
            """.trimMargin(), 0)
      }
      return QueryResult.Unit
    }

    override fun migrate(
      driver: SqlDriver,
      oldVersion: Long,
      newVersion: Long,
      vararg callbacks: AfterVersion,
    ): QueryResult.Value<Unit> {
      var lastVersion = oldVersion

      callbacks.filter { it.afterVersion in oldVersion until newVersion }
      .sortedBy { it.afterVersion }
      .forEach { callback ->
        migrateInternal(driver, oldVersion = lastVersion, newVersion = callback.afterVersion + 1)
        callback.block(driver)
        lastVersion = callback.afterVersion + 1
      }

      if (lastVersion < newVersion) {
        migrateInternal(driver, lastVersion, newVersion)
      }
      return QueryResult.Unit
    }
  }
}

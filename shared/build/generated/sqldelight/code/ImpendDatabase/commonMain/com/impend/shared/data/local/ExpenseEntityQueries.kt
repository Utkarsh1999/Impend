package com.impend.shared.`data`.local

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor
import app.cash.sqldelight.db.SqlDriver
import kotlin.Any
import kotlin.Double
import kotlin.Long
import kotlin.String

public class ExpenseEntityQueries(
  driver: SqlDriver,
) : TransacterImpl(driver) {
  public fun <T : Any> getExpenseById(id: Long, mapper: (
    id: Long,
    amount: Double,
    category: String,
    dateMillis: Long,
    customTag: String?,
    moodScore: Long?,
  ) -> T): Query<T> = GetExpenseByIdQuery(id) { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getDouble(1)!!,
      cursor.getString(2)!!,
      cursor.getLong(3)!!,
      cursor.getString(4),
      cursor.getLong(5)
    )
  }

  public fun getExpenseById(id: Long): Query<ExpenseEntity> = getExpenseById(id) { id_, amount,
      category, dateMillis, customTag, moodScore ->
    ExpenseEntity(
      id_,
      amount,
      category,
      dateMillis,
      customTag,
      moodScore
    )
  }

  public fun <T : Any> getAllExpenses(mapper: (
    id: Long,
    amount: Double,
    category: String,
    dateMillis: Long,
    customTag: String?,
    moodScore: Long?,
  ) -> T): Query<T> = Query(1_381_132_627, arrayOf("ExpenseEntity"), driver, "ExpenseEntity.sq",
      "getAllExpenses",
      "SELECT ExpenseEntity.id, ExpenseEntity.amount, ExpenseEntity.category, ExpenseEntity.dateMillis, ExpenseEntity.customTag, ExpenseEntity.moodScore FROM ExpenseEntity ORDER BY dateMillis DESC") {
      cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getDouble(1)!!,
      cursor.getString(2)!!,
      cursor.getLong(3)!!,
      cursor.getString(4),
      cursor.getLong(5)
    )
  }

  public fun getAllExpenses(): Query<ExpenseEntity> = getAllExpenses { id, amount, category,
      dateMillis, customTag, moodScore ->
    ExpenseEntity(
      id,
      amount,
      category,
      dateMillis,
      customTag,
      moodScore
    )
  }

  public fun <T : Any> getActivePledgeForCategory(
    category: String,
    endTime: Long,
    mapper: (
      id: Long,
      category: String,
      endTime: Long,
      status: String,
    ) -> T,
  ): Query<T> = GetActivePledgeForCategoryQuery(category, endTime) { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getLong(2)!!,
      cursor.getString(3)!!
    )
  }

  public fun getActivePledgeForCategory(category: String, endTime: Long): Query<PledgeEntity> =
      getActivePledgeForCategory(category, endTime) { id, category_, endTime_, status ->
    PledgeEntity(
      id,
      category_,
      endTime_,
      status
    )
  }

  public fun <T : Any> getAllPledges(mapper: (
    id: Long,
    category: String,
    endTime: Long,
    status: String,
  ) -> T): Query<T> = Query(-937_702_238, arrayOf("PledgeEntity"), driver, "ExpenseEntity.sq",
      "getAllPledges",
      "SELECT PledgeEntity.id, PledgeEntity.category, PledgeEntity.endTime, PledgeEntity.status FROM PledgeEntity ORDER BY endTime DESC") {
      cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getLong(2)!!,
      cursor.getString(3)!!
    )
  }

  public fun getAllPledges(): Query<PledgeEntity> = getAllPledges { id, category, endTime, status ->
    PledgeEntity(
      id,
      category,
      endTime,
      status
    )
  }

  public fun <T : Any> getExpensesInRange(
    dateMillis: Long,
    dateMillis_: Long,
    mapper: (
      id: Long,
      amount: Double,
      category: String,
      dateMillis: Long,
      customTag: String?,
      moodScore: Long?,
    ) -> T,
  ): Query<T> = GetExpensesInRangeQuery(dateMillis, dateMillis_) { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getDouble(1)!!,
      cursor.getString(2)!!,
      cursor.getLong(3)!!,
      cursor.getString(4),
      cursor.getLong(5)
    )
  }

  public fun getExpensesInRange(dateMillis: Long, dateMillis_: Long): Query<ExpenseEntity> =
      getExpensesInRange(dateMillis, dateMillis_) { id, amount, category, dateMillis__, customTag,
      moodScore ->
    ExpenseEntity(
      id,
      amount,
      category,
      dateMillis__,
      customTag,
      moodScore
    )
  }

  public fun <T : Any> getPledgeSummaryStats(mapper: (status: String, pledgeCount: Long) -> T):
      Query<T> = Query(1_455_287_195, arrayOf("PledgeEntity"), driver, "ExpenseEntity.sq",
      "getPledgeSummaryStats", """
  |SELECT status, COUNT(*) AS pledgeCount 
  |FROM PledgeEntity 
  |GROUP BY status
  """.trimMargin()) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getLong(1)!!
    )
  }

  public fun getPledgeSummaryStats(): Query<GetPledgeSummaryStats> = getPledgeSummaryStats { status,
      pledgeCount ->
    GetPledgeSummaryStats(
      status,
      pledgeCount
    )
  }

  public fun insertExpense(
    amount: Double,
    category: String,
    dateMillis: Long,
    customTag: String?,
    moodScore: Long?,
  ) {
    driver.execute(933_564_178, """
        |INSERT INTO ExpenseEntity(amount, category, dateMillis, customTag, moodScore)
        |VALUES (?, ?, ?, ?, ?)
        """.trimMargin(), 5) {
          bindDouble(0, amount)
          bindString(1, category)
          bindLong(2, dateMillis)
          bindString(3, customTag)
          bindLong(4, moodScore)
        }
    notifyQueries(933_564_178) { emit ->
      emit("ExpenseEntity")
    }
  }

  public fun deleteExpenseById(id: Long) {
    driver.execute(617_502_674, """DELETE FROM ExpenseEntity WHERE id = ?""", 1) {
          bindLong(0, id)
        }
    notifyQueries(617_502_674) { emit ->
      emit("ExpenseEntity")
    }
  }

  public fun clearAllExpenses() {
    driver.execute(1_998_527_164, """DELETE FROM ExpenseEntity""", 0)
    notifyQueries(1_998_527_164) { emit ->
      emit("ExpenseEntity")
    }
  }

  public fun insertPledge(
    category: String,
    endTime: Long,
    status: String,
  ) {
    driver.execute(1_164_908_479, """
        |INSERT INTO PledgeEntity(category, endTime, status)
        |VALUES (?, ?, ?)
        """.trimMargin(), 3) {
          bindString(0, category)
          bindLong(1, endTime)
          bindString(2, status)
        }
    notifyQueries(1_164_908_479) { emit ->
      emit("PledgeEntity")
    }
  }

  public fun updatePledgeStatus(status: String, id: Long) {
    driver.execute(-189_159_775, """UPDATE PledgeEntity SET status = ? WHERE id = ?""", 2) {
          bindString(0, status)
          bindLong(1, id)
        }
    notifyQueries(-189_159_775) { emit ->
      emit("PledgeEntity")
    }
  }

  private inner class GetExpenseByIdQuery<out T : Any>(
    public val id: Long,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("ExpenseEntity", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("ExpenseEntity", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(1_520_051_649,
        """SELECT ExpenseEntity.id, ExpenseEntity.amount, ExpenseEntity.category, ExpenseEntity.dateMillis, ExpenseEntity.customTag, ExpenseEntity.moodScore FROM ExpenseEntity WHERE id = ?""",
        mapper, 1) {
      bindLong(0, id)
    }

    override fun toString(): String = "ExpenseEntity.sq:getExpenseById"
  }

  private inner class GetActivePledgeForCategoryQuery<out T : Any>(
    public val category: String,
    public val endTime: Long,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("PledgeEntity", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("PledgeEntity", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(107_851_263, """
    |SELECT PledgeEntity.id, PledgeEntity.category, PledgeEntity.endTime, PledgeEntity.status FROM PledgeEntity 
    |WHERE category = ? AND status = 'ACTIVE' AND endTime > ?
    |LIMIT 1
    """.trimMargin(), mapper, 2) {
      bindString(0, category)
      bindLong(1, endTime)
    }

    override fun toString(): String = "ExpenseEntity.sq:getActivePledgeForCategory"
  }

  private inner class GetExpensesInRangeQuery<out T : Any>(
    public val dateMillis: Long,
    public val dateMillis_: Long,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("ExpenseEntity", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("ExpenseEntity", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(-958_316_172, """
    |SELECT ExpenseEntity.id, ExpenseEntity.amount, ExpenseEntity.category, ExpenseEntity.dateMillis, ExpenseEntity.customTag, ExpenseEntity.moodScore FROM ExpenseEntity 
    |WHERE dateMillis >= ? AND dateMillis <= ? 
    |ORDER BY dateMillis ASC
    """.trimMargin(), mapper, 2) {
      bindLong(0, dateMillis)
      bindLong(1, dateMillis_)
    }

    override fun toString(): String = "ExpenseEntity.sq:getExpensesInRange"
  }
}

package com.impend.shared.`data`.local

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.SqlDriver
import kotlin.Any
import kotlin.Double
import kotlin.Long
import kotlin.String

public class CircleEntityQueries(
  driver: SqlDriver,
) : TransacterImpl(driver) {
  public fun <T : Any> getAllCircles(mapper: (
    id: String,
    name: String,
    description: String?,
    memberCount: Long?,
    groupSuccessRate: Double?,
    isJoined: Long?,
  ) -> T): Query<T> = Query(-301_553_605, arrayOf("CircleEntity"), driver, "CircleEntity.sq",
      "getAllCircles",
      "SELECT CircleEntity.id, CircleEntity.name, CircleEntity.description, CircleEntity.memberCount, CircleEntity.groupSuccessRate, CircleEntity.isJoined FROM CircleEntity") {
      cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2),
      cursor.getLong(3),
      cursor.getDouble(4),
      cursor.getLong(5)
    )
  }

  public fun getAllCircles(): Query<CircleEntity> = getAllCircles { id, name, description,
      memberCount, groupSuccessRate, isJoined ->
    CircleEntity(
      id,
      name,
      description,
      memberCount,
      groupSuccessRate,
      isJoined
    )
  }

  public fun <T : Any> getJoinedCircles(mapper: (
    id: String,
    name: String,
    description: String?,
    memberCount: Long?,
    groupSuccessRate: Double?,
    isJoined: Long?,
  ) -> T): Query<T> = Query(2_071_242_753, arrayOf("CircleEntity"), driver, "CircleEntity.sq",
      "getJoinedCircles",
      "SELECT CircleEntity.id, CircleEntity.name, CircleEntity.description, CircleEntity.memberCount, CircleEntity.groupSuccessRate, CircleEntity.isJoined FROM CircleEntity WHERE isJoined = 1") {
      cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2),
      cursor.getLong(3),
      cursor.getDouble(4),
      cursor.getLong(5)
    )
  }

  public fun getJoinedCircles(): Query<CircleEntity> = getJoinedCircles { id, name, description,
      memberCount, groupSuccessRate, isJoined ->
    CircleEntity(
      id,
      name,
      description,
      memberCount,
      groupSuccessRate,
      isJoined
    )
  }

  public fun joinCircle(id: String) {
    driver.execute(1_194_920_439, """UPDATE CircleEntity SET isJoined = 1 WHERE id = ?""", 1) {
          bindString(0, id)
        }
    notifyQueries(1_194_920_439) { emit ->
      emit("CircleEntity")
    }
  }

  public fun leaveCircle(id: String) {
    driver.execute(-1_594_051_926, """UPDATE CircleEntity SET isJoined = 0 WHERE id = ?""", 1) {
          bindString(0, id)
        }
    notifyQueries(-1_594_051_926) { emit ->
      emit("CircleEntity")
    }
  }

  public fun updateCircleStats(
    memberCount: Long?,
    groupSuccessRate: Double?,
    id: String,
  ) {
    driver.execute(-1_292_680_407,
        """UPDATE CircleEntity SET memberCount = ?, groupSuccessRate = ? WHERE id = ?""", 3) {
          bindLong(0, memberCount)
          bindDouble(1, groupSuccessRate)
          bindString(2, id)
        }
    notifyQueries(-1_292_680_407) { emit ->
      emit("CircleEntity")
    }
  }

  public fun insertCircle(
    id: String,
    name: String,
    description: String?,
    memberCount: Long?,
    groupSuccessRate: Double?,
    isJoined: Long?,
  ) {
    driver.execute(492_692_742, """
        |INSERT OR REPLACE INTO CircleEntity (id, name, description, memberCount, groupSuccessRate, isJoined)
        |VALUES (?, ?, ?, ?, ?, ?)
        """.trimMargin(), 6) {
          bindString(0, id)
          bindString(1, name)
          bindString(2, description)
          bindLong(3, memberCount)
          bindDouble(4, groupSuccessRate)
          bindLong(5, isJoined)
        }
    notifyQueries(492_692_742) { emit ->
      emit("CircleEntity")
    }
  }
}

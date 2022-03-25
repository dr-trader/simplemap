package ru.donspb.simplemap.room

import androidx.room.*

@Dao
interface PointsDao {
    @Query("SELECT * FROM entity")
    suspend fun getAll(): List<Entity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: Entity)

    @Update
    suspend fun update(entity: Entity)

    @Delete
    suspend fun delete(entity: Entity)
}
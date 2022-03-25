package ru.donspb.simplemap.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Entity::class], version = 1, exportSchema = false)
abstract class PointsDB : RoomDatabase() {
    abstract fun pointsDao(): PointsDao
}
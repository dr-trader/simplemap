package ru.donspb.simplemap.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Entity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String?,
    val description: String?,
    val lat: Double,
    val lon: Double
)
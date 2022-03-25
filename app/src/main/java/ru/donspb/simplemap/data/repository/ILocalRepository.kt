package ru.donspb.simplemap.data.repository

import ru.donspb.simplemap.PointData

interface ILocalRepository {
    suspend fun getPoints(): List<PointData>
    suspend fun setPoint(point: PointData)
    suspend fun savePoint(point: PointData)
}
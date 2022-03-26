package ru.donspb.simplemap.data.repository

import ru.donspb.simplemap.data.data.PointData

interface ILocalRepository {
    suspend fun getPoints(): List<PointData>
    suspend fun setPoint(point: PointData)
    suspend fun savePoint(point: PointData)
    suspend fun deletePoint(point: PointData)
}
package ru.donspb.simplemap.data.repository

import ru.donspb.simplemap.data.data.PointData
import ru.donspb.simplemap.room.Entity
import ru.donspb.simplemap.room.PointsDao

class LocalRepository(private val dao: PointsDao) : ILocalRepository {
    override suspend fun getPoints(): List<PointData> {
        return dao.getAll().map{ PointData(it.lat, it.lon, it.name, it.description) }
    }

    override suspend fun setPoint(point: PointData) {
        dao.insert(Entity(null, point.name, point.description, point.lat, point.lon))
    }

    override suspend fun savePoint(point: PointData) {
        val id = dao.getEntityId(point.lat, point.lon)
        dao.update(Entity(id, point.name, point.description, point.lat, point.lon))
    }
}
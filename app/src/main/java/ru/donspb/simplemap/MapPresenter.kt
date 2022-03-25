package ru.donspb.simplemap

import kotlinx.coroutines.*
import ru.donspb.simplemap.data.repository.ILocalRepository

class MapPresenter(
    private val mapView: IMapView,
    private val localRepository: ILocalRepository,
    private val localCoroutineScope: CoroutineScope) {

    private var newPoint: PointData? = null

    fun showPoints() = localCoroutineScope.launch {
        val pointsList = localRepository.getPoints()
        if (!pointsList.isNullOrEmpty()) mapView.showPoints(pointsList)
    }

    fun pointSelected(lat: Double, lon: Double) {
        newPoint = PointData(lat, lon)
        mapView.showDialog()
    }

    fun savePointToDB(name: String?) {
        if (!name.isNullOrEmpty() && (newPoint != null)) {
            newPoint!!.name = name
            localCoroutineScope.launch {
                localRepository.setPoint(newPoint!!)
                mapView.showPoints(listOf(newPoint!!))
                newPoint = null
            }
        } else newPoint = null
    }


}
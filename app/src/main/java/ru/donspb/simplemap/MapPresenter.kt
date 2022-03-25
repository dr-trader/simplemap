package ru.donspb.simplemap

import kotlinx.coroutines.*
import ru.donspb.simplemap.data.repository.ILocalRepository

class MapPresenter(val mapView: IMapView,
                   val localRepository: ILocalRepository,
                   val localCoroutineScope: CoroutineScope) {

    var newPoint: PointData? = null



    fun showPoints() = localCoroutineScope.launch(Dispatchers.IO) {
        val pointsList = withContext(Dispatchers.IO) { localRepository.getPoints() }
        if (!pointsList.isNullOrEmpty()) mapView.showPoints(pointsList)
    }

    fun pointSelected(lat: Double, lon: Double) {
        newPoint = PointData(lat, lon)
        mapView.showDialog()
    }

    fun savePointToDB(name: String?) {
        if (!name.isNullOrEmpty() && (newPoint != null)) {
            newPoint!!.name = name
            localCoroutineScope.launch(Dispatchers.IO) {
                localRepository.setPoint(newPoint!!)
            }
        }
        newPoint = null
    }


}
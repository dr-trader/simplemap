package ru.donspb.simplemap.ui.map

import ru.donspb.simplemap.data.data.PointData

interface IMapView {
    fun showDialog()
    fun showPoints(pointsList: List<PointData>)
}
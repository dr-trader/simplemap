package ru.donspb.simplemap

interface IMapView {
    fun showDialog()
    fun showPoints(pointsList: List<PointData>)
}
package ru.donspb.simplemap

import android.view.View

interface IMapView {
    fun showDialog()
    fun showPoints(pointsList: List<PointData>)
}
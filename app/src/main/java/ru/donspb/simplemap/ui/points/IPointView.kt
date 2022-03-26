package ru.donspb.simplemap.ui.points

import ru.donspb.simplemap.data.data.PointData

interface IPointView {
    fun fillAdapter(data: List<PointData>)
    fun showEditDialog(data: PointData)
    fun updateRV(position: Int, data: PointData)
    fun updateDeletedRV(position: Int)
}
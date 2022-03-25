package ru.donspb.simplemap.ui.points

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.donspb.simplemap.data.data.PointData
import ru.donspb.simplemap.data.repository.ILocalRepository

class PointsPresenter(
    private val pointView: IPointView,
    private val localRepository: ILocalRepository,
    private val localCoroutineScope: CoroutineScope) {

    private var editedPosition: Int? = null

    fun getPointsData() = localCoroutineScope.launch {
        val pointsList = localRepository.getPoints()
        if (!pointsList.isNullOrEmpty()) pointView.fillAdapter(pointsList)
    }

    fun editPoint(data: PointData, pos: Int) {
        editedPosition = pos
        pointView.showEditDialog(data)
    }

    fun saveChangesToDb(data: PointData) = localCoroutineScope.launch {
        localRepository.savePoint(data)
        editedPosition?.let { pointView.updateRV(it, data) }
    }
}
package ru.donspb.simplemap

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.donspb.simplemap.data.repository.ILocalRepository

class PointsPresenter(
    private val pointView: IPointView,
    private val localRepository: ILocalRepository,
    private val localCoroutineScope: CoroutineScope) {

    fun getPointsData() = localCoroutineScope.launch {
        val pointsList = localRepository.getPoints()
        if (!pointsList.isNullOrEmpty()) pointView.fillAdapter(pointsList)
    }
}
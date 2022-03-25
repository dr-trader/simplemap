package ru.donspb.simplemap

data class PointData(
    val lat: Double,
    val lon: Double,
    var name: String? = null,
    var description: String? = null
)

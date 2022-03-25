package ru.donspb.simplemap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PointsRVAdapter : RecyclerView.Adapter<PointsRVAdapter.PointsRVHolder>() {

    val dataSet: MutableList<PointData> = mutableListOf()

    inner class PointsRVHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun setPointsData(data: PointData) {

        }

    }

    fun setData(data: List<PointData>) {
        dataSet.clear()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointsRVHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.points_rv_item, parent, false)
        return PointsRVHolder(view)
    }

    override fun onBindViewHolder(holder: PointsRVHolder, position: Int) {
        holder.setPointsData(dataSet[position])
    }

    override fun getItemCount() = dataSet.size
}
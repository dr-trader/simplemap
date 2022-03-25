package ru.donspb.simplemap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import ru.donspb.simplemap.databinding.PointsRvItemBinding

class PointsRVAdapter(presenter: PointsPresenter)
    : RecyclerView.Adapter<PointsRVAdapter.PointsRVHolder>() {

    var dataSet: List<PointData> = listOf()

    init {
        presenter.getPointsData()
    }

    inner class PointsRVHolder(val binding: PointsRvItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun setPointsData(data: PointData) {
            with(binding) {
                tvName.text = data.name
                tvLat.text = String.format("%.3f", data.lat)
                tvLon.text = String.format("%.3f", data.lon)
                if (data.description.isNullOrEmpty()) {
                    tvDescr.text = R.string.description_default_text.toString()
                } else {
                    tvDescr.text = data.description
                }
            }
        }

    }

    fun setData(data: List<PointData>) {
        if (!data.isNullOrEmpty()) {
            dataSet = data
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointsRVHolder =
        PointsRVHolder(PointsRvItemBinding.inflate(LayoutInflater.from(parent.context),
        parent, false)).apply {
            itemView.setOnClickListener {

            }
        }

    override fun onBindViewHolder(holder: PointsRVHolder, position: Int) {
        holder.setPointsData(dataSet[position])
    }

    override fun getItemCount() = dataSet.size
}
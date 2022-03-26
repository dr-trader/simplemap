package ru.donspb.simplemap.ui.points

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.donspb.simplemap.R
import ru.donspb.simplemap.data.data.PointData

import ru.donspb.simplemap.databinding.PointsRvItemBinding

class PointsRVAdapter(private val presenter: PointsPresenter)
    : RecyclerView.Adapter<PointsRVAdapter.PointsRVHolder>() {

    private var dataSet: MutableList<PointData> = mutableListOf()

    init {
        presenter.getPointsData()
    }

    inner class PointsRVHolder(private val binding: PointsRvItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun setPointsData(data: PointData) {
            with(binding) {
                tvName.text = data.name
                tvLat.text = String.format("%.3f", data.lat)
                tvLon.text = String.format("%.3f", data.lon)
                if (data.description.isNullOrEmpty()) {
                    tvDescr.text = itemView.context.getString(R.string.description_default_text)
                } else {
                    tvDescr.text = data.description
                }
                btnDelete.setOnClickListener {
                    presenter.deletePoint(data, adapterPosition)
                }
            }
        }

    }

    fun setData(data: List<PointData>) {
        if (!data.isNullOrEmpty()) {
            dataSet.addAll(data)
            notifyDataSetChanged()
        }
    }

    fun updateData(position: Int, data: PointData) {
        dataSet[position].name = data.name
        dataSet[position].description = data.description
        notifyItemChanged(position)
    }

    fun dataRemoved(position: Int) {
        dataSet.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointsRVHolder =
        PointsRVHolder(PointsRvItemBinding.inflate(LayoutInflater.from(parent.context),
        parent, false)).apply {
            itemView.setOnClickListener {
                presenter.editPoint(dataSet[adapterPosition], adapterPosition)
            }
        }

    override fun onBindViewHolder(holder: PointsRVHolder, position: Int) {
        holder.setPointsData(dataSet[position])
    }

    override fun getItemCount() = dataSet.size
}
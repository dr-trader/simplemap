package ru.donspb.simplemap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import ru.donspb.simplemap.App.Companion.getDatabase
import ru.donspb.simplemap.data.repository.LocalRepository
import ru.donspb.simplemap.databinding.ActivityPointsBinding

class PointsActivity : AppCompatActivity(), IPointView {

    private lateinit var binding: ActivityPointsBinding
    private val presenter: PointsPresenter = PointsPresenter(
        this,
        LocalRepository(getDatabase()),
        lifecycleScope)
    private val adapter = PointsRVAdapter(presenter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPointsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pointsRecycler.adapter = adapter
    }

    override fun fillAdapter(data: List<PointData>) {
        adapter.setData(data)
    }

}
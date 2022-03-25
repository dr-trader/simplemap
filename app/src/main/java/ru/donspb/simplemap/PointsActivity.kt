package ru.donspb.simplemap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.donspb.simplemap.databinding.ActivityPointsBinding

class PointsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPointsBinding
    private val adapter = PointsRVAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPointsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pointsRecycler.adapter = adapter
    }

}
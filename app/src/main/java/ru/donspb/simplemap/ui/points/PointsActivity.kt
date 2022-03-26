package ru.donspb.simplemap.ui.points

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import ru.donspb.simplemap.App.Companion.getDatabase
import ru.donspb.simplemap.R
import ru.donspb.simplemap.data.data.PointData
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

    override fun showEditDialog(data: PointData) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(String.format("%.3f, %.3f", data.lat, data.lon))
        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.VERTICAL
        val nameInput = EditText(this)
        val descrInput = EditText(this)
        val nameTitle = TextView(this)
        nameTitle.text = getString(R.string.title_name)
        val descrTitle = TextView(this)
        descrTitle.text = getString(R.string.title_descr)
        if (data.name != null) nameInput.text = Editable.Factory.getInstance().newEditable(data.name)
        else nameInput.setText("")
        if (data.description != null) descrInput.text = Editable.Factory.getInstance().newEditable(data.description)
        else descrInput.setText("")
        nameInput.inputType = InputType.TYPE_CLASS_TEXT
        descrInput.inputType = InputType.TYPE_CLASS_TEXT
        linearLayout.addView(nameTitle)
        linearLayout.addView(nameInput)
        linearLayout.addView(descrTitle)
        linearLayout.addView(descrInput)
        builder.setView(linearLayout)
        builder.setPositiveButton(getString(R.string.dialog_ok_button)) { _, _ ->
            presenter.saveChangesToDb(
                PointData(data.lat, data.lon, nameInput.text.toString(), descrInput.text.toString())
            )
        }
        builder.setNegativeButton(getString(R.string.dialog_cancel_button)) { dialogInterface, _ -> dialogInterface.cancel() }
        builder.show()
    }

    override fun updateRV(position: Int, data: PointData) {
        adapter.updateData(position, data)
    }

    override fun updateDeletedRV(position: Int) {
        adapter.notifyItemRemoved(position)
    }

}
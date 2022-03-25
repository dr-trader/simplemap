package ru.donspb.simplemap

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import ru.donspb.simplemap.App.Companion.getDatabase
import ru.donspb.simplemap.data.repository.LocalRepository
import ru.donspb.simplemap.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, IMapView {

    private val REQUEST_CODE = 22001
    private var locationPermissionGranted: Boolean = false
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    val mapCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        })
    private val presenter: MapPresenter = MapPresenter(this,
        LocalRepository(getDatabase()), mapCoroutineScope)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        binding.fab.setOnClickListener {
            val intent = Intent(this@MapsActivity, PointsActivity::class.java)
            startActivity(intent)
        }

        getLocationPermission()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapClickListener {
            presenter.pointSelected(it.latitude, it.longitude)
        }

        if (locationPermissionGranted) getDeviceLocation()
        else setDefaultLocation()

        presenter.showPoints()
    }

    private fun setDefaultLocation() {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(55.0, 37.0)))
        mMap.moveCamera(CameraUpdateFactory.zoomTo(5F))
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true

        val fusedLocationProviderClient = FusedLocationProviderClient(this)
        val locationResult = fusedLocationProviderClient.lastLocation
        locationResult.addOnCompleteListener(this) { task ->
            if (task.isSuccessful && (task.result != null)) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(
                    LatLng(task.result.latitude, task.result.longitude)
                ))
                mMap.moveCamera(CameraUpdateFactory.zoomTo(12F))
            }
            else {
                Log.d("SimpleMap LocWarn:", "Location is null. Defaults used.")
                Log.d("SimpleMap LocWarn:", "Details: %s", task.exception)
                setDefaultLocation()
            }
        }
    }

    override fun showDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val input = EditText(this)
        builder.setTitle(getString(R.string.dialog_addpoint_title))
        input.setHint(R.string.dialog_edittext_hint)
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)
        builder.setPositiveButton(getString(R.string.dialog_ok_button),
            { _, _ -> presenter.savePointToDB(input.text.toString()) })
        builder.setNegativeButton(getString(R.string.dialog_cancel_button),
            { dialogInterface, _ -> dialogInterface.cancel() })
        builder.show()
    }

    override fun showPoints(pointsList: List<PointData>) {
        pointsList.forEach {
            mMap.addMarker(
                MarkerOptions()
                    .title(it.name)
                    .position(LatLng(it.lat, it.lon))
            )
        }
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun handleError(throwable : Throwable) {

    }
}
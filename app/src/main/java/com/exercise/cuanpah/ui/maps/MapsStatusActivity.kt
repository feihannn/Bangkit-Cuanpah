package com.exercise.cuanpah.ui.maps

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.exercise.cuanpah.databinding.ActivityMapsBinding
import com.exercise.cuanpah.databinding.ActivityMapsStatusBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback

class MapsStatusActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding : ActivityMapsStatusBinding
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()

        binding.namaDriverPlaceholder.text= DRIVERNAME
        binding.pickUpTimePlaceholder.text= PICKUPTIME
        binding.statusPlaceholder.text= STATUS
    }
    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
    }

    companion object{
        var DRIVERNAME="NAME"
        var PICKUPTIME="TIME"
        var STATUS="STATUS"
    }
}
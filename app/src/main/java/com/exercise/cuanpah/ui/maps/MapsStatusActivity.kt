package com.exercise.cuanpah.ui.maps

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.exercise.cuanpah.data.ApiService
import com.exercise.cuanpah.databinding.ActivityMapsBinding
import com.exercise.cuanpah.databinding.ActivityMapsStatusBinding
import com.exercise.cuanpah.ui.home.HomeFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

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

        HomeFragment.COUNT=1
        if(STATUS=="Completed"){
            HomeFragment.ORDERED=false
            HomeFragment.COUNT=0
        }



    }

//    override fun onResume() {
//        super.onResume()
//        STATUS="Completed"
//        binding.statusPlaceholder.text= STATUS
//        HomeFragment.ORDERED=false
//        DRIVERNAME="NAME"
//        PICKUPTIME="TIME"
//        STATUS="STATUS"
//        LAT=0.0
//        LONG=0.0
//    }
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

        val latLng = LatLng(LAT, LONG)
        mMap.addMarker(MarkerOptions().position(latLng))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
    }

    companion object{
        var DRIVERNAME="NAME"
        var PICKUPTIME="TIME"
        var STATUS="STATUS"
        var LAT=0.0
        var LONG=0.0
    }
}
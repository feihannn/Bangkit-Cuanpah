package com.exercise.cuanpah.ui.maps

import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.exercise.cuanpah.data.ApiConfig
import com.exercise.cuanpah.data.OrderData
import com.exercise.cuanpah.data.OrderResponse
import com.exercise.cuanpah.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var searchView:SearchView
    private var latGlobal :Double=0.0
    private var lonGlobal :Double=0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        var marker:Marker?=null
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()

        searchView = binding.searchLokasi

        val mapFragment = supportFragmentManager
            .findFragmentById(com.exercise.cuanpah.R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val location: String = searchView.query.toString()
                var addressList: List<Address>? = null

                val geocoder = Geocoder(this@MapsActivity)
                try {
                    addressList = geocoder.getFromLocationName(location, 1)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                val address = addressList!![0]

                marker?.remove()
                val latLng = LatLng(address.latitude, address.longitude)
                latGlobal=address.latitude
                lonGlobal=address.longitude
                marker=mMap.addMarker(MarkerOptions().position(latLng).title(location))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.pesanKurirButton.setOnClickListener { order() }
    }

    private fun order(){
        val preference=getDefaultSharedPreferences(this)
        val userId=preference.getInt("ID",0)
        val selectedTrash=binding.jenisSampahDropdown.selectedItem.toString()
        val inputWeight=binding.beratInputText.text.toString().toDouble()


        Toast.makeText(this@MapsActivity,"Mohon Menunggu",Toast.LENGTH_SHORT).show()

//        Toast.makeText(this@MapsActivity,"$latGlobal,$lonGlobal,$inputWeight,$selectedTrash",Toast.LENGTH_SHORT).show()
        val orderService=ApiConfig().getApiService().requestOrder(OrderData(31,2,latGlobal,lonGlobal,"completed",inputWeight,selectedTrash))
        orderService.enqueue(object : Callback<OrderResponse>{
            override fun onResponse(
                call: Call<OrderResponse>,
                response: Response<OrderResponse>
            ) {
//                Toast.makeText(this@MapsActivity, response.body()?.message ?: "body kosong",Toast.LENGTH_SHORT).show()
                if(response.isSuccessful){
                    val responseBody=response.body()
                    if(responseBody!=null){
                        Toast.makeText(this@MapsActivity,"$responseBody",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this@MapsActivity,"responsenya gagal",Toast.LENGTH_SHORT).show()

                }

            }

            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                Toast.makeText(this@MapsActivity,"Gagal",Toast.LENGTH_SHORT).show()
            }

        })
    }

//    private val requestPermissionLauncher =
//        registerForActivityResult(
//            ActivityResultContracts.RequestPermission()
//        ) { isGranted: Boolean ->
//            if (isGranted) {
//                getMyLocation()
//            }
//        }
//    private fun getMyLocation() {
//        if (ContextCompat.checkSelfPermission(
//                this.applicationContext,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            mMap.isMyLocationEnabled = true
//
//        } else {
//            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//        }
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

        //Setting for dropdown menu
        val spinner: Spinner = findViewById(com.exercise.cuanpah.R.id.jenisSampahDropdown)
        ArrayAdapter.createFromResource(
            this,
            com.exercise.cuanpah.R.array.trash_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        //Setting hint for edit text
        binding.lokasiAndaPlaceholder.hint = "Cari lokasi Anda"
        binding.beratInputText.hint="Berat Sampah"


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true

//        getMyLocation()
    }
}
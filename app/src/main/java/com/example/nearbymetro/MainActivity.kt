package com.example.nearbymetro

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.nearbymetro.databinding.ActivityMainBinding
import com.example.nearbymetro.utils.Constants
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var mMap: GoogleMap? = null
    private var latLang: LatLng? = null
    private var mapFragment: SupportMapFragment? = null

    private var findNearestMetroStationButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        instantiateClassInstances()
        getCurrentLocation()

        findNearestMetroStationButton?.setOnClickListener {
            displayNearestMetroStation()
        }

    }

    private fun instantiateClassInstances() {

        findNearestMetroStationButton = binding.buttonFindNearestMetro
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        if (mapFragment != null) {
            mapFragment?.getMapAsync { googleMap ->
                mMap = googleMap
            }
        }
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "Please grant permission", Toast.LENGTH_SHORT).show()
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), Constants.LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }else {
            fusedLocationClient?.lastLocation?.
                addOnSuccessListener { location : Location? ->
                    location?.let {
                        latLang = LatLng(location.latitude, location.longitude)
                        mMap?.addMarker(
                            MarkerOptions().position(latLang!!).title("Current location")
                        )
                        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLang!!, 15f))
                    }
                }
        }

    }

    private fun displayNearestMetroStation() {

    }
}
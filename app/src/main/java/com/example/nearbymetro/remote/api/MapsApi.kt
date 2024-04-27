package com.example.nearbymetro.remote.api

import com.example.nearbymetro.data.MapsLocation
import retrofit2.Call
import retrofit2.http.GET

interface MapsApi {

    @GET
    fun nearestMetroLocation(): Call<MapsLocation>

}
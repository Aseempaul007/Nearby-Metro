package com.example.nearbymetro.di

import com.example.nearbymetro.remote.api.MapsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun getRetrofitInstance(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun getMapsApi(retrofit: Retrofit): MapsApi {
        return retrofit.create(MapsApi::class.java)
    }

}
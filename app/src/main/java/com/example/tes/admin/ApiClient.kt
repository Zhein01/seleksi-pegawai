package com.example.tes.admin

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object ApiClient {
    const val BASE_URL = "http://192.168.0.102:8000/"

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }
}
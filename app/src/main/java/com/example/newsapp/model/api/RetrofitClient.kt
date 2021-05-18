package com.example.newsapp.model.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    const val BASE_URL = "https://newsapi.org/v2/"
    const val API_KEY = "c447104dd4014982b75313365151999f"
    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val requestApi = retrofitBuilder.create(RequestApi::class.java)

}
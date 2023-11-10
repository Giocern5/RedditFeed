package com.example.tinderonsight.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val base_url = "https://www.reddit.com/"
object RetrofitInstance {
    val instance = Retrofit
        .Builder()
        .baseUrl(base_url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(HotPostAPI::class.java)
}
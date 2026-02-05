package com.example.ass8mysql

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object EmployeeClient {
    private const val BASE_URL = "http://127.0.0.1:3000/"

    val EmployeeAPI: EmployeeAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(com.example.ass8mysql.EmployeeAPI::class.java)
    }
}
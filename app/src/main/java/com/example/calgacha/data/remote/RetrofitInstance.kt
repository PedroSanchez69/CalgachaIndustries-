package com.example.calgacha.data.remote


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.getValue
import kotlin.jvm.java


object RetrofitInstance {

    private const val BASE_URL = "https://calgachaindustries-backend.onrender.com" // url base de la api

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val api: ChickenApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()) // usa la libreria gson para convertir json a objetos
            .build()
            .create(ChickenApiService::class.java) // esto para implementar la interfaz de la api y los endpoints (get, patch, etc.)
    }
}
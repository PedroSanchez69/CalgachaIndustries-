package com.example.calgacha.data.remote

import com.example.calgacha.data.remote.model.Chicken
import retrofit2.Response
import retrofit2.http.*

interface ChickenApiService {

    @GET("chickens")
    suspend fun getChickens(): Response<List<Chicken>>

    @POST("chickens")
    suspend fun createChicken(@Body chicken: Chicken): Response<Chicken>

    @GET("chickens/{id}")
    suspend fun getChicken(@Path("id") id: String): Response<Chicken>

    @PUT("chickens/{id}")
    suspend fun updateChicken(
        @Path("id") id: String,
        @Body chicken: Chicken
    ): Response<Chicken>

    @DELETE("chickens/{id}")
    suspend fun deleteChicken(@Path("id") id: Int): Response<Unit>
}

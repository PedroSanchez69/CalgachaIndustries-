package com.example.calgacha.data.repository

import com.example.calgacha.data.remote.ChickenApiService
import com.example.calgacha.data.remote.dao.ChickenDao
import com.example.calgacha.data.remote.model.Chicken
import kotlinx.coroutines.flow.Flow

class chickenRepository(private val chickenDao: ChickenDao, private val apiService: ChickenApiService) {

    fun getChickens(): Flow<List<Chicken>> = chickenDao.getAllChickens()

    suspend fun refreshChickens() {
        try {
            val response = apiService.getChickens()
            if (response.isSuccessful) {
                response.body()?.let {
                    chickenDao.insertAll(it)
                }
            }
        } catch (e: Exception) {
            // Handle network errors
        }
    }

    suspend fun getChickenById(id: Int) = chickenDao.getChickenById(id)

    suspend fun insertChicken(chicken: Chicken) {
        try {
            val response = apiService.createChicken(chicken)
            if (response.isSuccessful) {
                response.body()?.let {
                    // Insert the returned chicken (with the server-generated ID) into the local DB
                    chickenDao.insertChicken(it)
                }
            }
        } catch (e: Exception) {
            // Handle network errors
        }
    }

    suspend fun deleteChicken(chicken: Chicken) {
        // Only delete if the chicken has a valid, non-null ID
        chicken.id?.let {
            try {
                val response = apiService.deleteChicken(it)
                if (response.isSuccessful) {
                    // If API deletion is successful, delete from local DB
                    chickenDao.deleteChicken(chicken)
                }
            } catch (e: Exception) {
                // Handle network errors
            }
        }
    }
}
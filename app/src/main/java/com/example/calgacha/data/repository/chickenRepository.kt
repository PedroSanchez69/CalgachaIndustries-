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
            // Maneja errores de conexión
        }
    }

    suspend fun getChickenById(id: Int) = chickenDao.getChickenById(id)

    suspend fun insertChicken(chicken: Chicken) {
        try {
            val response = apiService.createChicken(chicken)
            if (response.isSuccessful) {
                response.body()?.let {
                    // Inserta las galllinas en la base de datos con el id generado automaticamente
                    chickenDao.insertChicken(it)
                }
            }
        } catch (e: Exception) {
            // Maneja errores de conexión
        }
    }

    suspend fun deleteChicken(chicken: Chicken) {
        // Solo permite la eliminación si el id de la gallina no es null
        chicken.id?.let {
            try {
                val response = apiService.deleteChicken(it)
                if (response.isSuccessful) {
                    // Si la eliminación es exitosa, se actualiza la base de datos
                    chickenDao.deleteChicken(chicken)
                }
            } catch (e: Exception) {
                // Maneja errores de conexión
            }
        }
    }
}
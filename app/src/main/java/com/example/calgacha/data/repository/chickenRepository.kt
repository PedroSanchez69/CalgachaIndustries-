package com.example.calgacha.data.repository

import com.example.calgacha.data.remote.dao.ChickenDao
import com.example.calgacha.data.remote.model.Chicken
import kotlinx.coroutines.flow.Flow

class chickenRepository(private val chickenDao: ChickenDao) {
    fun getChickens(): Flow<List<Chicken>> = chickenDao.getAllChickens()
    suspend fun getChickenById(id: Int) = chickenDao.getChickenById(id)
    suspend fun getAllChickens(chicken: Chicken) = chickenDao.getAllChickens()
    suspend fun insertChicken(chicken: Chicken) = chickenDao.insertChicken(chicken)
    suspend fun deleteChicken(chicken: Chicken) = chickenDao.deleteChicken(chicken)
}
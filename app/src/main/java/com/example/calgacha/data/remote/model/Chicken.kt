package com.example.calgacha.data.remote.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Gallinas")
data class Chicken(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val nombre: String,
    val edad: Int,
    val raza: String,
    val descripcion: String,
)
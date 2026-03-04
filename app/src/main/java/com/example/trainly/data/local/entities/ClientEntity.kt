package com.example.trainly.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "clients")
data class ClientEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val phone: String,
    val photoUri: String? = null,
    val totalWorkoutsPaid: Int,
    val workoutsUsed: Int,
    val isActive: Boolean = true
)
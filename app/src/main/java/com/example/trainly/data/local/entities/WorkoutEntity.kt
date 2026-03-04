package com.example.trainly.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "workouts",
    foreignKeys = [
        ForeignKey(
            entity = ClientEntity::class,
            parentColumns = ["id"],
            childColumns = ["clientId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WorkoutEntity(
    @PrimaryKey val workoutId: String = UUID.randomUUID().toString(),
    val clientId: String, // Внешний ключ (Foreign Key)
    val date: Long,
    val note: String?
)

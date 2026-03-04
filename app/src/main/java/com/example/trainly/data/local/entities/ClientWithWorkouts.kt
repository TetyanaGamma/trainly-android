package com.example.trainly.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ClientWithWorkouts(
    @Embedded val client: ClientEntity, // "One"
    @Relation(
        parentColumn = "id",   // ID from ClientEntity
        entityColumn = "clientId" // from WorkoutEntity
    )
    val workouts: List<WorkoutEntity> // "Many"
)

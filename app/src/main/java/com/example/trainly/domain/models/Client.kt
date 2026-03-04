package com.example.trainly.domain.models

import java.util.UUID

// Client
data class Client(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val phone: String,
    val photoUri: String?,
    val totalWorkoutsPaid: Int,
    val workoutsUsed: Int,
    val isActive: Boolean = true
) {
    val workoutsLeft: Int get() = totalWorkoutsPaid - workoutsUsed
    val needsPayment: Boolean get() = workoutsLeft <= 1
}

// exercise in routine
data class Exercise(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val sets: List<ExerciseSet>
)

// Set
data class ExerciseSet(
    val weight: Double,
    val reps: Int,
    val isCompleted: Boolean = false
)

// Workout
data class Workout(
    val id: String = UUID.randomUUID().toString(),
    val clientId: String,
    val date: Long, // Timestamp
    val exercises: List<Exercise>,
    val note: String? = null
)

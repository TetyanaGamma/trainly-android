package com.example.trainly.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "client_anamnesis",
    foreignKeys = [
        ForeignKey(
            entity = ClientEntity::class,
            parentColumns = ["id"],
            childColumns = ["clientId"],
            onDelete = ForeignKey.CASCADE // If client is deleted, form is deleted too
        )
    ]
)
data class AnamnesisEntity(
    @PrimaryKey val clientId: String, // One-to-one relationship
    val dateOfBirth: Long?,
    val height: Float?,
    val currentWeight: Float?,
    val targetWeight: Float?,

    // Medical (Storing as Strings to allow detailed notes directly or JSON)
    val cardiovascularNotes: String?,
    val metabolicNotes: String?,
    val musculoskeletalNotes: String?,
    val surgeryNotes: String?,
    val medicalContraindications: String?,

    // Goals & Experience
    val goal: String?,
    val fitnessExperience: String?,
    val previousInjuries: String?,

    // Lifestyle
    val occupation: String?,
    val sleepHours: Int?,
    val nutritionNotes: String?,
    val badHabits: String?,
    val stressLevel: String?
)

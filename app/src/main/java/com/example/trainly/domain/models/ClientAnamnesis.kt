package com.example.trainly.domain.models

data class ClientAnamnesis(
    val clientId: String,
    val dateOfBirth: Long? = null,
    val height: Float? = null,
    val currentWeight: Float? = null,
    val targetWeight: Float? = null,

    // Medical
    val cardiovascularNotes: String = "",
    val metabolicNotes: String = "",
    val musculoskeletalNotes: String = "",
    val surgeryNotes: String = "",
    val medicalContraindications: String = "",

    // Goals
    val goal: String = "",
    val fitnessExperience: String = "",
    val previousInjuries: String = "",

    // Lifestyle
    val occupation: String = "",
    val sleepHours: Int? = null,
    val nutritionNotes: String = "",
    val badHabits: String = "",
    val stressLevel: String = ""
)

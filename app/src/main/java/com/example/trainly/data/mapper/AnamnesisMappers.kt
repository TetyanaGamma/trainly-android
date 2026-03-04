package com.example.trainly.data.mapper

import com.example.trainly.data.local.entities.AnamnesisEntity
import com.example.trainly.domain.models.ClientAnamnesis

fun AnamnesisEntity.toDomain(): ClientAnamnesis {
    return ClientAnamnesis(
        clientId = clientId,
        dateOfBirth = dateOfBirth,
        height = height,
        currentWeight = currentWeight,
        targetWeight = targetWeight,
        cardiovascularNotes = cardiovascularNotes ?: "",
        metabolicNotes = metabolicNotes ?: "",
        musculoskeletalNotes = musculoskeletalNotes ?: "",
        surgeryNotes = surgeryNotes ?: "",
        medicalContraindications = medicalContraindications ?: "",
        goal = goal ?: "",
        fitnessExperience = fitnessExperience ?: "",
        previousInjuries = previousInjuries ?: "",
        occupation = occupation ?: "",
        sleepHours = sleepHours,
        nutritionNotes = nutritionNotes ?: "",
        badHabits = badHabits ?: "",
        stressLevel = stressLevel ?: ""
    )
}

fun ClientAnamnesis.toEntity(): AnamnesisEntity {
    return AnamnesisEntity(
        clientId = clientId,
        dateOfBirth = dateOfBirth,
        height = height,
        currentWeight = currentWeight,
        targetWeight = targetWeight,
        cardiovascularNotes = cardiovascularNotes,
        metabolicNotes = metabolicNotes,
        musculoskeletalNotes = musculoskeletalNotes,
        surgeryNotes = surgeryNotes,
        medicalContraindications = medicalContraindications,
        goal = goal,
        fitnessExperience = fitnessExperience,
        previousInjuries = previousInjuries,
        occupation = occupation,
        sleepHours = sleepHours,
        nutritionNotes = nutritionNotes,
        badHabits = badHabits,
        stressLevel = stressLevel
    )
}
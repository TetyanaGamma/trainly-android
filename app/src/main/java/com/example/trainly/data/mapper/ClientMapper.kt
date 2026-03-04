package com.example.trainly.data.mapper

import com.example.trainly.data.local.entities.ClientEntity
import com.example.trainly.domain.models.Client


    // Extension functions for easy conversion
    fun ClientEntity.toDomain(): Client {
        return Client(
            id = id,
            name = name,
            phone = phone,
            photoUri = photoUri,
            totalWorkoutsPaid = totalWorkoutsPaid,
            workoutsUsed = workoutsUsed,
            isActive = isActive
        )
    }

    fun Client.toEntity(): ClientEntity {
        return ClientEntity(
            id = id,
            name = name,
            phone = phone,
            photoUri = photoUri,
            totalWorkoutsPaid = totalWorkoutsPaid,
            workoutsUsed = workoutsUsed,
            isActive = isActive
        )
    }

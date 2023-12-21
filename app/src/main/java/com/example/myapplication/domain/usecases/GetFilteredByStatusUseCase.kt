package com.example.myapplication.domain.usecases

import com.example.myapplication.data.database.entity.AuthorizationDataEntity

interface GetFilteredByStatusUseCase {
    suspend fun execute(status: Boolean):List<AuthorizationDataEntity>
}
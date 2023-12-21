package com.example.myapplication.domain.usecases

import com.example.myapplication.data.database.entity.AuthorizationDataEntity

interface AuthorizationTransactionUseCase {
    suspend fun execute(authorizationDataEntity: AuthorizationDataEntity)
}
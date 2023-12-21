package com.example.myapplication.domain.usecases

import com.example.myapplication.data.database.entity.AuthorizationDataEntity

interface GetTransactionListUseCase {
    suspend fun execute(): List<AuthorizationDataEntity>
}
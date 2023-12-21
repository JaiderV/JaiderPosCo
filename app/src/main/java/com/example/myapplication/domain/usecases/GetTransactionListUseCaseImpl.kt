package com.example.myapplication.domain.usecases

import com.example.myapplication.data.database.entity.AuthorizationDataEntity
import com.example.myapplication.domain.repository.AuthorizationRepository
import javax.inject.Inject

class GetTransactionListUseCaseImpl @Inject constructor(
    private val authorizationRepository: AuthorizationRepository
) : GetTransactionListUseCase {
    override suspend fun execute(): List<AuthorizationDataEntity> {
        return authorizationRepository.getTransactionList()
    }
}

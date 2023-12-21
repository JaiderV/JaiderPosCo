package com.example.myapplication.domain.usecases

import com.example.myapplication.data.database.entity.AuthorizationDataEntity
import com.example.myapplication.domain.repository.AuthorizationRepository
import javax.inject.Inject

class AuthorizationTransactionUseCaseImpl @Inject constructor(
    private val authorizationRepository: AuthorizationRepository,
) : AuthorizationTransactionUseCase {

    override suspend fun execute(authorizationDataEntity: AuthorizationDataEntity) {
        authorizationRepository.authorizeTransaction(authorizationDataEntity)
    }

}

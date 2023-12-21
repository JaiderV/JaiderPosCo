package com.example.myapplication.domain.usecases

import com.example.myapplication.domain.repository.AuthorizationRepository
import javax.inject.Inject

class CancelTransactionUseCaseImpl @Inject constructor(
    private val authorizationRepository: AuthorizationRepository
) : CancelTransactionUseCase {
    override suspend fun execute(transactionId: String, newStatus: Boolean) {
        authorizationRepository.cancelTransaction(transactionId, newStatus)
    }
}

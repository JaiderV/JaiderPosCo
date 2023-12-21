package com.example.myapplication.domain.usecases

interface CancelTransactionUseCase {
    suspend fun execute (transactionId: String, newStatus: Boolean)
}
package com.example.myapplication.domain.repository

import com.example.myapplication.data.database.entity.AuthorizationDataEntity
import com.example.myapplication.data.utilApiResponse.RequestAnnulment
import com.example.myapplication.data.utilApiResponse.RequestAuthorization
import com.example.myapplication.data.utilApiResponse.ResponseAnnulment
import com.example.myapplication.data.utilApiResponse.ResponseAuthorization

interface AuthorizationRepository {
    suspend fun authorizeTransaction(authorizationDataEntity: AuthorizationDataEntity)
    suspend fun cancelTransaction(transactionId: String, newStatus: Boolean)
    suspend fun getTransactionList(): List<AuthorizationDataEntity>
    suspend fun getFilterByStatus(status: Boolean): List<AuthorizationDataEntity>
    suspend fun postAuthorization(authorizationKey: String, authorizationRequest: RequestAuthorization): ResponseAuthorization
    suspend fun postAnnulment (annulmentKey: String, annulmentRequest: RequestAnnulment): ResponseAnnulment
}
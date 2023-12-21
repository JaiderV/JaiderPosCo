package com.example.myapplication.data.repositoryImplement

import com.example.myapplication.data.MyApiService
import com.example.myapplication.data.database.entity.AuthorizationDataEntity
import com.example.myapplication.data.database.dao.AuthorizationDataDao
import com.example.myapplication.data.utilApiResponse.RequestAnnulment
import com.example.myapplication.data.utilApiResponse.RequestAuthorization
import com.example.myapplication.data.utilApiResponse.ResponseAnnulment
import com.example.myapplication.data.utilApiResponse.ResponseAuthorization
import com.example.myapplication.domain.repository.AuthorizationRepository
import javax.inject.Inject

class AuthorizationRepositoryImpl @Inject constructor (
    private val authorizationDataDao: AuthorizationDataDao,
    private val authorizationApi: MyApiService
) : AuthorizationRepository {

    override suspend fun authorizeTransaction(authorizationDataEntity: AuthorizationDataEntity) {
        // Lógica específica para autorizar la transacción
        authorizationDataDao.insertAuthorizationData(authorizationDataEntity)
    }

    override suspend fun cancelTransaction(transactionId: String, newStatus: Boolean) {
        authorizationDataDao.updateStatus(transactionId, newStatus)
    }

    override suspend fun getTransactionList(): List<AuthorizationDataEntity> {
        //Logica para consultar listado de transacciones
        return authorizationDataDao.getAllAuthorizationData()
    }

    override suspend fun getFilterByStatus (status: Boolean): List<AuthorizationDataEntity>{
         return authorizationDataDao.getAuthorizationDataByStatus(status)
    }

    override suspend fun postAuthorization (
        authorizationKey: String,
        authotizationBody: RequestAuthorization
    ): ResponseAuthorization{
        return authorizationApi.authorization(authorizationKey = authorizationKey, requestAuthorization = authotizationBody)
    }

    override suspend fun postAnnulment (
        authorizationKey: String,
        annulmentBody: RequestAnnulment
    ): ResponseAnnulment{
        return authorizationApi.annulment(annulmentKey = authorizationKey, requestAnnulment = annulmentBody)
    }
}
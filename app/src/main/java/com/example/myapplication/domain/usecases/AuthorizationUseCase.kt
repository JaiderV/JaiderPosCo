package com.example.myapplication.domain.usecases

import com.example.myapplication.data.utilApiResponse.RequestAuthorization
import com.example.myapplication.data.utilApiResponse.ResponseAuthorization
import com.example.myapplication.domain.repository.AuthorizationRepository
import com.example.myapplication.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class AuthorizationUseCase @Inject constructor(
    private val repository: AuthorizationRepository
) {
    operator fun invoke(
        authorizationKey: String,
        authorizationRequest: RequestAuthorization
    ): Flow<Resource<Any>> = flow {
        try {
            emit(Resource.Loading(null))
            val authorizationJson =
                repository.postAuthorization(authorizationKey, authorizationRequest)
            val authorizationResponse = ResponseAuthorization(
                authorizationJson.receiptId,
                authorizationJson.rrn,
                authorizationJson.statusCode,
                authorizationJson.statusDescription
            )
            if (authorizationResponse.statusCode == "00") {
                emit(Resource.Success(authorizationResponse))
            } else {
                emit(Resource.Error(authorizationResponse?.statusDescription.toString()))
            }
        } catch (e: Exception) {
            emit(Resource.Error("400"))
        }
    }
}
package com.example.myapplication.domain.usecases

import com.example.myapplication.data.utilApiResponse.RequestAnnulment
import com.example.myapplication.data.utilApiResponse.ResponseAnnulment
import com.example.myapplication.domain.repository.AuthorizationRepository
import com.example.myapplication.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AnnulmentUseCase @Inject constructor(
    private val repository: AuthorizationRepository
) {
    operator fun invoke(
        annulmentKey: String,
        annulmentRequest: RequestAnnulment
    ): Flow<Resource<Any>> = flow {
        try {
            emit(Resource.Loading(null))
            val annulmentJson = repository.postAnnulment(annulmentKey, annulmentRequest)
            val annulmentResponse =
                ResponseAnnulment(annulmentJson.statusCode, annulmentJson.statusDescription)
            if (annulmentResponse.statusCode == "00") {
                emit(Resource.Success(annulmentResponse))
            } else {
                emit(Resource.Error(annulmentResponse?.statusDescription.toString()))
            }
        } catch (e: Exception) {
            emit(Resource.Error("400"))
        }
    }

}

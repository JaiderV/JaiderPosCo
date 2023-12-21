package com.example.myapplication.domain.usecases

import com.example.myapplication.data.database.entity.AuthorizationDataEntity
import com.example.myapplication.domain.repository.AuthorizationRepository
import javax.inject.Inject

class GetFilteredByStatusUseCaseImpl @Inject constructor(
    private val authorizationRepository: AuthorizationRepository
) : GetFilteredByStatusUseCase {
    override suspend fun execute(status: Boolean): List<AuthorizationDataEntity> {
        return authorizationRepository.getFilterByStatus(status)
    }
}
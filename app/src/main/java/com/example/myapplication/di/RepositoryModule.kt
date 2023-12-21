package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.data.MyApiService
import com.example.myapplication.presentation.viewModel.SharedViewModel
import com.example.myapplication.data.database.AppDatabase
import com.example.myapplication.data.database.dao.AuthorizationDataDao
import com.example.myapplication.domain.repository.AuthorizationRepository
import com.example.myapplication.data.repositoryImplement.AuthorizationRepositoryImpl
import com.example.myapplication.domain.usecases.AnnulmentUseCase
import com.example.myapplication.domain.usecases.AuthorizationTransactionUseCase
import com.example.myapplication.domain.usecases.AuthorizationTransactionUseCaseImpl
import com.example.myapplication.domain.usecases.AuthorizationUseCase
import com.example.myapplication.domain.usecases.CancelTransactionUseCase
import com.example.myapplication.domain.usecases.CancelTransactionUseCaseImpl
import com.example.myapplication.domain.usecases.GetFilteredByStatusUseCase
import com.example.myapplication.domain.usecases.GetFilteredByStatusUseCaseImpl
import com.example.myapplication.domain.usecases.GetTransactionListUseCase
import com.example.myapplication.domain.usecases.GetTransactionListUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "App-database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideAuthorizationDataDao(appDatabase: AppDatabase): AuthorizationDataDao {
        return appDatabase.authorizationDataDao()
    }

    @Provides
    @Singleton
    fun provideAuthorizationRepository(authorizationDataDao: AuthorizationDataDao, repositoryApi: MyApiService): AuthorizationRepository {
        return AuthorizationRepositoryImpl(authorizationDataDao, repositoryApi)
    }

    @Provides
    @Singleton
    fun provideAuthorizationTransactionUseCase(
        authorizationRepository: AuthorizationRepository
    ): AuthorizationTransactionUseCase {
        return AuthorizationTransactionUseCaseImpl(authorizationRepository)
    }

    @Provides
    @Singleton
    fun provideCancelTransactionUseCase(
        authorizationRepository: AuthorizationRepository
    ): CancelTransactionUseCase {
        return CancelTransactionUseCaseImpl(authorizationRepository)
    }

    @Provides
    @Singleton
    fun provideGetFilteredByStatusUseCase(
        authorizationRepository: AuthorizationRepository
    ): GetFilteredByStatusUseCase {
        return GetFilteredByStatusUseCaseImpl(authorizationRepository)
    }

    @Provides
    @Singleton
    fun provideGetTransactionListUseCase(
        authorizationRepository: AuthorizationRepository
    ): GetTransactionListUseCase {
        return GetTransactionListUseCaseImpl(authorizationRepository)
    }

    @Provides
    fun provideSharedViewModel(
        authorizationTransactionUseCase: AuthorizationTransactionUseCase,
        cancelTransactionUseCase: CancelTransactionUseCase,
        getFilteredByStatusUseCase: GetFilteredByStatusUseCase,
        getTransactionListUseCase: GetTransactionListUseCase,
        authorizationUseCase: AuthorizationUseCase,
        annulmentUseCase: AnnulmentUseCase
    ): SharedViewModel {
        return SharedViewModel(
            authorizationUseCase,
            annulmentUseCase,
            authorizationTransactionUseCase,
            cancelTransactionUseCase,
            getTransactionListUseCase,
            getFilteredByStatusUseCase
        )
    }

    @Provides
    @Singleton
    fun providerApi (): MyApiService {
        return Retrofit.Builder()
            .baseUrl("http://172.20.10.7:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApiService::class.java)
    }

}
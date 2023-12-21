package com.example.myapplication.presentation.viewModel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.database.entity.AuthorizationDataEntity
import com.example.myapplication.data.utilApiResponse.RequestAnnulment
import com.example.myapplication.data.utilApiResponse.RequestAuthorization
import com.example.myapplication.data.utilApiResponse.ResponseAnnulment
import com.example.myapplication.data.utilApiResponse.ResponseAuthorization
import com.example.myapplication.domain.usecases.AnnulmentUseCase
import com.example.myapplication.domain.usecases.AuthorizationTransactionUseCase
import com.example.myapplication.domain.usecases.AuthorizationUseCase
import com.example.myapplication.domain.usecases.CancelTransactionUseCase
import com.example.myapplication.domain.usecases.GetFilteredByStatusUseCase
import com.example.myapplication.domain.usecases.GetTransactionListUseCase
import com.example.myapplication.utils.DataState
import com.example.myapplication.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val authorizationUseCase: AuthorizationUseCase,
    private val annulmentUseCase: AnnulmentUseCase,
    private val authorizationTransactionUseCase: AuthorizationTransactionUseCase,
    private val cancelTransactionUseCase: CancelTransactionUseCase,
    private val getTransactionListUseCase: GetTransactionListUseCase,
    private val getFilteredByStatusUseCase: GetFilteredByStatusUseCase
) : ViewModel() {

    //Manejan listas de AuthorizationData en diferentes contextos
    val authorizationDataEntityList = MutableLiveData<List<AuthorizationDataEntity>>()
    val authorizationDataEntityQuery = MutableLiveData<List<AuthorizationDataEntity>>()
    val filteredAuthorizationDataEntity = MutableLiveData<List<AuthorizationDataEntity>>()
    private val _authorization = MutableLiveData<DataState<ResponseAuthorization>>(DataState())
    var authorization = _authorization


    private val _annulment = MutableLiveData<DataState<ResponseAnnulment>>(DataState())
    var annulment = _annulment

    //Se utiliza para almacenar una transaccion seleccionada
    lateinit var transactionSelect: AuthorizationDataEntity
    lateinit var  annulmentResponseData: ResponseAnnulment

    fun authorizeTransaction(request: AuthorizationDataEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                authorizationTransactionUseCase.execute(request)
                val trxList = getTransactionListUseCase.execute()
                authorizationDataEntityList.postValue(trxList)
            } catch (e: Exception) {
                Log.e("SharedViewModel", "Error authorizing transaction", e)
            }
        }
    }

    fun cancelTransaction(transactionId: String, newStatus: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                cancelTransactionUseCase.execute(transactionId, newStatus)
                val trxList = getTransactionListUseCase.execute()
                authorizationDataEntityList.postValue(trxList)
            } catch (e: Exception) {
                Log.e("SharedViewModel", "Error canceling transaction", e)
            }
        }
    }

    fun getTransactionList() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val trxList = getTransactionListUseCase.execute()
                authorizationDataEntityList.postValue(trxList)
            } catch (e: Exception) {
                Log.e("SharedViewModel", "Error getting transaction list", e)
            }
        }
    }

    fun getFilteredTrxByStatus(status: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val filterTrxList = getFilteredByStatusUseCase.execute(status)
                filteredAuthorizationDataEntity.postValue(filterTrxList)
            } catch (e: Exception) {
                Log.e("SharedViewModel", "Error getting filtered transactions", e)
            }
        }
    }

    fun postAuthorization(authorizationKey: String, authorizationRequest: RequestAuthorization) {
        authorizationUseCase(authorizationKey, authorizationRequest).onEach { resource ->
            when (resource) {
                is Resource.Success -> _authorization.value =
                    DataState(data = resource.data as ResponseAuthorization)

                is Resource.Loading -> _authorization.value = DataState(isLoading = true)
                is Resource.Error -> _authorization.value = DataState(error = resource.errorCode)
            }
        }.launchIn(viewModelScope)
    }

    fun postAnnulment(annulmentKey: String, annulmentRequest: RequestAnnulment) {
        annulmentUseCase(annulmentKey = annulmentKey, annulmentRequest).onEach { resource ->

            when (resource) {
                is Resource.Success -> _annulment.value =
                    DataState(data = resource.data as ResponseAnnulment)

                is Resource.Loading -> _annulment.value = DataState(isLoading = true)
                is Resource.Error -> _annulment.value = DataState(error = resource.errorCode)
            }
        }.launchIn(viewModelScope)
    }

    fun setTransactionRoom(transactionSelect: AuthorizationDataEntity) {
        this.transactionSelect = transactionSelect
    }
}
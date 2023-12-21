package com.example.myapplication.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.data.database.entity.AuthorizationDataEntity
import com.example.myapplication.databinding.FragmentAuthorizationFormBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.MyApiService
import com.example.myapplication.presentation.viewModel.SharedViewModel
import com.example.myapplication.data.utilApiResponse.RequestAuthorization
import com.example.myapplication.data.utilApiResponse.ResponseAuthorization
import com.example.myapplication.utils.DecimalFormatterTextWatcher
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.migration.OptionalInject

@OptionalInject
@AndroidEntryPoint
class FragmentAuthorizationForm : Fragment() {
    private var _binding: FragmentAuthorizationFormBinding? = null
    private val binding get() = _binding!!

    // Inicializa la ViewModel compartida
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthorizationFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtonListener()
        initObserver()

        val editTextAmount = binding.editTextAmount
        editTextAmount.addTextChangedListener(DecimalFormatterTextWatcher(editTextAmount))
    }

    private fun initButtonListener() {
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        with(binding) {
            authorizationFormButton.setOnClickListener {
                val dataForms = getDataForm()
                if (dataForms != null) {
                    sendForm(dataForms)
                } else {
                    showToast("Por favor, completa todos los campos.")
                }
            }
        }
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun getDataForm(): RequestAuthorization? {
        var id = binding.editTextId.text.toString()
        var commerceCode = binding.editTextCommerceCode.text.toString()
        var terminalCode = binding.editTextTerminalCode.text.toString()
        var amount = binding.editTextAmount.text.toString()
        var card = binding.editTextCardNumber.text.toString()

        if (id.isEmpty() || commerceCode.isEmpty() || terminalCode.isEmpty()
            || amount.isEmpty() || card.isEmpty()
        ) {
            showToast("Por favor, completa todos los campos.")
            return null
        } else {
            return RequestAuthorization(
                id = id,
                commerceCode = commerceCode,
                terminalCode = terminalCode,
                amount = amount,
                card = card
            )

        }
    }

    fun sendForm(dataForms: RequestAuthorization) {
        // Elimina los puntos decimales del monto antes de guardarlo en la base de datos
        val formattedAmount = binding.editTextAmount.text.toString()
        val amountWithoutDecimal = formattedAmount.replace("[.,]".toRegex(), "")

        sharedViewModel.postAuthorization("Basic MDAwMTIzMDAwQUJD", dataForms)

        val authorizationDataEntity = AuthorizationDataEntity(
            id = dataForms?.id.toString(),
            commerceCode = dataForms?.commerceCode.toString(),
            terminalCode = dataForms?.terminalCode.toString(),
            amount = amountWithoutDecimal,
            card = dataForms?.card.toString(),
            receiptId = "",
            rrn = "",
            status = true,
            statusCode = "",
            statusDescription = ""
        )
        sharedViewModel.setTransactionRoom(authorizationDataEntity)
    }

    fun initObserver() {
        sharedViewModel.authorization.observe(viewLifecycleOwner) { data ->
            data?.run {
                this.data?.let {
                    sharedViewModel.transactionSelect.receiptId = it?.receiptId.toString()
                    sharedViewModel.transactionSelect.rrn = it?.rrn.toString()
                    sharedViewModel.transactionSelect.statusCode = it?.statusCode.toString()
                    sharedViewModel.transactionSelect.statusDescription = it?.statusDescription.toString()
                    // Insertar en la base de datos a través del SharedViewModel
                    sharedViewModel.authorizeTransaction(sharedViewModel.transactionSelect)
                    showToast("Transacción exitosa")
                }
                this.error?.let {
                    showToast("Transaccón no autorizada, verifique los datos")
                }
            }
        }
    }
}



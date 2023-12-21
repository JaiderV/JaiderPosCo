package com.example.myapplication.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.data.database.entity.AuthorizationDataEntity
import com.example.myapplication.data.utilApiResponse.RequestAnnulment
import com.example.myapplication.presentation.viewModel.SharedViewModel
import com.example.myapplication.databinding.FragmentQueryBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.migration.OptionalInject


@OptionalInject
@AndroidEntryPoint
class FragmentQuery : Fragment() {
    private var _binding: FragmentQueryBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQueryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        initButtonListener()
        initLinearLayout()
        observeData()
    }

    private fun initButtonListener() {
        with(binding) {
            cancelQuery.setOnClickListener {
                findNavController().navigate(R.id.action_fragmentQuery_to_fragmentHome)
            }
            AnullmentTransaction.setOnClickListener {

                val receiptId = sharedViewModel.transactionSelect.receiptId
                val rrn = sharedViewModel.transactionSelect.rrn
                val annulmentRequest = RequestAnnulment(receiptId, rrn)
                sharedViewModel.postAnnulment("Basic MDAwMTIzMDAwQUJD", annulmentRequest = annulmentRequest)
                    sharedViewModel.annulment.observe(viewLifecycleOwner) { data ->
                        data?.run {
                            this.data?.let {
                                sharedViewModel.transactionSelect.statusCode = it?.statusCode.toString()
                                sharedViewModel.transactionSelect.statusDescription =
                                    it?.statusDescription.toString()
                                sharedViewModel.cancelTransaction(
                                    sharedViewModel.transactionSelect.id,
                                    false
                                )
                                findNavController().navigate(R.id.action_fragmentQuery_to_fragmentHome)
                                showToast("Transacción anulada exitosamente")
                            }
                            this.error?.let {
                                showToast("Transacción no anulada, por favor verifique")
                            }
                        }
                    }
            }
        }
    }

        private fun initLinearLayout() {
            //Inicializa los elementos de la interfaz de usuario
            val transactionSelect = sharedViewModel.transactionSelect
            with(binding) {
                textViewId.text = "ID: ${transactionSelect.id}"
                textViewCommerceCode.text = "Código de Comercio: ${transactionSelect.commerceCode}"
                textViewTerminalCode.text = "Terminal: ${transactionSelect.terminalCode}"
                textViewAmount.text = "Monto: ${transactionSelect.amount}"
                textViewCardNumber.text = "Número de Tarjeta: ${transactionSelect.card}"
                textViewReceiptId.text = "Número de Recibo: ${transactionSelect.receiptId}"
                textViewRrn.text = "RRN: ${transactionSelect.rrn}"
            }
        }

        private fun observeData() {
            sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
            //Observa la liveData authorizationDataQuery de la ViewModel
            sharedViewModel.authorizationDataEntityQuery.observe(viewLifecycleOwner) { trxList ->
            }
        }

        private fun showToast(message: String) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

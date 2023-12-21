package com.example.myapplication.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentAnullmentBinding
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.example.myapplication.R
import com.example.myapplication.presentation.viewModel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.migration.OptionalInject

@OptionalInject
@AndroidEntryPoint
class FragmentAnullment : Fragment() {
    private var _binding: FragmentAnullmentBinding? = null
    private val binding get() = _binding!!

    // Inicializa la ViewModel compartida
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var lifecycleOwner: LifecycleOwner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnullmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleOwner = viewLifecycleOwner
        initButtonListener()
    }

    private fun initButtonListener() {
        // Inicializa la ViewModel compartida
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        with(binding) {
            queryAnullment.setOnClickListener {

                val receiptNumber = editText.text.toString().trim()

                if (receiptNumber.isNotEmpty()) {
                    Log.d("FragmentAnullment", "Receipt number is not empty: $receiptNumber")
                    //Llama a la función getAllTrx de SharedViewModel con el nuevo argumento
                    sharedViewModel.getTransactionList()
                    sharedViewModel.authorizationDataEntityList.observe(viewLifecycleOwner) { trxList ->
                        if (trxList.isNotEmpty()) {
                            val transactionSelect = trxList.find {
                                it.receiptId == binding.editText.text.toString()
                            }
                            //Se utiliza el operador de llamada ? con la extension .let para el caso que transactionSelect no es nulo
                            transactionSelect?.let {
                                //Valida si el status de la trx es true
                                if(it.status){
                                    sharedViewModel.transactionSelect = it
                                    findNavController().navigate(R.id.action_fragmentAnullment_to_fragmentQuery)
                                    binding.editText.text = null
                                }else{
                                    showToast("Transacción ya anulada")
                                }
                            //Si la trx es false
                            }?: showToast("El número de recibo ingresado, no esta registrado")

                        } else {
                            showToast("No hay transacciones disponibles")
                        }

                    }
                } else {
                    showToast("Por favor, ingrese el número de recibo que desea consultar")
                    return@setOnClickListener
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}




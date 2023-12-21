package com.example.myapplication.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.presentation.viewModel.SharedViewModel
import com.example.myapplication.databinding.FragmentChooseTrxlistBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.migration.OptionalInject

@OptionalInject
@AndroidEntryPoint
class FragmentChooseTrxList : Fragment() {

    private var _binding: FragmentChooseTrxlistBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseTrxlistBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtonListener()
    }

    private fun initButtonListener(){
        // Inicializa la ViewModel compartida
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        with(binding){
            approvedTrxButton.setOnClickListener {
                // Filtra y muestra solo las transacciones aprobadas
                sharedViewModel.getFilteredTrxByStatus(true)
                findNavController().navigate(R.id.action_fragmentChooseTrxList_to_fragmentTransactionList)
            }
            anullmentTrxButton.setOnClickListener {
                // Filtra y muestra solo las transacciones anuladas
                sharedViewModel.getFilteredTrxByStatus(false)
                findNavController().navigate(R.id.action_fragmentChooseTrxList_to_fragmentTransactionList)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
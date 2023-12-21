package com.example.myapplication.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.migration.OptionalInject

@OptionalInject
@AndroidEntryPoint
class FragmentHome: Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtonListener()
    }

    private fun initButtonListener(){
        with(binding){
            authorizationButton.setOnClickListener {
                findNavController().navigate(R.id.action_fragmentHome_to_fragmentAuthorizationForm)
            }
            anullmentButton.setOnClickListener {
                findNavController().navigate(R.id.action_fragmentHome_to_fragmentAnullment)
            }
            transactionListButton.setOnClickListener {
                findNavController().navigate(R.id.action_fragmentHome_to_fragmentChooseTrxList)
            }
        }
    }
}
package com.example.myapplication.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.myapplication.databinding.FragmentTransactionListBinding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.utils.AuthorizationDataAdapter
import com.example.myapplication.presentation.viewModel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.migration.OptionalInject


@OptionalInject
@AndroidEntryPoint
class FragmentTransactionList : Fragment() {
    private var _binding: FragmentTransactionListBinding? = null
    private val binding get() = _binding!!

    // Inicializa la ViewModel compartida
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var adapter: AuthorizationDataAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionListBinding.inflate(inflater, container, false)

        // Inicializa la ViewModel compartida
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)


        // Inicializa el RecyclerView con un LinearLayoutManager
        binding.recyclerViewTransactionList.layoutManager = LinearLayoutManager(requireContext())

        //Se crea el adaptador vacio
        adapter = AuthorizationDataAdapter(emptyList())
        binding.recyclerViewTransactionList.adapter = adapter


        // Observa el livedata filteredAuthorizationData y actualiza la lista si llegase a cambiar
        sharedViewModel.filteredAuthorizationDataEntity.observe(viewLifecycleOwner, Observer { filteredAuthorizationData ->
            if (!filteredAuthorizationData.isNullOrEmpty()){
                    //Actualiza los datos dentro del adaptador pasando la nueva lista filtrada
                    adapter.updateData(filteredAuthorizationData)
            }else{
                showToast("La lista de transacciones se encuentra vacia.")
            }
        })
        return binding.root
    }
    private fun showToast(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    //Se llama cuando la lista del fragment es destruida y establece el "_binding = null" para posibles fugas de memoria
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
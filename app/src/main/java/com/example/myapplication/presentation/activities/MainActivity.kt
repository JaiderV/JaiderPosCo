package com.example.myapplication.presentation.activities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ContainerHomeBinding
import com.example.myapplication.presentation.viewModel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.migration.OptionalInject


@OptionalInject
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ContainerHomeBinding
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ContainerHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar la ViewModel compartida
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
    }
}


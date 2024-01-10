package com.example.myapplication.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.migration.OptionalInject

@OptionalInject
@AndroidEntryPoint
class FragmentHome : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var bubbleCardViewVar: CardView
    private lateinit var bubbleContentTextView: TextView
    private var currentIndex = 0


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

    private fun initButtonListener() {
        with(binding) {
            authorizationButton.setOnClickListener {
                findNavController().navigate(R.id.action_fragmentHome_to_fragmentAuthorizationForm)
            }
            anullmentButton.setOnClickListener {
                findNavController().navigate(R.id.action_fragmentHome_to_fragmentAnullment)
            }
            transactionListButton.setOnClickListener {
                findNavController().navigate(R.id.action_fragmentHome_to_fragmentChooseTrxList)
            }
            bubbleCardViewVar = root.findViewById(R.id.bubbleCardView)
            bubbleContentTextView = bubbleCardView.findViewById(R.id.bubbleContent)
            bubbleCardView.setOnClickListener {
                showNews()
            }
        }
    }

    private val informationNews = listOf<String>(
        "¿Sabías que puedes realizar transacciones por el monto que desees?",
        "¿Sabías que puedes anular tus transacciones solo con tu número de recibo?",
        "¿Sabías que puedes consultar tus transacciones aprobadas?",
        "¿Sabías que puedes consultar tus transacciones anuladas?"
    )

    private fun showNews() {
        if (currentIndex < informationNews.size) {
            val currentNews = informationNews[currentIndex]
            bubbleContentTextView.text = currentNews
            currentIndex++
            if (currentIndex == informationNews.size) {
                currentIndex = 0
            }
        }
    }
}

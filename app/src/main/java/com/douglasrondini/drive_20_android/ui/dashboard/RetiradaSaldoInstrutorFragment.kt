package com.douglasrondini.drive_20_android.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.douglasrondini.drive_20_android.databinding.FragmentRetiradaSaldoInstrutorBinding

class RetiradaSaldoInstrutorFragment : Fragment() {

    private var _binding: FragmentRetiradaSaldoInstrutorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRetiradaSaldoInstrutorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        // Chips de valores rápidos
        binding.chip100.setOnClickListener { binding.inputValor.setText("100,00") }
        binding.chip300.setOnClickListener { binding.inputValor.setText("300,00") }
        binding.chip500.setOnClickListener { binding.inputValor.setText("500,00") }
        binding.chipAll.setOnClickListener { binding.inputValor.setText(binding.txtSaldo.text) }

        // Alternância Pix/Conta (somente rótulos aqui; lógica real pode ser ligada depois)
        binding.btnPix.isEnabled = true
        binding.btnConta.isEnabled = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


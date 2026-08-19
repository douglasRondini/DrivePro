package com.douglasrondini.drive_20_android.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.douglasrondini.drive_20_android.R
import com.douglasrondini.drive_20_android.data.local.PreferenceManager
import com.douglasrondini.drive_20_android.databinding.FragmentPerfilInstrutorBinding
import com.douglasrondini.drive_20_android.ui.activities.AccountActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PerfilInstrutorFragment : Fragment() {

    private var _binding: FragmentPerfilInstrutorBinding? = null
    private val binding get() = _binding!!
    private val preferenceManager: PreferenceManager by inject()
    private val viewModel: PerfilInstrutorViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfilInstrutorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupStaticData()
        setupInitialValues()
        setupClicks()
        observeUiState()
    }

    private fun setupInitialValues() {
        val savedPrice = viewModel.getInitialPrice()
        if (savedPrice > 0) {
            binding.inputPrecoAula.setText("%.2f".format(savedPrice))
        }
    }

    private fun setupStaticData() {
        binding.txtNome.text = preferenceManager.getUserName() ?: "Instrutor"
        binding.txtSubtitulo.text = "Instrutor Credenciado • Categoria B"
        binding.txtAvaliacao.text = "4.9"
        binding.txtAulas.text = preferenceManager.getSavedCompletedCount().toString()
        binding.txtAnos.text = "3"
        binding.txtSaldoDisponivel.text = "Disponível: R$ %.2f".format(preferenceManager.getSavedTotalRevenue())
    }

    private fun setupClicks() {
        binding.cardRetirarSaldo.setOnClickListener {
            findNavController().navigate(R.id.action_perfilInstrutorFragment_to_retiradaSaldoInstrutorFragment)
        }

        binding.btnLogout.setOnClickListener {
            preferenceManager.clearData()
            val intent = Intent(requireContext(), AccountActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.btnSavePrice.setOnClickListener {
            val priceText = binding.inputPrecoAula.text.toString()
            if (priceText.isNotEmpty()) {
                val price = priceText.toDoubleOrNull()
                if (price != null) {
                    viewModel.updatePrice(price)
                }
            }
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.btnSavePrice.isEnabled = !state.isLoading
                    
                    if (state.isSuccess) {
                        Snackbar.make(binding.root, "Preço atualizado com sucesso!", Snackbar.LENGTH_SHORT).show()
                        viewModel.resetSuccessState()
                    }

                    state.errorMessage?.let { msg ->
                        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
                        viewModel.consumeErrorMessage()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

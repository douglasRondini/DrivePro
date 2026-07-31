package com.douglasrondini.drive_20_android.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.douglasrondini.drive_20_android.R
import com.douglasrondini.drive_20_android.databinding.FragmentSolicitacoesInstrutorBinding
import com.douglasrondini.drive_20_android.domain.model.Appointment
import com.douglasrondini.drive_20_android.ui.dashboard.adapter.AppointmentAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SolicitacoesInstrutorFragment : Fragment() {

    private var _binding: FragmentSolicitacoesInstrutorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SolicitacoesInstrutorViewModel by viewModel()

    private val adapter by lazy {
        AppointmentAdapter(emptyList()) { appointment ->
            navigateToDetail(appointment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSolicitacoesInstrutorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        setupFilters()
        observeUiState()
    }

    private fun setupRecycler() {
        binding.rvSolicitacoesInstrutor.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSolicitacoesInstrutor.adapter = adapter
    }

    private fun setupFilters() {
        binding.chipGroupFilters.setOnCheckedStateChangeListener { _, checkedIds ->
            val status = when (checkedIds.firstOrNull()) {
                binding.chipPending.id -> "PENDENTE"
                binding.chipAccepted.id -> "ACEITA"
                binding.chipCompleted.id -> "CONCLUIDA"
                else -> "TODAS"
            }
            viewModel.filterByStatus(status)
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    adapter.updateItems(state.appointments)
                    handlePlaceholder(state.appointments.isEmpty())

                    state.errorMessage?.let { msg ->
                        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun handlePlaceholder(isEmpty: Boolean) {
        binding.emptyPlaceholder.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.rvSolicitacoesInstrutor.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }

    private fun navigateToDetail(appointment: Appointment) {
        val args = Bundle().apply {
            putString("argNome", appointment.alunoNome)
            putString("argInfo", appointment.localOrigem)
            putString("argData", appointment.dataHora)
            putString("argHorario", "")
            putString("argContato", appointment.alunoTelefone)
            putString("argStatus", appointment.status)
            putString("argPreco", "R$ %.2f".format(appointment.preco))
            putInt("argAvatar", R.drawable.ic_launcher_foreground)
            
            val statusBg = if (appointment.status.uppercase() == "ACEITA") R.color.accent_green else R.color.primary
            val statusText = if (appointment.status.uppercase() == "ACEITA") android.R.color.white else android.R.color.black
            putInt("argStatusBg", statusBg)
            putInt("argStatusText", statusText)
        }
        findNavController().navigate(
            R.id.action_solicitacoesFragment_to_solicitacaoDetalheInstrutorFragment,
            args
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

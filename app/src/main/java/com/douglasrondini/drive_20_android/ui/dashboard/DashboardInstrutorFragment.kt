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
import com.douglasrondini.drive_20_android.data.local.PreferenceManager
import com.douglasrondini.drive_20_android.databinding.FragmentDashboardInstrutorBinding
import com.douglasrondini.drive_20_android.domain.model.Appointment
import com.douglasrondini.drive_20_android.ui.dashboard.adapter.AppointmentAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardInstrutorFragment : Fragment() {

    private var _binding: FragmentDashboardInstrutorBinding? = null
    private val binding get() = _binding!!
    private val preferenceManager: PreferenceManager by inject()
    private val viewModel: DashboardInstrutorViewModel by viewModel()
    
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
        _binding = FragmentDashboardInstrutorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupRecycler()
        setupFilters()
        observeUiState()
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

    private fun setupUI() {
        val userName = preferenceManager.getUserName() ?: "Instrutor"
        binding.txtGreeting.text = "Olá, $userName!"
    }

    private fun setupRecycler() {
        binding.rvDashboardRequests.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDashboardRequests.adapter = adapter
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    if (state.isLoading) {
                        // Opcional: mostrar progresso
                    }
                    
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
        binding.rvDashboardRequests.visibility = if (isEmpty) View.GONE else View.VISIBLE
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
            R.id.action_dashboardInstrutorFragment_to_solicitacaoDetalheInstrutorFragment,
            args
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

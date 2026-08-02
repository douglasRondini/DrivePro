package com.douglasrondini.drive_20_android.ui.solicitações

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
import com.douglasrondini.drive_20_android.databinding.FragmentSolicitacoesBinding
import com.douglasrondini.drive_20_android.domain.model.Appointment
import com.douglasrondini.drive_20_android.ui.dashboard.adapter.AppointmentAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SolicitacoesFragment : Fragment() {

    private var _binding: FragmentSolicitacoesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeAlunoSolicitacoesViewModel by viewModel()

    private val adapter by lazy {
        AppointmentAdapter(emptyList()) { appointment ->
            navigateToDetail(appointment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSolicitacoesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        setupTabs()
        observeUiState()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadAppointments()
    }

    private fun setupRecycler() {
        binding.recyclerSolicitacoes.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerSolicitacoes.adapter = adapter
    }

    private fun setupTabs() {
        binding.tabLayoutSolicitacoes.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val status = when (tab?.position) {
                    1 -> "PENDENTE"
                    2 -> "ACEITA"
                    3 -> "CONCLUIDA"
                    else -> "TODAS"
                }
                viewModel.filterByStatus(status)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
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
        binding.recyclerSolicitacoes.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }

    private fun navigateToDetail(appointment: Appointment) {
        val args = Bundle().apply {
            putString("argId", appointment.id)
            putString("argNome", appointment.partnerName)
            putString("argInfo", appointment.localOrigem)
            putString("argData", appointment.dataHora)
            putString("argHorario", "")
            putString("argContato", appointment.partnerPhone)
            putString("argStatus", appointment.status)
            putString("argPreco", "R$ %.2f".format(appointment.preco))
            putInt("argAvatar", R.drawable.ic_person)
            
            val statusBg = if (appointment.status.uppercase() == "ACEITA") R.color.accent_green else R.color.primary
            val statusText = if (appointment.status.uppercase() == "ACEITA") android.R.color.white else android.R.color.black
            putInt("argStatusBg", statusBg)
            putInt("argStatusText", statusText)
        }
        
        findNavController().navigate(R.id.action_solicitacoesFragment_to_solicitacaoDetalheInstrutorFragment, args)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

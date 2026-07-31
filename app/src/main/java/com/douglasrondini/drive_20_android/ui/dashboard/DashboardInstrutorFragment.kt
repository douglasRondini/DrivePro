package com.douglasrondini.drive_20_android.ui.dashboard

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
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

    override fun onResume() {
        super.onResume()
        viewModel.loadAppointments()
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
        binding.imgAvatar.setImageResource(R.drawable.ic_person)
        binding.imgAvatar.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))
    }

    private fun setupRecycler() {
        binding.rvDashboardRequests.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDashboardRequests.adapter = adapter
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    updateDashboardStats(state)
                    adapter.updateItems(state.appointments)
                    handlePlaceholder(state.appointments.isEmpty())

                    state.errorMessage?.let { msg ->
                        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun updateDashboardStats(state: DashboardInstrutorUiState) {
        binding.txtCompleted.text = state.completedCount.toString()
        binding.txtPending.text = state.pendingCount.toString()
        binding.txtRevenue.text = "R$ %.2f".format(state.totalRevenue)
        binding.txtTotalClasses.text = state.totalRequests.toString()

        val total = state.totalRequests.toFloat()
        if (total > 0) {
            // Solicitadas é sempre 100%
            updateGraphBar(binding.viewBarRequested, binding.spacerRequested, 1.0f)
            
            // Proporções calculadas
            val completedRatio = state.completedCount.toFloat() / total
            val cancelledRatio = state.cancelledCount.toFloat() / total
            
            updateGraphBar(binding.viewBarCompleted, binding.spacerCompleted, completedRatio)
            updateGraphBar(binding.viewBarPending, binding.spacerCancelled, cancelledRatio)
        } else {
            // Sem dados, as barras somem
            updateGraphBar(binding.viewBarRequested, binding.spacerRequested, 0.0f)
            updateGraphBar(binding.viewBarCompleted, binding.spacerCompleted, 0.0f)
            updateGraphBar(binding.viewBarPending, binding.spacerCancelled, 0.0f)
        }
    }

    private fun updateGraphBar(bar: View, spacer: View, ratio: Float) {
        val targetBarWeight = ratio.coerceIn(0.01f, 1.0f)
        val targetSpacerWeight = (1.0f - targetBarWeight).coerceIn(0.0f, 0.99f)

        val barParams = bar.layoutParams as LinearLayout.LayoutParams
        val spacerParams = spacer.layoutParams as LinearLayout.LayoutParams

        val initialBarWeight = barParams.weight
        val initialSpacerWeight = spacerParams.weight

        // Animator para criar o efeito de "crescimento" das barras
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.duration = 1000 // 1 segundo de animação
        animator.interpolator = DecelerateInterpolator() // Começa rápido e desacelera

        animator.addUpdateListener { animation ->
            val fraction = animation.animatedValue as Float
            
            // Interpolação entre o peso atual e o peso final
            barParams.weight = initialBarWeight + (targetBarWeight - initialBarWeight) * fraction
            spacerParams.weight = initialSpacerWeight + (targetSpacerWeight - initialSpacerWeight) * fraction
            
            bar.layoutParams = barParams
            spacer.layoutParams = spacerParams
        }
        animator.start()
    }

    private fun handlePlaceholder(isEmpty: Boolean) {
        binding.emptyPlaceholder.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.rvDashboardRequests.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }

    private fun navigateToDetail(appointment: Appointment) {
        val args = Bundle().apply {
            putString("argId", appointment.id)
            putString("argNome", appointment.alunoNome)
            putString("argInfo", appointment.localOrigem)
            putString("argData", appointment.dataHora)
            putString("argHorario", "")
            putString("argContato", appointment.alunoTelefone)
            putString("argStatus", appointment.status)
            putString("argPreco", "R$ %.2f".format(appointment.preco))
            putInt("argAvatar", R.drawable.ic_person)
            
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

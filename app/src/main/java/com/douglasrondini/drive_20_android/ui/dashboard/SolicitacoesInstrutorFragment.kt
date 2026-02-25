package com.douglasrondini.drive_20_android.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.douglasrondini.drive_20_android.R
import com.douglasrondini.drive_20_android.databinding.FragmentSolicitacoesInstrutorBinding
import com.douglasrondini.drive_20_android.domain.home.instrutor.DashboardSolicitacao
import com.douglasrondini.drive_20_android.ui.home.adapter.DashboardSolicitacaoAdapter

class SolicitacoesInstrutorFragment : Fragment() {

    private var _binding: FragmentSolicitacoesInstrutorBinding? = null
    private val binding get() = _binding!!

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
    }

    private fun setupRecycler() {
        val sampleData = listOf(
            DashboardSolicitacao(
                nome = "Ana Silva",
                info = "Hoje, 14:00 - Aula Prática",
                dataSolicitada = "25/10/2024",
                horario = "14:30",
                contato = "(11) 98765-4321",
                status = "Pendente",
                statusBgColor = R.color.primary,
                statusTextColor = android.R.color.black,
                avatarRes = R.drawable.ic_launcher_foreground
            ),
            DashboardSolicitacao(
                nome = "Bruno Costa",
                info = "Amanhã, 10:00 - Aula Teórica",
                dataSolicitada = "26/10/2024",
                horario = "10:00",
                contato = "(11) 91234-5678",
                status = "Aceita",
                statusBgColor = R.color.accent_green,
                statusTextColor = android.R.color.white,
                avatarRes = R.drawable.ic_launcher_foreground
            ),
            DashboardSolicitacao(
                nome = "Julia Martins",
                info = "Ontem, 16:30 - Aula Prática",
                dataSolicitada = "24/10/2024",
                horario = "16:30",
                contato = "(11) 97654-3210",
                status = "Concluída",
                statusBgColor = R.color.gray,
                statusTextColor = android.R.color.white,
                avatarRes = R.drawable.ic_launcher_foreground
            )
        )

        binding.rvSolicitacoesInstrutor.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = DashboardSolicitacaoAdapter(sampleData) { item ->
                navigateToDetail(item)
            }
        }
    }

    private fun navigateToDetail(item: DashboardSolicitacao) {
        val args = Bundle().apply {
            putString("argNome", item.nome)
            putString("argInfo", item.info)
            putString("argData", item.dataSolicitada)
            putString("argHorario", item.horario)
            putString("argContato", item.contato)
            putString("argStatus", item.status)
            putInt("argAvatar", item.avatarRes)
            putInt("argStatusBg", item.statusBgColor)
            putInt("argStatusText", item.statusTextColor)
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


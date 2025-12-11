package com.douglasrondini.drive_20_android.ui.solicitações

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.douglasrondini.drive_20_android.R
import com.douglasrondini.drive_20_android.databinding.FragmentSolicitacoesBinding
import com.douglasrondini.drive_20_android.domain.solicitações.Solicitacoes
import com.douglasrondini.drive_20_android.ui.solicitações.adapter.SolicitacoesAdapter

class SolicitacoesFragment : Fragment() {
    private lateinit var binding: FragmentSolicitacoesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSolicitacoesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {


        val solicitacoes = listOf(
            Solicitacoes("Aula com Douglas Rondini","25 de Novembro de 2025 - 14:00", R.drawable.ic_check_circule),
            Solicitacoes("Aula com Rodrigo Mendes","25 de Novembro de 2025 - 15:00", R.drawable.ic_check_circule),
            Solicitacoes("Aula com Ailson Neves","25 de Novembro de 2025 - 16:00", R.drawable.ic_check_circule)
        )

        val adapter = SolicitacoesAdapter(
            onItemClicked = { solicitacoes ->

            },
            listSolicitacoes = solicitacoes
        )
        binding.recyclerSolicitacoes.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerSolicitacoes.setHasFixedSize(true)
        binding.recyclerSolicitacoes.adapter = adapter
    }


}
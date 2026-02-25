package com.douglasrondini.drive_20_android.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.douglasrondini.drive_20_android.R
import com.douglasrondini.drive_20_android.databinding.FragmentPerfilInstrutorBinding

class PerfilInstrutorFragment : Fragment() {

    private var _binding: FragmentPerfilInstrutorBinding? = null
    private val binding get() = _binding!!

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
        setupClicks()
    }

    private fun setupStaticData() {
        binding.txtNome.text = "Carlos Silva"
        binding.txtSubtitulo.text = "Instrutor Credenciado • Categoria B"
        binding.txtAvaliacao.text = "4.9"
        binding.txtAulas.text = "154"
        binding.txtAnos.text = "3"
        binding.txtSaldoDisponivel.text = "Disponível: R$ 1.850,00"
    }

    private fun setupClicks() {
        binding.cardRetirarSaldo.setOnClickListener {
            findNavController().navigate(R.id.action_perfilInstrutorFragment_to_retiradaSaldoInstrutorFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


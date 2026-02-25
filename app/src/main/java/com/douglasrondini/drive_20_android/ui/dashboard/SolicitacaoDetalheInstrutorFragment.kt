package com.douglasrondini.drive_20_android.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.douglasrondini.drive_20_android.R
import com.douglasrondini.drive_20_android.databinding.FragmentSolicitacaoDetalheInstrutorBinding

class SolicitacaoDetalheInstrutorFragment : Fragment() {

    private var _binding: FragmentSolicitacaoDetalheInstrutorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSolicitacaoDetalheInstrutorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindArgs()
        setupClicks()
    }

    private fun bindArgs() {
        val args = requireArguments()
        val nome = args.getString("argNome").orEmpty()
        val info = args.getString("argInfo").orEmpty()
        val data = args.getString("argData").orEmpty()
        val horario = args.getString("argHorario").orEmpty()
        val contato = args.getString("argContato").orEmpty()
        val status = args.getString("argStatus").orEmpty()
        val avatar = args.getInt("argAvatar", R.drawable.ic_launcher_foreground)
        val statusBg = args.getInt("argStatusBg", R.color.primary)
        val statusText = args.getInt("argStatusText", android.R.color.black)

        binding.imgAvatar.setImageResource(avatar)
        binding.txtNome.text = nome
        binding.txtContato.text = contato
        binding.txtData.text = data
        binding.txtHorario.text = horario
        binding.txtStatus.text = status

        val context = requireContext()
        binding.txtStatus.backgroundTintList =
            ContextCompat.getColorStateList(context, statusBg)
        binding.txtStatus.setTextColor(ContextCompat.getColor(context, statusText))

        // Reaproveitando info completa caso queira mostrar em outro lugar
        binding.txtInfo.text = info
    }

    private fun setupClicks() {
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed() }
        // Botões de ação podem ser conectados a lógicas reais
        binding.btnAceitar.setOnClickListener { /* TODO: aceitar solicitação */ }
        binding.btnRecusar.setOnClickListener { /* TODO: recusar solicitação */ }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


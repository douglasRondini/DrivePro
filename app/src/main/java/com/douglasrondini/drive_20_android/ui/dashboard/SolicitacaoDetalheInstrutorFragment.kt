package com.douglasrondini.drive_20_android.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.douglasrondini.drive_20_android.R
import com.douglasrondini.drive_20_android.databinding.FragmentSolicitacaoDetalheInstrutorBinding
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

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
        val rawDataHora = args.getString("argData").orEmpty()
        val contato = args.getString("argContato").orEmpty()
        val status = args.getString("argStatus").orEmpty()
        val preco = args.getString("argPreco").orEmpty()
        val avatar = args.getInt("argAvatar", R.drawable.ic_launcher_foreground)
        val statusBg = args.getInt("argStatusBg", R.color.primary)
        val statusText = args.getInt("argStatusText", android.R.color.black)

        // Manipulação e Conversão de Data e Hora
        var formattedDate = "---"
        var formattedTime = "---"

        try {
            // Formato que vem da API (ISO 8601)
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val date = inputFormat.parse(rawDataHora)
            
            if (date != null) {
                formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)
                formattedTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(date)
            }
        } catch (e: Exception) {
            // Fallback caso o formato mude ou falhe
            formattedDate = rawDataHora
        }

        binding.imgAvatar.setImageResource(avatar)
        binding.txtNome.text = nome
        binding.txtContato.text = "Contato: $contato"
        
        // Atribuindo os valores manipulados aos respectivos campos
        binding.txtData.text = "Data Solicitada: $formattedDate"
        binding.txtHorario.text = "Horário: $formattedTime"
        binding.txtInfo.text = info
        binding.txtPreco.text = "Valor: $preco"
        binding.txtStatus.text = status

        val context = requireContext()
        binding.txtStatus.backgroundTintList =
            ContextCompat.getColorStateList(context, statusBg)
        binding.txtStatus.setTextColor(ContextCompat.getColor(context, statusText))

        updateActionButtons(status)
    }

    private fun updateActionButtons(status: String) {
        when (status.uppercase()) {
            "PENDENTE" -> {
                binding.btnAceitar.visibility = View.VISIBLE
                binding.btnRecusar.visibility = View.VISIBLE
                binding.btnAceitar.text = "Aceitar Solicitação"
                binding.btnRecusar.text = "Recusar Solicitação"
            }
            "ACEITA" -> {
                binding.btnAceitar.visibility = View.VISIBLE
                binding.btnRecusar.visibility = View.VISIBLE
                binding.btnAceitar.text = "Marcar como Concluída"
                binding.btnRecusar.text = "Cancelar Aula"
            }
            else -> {
                binding.btnAceitar.visibility = View.GONE
                binding.btnRecusar.visibility = View.GONE
            }
        }
    }

    private fun setupClicks() {
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed() 
        }
        binding.btnAceitar.setOnClickListener { /* Proxima tarefa: API update */ }
        binding.btnRecusar.setOnClickListener { /* Proxima tarefa: API update */ }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

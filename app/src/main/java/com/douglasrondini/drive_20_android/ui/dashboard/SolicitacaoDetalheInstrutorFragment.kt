package com.douglasrondini.drive_20_android.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.douglasrondini.drive_20_android.R
import com.douglasrondini.drive_20_android.databinding.FragmentSolicitacaoDetalheInstrutorBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class SolicitacaoDetalheInstrutorFragment : Fragment() {

    private var _binding: FragmentSolicitacaoDetalheInstrutorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SolicitacaoDetalheInstrutorViewModel by viewModel()
    
    private var currentAppointmentId: String? = null
    private var currentStatus: String? = null

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
        observeUiState()
    }

    private fun bindArgs() {
        val args = requireArguments()
        currentAppointmentId = args.getString("argId") // Certifique-se que o ID está sendo passado
        val nome = args.getString("argNome").orEmpty()
        val info = args.getString("argInfo").orEmpty()
        val rawDataHora = args.getString("argData").orEmpty()
        val contato = args.getString("argContato").orEmpty()
        currentStatus = args.getString("argStatus").orEmpty()
        val preco = args.getString("argPreco").orEmpty()
        val avatar = args.getInt("argAvatar", R.drawable.ic_person)
        val statusBg = args.getInt("argStatusBg", R.color.primary)
        val statusText = args.getInt("argStatusText", android.R.color.black)

        var formattedDate = rawDataHora
        var formattedTime = ""

        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val date = inputFormat.parse(rawDataHora)
            if (date != null) {
                // Criamos o formatador de saída e definimos o TimeZone como UTC para não subtrair as horas locais
                val outputDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val outputTimeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                outputDateFormat.timeZone = TimeZone.getTimeZone("UTC")
                outputTimeFormat.timeZone = TimeZone.getTimeZone("UTC")

                formattedDate = outputDateFormat.format(date)
                formattedTime = outputTimeFormat.format(date)
            }
        } catch (e: Exception) {}

        binding.imgAvatar.setImageResource(avatar)
        binding.imgAvatar.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))
        binding.txtNome.text = nome
        binding.txtContato.text = "Contato: $contato"
        binding.txtData.text = "Data Solicitada: $formattedDate"
        binding.txtHorario.text = "Horário: $formattedTime"
        binding.txtStatus.text = currentStatus
        binding.txtPreco.text = "Valor: $preco"

        val context = requireContext()
        binding.txtStatus.backgroundTintList = ContextCompat.getColorStateList(context, statusBg)
        binding.txtStatus.setTextColor(ContextCompat.getColor(context, statusText))
        binding.txtInfo.text = info

        updateActionButtons(currentStatus!!)
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

        binding.btnAceitar.setOnClickListener {
            val id = currentAppointmentId ?: return@setOnClickListener
            val status = currentStatus?.uppercase() ?: return@setOnClickListener
            
            if (status == "PENDENTE") {
                viewModel.accept(id)
            } else if (status == "ACEITA") {
                viewModel.complete(id)
            }
        }

        binding.btnRecusar.setOnClickListener {
            val id = currentAppointmentId ?: return@setOnClickListener
            val status = currentStatus?.uppercase() ?: return@setOnClickListener

            if (status == "PENDENTE") {
                viewModel.refuse(id)
            } else if (status == "ACEITA") {
                viewModel.cancel(id)
            }
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.btnAceitar.isEnabled = !state.isLoading
                    binding.btnRecusar.isEnabled = !state.isLoading
                    
                    if (state.isSuccess) {
                        Snackbar.make(binding.root, "Status atualizado com sucesso!", Snackbar.LENGTH_SHORT).show()
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    }

                    state.errorMessage?.let { msg ->
                        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
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

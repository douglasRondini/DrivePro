package com.douglasrondini.drive_20_android.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.douglasrondini.drive_20_android.R
import com.douglasrondini.drive_20_android.databinding.DialogCalendarBinding
import com.douglasrondini.drive_20_android.databinding.FragmentDetalhesInstrutorBinding
import com.douglasrondini.drive_20_android.domain.model.Instructor
import com.douglasrondini.drive_20_android.ui.home.adapter.CustomSpinnerAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetalhesInstrutorFragment : Fragment() {
    private lateinit var binding: FragmentDetalhesInstrutorBinding
    private val viewModel: DetalhesInstrutorViewModel by viewModel()
    private var instructor: Instructor? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetalhesInstrutorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        instructor = arguments?.getParcelable("argInstructor")
        
        setupUI()
        setupListeners()
        observeUiState()
        selectData()
        configurarSpinners()
    }

    private fun setupUI() {
        instructor?.let { item ->
            binding.txtNomeInstrutor.text = item.name
            binding.txtTelefoneInstrutor.text = item.phone
            binding.txtCNHInstrutor.text = "CNH: ${item.cnh}"
            binding.txtVeiculoInstrutor.text = "Veículo: ${item.plate}"
            binding.imgInstrutor.setImageResource(R.drawable.ic_person)
            binding.imgInstrutor.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white))
            
            binding.txtDisponibilidade.text = if (item.isAvailable) "Disponível" else "Indisponível"
            binding.txtDisponibilidade.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    if (item.isAvailable) R.color.accent_green else R.color.gray
                )
            )

            binding.txtNota.text = "4.9"
            binding.txtExperienciaInstrutor.text = "Experiência comprovada"
            binding.txtPrecoAula.text = if (item.price != null) {
                "R$ %.2f / 50 min".format(item.price)
            } else {
                "Consultar valor"
            }
        }
    }

    private fun setupListeners() {
        binding.btnSolicitarAula.setOnClickListener {
            val selectedDate = binding.data.text.toString()
            val selectedTime = binding.spinnerHorarios.selectedItem?.toString() ?: ""
            val instructorId = instructor?.id ?: return@setOnClickListener
            val price = instructor?.price ?: 0.0

            if (selectedDate.isNotEmpty() && selectedDate != "Selecione uma Data" && selectedTime.isNotEmpty()) {
                viewModel.createAppointment(
                    instructorId = instructorId,
                    location = "Av. Paulista, 1000", // Pode ser dinâmico no futuro
                    date = selectedDate,
                    time = selectedTime,
                    price = price
                )
            } else {
                Toast.makeText(requireContext(), "Selecione a data e o horário", Toast.LENGTH_SHORT).show()
            }
        }
        
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.btnSolicitarAula.isEnabled = !state.isLoading
                    binding.btnSolicitarAula.text = if (state.isLoading) "Solicitando..." else "Solicitar Aula"

                    if (state.isSuccess) {
                        Snackbar.make(binding.root, "Aula solicitada com sucesso!", Snackbar.LENGTH_LONG).show()
                        viewModel.consumeSuccess()
                        findNavController().navigate(R.id.action_detalhesInstrutorFragment_to_confirmSolicitacoesFragment)
                    }

                    state.errorMessage?.let { msg ->
                        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
                        viewModel.consumeError()
                    }
                }
            }
        }
    }

    private fun selectData() {
        binding.data.setOnClickListener {
            val dialogBinding = DialogCalendarBinding.inflate(layoutInflater)

            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogBinding.root)
                .setTitle("Selecione uma data")
                .setNegativeButton("Cancelar", null)
                .create()

            dialogBinding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
                val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                val monthStr = if (month + 1 < 10) "0${month + 1}" else "${month + 1}"
                val selectedDate = "$dayStr/$monthStr/$year"
                binding.data.text = selectedDate
                binding.data.setTextColor(requireContext().getColor(R.color.white))
                dialog.dismiss()
            }

            dialog.show()
        }
    }

    private fun configurarSpinners() {
        val horarios = resources.getStringArray(R.array.horarios_array).toList()
        val adapterSpinner = CustomSpinnerAdapter(requireContext(), horarios)
        binding.spinnerHorarios.adapter = adapterSpinner
    }
}

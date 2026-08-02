package com.douglasrondini.drive_20_android.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.douglasrondini.drive_20_android.R
import com.douglasrondini.drive_20_android.databinding.DialogCalendarBinding
import com.douglasrondini.drive_20_android.databinding.FragmentDetalhesInstrutorBinding
import com.douglasrondini.drive_20_android.domain.model.Instructor
import com.douglasrondini.drive_20_android.ui.home.adapter.CustomSpinnerAdapter

class DetalhesInstrutorFragment : Fragment() {
    private lateinit var binding: FragmentDetalhesInstrutorBinding
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

            // Dados estáticos para campos que não vêm na lista da API
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
            findNavController().navigate(R.id.action_detalhesInstrutorFragment_to_confirmSolicitacoesFragment)
        }
        
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
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
                val selectedDate = "$dayOfMonth/${month + 1}/$year"
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

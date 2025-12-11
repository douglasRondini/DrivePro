package com.douglasrondini.drive_20_android.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.douglasrondini.drive_20_android.R
import com.douglasrondini.drive_20_android.databinding.DialogCalendarBinding
import com.douglasrondini.drive_20_android.databinding.FragmentDetalhesInstrutorBinding
import com.douglasrondini.drive_20_android.ui.home.adapter.CustomSpinnerAdapter

class DetalhesInstrutorFragment : Fragment() {
    private lateinit var binding: FragmentDetalhesInstrutorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetalhesInstrutorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSolicitarAula.setOnClickListener {
            findNavController().navigate(R.id.action_detalhesInstrutorFragment_to_confirmSolicitacoesFragment)
        }

        selectData()
        configurarSpinners()

    }

    private fun selectData() {
        binding.data.setOnClickListener {
            // inflar o layout do dialog usando binding do layout do diálogo
            val dialogBinding = DialogCalendarBinding.inflate(layoutInflater)

            val dialog = AlertDialog.Builder(requireContext()) // aqui muda!
                .setView(dialogBinding.root)
                .setTitle("Selecione uma data")
                .setNegativeButton("Cancelar", null)
                .create()

            // Listener para seleção de data
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
        val adapterSpinner = CustomSpinnerAdapter(requireContext(),horarios)

        binding.spinnerHorarios.adapter = adapterSpinner
    }
}
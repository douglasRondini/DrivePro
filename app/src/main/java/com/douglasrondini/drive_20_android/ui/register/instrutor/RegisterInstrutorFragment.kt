package com.douglasrondini.drive_20_android.ui.register.instrutor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.douglasrondini.drive_20_android.databinding.FragmentRegisterInstrutorBinding
import com.douglasrondini.drive_20_android.domain.home.instrutor.InstrutorRegister
import com.douglasrondini.drive_20_android.utils.MaskWatcher
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterInstrutorFragment : Fragment() {
    private lateinit var binding: FragmentRegisterInstrutorBinding
    private val viewModel: RegisterInstrutorViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterInstrutorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        observeUiState()
        applyMasks()
    }

    private fun applyMasks() {
        MaskWatcher.applyMask(MaskWatcher.MASK_PHONE, binding.inputTelefone)
        // Adicionando máscaras para CNH e Placa também
        MaskWatcher.applyMask(MaskWatcher.MASK_CNH, binding.inputCNH)
        MaskWatcher.applyMask(MaskWatcher.MASK_PLATE, binding.inputPlaca)
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnCadastrar.setOnClickListener {
            val register = getInstrutorValues()
            if (validateFields(register)) {
                viewModel.registerInstrutor(register)
            } else {
                Toast.makeText(requireContext(), "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getInstrutorValues(): InstrutorRegister {
        val telefone = MaskWatcher.unmask(binding.inputTelefone.text.toString())
        val cnh = MaskWatcher.unmask(binding.inputCNH.text.toString())
        val placa = MaskWatcher.unmask(binding.inputPlaca.text.toString())

        return InstrutorRegister(
            email = binding.inputEmail.text.toString().trim(),
            name = binding.inputNome.text.toString().trim(),
            age = binding.inputIdade.text.toString().trim(),
            password = binding.inputSenha.text.toString().trim(),
            telefone = telefone,
            cnh = cnh,
            placaVeiculo = placa
        )
    }

    private fun validateFields(instrutor: InstrutorRegister): Boolean {
        return instrutor.email.isNotEmpty() &&
                instrutor.name.isNotEmpty() &&
                instrutor.password.isNotEmpty() &&
                instrutor.age.isNotEmpty() &&
                instrutor.telefone.isNotEmpty() &&
                instrutor.cnh.isNotEmpty() &&
                instrutor.placaVeiculo.isNotEmpty()
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    handleLoading(state.isLoading)
                    
                    if (state.isSuccess) {
                        Snackbar.make(binding.root, "Instrutor cadastrado com sucesso!", Snackbar.LENGTH_LONG).show()
                        findNavController().popBackStack()
                    }

                    state.errorMessage?.let { msg ->
                        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.btnCadastrar.isEnabled = !isLoading
        binding.btnCadastrar.text = if (isLoading) "Cadastrando..." else "Cadastrar"
    }
}

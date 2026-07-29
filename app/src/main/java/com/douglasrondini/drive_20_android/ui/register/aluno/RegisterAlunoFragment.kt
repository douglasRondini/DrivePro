package com.douglasrondini.drive_20_android.ui.register.aluno

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.douglasrondini.drive_20_android.databinding.FragmentRegisterAlunoBinding
import com.douglasrondini.drive_20_android.domain.home.aluno.AlunoRegister
import com.douglasrondini.drive_20_android.domain.home.aluno.Role
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterAlunoFragment : Fragment() {
    private lateinit var binding: FragmentRegisterAlunoBinding
    private val viewModel: RegisterAlunoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterAlunoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        observerUiState()
    }

    private fun setup() {
        binding.btnCadastrar.setOnClickListener {
            val register = registerAlunoValues()
            viewModel.registerAluno(register)
        }
    }

    private fun observerUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    handleLoading(state.isLoading)
                    handleSuccess(state.isSuccess)
                    handleError(state.errorMessage)
                }
            }
        }
    }

    private fun handleLoading(isLoading: Boolean) {
        // Exemplo: binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        if (isLoading) {
            Snackbar.make(binding.root, "Realizando cadastro...", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun handleSuccess(isSuccess: Boolean) {
        if (isSuccess) {
            Snackbar.make(binding.root, "Cadastro realizado com sucesso!", Snackbar.LENGTH_LONG).show()
            findNavController().popBackStack()
        }
    }

    private fun handleError(message: String?) {
        message?.let {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun registerAlunoValues(): AlunoRegister {
        var aluno = AlunoRegister(
            email = binding.inputEmail.text.toString().trim(),
            name = binding.inputNome.text.toString().trim(),
            age = "28",
            password = binding.inputSenha.text.toString().trim(),
            role = Role.ALUNO,
            telefone = "65996891997",
            cpf = "04041686121"
        )
        return aluno
    }


}
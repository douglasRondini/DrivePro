package com.douglasrondini.drive_20_android.ui.login

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
import com.douglasrondini.drive_20_android.R
import com.douglasrondini.drive_20_android.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        observeUiState()
    }

    private fun setupListeners() {
        binding.btnEntrar.setOnClickListener {
            val email = binding.inputEmail.text.toString().trim()
            val password = binding.inputSenha.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(email, password)
            } else {
                Toast.makeText(requireContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.linkCadastro.setOnClickListener {
            DialogCreatAccountFragment().show(
                requireActivity().supportFragmentManager,
                "DialogCreatAccount"
            )
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    handleLoading(state.isLoading)
                    
                    if (state.isSuccess && state.userRole != null) {
                        navHome(state.userRole)
                    }

                    state.errorMessage?.let { msg ->
                        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.btnEntrar.isEnabled = !isLoading
        binding.btnEntrar.text = if (isLoading) "Entrando..." else "Entrar"
    }

    private fun navHome(role: String) {
        val destinationId = when (role.lowercase()) {
            "aluno" -> R.id.action_loginFragment_to_bottomNavHomeActivity
            "instrutor" -> R.id.action_loginFragment_to_bottomNavInstrutorActivity
            else -> {
                Toast.makeText(requireContext(), "Perfil não identificado", Toast.LENGTH_SHORT).show()
                null
            }
        }
        
        destinationId?.let { findNavController().navigate(it) }
    }
}

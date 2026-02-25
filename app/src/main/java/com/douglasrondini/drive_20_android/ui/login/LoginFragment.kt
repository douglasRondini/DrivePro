package com.douglasrondini.drive_20_android.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.douglasrondini.drive_20_android.R
import com.douglasrondini.drive_20_android.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnEntrar.setOnClickListener {
            var user = binding.inputNome.text.toString().trim()
            loginNav(user)
        }

        binding.linkCadastro.setOnClickListener {
            DialogCreatAccountFragment().show(
                requireActivity().supportFragmentManager,
                "DialogCreatAccount"
            )
        }
    }

    private fun loginNav(user: String) {
        val aluno = "aluno"
        val instrutor = "instrutor"

        when (user) {
            aluno -> navHomeAluno()
            instrutor -> navHomeInstrutor()
            else -> Toast.makeText(requireContext(), "Usuário não reconhecido", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navHomeAluno() {
        findNavController().navigate(R.id.action_loginFragment_to_bottomNavHomeActivity)
    }

    private fun navHomeInstrutor() {
        findNavController().navigate(R.id.action_loginFragment_to_bottomNavInstrutorActivity)
    }


}
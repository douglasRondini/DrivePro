package com.douglasrondini.drive_20_android.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.douglasrondini.drive_20_android.databinding.FragmentPerfilAlunoBinding
import com.douglasrondini.drive_20_android.ui.activities.AccountActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class PerfilAlunoFragment : Fragment() {

    private var _binding: FragmentPerfilAlunoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PerfilAlunoViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfilAlunoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupClicks()
    }

    private fun setupUI() {
        binding.txtNome.text = viewModel.getUserName() ?: "Aluno"
    }

    private fun setupClicks() {
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            val intent = Intent(requireContext(), AccountActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

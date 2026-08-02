package com.douglasrondini.drive_20_android.ui.home

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.douglasrondini.drive_20_android.R
import com.douglasrondini.drive_20_android.data.local.PreferenceManager
import com.douglasrondini.drive_20_android.databinding.FragmentHomeAlunoBinding
import com.douglasrondini.drive_20_android.ui.home.adapter.InstrutorAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeAlunoFragment : Fragment() {
    private lateinit var binding: FragmentHomeAlunoBinding
    private val preferenceManager: PreferenceManager by inject()
    private val viewModel: HomeAlunoViewModel by viewModel()

    private val adapter by lazy {
        InstrutorAdapter(emptyList()) { instrutor ->
            val args = Bundle().apply {
                putParcelable("argInstructor", instrutor)
            }
            findNavController().navigate(R.id.action_homeAlunoFragment_to_detalhesInstrutorFragment, args)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeAlunoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupRecycler()
        observeUiState()
    }

    private fun setupUI() {
        val userName = preferenceManager.getUserName() ?: "Aluno"
        binding.txtGreeting.text = "Olá, $userName"
    }

    private fun setupRecycler() {
        binding.instructorRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.instructorRecycler.adapter = adapter
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    adapter.updateItems(state.instructors)
                    
                    state.errorMessage?.let { msg ->
                        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}

package com.douglasrondini.drive_20_android.ui.login

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.douglasrondini.drive_20_android.R
import com.douglasrondini.drive_20_android.databinding.FragmentDialogCreatAccountBinding

class DialogCreatAccountFragment : DialogFragment() {
    private var _binding: FragmentDialogCreatAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentDialogCreatAccountBinding.inflate(
        inflater,container,false
    ).apply {
        _binding = this
    }.root

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        binding.cardAluno.setOnClickListener {
            dismiss()
            findNavController().navigate(R.id.registerAlunoFragment)
        }

        binding.cardInstrutor.setOnClickListener {
            dismiss()
            findNavController().navigate(R.id.registerInstrutorFragment)
        }
    }
}
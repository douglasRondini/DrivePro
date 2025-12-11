package com.douglasrondini.drive_20_android.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.douglasrondini.drive_20_android.R
import com.douglasrondini.drive_20_android.databinding.FragmentHomeAlunoBinding
import com.douglasrondini.drive_20_android.domain.home.aluno.InfoInstrutor
import com.douglasrondini.drive_20_android.ui.home.adapter.InstrutorAdapter

class HomeAlunoFragment : Fragment() {
    private lateinit var binding: FragmentHomeAlunoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeAlunoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }


    private fun setupUi() {
        val instrutores = listOf(
            InfoInstrutor(
                nome = "Mariana Almeida",
                rating = "4.8",
                distance = "A 2,5km de você",
                disponibilidade = "Disponível Hoje",
                price = "R$ 50,00",
                imgPerfil = R.drawable.ic_foto
            ),
            InfoInstrutor(
                nome = "Roberto Almeida",
                rating = "4.8",
                distance = "A 2,5km de você",
                disponibilidade = "Disponível Hoje",
                price = "R$ 50,00",
                imgPerfil = R.drawable.img_instrutor
            ),
//            InfoInstrutor(
//                nome = "Mariana Almeida",
//                rating = "4.8",
//                distance = "A 2,5km de você",
//                disponibilidade = "Disponível Hoje",
//                price = "R$ 50,00",
//                imgPerfil = R.drawable.img_instrutor2
//            ),
//            InfoInstrutor(
//                nome = "Mariana Almeida",
//                rating = "4.8",
//                distance = "A 2,5km de você",
//                disponibilidade = "Disponível Hoje",
//                price = "R$ 50,00",
//                imgPerfil = R.drawable.img_instrutor3
//            ),
//            InfoInstrutor(
//                nome = "Mariana Almeida",
//                rating = "4.8",
//                distance = "A 2,5km de você",
//                disponibilidade = "Disponível Hoje",
//                price = "R$ 50,00",
//                imgPerfil = R.drawable.ic_foto
//            ),
//            InfoInstrutor(
//                nome = "Mariana Almeida",
//                rating = "4.8",
//                distance = "A 2,5km de você",
//                disponibilidade = "Disponível Hoje",
//                price = "R$ 50,00",
//                imgPerfil = R.drawable.img_instrutor1
//            )
        )

        val adapter = InstrutorAdapter(
            onItemClicked = { instrutor ->
                // ação ao clicar no item
                Toast.makeText(requireContext(), "Instrutor: ${instrutor.nome}", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_homeAlunoFragment_to_detalhesInstrutorFragment)
            },
            listaInstrutores = instrutores
        )

        binding.instructorRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.instructorRecycler.setHasFixedSize(true)
        binding.instructorRecycler.adapter = adapter
    }





}
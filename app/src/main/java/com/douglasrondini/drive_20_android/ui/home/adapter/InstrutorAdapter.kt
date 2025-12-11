package com.douglasrondini.drive_20_android.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.douglasrondini.drive_20_android.databinding.ItemInstrutoresBinding
import com.douglasrondini.drive_20_android.domain.home.aluno.InfoInstrutor

class InstrutorAdapter(
    private val onItemClicked: (InfoInstrutor) -> Unit,
    private val listaInstrutores: List<InfoInstrutor>
): RecyclerView.Adapter<InstrutorAdapter.InstrutorViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InstrutorViewHolder {
        val binding = ItemInstrutoresBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return InstrutorViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: InstrutorViewHolder,
        position: Int
    ) {
        holder.bind(listaInstrutores[position])
    }

    override fun getItemCount(): Int = listaInstrutores.size


    inner class InstrutorViewHolder(
        private var binding: ItemInstrutoresBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(instrutor: InfoInstrutor) {

            binding.tvNome.text = instrutor.nome
            binding.tvAvaliacao.text = instrutor.rating
            binding.tvDistancia.text = instrutor.distance
            binding.tvDisponibilidade.text = instrutor.disponibilidade
            binding.tvPreco.text = instrutor.price
            binding.imgPerfil.setImageResource(instrutor.imgPerfil)

            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClicked(listaInstrutores[position])
                }
            }

        }
    }
}
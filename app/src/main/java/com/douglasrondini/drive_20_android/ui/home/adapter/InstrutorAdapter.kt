package com.douglasrondini.drive_20_android.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.douglasrondini.drive_20_android.R
import com.douglasrondini.drive_20_android.databinding.ItemInstrutoresBinding
import com.douglasrondini.drive_20_android.domain.model.Instructor

class InstrutorAdapter(
    private var listaInstrutores: List<Instructor>,
    private val onItemClicked: (Instructor) -> Unit
) : RecyclerView.Adapter<InstrutorAdapter.InstrutorViewHolder>() {

    fun updateItems(newItems: List<Instructor>) {
        listaInstrutores = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstrutorViewHolder {
        val binding = ItemInstrutoresBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return InstrutorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InstrutorViewHolder, position: Int) {
        holder.bind(listaInstrutores[position])
    }

    override fun getItemCount(): Int = listaInstrutores.size

    inner class InstrutorViewHolder(
        private var binding: ItemInstrutoresBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(instrutor: Instructor) {
            binding.tvNome.text = instrutor.name
            binding.tvAvaliacao.text = "4.9" // Mockado até ter na API
            binding.tvDistancia.text = "A 2.5 km de você" // Mockado até ter na API
            binding.tvDisponibilidade.text = if (instrutor.isAvailable) "Disponível Agora" else "Indisponível"
            binding.tvDisponibilidade.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    if (instrutor.isAvailable) R.color.accent_green else R.color.gray
                )
            )
            binding.tvPreco.text = "Consultar valor" // Ajustado conforme resposta da API que não veio preço

            binding.imgPerfil.setImageResource(R.drawable.ic_person)
            binding.imgPerfil.setColorFilter(ContextCompat.getColor(itemView.context, R.color.white))

            itemView.setOnClickListener {
                onItemClicked(instrutor)
            }
        }
    }
}

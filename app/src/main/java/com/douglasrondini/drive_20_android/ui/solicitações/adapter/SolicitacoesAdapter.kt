package com.douglasrondini.drive_20_android.ui.solicitações.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.douglasrondini.drive_20_android.databinding.ItemSolicitacoesBinding
import com.douglasrondini.drive_20_android.domain.solicitações.Solicitacoes

class SolicitacoesAdapter(
    private val onItemClicked: (Solicitacoes) -> Unit,
    private val listSolicitacoes: List<Solicitacoes>
): RecyclerView.Adapter<SolicitacoesAdapter.SolicitacoesViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SolicitacoesViewHolder {
        val binding = ItemSolicitacoesBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )
        return SolicitacoesViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: SolicitacoesViewHolder,
        position: Int
    ) {
        holder.bind(listSolicitacoes[position])
    }

    override fun getItemCount(): Int = listSolicitacoes.size

    inner class SolicitacoesViewHolder(
        private val binding: ItemSolicitacoesBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(solicitacoes: Solicitacoes) {
            binding.txtTitulo.text = solicitacoes.description
            binding.txtDataHora.text = solicitacoes.data
            binding.iconStatus.setImageResource(solicitacoes.icon)

            binding.iconNext.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClicked(listSolicitacoes[position])
                }
            }

        }
    }
}
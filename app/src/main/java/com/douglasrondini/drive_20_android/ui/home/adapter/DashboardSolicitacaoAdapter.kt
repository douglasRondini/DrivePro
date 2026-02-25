package com.douglasrondini.drive_20_android.ui.home.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.douglasrondini.drive_20_android.databinding.ItemDashboardRequestBinding
import com.douglasrondini.drive_20_android.domain.home.instrutor.DashboardSolicitacao

class DashboardSolicitacaoAdapter(
    private val items: List<DashboardSolicitacao>,
    private val onItemClick: (DashboardSolicitacao) -> Unit = {}
) : RecyclerView.Adapter<DashboardSolicitacaoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDashboardRequestBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(
        private val binding: ItemDashboardRequestBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DashboardSolicitacao) {
            binding.txtName.text = item.nome
            binding.txtInfo.text = item.info
            binding.txtStatus.text = item.status
            binding.imgAvatar.setImageResource(item.avatarRes)

            val context = binding.root.context
            val bgColor = ContextCompat.getColor(context, item.statusBgColor)
            val textColor = ContextCompat.getColor(context, item.statusTextColor)
            binding.txtStatus.backgroundTintList = ColorStateList.valueOf(bgColor)
            binding.txtStatus.setTextColor(textColor)

            binding.root.setOnClickListener { onItemClick(item) }
        }
    }
}
package com.douglasrondini.drive_20_android.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.douglasrondini.drive_20_android.R
import com.douglasrondini.drive_20_android.databinding.ItemDashboardRequestBinding
import com.douglasrondini.drive_20_android.domain.model.Appointment
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class AppointmentAdapter(
    private var items: List<Appointment>,
    private val onItemClick: (Appointment) -> Unit
) : RecyclerView.Adapter<AppointmentAdapter.ViewHolder>() {

    fun updateItems(newItems: List<Appointment>) {
        items = newItems
        notifyDataSetChanged()
    }

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

    inner class ViewHolder(private val binding: ItemDashboardRequestBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(appointment: Appointment) {
            binding.txtName.text = appointment.alunoNome
            binding.txtLocation.text = appointment.localOrigem
            
            // Formatação de Data e Hora
            try {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                inputFormat.timeZone = TimeZone.getTimeZone("UTC")
                val date = inputFormat.parse(appointment.dataHora)
                
                if (date != null) {
                    binding.txtDate.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)
                    binding.txtTime.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(date)
                }
            } catch (e: Exception) {
                binding.txtDate.text = appointment.dataHora
                binding.txtTime.text = "--:--"
            }

            // Regra de cores por Status
            val context = itemView.context
            binding.txtStatus.text = appointment.status.uppercase()
            
            val (bgColor, textColor) = when (appointment.status.uppercase()) {
                "ACEITA" -> R.color.accent_green to android.R.color.white
                "PENDENTE" -> R.color.primary to android.R.color.black
                "CANCELADA" -> android.R.color.holo_red_dark to android.R.color.white
                "CONCLUIDA" -> R.color.gray to android.R.color.white
                else -> R.color.gray to android.R.color.white
            }

            binding.txtStatus.setBackgroundResource(R.drawable.bg_status_chip)
            binding.txtStatus.backgroundTintList = ContextCompat.getColorStateList(context, bgColor)
            binding.txtStatus.setTextColor(ContextCompat.getColor(context, textColor))

            itemView.setOnClickListener { onItemClick(appointment) }
        }
    }
}

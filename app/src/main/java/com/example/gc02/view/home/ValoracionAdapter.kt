package com.example.gc02.view.home


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gc02.databinding.ListValoracionBinding
import com.example.gc02.model.Valuation
class ValoracionAdapter(
    private var valoracion: List<Valuation>
) : RecyclerView.Adapter<ValoracionAdapter.ValoracionViewHolder>() {

    class ValoracionViewHolder(
        private val binding: ListValoracionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(valuation: Valuation, totalItems: Int) {
            with(binding) {
                rating.text = valuation.points.toString()
                textComentario.text = valuation.comment
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValoracionViewHolder {
        val binding =
            ListValoracionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ValoracionViewHolder(binding)
    }
    override fun getItemCount() = valoracion.size
    override fun onBindViewHolder(holder: ValoracionViewHolder, position: Int) {
        holder.bind(valoracion[position],valoracion.size)
    }

    fun updateData(valoracion: List<Valuation>) {
        this.valoracion = valoracion
        notifyDataSetChanged()
    }
}


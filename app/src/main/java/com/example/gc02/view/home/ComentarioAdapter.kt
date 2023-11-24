package com.example.gc02.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gc02.databinding.ListComentarioBinding
import com.example.gc02.model.Comentario

class ComentarioAdapter(
    private var comentarios: List<Comentario>
) : RecyclerView.Adapter<ComentarioAdapter.ComentarioViewHolder>() {

    class ComentarioViewHolder(
        private val binding: ListComentarioBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comentario: Comentario, totalItems: Int) {
            with(binding) {
                usuario.text = comentario.autor
                textComentario.text = comentario.comentario
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComentarioViewHolder {
        val binding =
        ListComentarioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ComentarioViewHolder(binding)
    }
    override fun getItemCount() = comentarios.size
    override fun onBindViewHolder(holder: ComentarioViewHolder, position: Int) {
        holder.bind(comentarios[position],comentarios.size)
    }

    fun updateData(comentarios: List<Comentario>) {
        this.comentarios = comentarios
        notifyDataSetChanged()
    }
}

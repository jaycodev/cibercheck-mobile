package com.example.cibercheck.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cibercheck.R
import com.example.cibercheck.entity.Asistencia


class HistorialAdapter(
    private val listaAsistencias: List<Asistencia>
) : RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder>() {

    inner class HistorialViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvInicial: TextView = itemView.findViewById(R.id.tv_inicial)
        val tvNombreCurso: TextView = itemView.findViewById(R.id.tv_nombreCurso)
        val tvModalidad: TextView = itemView.findViewById(R.id.tv_modalidad)
        val tvAsistencia: TextView = itemView.findViewById(R.id.tv_asistencia)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistorialViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_historial, parent, false)
        return HistorialViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistorialViewHolder, position: Int) {
        val asistencia = listaAsistencias[position]

        // Primera letra del curso
        holder.tvInicial.text = asistencia.nombreCurso.first().toString()

        holder.tvNombreCurso.text = asistencia.nombreCurso
        holder.tvModalidad.text = asistencia.modalidad
        holder.tvAsistencia.text = asistencia.asistencia
    }

    override fun getItemCount(): Int = listaAsistencias.size
}

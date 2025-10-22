package com.example.cibercheck.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cibercheck.R
import com.example.cibercheck.entity.Curso

class CursoAdapter(
    private val items: MutableList<Curso>
) : RecyclerView.Adapter<CursoAdapter.VH>() {

    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        val txtCodigo: TextView = v.findViewById(R.id.txtCodigoPeriodo)
        val txtNombre: TextView = v.findViewById(R.id.txtNombreCurso)
        val txtEstado: TextView = v.findViewById(R.id.txtEstado)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_curso, parent, false)
        return VH(v)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(h: VH, position: Int) {
        val c = items[position]
        val ctx = h.itemView.context
        h.txtCodigo.text = c.periodoId
        h.txtNombre.text = c.nombre

        if (c.enCurso) {
            h.txtEstado.text = ctx.getString(R.string.estado_curso)
            h.txtEstado.setTextColor(ContextCompat.getColor(ctx, R.color.state_ok))
        } else {
            h.txtEstado.text = ctx.getString(R.string.estado_inicia_en, c.tiempo ?: "")
            h.txtEstado.setTextColor(ContextCompat.getColor(ctx, R.color.state_warning))
        }
    }

    fun replaceAll(list: List<Curso>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}
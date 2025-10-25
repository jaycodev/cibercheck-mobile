package com.example.cibercheck.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cibercheck.R
import com.example.cibercheck.entity.Curso
import com.example.cibercheck.entity.CursoStatus

class CursoAdapter(
    private val items: MutableList<Curso>
) : RecyclerView.Adapter<CursoAdapter.VH>() {

    // Definimos los colores para que sea más fácil gestionarlos
    private val COLOR_GREEN = Color.parseColor("#2E8B57") // Verde "SeaGreen"
    private val COLOR_ORANGE = Color.parseColor("#FFA500") // Naranja
    private val COLOR_GRAY = Color.parseColor("#808080")   // Gris

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
        val curso = items[position]

        h.txtCodigo.text = curso.periodoId
        h.txtNombre.text = curso.nombre

        // Lógica de estado y color basada en el nuevo campo `status`
        when (curso.status) {
            CursoStatus.IN_PROGRESS -> {
                h.txtEstado.text = "En curso: ${curso.tiempo}"
                h.txtEstado.setTextColor(COLOR_GREEN)
            }
            CursoStatus.STARTING_SOON -> {
                h.txtEstado.text = "${curso.tiempo}" // El texto ya viene calculado (ej: "Empieza en 5 min")
                h.txtEstado.setTextColor(COLOR_ORANGE)
            }
            CursoStatus.FINISHED -> {
                h.txtEstado.text = "Finalizado: ${curso.tiempo}"
                h.txtEstado.setTextColor(COLOR_GRAY)
            }
            CursoStatus.UPCOMING -> {
                h.txtEstado.text = "Próximo: ${curso.tiempo}"
                h.txtEstado.setTextColor(COLOR_GRAY)
            }
        }
    }

    fun replaceAll(list: List<Curso>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}

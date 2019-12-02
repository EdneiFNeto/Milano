package com.example.milano.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.milano.R
import com.example.milano.model.Caixas

class CaixasAdapter(
    val context: Context,
    private val caixas: ArrayList<Caixas>
) : RecyclerView.Adapter<CaixasAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(context).inflate(R.layout.layout_caixas, parent, false))
    }

    override fun getItemCount(): Int {
        return caixas.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.add(caixas[position])
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var codigo: TextView = itemView.findViewById(R.id.text_codigo_caixa)
        private var documento: TextView = itemView.findViewById(R.id.text_documento_caixa)
        fun add(caixas: Caixas) {
            codigo.text = caixas.codigo
            documento.text = caixas.documento
        }
    }

}

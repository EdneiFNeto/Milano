package com.example.milano.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.milano.R


class BarCodeAdapter(
    val context: Context,
    private val listBarCode: ArrayList<String>
) : RecyclerView.Adapter<BarCodeAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.layout_list_de_codigo_de_barra,
            parent, false
        )

        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return listBarCode.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        var texto: String = listBarCode[position]
        holder.add(texto)
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var text: TextView = itemView.findViewById(R.id.textView_barcode)

        fun add(str: String) {
            text.text = str
        }
    }

}

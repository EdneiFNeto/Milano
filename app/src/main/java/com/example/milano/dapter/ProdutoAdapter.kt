package com.example.milano.dapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.milano.R
import com.example.milano.async.DeletarProdutoAsyncTasks
import com.example.milano.model.Produto
import com.example.milano.ui.PrinterActivity
import com.github.clans.fab.FloatingActionButton
import java.io.Serializable

class ProdutoAdapter(
    private val context: Context,
    private val produtos: List<Produto>,
    private val buttonFab: FloatingActionButton
) :
    RecyclerView.Adapter<ProdutoAdapter.ViewHolder>() {

    val TAG = "ProdutoAdapterLog"
    var listaProdutos = ArrayList<Produto>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_produto_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return produtos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var produto = produtos[position]
        holder.add(produto)


//        holder.checkbox.setOnClickListener {
//
//            if (holder.checkbox.isChecked) {
//                //add a lista de arquivos para serem imprimidos
//                Log.e(TAG, "Add Position  ${produtos[position].id} - ${produtos[position].nome}")
//                listaProdutos.add(produtos[position])
//            } else {
//                Log.e(TAG, "Removed Position  ${produtos[position].id} - ${produtos[position].nome}")
//                listaProdutos.remove(produtos[position])
//            }
//
//            Log.e(TAG, "Total: ${listaProdutos.size}")
//        }

        buttonFab.setOnClickListener {

            if(produtos.isNotEmpty()){
                for (p in produtos)
                    listaProdutos.add(p)

                var intent = Intent(context, PrinterActivity::class.java)
                intent.putExtra("produtos",listaProdutos as Serializable)
                context.startActivity(intent)

            }else{
                Toast.makeText(context,"Lista vazia ", Toast.LENGTH_LONG).show()
            }

        }
    }

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        var barCode: TextView = item.findViewById(R.id.cod_bar_produto_adapter)
        var checkbox: CheckBox = item.findViewById(R.id.item_checkbox_bar_code)

        fun add(p: Produto) {
            barCode.text = p.nome
        }
    }

}

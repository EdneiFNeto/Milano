package com.example.milano.async

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.milano.R
import com.example.milano.dapter.ProdutoAdapter
import com.example.milano.database.AppDatabase
import com.example.milano.database.dao.ProdutoDao
import com.example.milano.model.Produto
import com.github.clans.fab.FloatingActionButton

class ListarProdutosAsyncTasks(
    private val context: Context,
    var recyclerView: RecyclerView,
    var buttonFab: ImageButton?
): AsyncTask<String, String, String>() {

    private val TAG: String="ListarProdutoLog"

    var produtos = emptyList<Produto>()

    override fun doInBackground(vararg params: String?): String {

        var database: AppDatabase = AppDatabase.getAppDatabase(context)
        var dao: ProdutoDao = database.produtoDao()
        produtos = dao.all()

        return ""
    }


    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)

        if(produtos.isNotEmpty()){
            var adapter = ProdutoAdapter(context, produtos, buttonFab)
            recyclerView.adapter = adapter
        }
    }
}

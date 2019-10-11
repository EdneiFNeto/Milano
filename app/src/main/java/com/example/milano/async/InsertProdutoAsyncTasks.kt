package com.example.milano.async

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import androidx.room.Room
import com.example.milano.R
import com.example.milano.database.AppDatabase
import com.example.milano.database.dao.ProdutoDao
import com.example.milano.model.Produto
import com.example.milano.utils.MediaPlayerUtils


class InsertProdutoAsyncTasks(

    private val context: Context,
    private val produto: Produto
) : AsyncTask<String, String, String>() {


    private var all:List<Produto> = emptyList()
    private val TAG = "InsertAsyncLog"

    override fun doInBackground(vararg params: String?): String {

        var database: AppDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            context.resources.getString(R.string.db_name))
            .allowMainThreadQueries()
            .build()

        var dao: ProdutoDao = database.produtoDao()
        dao.inserir(produto)
        Log.e(TAG, "Produt nome ${produto.nome}")
        all = dao.all()
        return ""
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        MediaPlayerUtils.playSound(context)
        Toast.makeText(context, "Total itens ${all.size}", Toast.LENGTH_SHORT).show()
    }
}

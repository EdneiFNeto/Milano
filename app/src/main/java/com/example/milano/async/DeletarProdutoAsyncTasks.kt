package com.example.milano.async

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.widget.Toast
import com.example.milano.database.AppDatabase
import com.example.milano.database.dao.ProdutoDao
import com.example.milano.model.Produto
import com.example.milano.ui.MainActivity

class DeletarProdutoAsyncTasks(val context: Context):AsyncTask<String, String, String>() {
    override fun doInBackground(vararg params: String?): String {

        var database: AppDatabase = AppDatabase.getAppDatabase(context)
        var dao: ProdutoDao = database.produtoDao()
        dao.deletar()
        return ""
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        context.startActivity(Intent(context, MainActivity::class.java))
    }

}

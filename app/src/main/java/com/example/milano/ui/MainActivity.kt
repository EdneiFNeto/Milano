package com.example.milano.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.milano.R
import com.example.milano.adapter.CaixasAdapter
import com.example.milano.model.Caixas
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    val TAG = "MainActivityLog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        PermissionUtils.checkPermissionCamera(this, this)

        var actionBar = supportActionBar
        actionBar?.title = null
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_white)

        var navigation_view = findViewById<NavigationView>(R.id.navigation_view)
        var draw_navigation = findViewById<DrawerLayout>(R.id.draw_navigation)

        var recycleView_caixas = findViewById<RecyclerView>(R.id.recycleView_caixas)
        val caixa = Caixas("212121212121", "Documento", "21")
        val caixas = arrayListOf(caixa)
        var adapter = CaixasAdapter(this, caixas)
        recycleView_caixas.adapter = adapter
    }



//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_toolbar, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//
//        var id = item.itemId
//        if (id == R.id.item_scanner) {
//            startActivity(Intent(this@MainActivity, ScannerActivity::class.java))
//        }
//
//        if (id == R.id.item_delete) {
//            AlertDialogUtils.alertDeletarProduto(this)
//        }
//
//        return super.onOptionsItemSelected(item)
//    }

    override fun onResume() {
        super.onResume()
//        ListarProdutosAsyncTasks(this, recycleView, button_fab).execute()
    }
}

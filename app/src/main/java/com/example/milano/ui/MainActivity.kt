package com.example.milano.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.milano.R
import com.example.milano.async.DeletarProdutoAsyncTasks
import com.example.milano.async.ListarProdutosAsyncTasks
import com.example.milano.utils.AlertDialogUtils
import com.example.milano.utils.ResouscesUtils
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity() {

    val TAG = "MainActivityLog"
    private val REQUEST_CODE_PERMISSSION_CAMERA = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermissionCamera()
    }

    private fun checkPermissionCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_DENIED) {

                //ask for permission
                requestPermissions(
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_CODE_PERMISSSION_CAMERA
                )
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        var id = item.itemId
        if (id == R.id.item_scanner) {
            startActivity(Intent(this@MainActivity, ScannerActivity::class.java))
        }

        if (id == R.id.item_delete) {
            AlertDialogUtils.alertDeletarProduto(this)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        ListarProdutosAsyncTasks(this, recycleView, button_fab).execute()
    }
}

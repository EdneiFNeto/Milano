package com.example.milano.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.milano.R
import com.example.milano.adapter.BarCodeAdapter
import com.example.milano.utils.AlertDialogUtils
import kotlinx.android.synthetic.main.activity_scanner_infra_red.*
import java.lang.StringBuilder
import java.util.ArrayList

class ScannerInfraRedActivity : AppCompatActivity() {

    private var TAG = "ScannerInfraRedActivityLog"
    private lateinit var adapter: BarCodeAdapter
    private lateinit var str: StringBuilder
    private lateinit var editextCodigoDeBarra: EditText;
    private var listBarCode = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner_infra_red)
        editextCodigoDeBarra = findViewById<EditText>(R.id.edt_text_barcode_infra)
    }

    override fun onResume() {
        super.onResume()
        var recyclerView = findViewById<RecyclerView>(R.id.listView_barcode)
        adapter = BarCodeAdapter(this, listBarCode)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        if (menu != null) {

            var item: MenuItem = menu.add("Enviar codigo")
            item.setIcon(R.drawable.ic_send)
            item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)

            item.setOnMenuItemClickListener {

                var str = edt_text_barcode_infra.text.toString().split("\n")

                Log.e(TAG, "str ${str.size}")
                for (i in str) {
                    if(i.isNotEmpty())
                        listBarCode.add(i)
                }

                for (lista in listBarCode) {
                    Log.e(TAG, "Lista $lista")
                }

                Handler().post(Runnable {
                    adapter.notifyDataSetChanged()
                    editextCodigoDeBarra.setText("")
                })

                true
            }
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId
        return super.onOptionsItemSelected(item)
    }


}

package com.example.milano.ui

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.milano.R
import com.example.milano.async.InsertProdutoAsyncTasks
import com.example.milano.model.Produto
import com.example.milano.utils.AlertDialogUtils
import com.example.milano.utils.ResouscesUtils
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView


class ScannerActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {


    private lateinit var zXingScannerView: ZXingScannerView
    private  var TAG = "ScannerLog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        zXingScannerView = ZXingScannerView(this)
        zXingScannerView.setBorderColor(Color.DKGRAY)
        zXingScannerView.setLaserColor(Color.RED)
        setContentView(zXingScannerView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        var id = item.itemId
        if (id == R.id.item_edit) {

            val view = LayoutInflater.from(this).inflate(R.layout.activity_digitar_code_bar, null)
            var builder = AlertDialogUtils.dialog(
                this,
                ResouscesUtils.get(this, R.string.mensagem_digitar_cod).toUpperCase(),
                null, false, view)

            var numeroCadigoDeBarra = view.findViewById<EditText>(R.id.edt_text_digitar_code_bar)


            builder.setPositiveButton(ResouscesUtils.get(this, R.string.button_confirmar)) {
                    dialogInterface, _ ->

                if(numeroCadigoDeBarra.text.isNotEmpty()){
                    InsertProdutoAsyncTasks(this, Produto(nome = numeroCadigoDeBarra.text.toString())).execute()
                }else{
                    Toast.makeText(this, "Preencha o campo !", Toast.LENGTH_SHORT).show()
                }

                dialogInterface.dismiss()

            }

            builder.setNegativeButton(ResouscesUtils.get(this, R.string.button_cancelar)) {
                    dialogInterface, _ ->
                dialogInterface.dismiss()
            }

            var alertDialog = builder.create()
            alertDialog.show()

        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()

        if (zXingScannerView != null) {
            zXingScannerView.setResultHandler(this)
            zXingScannerView.flash = true
            zXingScannerView.startCamera()

        }
    }

    override fun handleResult(result: Result?) {
        if (result != null) {
            InsertProdutoAsyncTasks(this, Produto(nome = result.text)).execute()
            Handler().postDelayed({
                zXingScannerView.setResultHandler(this)
                zXingScannerView.flash = true
                zXingScannerView.startCamera()
            }, 1000)
        }
    }

    override fun onPause() {
        super.onPause()
        zXingScannerView.stopCamera()
    }
}

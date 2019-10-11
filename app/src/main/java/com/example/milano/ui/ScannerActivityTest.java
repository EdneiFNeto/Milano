package com.example.milano.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.milano.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivityTest extends AppCompatActivity implements
        ZXingScannerView.ResultHandler {

    private ZXingScannerView zXingScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        //recebe os dados passados pela intent
        zXingScannerView = new ZXingScannerView(this);
        zXingScannerView.setBorderColor(R.color.colorPrimary);
        zXingScannerView.setLaserColor(Color.RED);
        setContentView(zXingScannerView);

    }


    @Override
    protected void onResume() {
        super.onResume();

        if (zXingScannerView != null) {
            zXingScannerView.setResultHandler(this);
            zXingScannerView.startCamera();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        zXingScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {

        Toast.makeText(this, "Result "+result.getText(),
                Toast.LENGTH_SHORT).show();

        //new InsertProdutoAsyncTasks(this, new Produto(0, result.getText())).execute();
    }

}

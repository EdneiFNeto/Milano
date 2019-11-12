package com.example.milano.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milano.IScanInterface;
import com.example.milano.R;

public class ScannerTestActivity extends AppCompatActivity {

    private EditText edtTextBarcode;
    private static final String TAG = "ScannerTestActivityLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_test);
        edtTextBarcode = findViewById(R.id.edt_text_code_bar);
        RecyclerView listView  = findViewById(R.id.listview);

    }

    @Override
    protected void onResume() {
        super.onResume();

        edtTextBarcode.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() > 0){
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


}

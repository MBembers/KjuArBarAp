package com.example.kjuarbarap.ui.reader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kjuarbarap.R;

public class ReaderActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnScanBarcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        initViews();
    }

    private void initViews() {
        btnScanBarcode = findViewById(R.id.btnScanBarcode);
        btnScanBarcode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() ==  R.id.btnScanBarcode){
            startActivity(new Intent(ReaderActivity.this, ScannedBarcodeActivity.class));
        }
    }
}
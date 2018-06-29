package com.kivanc.streamify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ConnectActivity extends AppCompatActivity implements View.OnClickListener {

    Button scanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        scanButton = (Button) findViewById(R.id.scanButtonID);
        scanButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        IntentIntegrator mIntegrator = new IntentIntegrator(this);
        mIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        mIntegrator.setBarcodeImageEnabled(false);
        mIntegrator.setPrompt("Scan the QR Code Generated On Your Streamify PC Application.");
        mIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                Log.i("test", "cancelled");
            } else {
                Log.i("IP:Port", result.getContents().toString());
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                ConnectServerTask connectServerTask = new ConnectServerTask(this, result.getContents().toString());
                connectServerTask.execute();
            }
        }

        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

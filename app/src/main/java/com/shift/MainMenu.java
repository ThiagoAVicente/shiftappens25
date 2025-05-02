package com.shift;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.util.Log;
import android.nfc.NfcAdapter;
import android.nfc.NdefMessage;
import android.os.Parcelable;
import android.nfc.Tag;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity {

    private String TAG = "MainMenu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.main_menu);

        Button registerButton = findViewById(R.id.registerButton);
        Button saveToCardButton = findViewById(R.id.saveCardButton);
        Button optionsButton = findViewById(R.id.optionsButton);

        Persistent.nfc = NfcControl.create(this);

        if (Persistent.nfc != null) {
            Log.d("MainMenu", "NfcControl created successfully");
            // Optionally check NFC availability
            if (Persistent.nfc.isNfcAvailable()) {
                Log.d("MainMenu", "NFC is available and enabled");
                startNfcBackgroundService();
            } else {
                Log.d("MainMenu", "NFC is not available or disabled");
            }
        } else {
            Log.e("MainMenu", "Device does not support NFC");
        }

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenu.this, Reg01.class);
            startActivity(intent);
        });

        optionsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenu.this, Options.class);
            startActivity(intent);
        });
    }
    
    private void startNfcBackgroundService() {
        if (Persistent.nfc != null && Persistent.nfc.nfcBackgroundRunning()) {
            Log.d(TAG, "Starting NFC background service...");
            Intent intent = new Intent(this, NfcHceService.class);
            startService(intent);
        } else {
            Log.e(TAG, "Failed to start NFC background service. NFC is not available.");
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG,"Activity paused, NFC remains active.");
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        // Check if the intent is related to NFC
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Log.d(TAG, "NDEF message detected");
            // Handle incoming NDEF message from another device
            Parcelable[] rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMessages != null && rawMessages.length > 0) {
                NdefMessage message = (NdefMessage) rawMessages[0];
                boolean connected = Persistent.nfc.connectToDevice(null, message);
                Log.d(TAG, "Connection to peer device " + (connected ? "successful" : "failed"));
            }
        } else if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction()) ||
                NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
            Log.d(TAG, "NFC tag detected");
            // Retrieve the NFC tag from the intent
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if (tag != null && Persistent.nfc != null) {
                boolean connected = Persistent.nfc.connectToDevice(tag, null);
                Log.d(TAG, "Connection to NFC tag " + (connected ? "successful" : "failed"));
            }
        }
    }
}

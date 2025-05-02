package com.shift;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Reg04 extends AppCompatActivity {

    private static final String TAG = "Reg04";

    private NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reg04);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize NFC Adapter
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Log.e(TAG, "NFC is not supported on this device.");
        } else if (!nfcAdapter.isEnabled()) {
            Log.e(TAG, "NFC is disabled. Please enable it in settings.");
        } else {
            Log.d(TAG, "NFC is available and enabled.");
        }

        LinearLayout mainLayout = findViewById(R.id.main);
        Button nextActivityButton = findViewById(R.id.next);
        Button goBackButton = findViewById(R.id.back);
        Button addAllergieButton = findViewById(R.id.add);
        EditText textAllergie = findViewById(R.id.name);
        LinearLayout allergiesContainer = findViewById(R.id.allergiesDiv);

        nextActivityButton.setOnClickListener(v -> {
            Intent intent = new Intent(Reg04.this, Reg05.class);
            startActivity(intent);

            int nmrFilhos = allergiesContainer.getChildCount();

            for (int i = 1; i < nmrFilhos; i++) {
                TextView viewText = (TextView) allergiesContainer.getChildAt(i);
                String alergia = viewText.getText().toString();
            }
        });

        goBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(Reg04.this, Reg03.class);
            startActivity(intent);
        });

        addAllergieButton.setOnClickListener(v -> {
            String newAllergie = textAllergie.getText().toString();
            TextView newText = new TextView(this);
            newText.setText(newAllergie);
            newText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            newText.setTextColor(Color.BLACK);
            newText.setGravity(Gravity.START);

            allergiesContainer.addView(newText);
        });

    }
}
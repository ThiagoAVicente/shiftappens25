package com.example.shift;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ScanView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_view);

        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener( v -> {
            startScan();
        });
    }

    private void startScan(){
        System.out.println("Teste");
    }
}

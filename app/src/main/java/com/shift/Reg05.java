package com.sshift;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Reg05 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reg05);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button nextActivityButton = findViewById(R.id.next);
        Button goBackButton = findViewById(R.id.back);

        nextActivityButton.setOnClickListener(v -> {
            Intent intent = new Intent(Reg05.this, Reg04.class);
            startActivity(intent);
        });

        goBackButton.setOnClickListener(v -> {
            Intent intent =  new Intent(Reg05.this, Reg04.class);
            startActivity(intent);
        });
    }
}
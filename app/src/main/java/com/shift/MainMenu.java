package com.shift;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.main_menu);

        Button registerButton = findViewById(R.id.registerButton);
        Button saveToCardButton = findViewById(R.id.saveCardButton);
        Button optionsButton = findViewById(R.id.optionsButton);

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenu.this, Reg01.class);
            startActivity(intent);
        });

        optionsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenu.this, Options.class);
            startActivity(intent);
        });
    }
}

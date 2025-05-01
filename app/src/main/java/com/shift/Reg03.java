package com.shift;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Reg03 extends AppCompatActivity {

    private Float height;
    private Float weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reg03);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences prefs = getSharedPreferences("preferences", MODE_PRIVATE);
        height = prefs.getFloat("height", 0);
        weight = prefs.getFloat("weight", 0);

        EditText inputHeight = findViewById(R.id.height);
        EditText inputWeight = findViewById(R.id.weight);
        Button nextAvtivityButton = findViewById(R.id.next);
        Button goBackButton = findViewById(R.id.back);

        inputHeight.setText(String.valueOf(height));
        inputWeight.setText(String.valueOf(weight));

        nextAvtivityButton.setOnClickListener(v -> {

            height = Float.valueOf(inputHeight.getText().toString());
            weight = Float.valueOf(inputWeight.getText().toString());

            SharedPreferences.Editor editor = prefs.edit();
            editor.putFloat("height", height);
            editor.putFloat("weight", weight);
            editor.apply();


            Intent intent = new Intent(Reg03.this, Reg04.class);
            startActivity(intent);
        });

        goBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(Reg03.this, Reg02.class);
            startActivity(intent);
        });
    }
}
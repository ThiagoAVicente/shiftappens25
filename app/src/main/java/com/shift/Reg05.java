package com.shift;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Reg05 extends AppCompatActivity {

    private int radioID;

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

        SharedPreferences prefs = getSharedPreferences("preferences", MODE_PRIVATE);
        radioID = prefs.getInt("radioID", -1);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        Button nextActivityButton = findViewById(R.id.next);
        Button goBackButton = findViewById(R.id.back);

        radioGroup.check(radioID);

        nextActivityButton.setOnClickListener(v -> {

            radioID = radioGroup.getCheckedRadioButtonId();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("radioID", radioID);
            editor.apply();

            Intent intent = new Intent(Reg05.this, Reg04.class);
            startActivity(intent);
        });

        goBackButton.setOnClickListener(v -> {
            Intent intent =  new Intent(Reg05.this, Reg04.class);
            startActivity(intent);
        });
    }
}
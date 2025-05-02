package com.shift;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.NumberPicker;
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

        NumberPicker bloodPicker = findViewById(R.id.bloodPicker);
        NumberPicker plusMinusPicker = findViewById(R.id.plusMinusPicker);
        Button nextActivityButton = findViewById(R.id.next);
        Button goBackButton = findViewById(R.id.back);

        String[] itemsBlood = {"A", "B", "AB", "O"};
        String[] plusMinus = {"+", "-", "+", "-"};

        bloodPicker.setMinValue(0);
        bloodPicker.setMaxValue(itemsBlood.length - 1);
        bloodPicker.setDisplayedValues(itemsBlood);

        plusMinusPicker.setMinValue(0);
        plusMinusPicker.setMaxValue(plusMinus.length-1);
        plusMinusPicker.setDisplayedValues(plusMinus);
        //ir buscar ao localStorage as definições
        bloodPicker.setValue(prefs.getInt("bloodType", 0));
        plusMinusPicker.setValue(prefs.getInt("plusMinus", 0));

        nextActivityButton.setOnClickListener(v -> {

            int bloodPickerIndex = bloodPicker.getValue();
            int plusMinusIndex = plusMinusPicker.getValue() % 2;

            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("bloodType", bloodPickerIndex);
            editor.putInt("plusMinus", plusMinusIndex);
            editor.apply();

            Intent intent = new Intent(Reg05.this, Reg06.class);
            startActivity(intent);
        });

        goBackButton.setOnClickListener(v -> {
            Intent intent =  new Intent(Reg05.this, Reg04.class);
            startActivity(intent);
        });
    }
}
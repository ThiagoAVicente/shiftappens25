package com.shift;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Reg02 extends AppCompatActivity {

    private int day;
    private int month;
    private int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reg02);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences prefs = getSharedPreferences("preferences", MODE_PRIVATE);
        day = prefs.getInt("date_day", 1);
        month = prefs.getInt("date_month", 5);
        year = prefs.getInt("date_year", 2025);

        Button nextActivityButton = findViewById(R.id.next);
        Button goBackButton = findViewById(R.id.back);
        DatePicker datePicker = findViewById(R.id.datePicker);

        datePicker.updateDate(year, month, day);

        nextActivityButton.setOnClickListener(v -> {

            day = datePicker.getDayOfMonth();
            month = datePicker.getMonth();
            year = datePicker.getYear();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("date_day", day);
            editor.putInt("date_month", month);
            editor.putInt("date_year", year);
            editor.apply();

            Intent intent = new Intent(Reg02.this, Reg03.class);
            startActivity(intent);
        });

        goBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(Reg02.this, Reg01.class);
            startActivity(intent);
        });
    }
}
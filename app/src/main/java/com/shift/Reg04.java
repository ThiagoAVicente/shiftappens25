package com.shift;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Reg04 extends AppCompatActivity {

    private StringBuilder sb;
    private String allergies;

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

        LinearLayout mainLayout = findViewById(R.id.main);
        Button nextActivityButton = findViewById(R.id.next);
        Button goBackButton = findViewById(R.id.back);
        Button addAllergieButton = findViewById(R.id.add);
        EditText textAllergie = findViewById(R.id.name);
        LinearLayout allergiesContainer = findViewById(R.id.allergiesDiv);

        SharedPreferences prefs = getSharedPreferences("preferences", MODE_PRIVATE);
        allergies = prefs.getString("allergies", "");

        String[] allergiesArray = allergies.split(";");

        for (String a : allergiesArray) {
            TextView newText = new TextView(this);
            newText.setText(a);
            newText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            newText.setTextColor(Color.WHITE);
            newText.setGravity(Gravity.CENTER);

            allergiesContainer.addView(newText);
        }

        sb = new StringBuilder();

        nextActivityButton.setOnClickListener(v -> {

            int nmrFilhos = allergiesContainer.getChildCount();

            for(int i = 1; i < nmrFilhos ; i++){
                TextView viewText = (TextView) allergiesContainer.getChildAt(i);
                String alergia = viewText.getText().toString();
                sb.append(alergia);
                sb.append(";");
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
            allergies = sb.toString();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("allergies", allergies);
            editor.apply();

            Intent intent = new Intent(Reg04.this, Reg05.class);
            startActivity(intent);
        });

        goBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(Reg04.this, Reg03.class);
            startActivity(intent);
        });

        addAllergieButton.setOnClickListener(v -> {
            String newAllergie = textAllergie.getText().toString().trim();
            if (!newAllergie.isEmpty()) {

                LinearLayout rowLayout = new LinearLayout(this);
                rowLayout.setOrientation(LinearLayout.HORIZONTAL);
                rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                rowLayout.setPadding(16, 16, 16, 16);
                rowLayout.setGravity(Gravity.CENTER);

                TextView allergyText = new TextView(this);
                allergyText.setText(newAllergie);
                allergyText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                allergyText.setTextColor(Color.WHITE);
                allergyText.setSingleLine(false);
                allergyText.setMaxLines(5);
                allergyText.setEllipsize(null);
                allergyText.setGravity(Gravity.CENTER);

                LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                allergyText.setLayoutParams(textParams);

                Button deleteButton = new Button(this,  null, com.google.android.material.R.attr.borderlessButtonStyle);
                deleteButton.setText("X");
                deleteButton.setTextColor(Color.RED);
                deleteButton.setOnClickListener(delView -> {
                    allergiesContainer.removeView(rowLayout);
                });

                rowLayout.addView(allergyText);
                rowLayout.addView(deleteButton);

                allergiesContainer.addView(rowLayout);

                textAllergie.setText("");
            }
        });

    }
}
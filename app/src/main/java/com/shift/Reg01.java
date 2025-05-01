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

public class Reg01 extends AppCompatActivity {

    private String nameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reg01);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText name = findViewById(R.id.name);

        SharedPreferences prefs = getSharedPreferences("preferences", MODE_PRIVATE);
        nameInput = prefs.getString("nameInput", "");
        name.setText(nameInput);

        Button nextActivityButton = findViewById(R.id.next);

        nextActivityButton.setOnClickListener(v -> {

            nameInput = name.getText().toString();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("nameInput", nameInput);
            editor.apply();

            Intent intent = new Intent(Reg01.this, Reg02.class);
            startActivity(intent);
        });
    }

    public String getNameInput() {
        return nameInput;
    }
}
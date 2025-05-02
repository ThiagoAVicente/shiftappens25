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


public class Reg06 extends AppCompatActivity {

    private String ccNumber;
    private String snsNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reg06);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        SharedPreferences prefs = getSharedPreferences("preferences", MODE_PRIVATE);
        ccNumber = prefs.getString("cc", "");
        snsNumber = prefs.getString("sns", "");


        EditText cc = findViewById(R.id.cc);
        EditText sns = findViewById(R.id.sns);
        Button nextActivityButton = findViewById(R.id.next);
        Button goBackButton = findViewById(R.id.back);

        cc.setText(ccNumber);
        sns.setText(snsNumber);

        nextActivityButton.setOnClickListener(v -> {

            ccNumber = cc.getText().toString();
            snsNumber = sns.getText().toString();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("cc", ccNumber);
            editor.putString("sns", snsNumber);
            editor.apply();

            Intent intent = new Intent(Reg06.this, Reg07.class);
            startActivity(intent);
        });

        goBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(Reg06.this, Reg05.class);
            startActivity(intent);
        });
    }
}
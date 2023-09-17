package com.example.lab2_iot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        Button ir = findViewById(R.id.cronometro);
        ir.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(inicio.this, cronometro.class);
                startActivity(intent);
            }
        });

        Button ir2 = findViewById(R.id.contador);
        ir2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(inicio.this, contador.class);
                startActivity(intent);
            }
        });


    }
    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(inicio.this, "Estas en el Menu Principal",Toast.LENGTH_SHORT).show();
    }
}
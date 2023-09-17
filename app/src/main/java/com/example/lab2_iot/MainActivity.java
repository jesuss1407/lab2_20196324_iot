package com.example.lab2_iot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button ir = findViewById(R.id.registro);
        ir.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, registro.class);
                startActivity(intent);
            }
        });

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        boolean siTiene = activeNetworkInfo != null && activeNetworkInfo.isConnected();

        if (siTiene){
            Toast.makeText(MainActivity.this, "Estas conectado a Internet",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "No estas conectado a Internet",Toast.LENGTH_SHORT).show();
        }

    }
}
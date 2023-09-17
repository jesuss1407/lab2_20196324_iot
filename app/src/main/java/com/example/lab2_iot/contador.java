package com.example.lab2_iot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class contador extends AppCompatActivity {

    private Button btnGeneral;
    private TextView cuenta;
    private int contador = 104;
    private boolean counting = false;
    private int aumentoVelocidad = 1;
    private final int tiempoInicial = 10000;

    private Thread counterThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contador);

        ImageView ir = findViewById(R.id.imageView);
        ir.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(contador.this, inicio.class);
                startActivity(intent);
            }
        });

        btnGeneral = findViewById(R.id.iniciar);
        cuenta = findViewById(R.id.cuenta);

        btnGeneral.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!counting) {
                    iniciarConta();
                } else {
                    detenerConta();
                    aumentoVelocidad++;
                }
            }
        });

    }
    public void iniciarConta(){
        btnGeneral.setText("Detener");
        counting = true;
        counterThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (counting && contador < 226) {
                    try {
                        Thread.sleep(tiempoInicial / aumentoVelocidad);
                        contador++;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cuenta.setText(String.valueOf(contador));
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (contador >= 226) {
                    Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                    if (vibrator != null) {
                        vibrator.vibrate(3000);
                    }
                }
            }
        });
        counterThread.start();

    }

    public void detenerConta(){
        counting = false;
        btnGeneral.setText("Iniciar");
        if (counterThread != null) {
            counterThread.interrupt();
            counterThread = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(contador.this, "Estas en el Contador",Toast.LENGTH_SHORT).show();
    }
}
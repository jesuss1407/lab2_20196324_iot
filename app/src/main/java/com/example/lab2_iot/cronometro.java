package com.example.lab2_iot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class cronometro extends AppCompatActivity {

    private TextView tiempo;
    private Button iniciar, pausar, despausar, limpiar;
    private boolean isRunning = false;
    private long elapsedTime;
    private long startTime;
    private SharedPreferences preferences;

    private TimerThread timerThread;
    private int milliseconds = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro);

        iniciar = findViewById(R.id.iniciar);
        iniciar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                iniciarCrono();

            }
        });
        pausar = findViewById(R.id.pausar);
        pausar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                pausarCrono();

            }
        });
        despausar = findViewById(R.id.despausar);
        despausar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                despausarCrono();

            }
        });
        limpiar = findViewById(R.id.limpiar);
        limpiar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                limpiarCrono();

            }
        });
        tiempo = findViewById(R.id.tiempo);

        ImageView ir = findViewById(R.id.imageView);
        ir.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(cronometro.this, inicio.class);
                startActivity(intent);
            }
        });

        if (savedInstanceState != null) {
            isRunning = savedInstanceState.getBoolean("isRunning");
            startTime = savedInstanceState.getLong("startTime");
            if (isRunning) {
                iniciarCrono();
            }
        }

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        elapsedTime = preferences.getLong("elapsedTime", 0);
        startTime = preferences.getLong("startTime", 0);

    }


    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isRunning", isRunning);
        outState.putLong("startTime", startTime);
    }


    private void iniciarCrono() {
        if (!isRunning) {
            if (elapsedTime == 0) {
                startTime = SystemClock.elapsedRealtime();
            } else {
                startTime = SystemClock.elapsedRealtime() - elapsedTime;
            }

            isRunning = true;
            timerThread = new TimerThread();
            timerThread.start();
            iniciar.setEnabled(false);
            pausar.setEnabled(true);
            despausar.setEnabled(false);
            limpiar.setEnabled(false);


            SharedPreferences.Editor edit = preferences.edit();
            edit.putLong("elapsedTime", elapsedTime);
            edit.putLong("startTime", startTime);
            edit.apply();
        }
    }

    private void pausarCrono() {
        if (isRunning) {
            isRunning = false;
            iniciar.setEnabled(false);
            pausar.setEnabled(false);
            despausar.setEnabled(true);
            limpiar.setEnabled(true);

            elapsedTime = SystemClock.elapsedRealtime() - startTime;
            timerThread.interrupt();

            SharedPreferences.Editor edit = preferences.edit();
            edit.putLong("elapsedTime", elapsedTime);
            edit.putLong("startTime", startTime);
            edit.apply();
        }
    }

    private void despausarCrono() {
        if (!isRunning) {
            startTime = SystemClock.elapsedRealtime() - elapsedTime;

            isRunning = true;
            timerThread = new TimerThread();
            timerThread.start();

            iniciar.setEnabled(false);
            pausar.setEnabled(true);
            despausar.setEnabled(false);
            limpiar.setEnabled(false);
        }
    }

    private void limpiarCrono() {
        isRunning = false;
        elapsedTime = 0;
        startTime = SystemClock.elapsedRealtime();
        milliseconds = 0;
        tiempo.setText("00:00.0");
        iniciar.setEnabled(true);
        pausar.setEnabled(false);
        despausar.setEnabled(false);
        limpiar.setEnabled(false);

        if (timerThread != null && timerThread.isAlive()) {
            timerThread.interrupt();
        }


        SharedPreferences.Editor edit = preferences.edit();
        edit.putLong("elapsedTime", elapsedTime);
        edit.putLong("startTime", startTime);
        edit.apply();
    }


    private class TimerThread extends Thread {
        @Override
        public void run() {
            while (isRunning) {
                long currentTime = SystemClock.elapsedRealtime();
                long elapsedTime = currentTime - startTime;
                milliseconds = (int) (elapsedTime / 100) % 10;
                elapsedTime /= 1000;
                final int seconds = (int) (elapsedTime % 60);
                final int minutes = (int) (elapsedTime / 60);
                final String time = String.format(Locale.getDefault(), "%02d:%02d.%01d", minutes, seconds, milliseconds);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tiempo.setText(time);
                    }
                });

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(cronometro.this, "Estas en el Cronometro",Toast.LENGTH_SHORT).show();
    }
}
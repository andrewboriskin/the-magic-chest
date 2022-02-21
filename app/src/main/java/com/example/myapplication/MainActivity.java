package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.security.KeyStore;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {

    private WebView webviewhtml, webviewhtml2;
    Button btnBack, btnMenu, btnNext, btnZad, btnVopr;
    int idStr, idZad, moment = 0;
    MediaPlayer mediaPlayer, fairyTales;
    ImageButton Play, Pause, Stop;
    LinearLayout talesLayout;
    Boolean idPlay = false, idPause = false, idWeb = false, playidStr = false, idPlayerVisible = false;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        webviewhtml = findViewById(R.id.webviewhtml);
        webviewhtml2 = findViewById(R.id.webviewhtml2);
        btnBack = findViewById(R.id.onBack);
        btnMenu = findViewById(R.id.onMenu);
        btnNext = findViewById(R.id.onNext);
        btnZad = findViewById(R.id.buttonzad);
        btnVopr = findViewById(R.id.buttonvopr);
        Pause = findViewById(R.id.imgBtnPause);
        Play = findViewById(R.id.imgBtnPlay);
        Stop = findViewById(R.id.imgBtnStop);
        talesLayout = findViewById(R.id.player_layout);

        talesLayout.setVisibility(LinearLayout.GONE);
        webviewhtml2.setVisibility(WebView.GONE);

        webviewhtml.setVerticalScrollBarEnabled(false);
        webviewhtml.setHorizontalScrollBarEnabled(false);
        webviewhtml.setInitialScale(150);
        webviewhtml.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

        final Intent intent = getIntent();
        idStr = intent.getIntExtra("idStr",1);

        LoadHTML();


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.onBack:
                        if ((idStr - 1) % 5 == 0) {
                            idStr = idStr - 5;
                        } else
                            idStr = idStr - 1;

                        if (idPlayerVisible == true) {
                            talesLayout.setVisibility(LinearLayout.GONE);
                            idPlayerVisible = false;
                        }

                        if (idPlay == true){
                            fairyTales.stop();
                            Toast.makeText(MainActivity.this, "Стоп", LENGTH_SHORT).show();
                        }
                        if (idWeb == true){
                            webviewhtml2.setVisibility(WebView.GONE);
                            idWeb = false;
                        }
                        if (playidStr == true){
                            mediaPlayer.stop();
                            playidStr = false;
                        }
                        LoadHTML();
                        break;
                    case R.id.onMenu:
                        if (idPlay == true){
                            fairyTales.stop();
                            Toast.makeText(MainActivity.this, "Стоп", LENGTH_SHORT).show();
                        }
                        if (playidStr == true){
                            mediaPlayer.stop();
                            playidStr = false;
                        }
                        Intent intent1 = new Intent(MainActivity.this, StartActivity.class);
                        startActivity(intent1);

                        break;
                    case R.id.onNext:
                        btnBack.setEnabled(true);
                        if (idPlay == true){
                            fairyTales.stop();
                            Toast.makeText(MainActivity.this, "Стоп", LENGTH_SHORT).show();
                        }
                        if (idPlayerVisible == true) {
                            talesLayout.setVisibility(LinearLayout.GONE);
                            idPlayerVisible = false;
                        }
                        if (playidStr == true){
                            mediaPlayer.stop();
                            playidStr = false;
                        }

                            idStr = idStr + 1;
                            LoadHTML();

                        if (idWeb == true) {
                            webviewhtml2.setVisibility(WebView.GONE);
                            idWeb = false;
                        }

                    break;
                    case R.id.buttonzad:
                        playidStrSound(idStr);
                        idZad = 0;
                        btnVopr.setEnabled(true);
                        if (idStr >= 31 & idStr <= 35 & idZad == 2) {
                            LoadHTML();
                        }
                        if (idPlay == true){
                            fairyTales.stop();
                            Toast.makeText(MainActivity.this, "Стоп", LENGTH_SHORT).show();
                        }
                        talesLayout.setVisibility(LinearLayout.GONE);
                        if(idWeb == true) {
                            webviewhtml2.setVisibility(WebView.GONE);
                            idWeb = false;
                        }
                        break;
                    case R.id.buttonvopr:
                        idZad++;
                        playidStridZadSound(idStr, idZad);

                        if (idStr >= 31 & idStr <= 35 & idZad == 2) {
                            webviewhtml2.setVisibility(WebView.VISIBLE);
                            LoadHTML_for7();
                        }

                        if (idStr >= 26 & idStr <= 30 & idZad == 2) {
                            talesLayout.setVisibility(LinearLayout.VISIBLE);
                            idPlayerVisible = true;
                            Play.setEnabled(true);
                        }
                        break;
                    case R.id.imgBtnPause:
                        moment = fairyTales.getCurrentPosition();
                        fairyTales.pause();
                        idPause = true;
                        idPlay = false;
                        Pause.setEnabled(false);
                        Play.setEnabled(true);
                        Stop.setEnabled(true);
                        break;
                    case R.id.imgBtnPlay:
                        if (idPause == true) {
                            fairyTales.seekTo(moment);
                            fairyTales.start();
                            idPause = false;
                        }
                        else {
                            FairyTales(idStr);
                            fairyTales.start();
                        }

                        idPlay = true;
                        Play.setEnabled(false);
                        Pause.setEnabled(true);
                        Stop.setEnabled(true);
                        break;
                    case R.id.imgBtnStop:
                        fairyTales.stop();
                        Play.setEnabled(true);
                        Stop.setEnabled(false);
                        Pause.setEnabled(true);
                        break;
                }
            }
        };

        btnBack.setOnClickListener(onClickListener);
        btnMenu.setOnClickListener(onClickListener);
        btnNext.setOnClickListener(onClickListener);
        btnZad.setOnClickListener(onClickListener);
        btnVopr.setOnClickListener(onClickListener);
        Pause.setOnClickListener(onClickListener);
        Play.setOnClickListener(onClickListener);
        Stop.setOnClickListener(onClickListener);

    }

    private void LoadHTML() {
        webviewhtml.clearView();
        webviewhtml.loadUrl("file:///android_asset/" + idStr + ".html");
        idZad = 0;
        playidStrSound(idStr);
        btnVopr.setEnabled(true);
        if ((idStr - 1) % 5 == 0) {
            btnBack.setText("Пред. раздел");
        }
        else
            btnBack.setText("Назад");

        if (idStr % 5 == 0) {
            btnNext.setText("След. раздел");
        }
        else
            btnNext.setText("Далее");

        if (idStr == 1) {
            btnBack.setVisibility(Button.GONE);
        }
        else
            btnBack.setVisibility(Button.VISIBLE);

        if (idStr == 50) {
            btnNext.setVisibility(Button.GONE);
        }
        else
            btnNext.setVisibility(Button.VISIBLE);

    }

    private void LoadHTML_for7() {
        webviewhtml2.clearView();
        webviewhtml2.loadUrl("file:///android_asset/" + idStr + "_2.html");
        webviewhtml2.setVerticalScrollBarEnabled(false);
        webviewhtml2.setHorizontalScrollBarEnabled(false);
        webviewhtml2.setInitialScale(150);
        webviewhtml2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });
        idWeb = true;
    }


    private void playidStrSound(int idStr) {
        try {
            AssetFileDescriptor afd = getAssets().openFd("sounds/" + idStr + ".mp3");
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mediaPlayer.prepare();
            mediaPlayer.start();
            playidStr = true;
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void playidStridZadSound(int idStr, int idZad) {
        try {
            AssetFileDescriptor afd = getAssets().openFd("sounds/" + idStr + "_" + idZad + ".mp3");
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mediaPlayer.prepare();
            mediaPlayer.start();
            playidStr = true;
        } catch (Exception ex) {
            Toast.makeText(this, "Конец", Toast.LENGTH_LONG).show();
            btnVopr.setEnabled(false);
        }
    }

    private void FairyTales(int idStr){
        try {
            AssetFileDescriptor afd = getAssets().openFd("fairytales/" + idStr + ".mp3");
            fairyTales = new MediaPlayer();
            fairyTales.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            fairyTales.prepare();
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


}

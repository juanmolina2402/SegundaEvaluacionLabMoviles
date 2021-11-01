package com.example.guia7;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imgPlay = findViewById(R.id.imvPlay);
        ImageView imgScore = findViewById(R.id.imvScore);
        ImageView imgHelp = findViewById(R.id.imvHelp);
        ImageView imgConfig = findViewById(R.id.imvConfig);
        ImageView imgDatos = findViewById(R.id.imvDatos);

        imgPlay.setOnClickListener(aux -> {
            if(validate()){
                startActivity(new Intent(MainActivity.this, PlayActivity.class));
            }else{
                Toast.makeText(MainActivity.this, "Ingrese un usuario en configuraciones", Toast.LENGTH_SHORT).show();
            }
        });

        imgScore.setOnClickListener(aux -> {
            if(validate()){
                startActivity(new Intent(MainActivity.this, ScoreActivity.class));
            }else{
                Toast.makeText(MainActivity.this, "Ingrese un usuario en configuraciones", Toast.LENGTH_SHORT).show();
            }
        });

        imgHelp.setOnClickListener(aux -> {
            if(validate()){
                startActivity(new Intent(MainActivity.this, HelpActivity.class));
            }else{
                Toast.makeText(MainActivity.this, "Ingrese un usuario en configuraciones", Toast.LENGTH_SHORT).show();
            }
        });

        imgConfig.setOnClickListener(aux -> {
            startActivity(new Intent(MainActivity.this, ConfigActivity.class));
        });

        imgDatos.setOnClickListener(aux -> {
            startActivity(new Intent(MainActivity.this, DatosActivity.class));
        });
    }

    private boolean validate(){
        boolean  b= false;
        File f = new File("/data/data/com.example.guia7/shared_prefs/configuration.xml");
        if(f.exists()){
            b = true;
        }
        return b;
    }
}
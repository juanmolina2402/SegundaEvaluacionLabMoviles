package com.example.guia7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PlayActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private TextView tvUser, tvIntent;
    private EditText edtNumber;
    private int i = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tvUser = findViewById(R.id.tvUser);
        tvIntent = findViewById(R.id.tvIntent);
        edtNumber = findViewById(R.id.edtNumber);
        addToTextView();

        Button btnAccept = findViewById(R.id.btnAccept);
        btnAccept.setOnClickListener(aux -> {
            play();
        });
    }

    private void addToTextView(){
        sharedPreferences = getSharedPreferences("configuration", MODE_PRIVATE);
        if(sharedPreferences != null){
            tvUser.setText(sharedPreferences.getString("USER", ""));
        }
    }

    private void play(){
        int x = Integer.parseInt(edtNumber.getText().toString());

        if(x > 0 && x < 11){
            sharedPreferences = getSharedPreferences("configuration", MODE_PRIVATE);
            if(edtNumber.getText().toString().equals(sharedPreferences.getString("NUMBER", ""))){
                int numero = (int) (Math.random() * 10) + 1;
                int score = Integer.parseInt(sharedPreferences.getString("SCORE", "")) + i;
                Toast.makeText(PlayActivity.this, "Felicidades, ¡has ganado!", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editorConfig = sharedPreferences.edit();
                editorConfig.putString("NUMBER", String.valueOf(numero));
                editorConfig.putString("SCORE", String.valueOf(score));
                editorConfig.commit();
                finish();
            }else{
                int k = Integer.parseInt(edtNumber.getText().toString());
                int j = Integer.parseInt(sharedPreferences.getString("NUMBER", ""));
                i--;
                tvIntent.setText("Intentos : " + i);
                edtNumber.setText("");
                if(k > j){
                    Toast.makeText(PlayActivity.this, "El numero oculto es menor", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(PlayActivity.this, "El numero oculto es mayor", Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            Toast.makeText(PlayActivity.this, "Número fuera de rango", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
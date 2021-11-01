package com.example.guia7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {
    private TextView tvName, tvScore;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tvName = findViewById(R.id.tvName);
        tvScore = findViewById(R.id.tvScore);
        addToTextView();
    }

    private void addToTextView(){
        sharedPreferences = getSharedPreferences("configuration", MODE_PRIVATE);
        tvName.setText(sharedPreferences.getString("USER", ""));
        tvScore.setText(sharedPreferences.getString("SCORE", ""));
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
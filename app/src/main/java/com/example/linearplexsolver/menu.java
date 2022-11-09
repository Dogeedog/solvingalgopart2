package com.example.linearplexsolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void linearreg(View h){
        Intent j = new Intent(this, MainActivity.class);
        startActivity(j);
    }

    public void doe(View h){
        Intent j = new Intent(this, MainActivity.class);
    }
}
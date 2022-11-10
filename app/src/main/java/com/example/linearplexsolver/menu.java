package com.example.linearplexsolver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class menu extends AppCompatActivity {

    BottomNavigationView btmview;
    NavController navsel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btmview = findViewById(R.id.btmv);
        navsel = Navigation.findNavController(this, R.id.fragContainer);

        NavigationUI.setupWithNavController(btmview,navsel);


    }

}
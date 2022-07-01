package com.example.linearplexsolver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    NumberPicker choices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        choices = (NumberPicker)findViewById(R.id.numberepicker);
        String[] choicesstrings = {
                "1", "2"
        };
        choices.setDisplayedValues(choicesstrings);
        choices.setMinValue(0);
        choices.setMaxValue(choicesstrings.length - 1);
        ((TextView)findViewById(R.id.textView)).setText(Html.fromHtml("y=B<sub><small>o</small></sub>+B<sub><small>1</small></sub>X<sub><small>1</small></sub>+B<sub><small>2</small></sub>X<sub><small>2</small></sub>"));
        /*AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        *
        *
        * */

    }

    public void solver(View h){
        //inicializar una nueva actividad
        Intent j = new Intent(this, solved.class);
        startActivity(j);
    }
}
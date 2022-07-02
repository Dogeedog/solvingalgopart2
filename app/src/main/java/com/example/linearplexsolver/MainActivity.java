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
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    NumberPicker choices;
    EditText nvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        choices = findViewById(R.id.numberepicker);
        String[] choicesstrings = {
                "1", "2"
        }; //string de 1 y 2 ?XD
        ((TextView)findViewById(R.id.textView)).setText(Html.fromHtml("y=B<sub><small>o</small></sub>+B<sub><small>1</small></sub>" +
                "X<sub><small>1</small></sub>")); //asigna texto a la view donde va el modelo

        choices.setDisplayedValues(choicesstrings);
        choices.setMinValue(0); //index value 0:"1" - 1:"2"
        choices.setMaxValue(choicesstrings.length - 1); //no idea
        /*AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //no night mode
        *
        *

         */
        //cambia el modelo en base a la cantidad de variables
        choices.setOnValueChangedListener((picker, oldVal, newVal) -> {
            int OneOrTwoVar = choices.getValue();
            if (OneOrTwoVar == 0){
                //Para 1 entrada y 1 de respuesta
                ((TextView)findViewById(R.id.textView)).setText(Html.fromHtml("y=B<sub><small>o</small></sub>+B<sub><small>1</small></sub>" +
                        "X<sub><small>1</small></sub>"));
                nvalue = findViewById(R.id.nnumber);
                nvalue.setOnFocusChangeListener((v, hasFocus) -> {
                    if(!hasFocus) {
                        String nvaluestr = nvalue.getText().toString(); //obtener cantidad de filas
                        int nvalueint;
                        try {
                            nvalueint = Integer.parseInt(nvaluestr);
                            if (nvalueint >= 4){
                                /* to do
                                Toast.makeText(MainActivity.this, "my Message", Toast.LENGTH_LONG).show();
                                LinearLayout layout = (LinearLayout) findViewById(R.id.CAMBIARLAYOUT); // combinar 1 linear layout vertical con 4 horizontales

                                String[] items = {"item 1", "item 2", "item 3", "item 4", "item 5"}; // hacerlo con el n value

                                for (int i = 0; i < items.length; i++) {
                                    Button btn = new Button(this);
                                    btn.setText(items[i]);
                                    btn.setId(i);
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT);

                                    params.setMargins(10, 10, 10, 10);
                                    btn.setLayoutParams(params);
                                    layout.addView(btn);
                                }
                                */

                            }else{
                                return;
                            }
                        }
                        catch(NumberFormatException ex) {
                            return;
                        }
                    }
                });
            } else {
                //Para 2 entradas y una respuesta
                ((TextView)findViewById(R.id.textView)).setText(Html.fromHtml("y=B<sub><small>o</small></sub>+B<sub><small>1</small></sub>" +
                        "X<sub><small>1</small></sub>+B<sub><small>2</small></sub>X<sub><small>2</small></sub>"));
                nvalue = findViewById(R.id.nnumber);
                nvalue.setOnFocusChangeListener((v, hasFocus) -> {
                    if(!hasFocus) {
                        String nvaluestr = nvalue.getText().toString(); //obtener cantidad de filas
                        int nvalueint;
                        try {
                            nvalueint = Integer.parseInt(nvaluestr);
                            if (nvalueint >= 4){
                                Toast.makeText(MainActivity.this, "my Message", Toast.LENGTH_LONG).show();
                            }else{
                                return;
                            }
                        }
                        catch(NumberFormatException ex) {
                            return;
                        }
                    }
                });
            }
        });


    }

    public void solver(View h){
        //inicializar una nueva actividad
        Intent j = new Intent(this, solved.class);
        startActivity(j);
    }
}
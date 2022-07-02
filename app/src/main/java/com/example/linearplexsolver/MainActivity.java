package com.example.linearplexsolver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
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
                "0", "1", "2"
        }; //string de 1 y 2 ?XD
        ((TextView)findViewById(R.id.textView)).setText(Html.fromHtml("y=B<sub><small>o</small></sub>")); //asigna texto a la view donde va el modelo
        ((TextView)findViewById(R.id.textView)).setEnabled(false);
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
            if (OneOrTwoVar == 1){
                //Para 1 entrada y 1 de respuesta
                ((TextView)findViewById(R.id.textView)).setText(Html.fromHtml("y=B<sub><small>o</small></sub>+B<sub><small>1</small></sub>" +
                        "X<sub><small>1</small></sub>"));
                nvalue = findViewById(R.id.nnumber);
                nvalue.setOnFocusChangeListener((v, hasFocus) -> {
                    if(!hasFocus) {
                        String nvaluestr = nvalue.getText().toString(); //obtener cantidad de filas TO DO: limpiar las layout para volver a poner views y no se acumulen
                        int nvalueint;
                        try {
                            nvalueint = Integer.parseInt(nvaluestr);
                            if (nvalueint >= 4){
                                Toast.makeText(MainActivity.this, "1var", Toast.LENGTH_LONG).show();
                                LinearLayout layout = (LinearLayout) findViewById(R.id.nlayout); // combinar 1 linear layout vertical con 4 horizontales
                                LinearLayout layouty = (LinearLayout) findViewById(R.id.ylayout);
                                LinearLayout layoutxa = (LinearLayout) findViewById(R.id.xalayout);
                                LinearLayout layoutxb = (LinearLayout) findViewById(R.id.xblayout);

                                for (int i = 1; i <= nvalueint; i++) {
                                    Button btn = new Button(this);
                                    btn.setText((String.valueOf(i)));
                                    btn.setId(i);
                                    btn.setBackgroundResource(R.drawable.rect3);
                                    btn.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                                    int width = (int) getResources().getDimension(R.dimen.width); //obtiene manualmente el width 58 de dimens
                                    int height = (int) getResources().getDimension(R.dimen.height); //obtiene manualmente el height 35 de dimens
                                    int topmargin = (int) getResources().getDimension(R.dimen.margin);
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height); //58, 35
                                    params.setMargins(0, topmargin, 0, 0);
                                    btn.setLayoutParams(params);
                                    layout.addView(btn);
                                }


                                for (int i = 1; i <= nvalueint; i++) {
                                    EditText btn = new EditText(this);
                                    btn.setText((String.valueOf(i)));
                                    btn.setId(i);
                                    btn.setBackgroundResource(R.drawable.rect);
                                    btn.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                                    int width = (int) getResources().getDimension(R.dimen.width); //obtiene manualmente el width 58 de dimens
                                    int height = (int) getResources().getDimension(R.dimen.height); //obtiene manualmente el height 35 de dimens
                                    int topmargin = (int) getResources().getDimension(R.dimen.margin);
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height); //58, 35
                                    params.setMargins(0, topmargin, 0, 0);
                                    btn.setLayoutParams(params);
                                    layouty.addView(btn);
                                }
                            }else{
                                return;
                            }
                        }
                        catch(NumberFormatException ex) {
                            return;
                        }
                    }
                });
            } else if (OneOrTwoVar == 2){
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
                                Toast.makeText(MainActivity.this, "2var", Toast.LENGTH_LONG).show();
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
                ((TextView)findViewById(R.id.textView)).setText(Html.fromHtml("y=B<sub><small>o</small></sub>"));
                Toast.makeText(MainActivity.this, "0var", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void solver(View h){
        //inicializar una nueva actividad
        Intent j = new Intent(this, solved.class);
        startActivity(j);
    }

}
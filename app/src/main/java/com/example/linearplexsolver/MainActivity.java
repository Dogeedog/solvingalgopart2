package com.example.linearplexsolver;


import static org.matheclipse.core.expression.ID.LinearModelFit;
import static org.matheclipse.core.expression.S.x;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import org.matheclipse.core.interfaces.ISymbol;


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
        };
        ((TextView)findViewById(R.id.textView)).setText(Html.fromHtml("y=B<sub><small>o</small></sub>")); //asigna texto a la view donde va el modelo
        (findViewById(R.id.textView)).setEnabled(false);
        choices.setDisplayedValues(choicesstrings);
        choices.setMinValue(0); //index value 0:"0" - 1:"1"...
        choices.setMaxValue(choicesstrings.length - 1); //no idea

        //cambia el modelo en base a la cantidad de variables
        ((TextView)findViewById(R.id.mean1)).setHint(Html.fromHtml("x<sub><small>1</small></sub>"));
        ((TextView)findViewById(R.id.mean2)).setHint(Html.fromHtml("x<sub><small>2</small></sub>"));
        ((TextView)findViewById(R.id.pre1)).setHint(Html.fromHtml("x<sub><small>1</small></sub>"));
        ((TextView)findViewById(R.id.pre2)).setHint(Html.fromHtml("x<sub><small>2</small></sub>"));
        ((TextView)findViewById(R.id.pre2)).setHint(Html.fromHtml("x<sub><small>2</small></sub>"));
        ((TextView)findViewById(R.id.xacol)).setText(Html.fromHtml("x<sub><small>1</small></sub>"));
        ((TextView)findViewById(R.id.xbcol)).setText(Html.fromHtml("x<sub><small>2</small></sub>"));


        choices.setOnValueChangedListener((picker, oldVal, newVal) -> {
            int OneOrTwoVar = choices.getValue();
            if (OneOrTwoVar == 1){
                //Para 1 entrada y 1 de respuesta
                ((TextView)findViewById(R.id.textView)).setText(Html.fromHtml("y=B<sub><small>o</small></sub>+B<sub><small>1</small></sub>" +
                        "X<sub><small>1</small></sub>"));
                nvalue = findViewById(R.id.nnumber);
                nvalue.getText().clear();
                nvalue.setOnFocusChangeListener((v, hasFocus) -> {
                    if(!hasFocus) {
                        String nvaluestr = nvalue.getText().toString(); //obtener cantidad de filas
                        int nvalueint;
                        try {
                            nvalueint = Integer.parseInt(nvaluestr);
                            double[][] a = new double[nvalueint][2];
                            if (nvalueint >= 4){
                                Toast.makeText(MainActivity.this, "1var", Toast.LENGTH_LONG).show();
                                LinearLayout layout = findViewById(R.id.nlayout);
                                LinearLayout layouty = findViewById(R.id.ylayout);
                                LinearLayout layoutxa = findViewById(R.id.xalayout);
                                LinearLayout layoutxb = findViewById(R.id.xblayout);
                                layout.removeAllViews();
                                layouty.removeAllViews();
                                layoutxa.removeAllViews();
                                layoutxb.removeAllViews();
                                //for para n textviews
                                for (int i = 1; i <= nvalueint; i++) {
                                    int width = (int) getResources().getDimension(R.dimen.width); //obtiene manualmente el width 58 de dimens
                                    int height = (int) getResources().getDimension(R.dimen.height); //obtiene manualmente el height 35 de dimens
                                    int topmargin = (int) getResources().getDimension(R.dimen.margin); //top margin
                                    int textsize = (int) getResources().getDimension(R.dimen.text);
                                    TextView btn = new TextView(this);
                                    btn.setTextSize(textsize);
                                    btn.setText((String.valueOf(i)));
                                    btn.setId(i);
                                    btn.setTextColor(Color.WHITE);
                                    btn.setBackgroundResource(R.drawable.rect3);
                                    btn.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height); //58, 35
                                    params.setMargins(0, 0, 0, topmargin);
                                    btn.setLayoutParams(params);
                                    layout.addView(btn);

                                }

                                //for para n edittexts(y)
                                for (int i = 1; i <= nvalueint; i++) {
                                    int width = (int) getResources().getDimension(R.dimen.widthinput); //obtiene manualmente el width 58 de dimens
                                    int height = (int) getResources().getDimension(R.dimen.height); //obtiene manualmente el height 35 de dimens
                                    int topmargin = (int) getResources().getDimension(R.dimen.margin); //top margin
                                    int textsize = (int) getResources().getDimension(R.dimen.text); //text size 15dp
                                    EditText btn = new EditText(this);
                                    btn.setTextSize(textsize);
                                    String butag = "y"+i;
                                    btn.setTag(butag);
                                    btn.setId(i);
                                    btn.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                                    btn.setBackgroundResource(R.drawable.rect);
                                    btn.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height); //58, 35
                                    params.setMargins(0, 0, 0, topmargin);
                                    btn.setLayoutParams(params);
                                    layouty.addView(btn);
                                }
                                //for para n edittexts(xa)
                                for (int i = 1; i <= nvalueint; i++) {
                                    int width = (int) getResources().getDimension(R.dimen.widthinput); //obtiene manualmente el width 58 de dimens
                                    int height = (int) getResources().getDimension(R.dimen.height); //obtiene manualmente el height 35 de dimens
                                    int topmargin = (int) getResources().getDimension(R.dimen.margin); //top margin
                                    int textsize = (int) getResources().getDimension(R.dimen.text); //text size 15dp
                                    EditText btn = new EditText(this);
                                    btn.setTextSize(textsize);
                                    String butag = "xa"+i;
                                    btn.setTag(butag);
                                    btn.setId(i);
                                    btn.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                                    btn.setBackgroundResource(R.drawable.rect);
                                    btn.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height); //58, 35
                                    params.setMargins(0, 0, 0, topmargin);
                                    btn.setLayoutParams(params);
                                    layoutxa.addView(btn);
                                }
                                Button tebtn = findViewById(R.id.btntest);
                                tebtn.setOnClickListener(v1 -> {
                                    //(y) for para encontrar cada boton por tag
                                    for (int i=1; i <= nvalueint; i++) {
                                        String butagy = "y"+i;
                                        EditText content = layouty.findViewWithTag(butagy);
                                        String contentstring = content.getText().toString();
                                            try {
                                                double contentint = Double.parseDouble(contentstring);
                                                a[i-1][0] = contentint;
                                                System.out.println(a[i-1][0]);
                                        } catch(NumberFormatException ex) {
                                            return;
                                        }
                                    }
                                    //(xa) for para encontrar cada boton por tag
                                    for (int i=1; i <= nvalueint; i++) {
                                        String butagxa = "xa"+i;
                                        EditText content = layoutxa.findViewWithTag(butagxa);
                                        String contentstring = content.getText().toString();
                                        try {
                                            double contentint = Double.parseDouble(contentstring);
                                            a[i-1][1] = contentint;
                                            System.out.println(a[i-1][1]);
                                        } catch(NumberFormatException ex) {
                                            return;
                                        }
                                    }
                                });
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
                //Para 2 entradas y 1 respuesta
                ((TextView)findViewById(R.id.textView)).setText(Html.fromHtml("y=B<sub><small>o</small></sub>+B<sub><small>1</small></sub>" +
                        "X<sub><small>1</small></sub>+B<sub><small>2</small></sub>X<sub><small>2</small></sub>"));
                nvalue = findViewById(R.id.nnumber);
                nvalue.getText().clear();
                nvalue.setOnFocusChangeListener((v, hasFocus) -> {
                    if(!hasFocus) {
                        String nvaluestr = nvalue.getText().toString(); //obtener cantidad de filas
                        int nvalueint;
                        try {
                            nvalueint = Integer.parseInt(nvaluestr);
                            double[][] b = new double[nvalueint][3];
                            if (nvalueint >= 4){
                                Toast.makeText(MainActivity.this, "2var", Toast.LENGTH_LONG).show();
                                LinearLayout layout = findViewById(R.id.nlayout);
                                LinearLayout layouty = findViewById(R.id.ylayout);
                                LinearLayout layoutxa = findViewById(R.id.xalayout);
                                LinearLayout layoutxb = findViewById(R.id.xblayout);
                                layout.removeAllViews();
                                layouty.removeAllViews();
                                layoutxa.removeAllViews();
                                layoutxb.removeAllViews();
                                //for para n textviews
                                for (int i = 1; i <= nvalueint; i++) { //for para bot
                                    int width = (int) getResources().getDimension(R.dimen.width); //obtiene manualmente el width 58 de dimens
                                    int height = (int) getResources().getDimension(R.dimen.height); //obtiene manualmente el height 35 de dimens
                                    int topmargin = (int) getResources().getDimension(R.dimen.margin); //top margin
                                    int textsize = (int) getResources().getDimension(R.dimen.text);
                                    TextView btn = new TextView(this);
                                    btn.setTextSize(textsize);
                                    btn.setText((String.valueOf(i)));
                                    btn.setId(i);
                                    btn.setTextColor(Color.WHITE);
                                    btn.setBackgroundResource(R.drawable.rect3);
                                    btn.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height); //58, 35
                                    params.setMargins(0, 0, 0, topmargin);
                                    btn.setLayoutParams(params);
                                    layout.addView(btn);
                                }
                                //for para n edittexts(y)
                                for (int i = 1; i <= nvalueint; i++) {
                                    int width = (int) getResources().getDimension(R.dimen.widthinput); //obtiene manualmente el width 58 de dimens
                                    int height = (int) getResources().getDimension(R.dimen.height); //obtiene manualmente el height 35 de dimens
                                    int topmargin = (int) getResources().getDimension(R.dimen.margin); //top margin
                                    int textsize = (int) getResources().getDimension(R.dimen.text); //text size 15dp
                                    EditText btn = new EditText(this);
                                    btn.setTextSize(textsize);
                                    String butag = "ytwo"+i;
                                    btn.setTag(butag);
                                    btn.setId(i);
                                    btn.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                                    btn.setBackgroundResource(R.drawable.rect);
                                    btn.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height); //58, 35
                                    params.setMargins(0, 0, 0, topmargin);
                                    btn.setLayoutParams(params);
                                    layouty.addView(btn);
                                }
                                //for para n edittexts(xa)
                                for (int i = 1; i <= nvalueint; i++) {
                                    int width = (int) getResources().getDimension(R.dimen.widthinput); //obtiene manualmente el width 58 de dimens
                                    int height = (int) getResources().getDimension(R.dimen.height); //obtiene manualmente el height 35 de dimens
                                    int topmargin = (int) getResources().getDimension(R.dimen.margin); //top margin
                                    int textsize = (int) getResources().getDimension(R.dimen.text); //text size 15dp
                                    EditText btn = new EditText(this);
                                    btn.setTextSize(textsize);
                                    String butag = "xatwo"+i;
                                    btn.setTag(butag);
                                    btn.setId(i);
                                    btn.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                                    btn.setBackgroundResource(R.drawable.rect);
                                    btn.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height); //58, 35
                                    params.setMargins(0, 0, 0, topmargin);
                                    btn.setLayoutParams(params);
                                    layoutxa.addView(btn);
                                }
                                //for para n edittexts(xb)
                                for (int i = 1; i <= nvalueint; i++) {
                                    int width = (int) getResources().getDimension(R.dimen.widthinput); //obtiene manualmente el width 58 de dimens
                                    int height = (int) getResources().getDimension(R.dimen.height); //obtiene manualmente el height 35 de dimens
                                    int topmargin = (int) getResources().getDimension(R.dimen.margin); //top margin
                                    int textsize = (int) getResources().getDimension(R.dimen.text); //text size 15dp
                                    EditText btn = new EditText(this);
                                    btn.setTextSize(textsize);
                                    String butag = "xbtwo"+i;
                                    btn.setTag(butag);
                                    btn.setId(i);
                                    btn.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                                    btn.setBackgroundResource(R.drawable.rect);
                                    btn.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height); //58, 35
                                    params.setMargins(0, 0, 0, topmargin);
                                    btn.setLayoutParams(params);
                                    layoutxb.addView(btn);
                                }
                                Button tebtn = findViewById(R.id.btntest);
                                tebtn.setOnClickListener(v12 -> {
                                    //(y) for para encontrar cada boton por tag
                                    for (int i=1; i <= nvalueint; i++) {
                                        String butagy = "ytwo"+i;
                                        EditText content = layouty.findViewWithTag(butagy);
                                        String contentstring = content.getText().toString();
                                        try {
                                            double contentint = Double.parseDouble(contentstring);
                                            b[i-1][0] = contentint;
                                            System.out.println(b[i-1][0]);
                                        } catch(NumberFormatException ex) {
                                            return;
                                        }
                                    }
                                    //(xa) for para encontrar cada boton por tag
                                    for (int i=1; i <= nvalueint; i++) {
                                        String butagxa = "xatwo"+i;
                                        EditText content = layoutxa.findViewWithTag(butagxa);
                                        String contentstring = content.getText().toString();
                                        try {
                                            double contentint = Double.parseDouble(contentstring);
                                            b[i-1][1] = contentint;
                                            System.out.println(b[i-1][1]);
                                        } catch(NumberFormatException ex) {
                                            return;
                                        }
                                    }
                                    //(xb) for para encontrar cada boton por tag
                                    for (int i=1; i <= nvalueint; i++) {
                                        String butagxa = "xbtwo"+i;
                                        EditText content = layoutxb.findViewWithTag(butagxa);
                                        String contentstring = content.getText().toString();
                                        try {
                                            double contentint = Double.parseDouble(contentstring);
                                            b[i-1][1] = contentint;
                                            System.out.println(b[i-1][1]);

                                             // Normal
                                        } catch(NumberFormatException ex) {
                                            return;
                                        }
                                    }
                                });
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

    private void LinearModelFit(double v, ISymbol x, ISymbol x1) {
    }



    public void solver(View h){
        //inicializar una nueva actividad
        Intent j = new Intent(this, solved.class);
        startActivity(j);
    }
}



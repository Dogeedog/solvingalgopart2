package com.example.linearplexsolver;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.hipparchus.stat.regression.OLSMultipleLinearRegression;
import org.hipparchus.stat.regression.SimpleRegression;

import java.math.RoundingMode;
import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity  {


    EditText nvalue;
    EditText nvaluedel;
    RadioGroup choicesv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        (findViewById(R.id.buttontexst)).setVisibility(View.INVISIBLE);
        ((TextView)findViewById(R.id.textView)).setText(Html.fromHtml("y=B<sub><small>o</small></sub>")); //asigna texto a la view donde va el modelo
        (findViewById(R.id.textView)).setEnabled(false);

        //cambia el modelo en base a la cantidad de variables
        ((TextView)findViewById(R.id.mean1)).setHint(Html.fromHtml("x<sub><small>1</small></sub>"));
        ((TextView)findViewById(R.id.mean2)).setHint(Html.fromHtml("x<sub><small>2</small></sub>"));
        ((TextView)findViewById(R.id.pre1)).setHint(Html.fromHtml("x<sub><small>1</small></sub>"));
        ((TextView)findViewById(R.id.pre2)).setHint(Html.fromHtml("x<sub><small>2</small></sub>"));
        ((TextView)findViewById(R.id.pre2)).setHint(Html.fromHtml("x<sub><small>2</small></sub>"));
        ((TextView)findViewById(R.id.xacol)).setText(Html.fromHtml("x<sub><small>1</small></sub>"));
        ((TextView)findViewById(R.id.xbcol)).setText(Html.fromHtml("x<sub><small>2</small></sub>"));

        choicesv2 = findViewById(R.id.vargroup);
        choicesv2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    //Para 1 entrada y 1 de respuesta
                    case R.id.onevar:
                        ((TextView)findViewById(R.id.textView)).setText(Html.fromHtml("y=B<sub><small>o</small></sub>+B<sub><small>1</small></sub>" +
                                "X<sub><small>1</small></sub>"));
                        nvalue = findViewById(R.id.nnumber);
                        nvalue.setVisibility(View.VISIBLE);
                        nvaluedel = findViewById(R.id.nnumber2);
                        nvaluedel.setVisibility(View.INVISIBLE);
                        nvalue.getText().clear();
                        nvalue.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                String nvaluestr = nvalue.getText().toString(); //obtener cantidad de filas
                                int nvalueint;
                                try {
                                    nvalueint = Integer.parseInt(nvaluestr);
                                }
                                catch(NumberFormatException ex) {
                                    return;
                                }
                                    double[][] a = new double[nvalueint][2];
                                    if (nvalueint >= 4){
                                        Toast.makeText(MainActivity.this, "1var", Toast.LENGTH_SHORT).show();
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
                                            TextView btn = new TextView(MainActivity.this);
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
                                            EditText btn = new EditText(MainActivity.this);
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
                                            EditText btn = new EditText(MainActivity.this);
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
                                        Button tebtn = findViewById(R.id.button2);
                                        tebtn.setOnClickListener(v1 -> {
                                            //(y) for para encontrar cada boton por tag
                                            for (int i=1; i <= nvalueint; i++) {
                                                String butagy = "y"+i;
                                                EditText content = layouty.findViewWithTag(butagy);
                                                String contentstring = content.getText().toString();
                                                try {
                                                    double contentint = Double.parseDouble(contentstring);
                                                    a[i-1][1] = contentint;
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
                                                    a[i-1][0] = contentint;
                                                } catch(NumberFormatException ex) {
                                                    return;
                                                }
                                            }
                                            SimpleRegression regression = new SimpleRegression();
                                            DecimalFormat df = new DecimalFormat("#.####; - #");
                                            df.setRoundingMode(RoundingMode.CEILING);
                                            DecimalFormat df2 = new DecimalFormat("+ #.####;- #");
                                            df2.setRoundingMode(RoundingMode.CEILING);
                                            // a array {x, y }
                                            regression.addData(a);
                                            double[] coef = regression.regress().getParameterEstimates();
                                            String b0 = (df.format(coef[0]));
                                            String b1 = (df2.format(coef[1]));
                                            String modelstr = "y= " + b0 + " " + b1 +
                                                    Html.fromHtml("X<sub><small>1</small></sub> ");
                                            Intent j = new Intent(MainActivity.this, solved.class);
                                            j.putExtra("modelstr", modelstr);
                                            startActivity(j);
                                        });
                                    }
                            }
                        });
                        break;
                    //Para 2 entradas y 1 respuesta
                    case R.id.twovar:
                        ((TextView)findViewById(R.id.textView)).setText(Html.fromHtml("y=B<sub><small>o</small></sub>+B<sub><small>1</small></sub>" +
                                "X<sub><small>1</small></sub>+B<sub><small>2</small></sub>X<sub><small>2</small></sub>"));
                        nvalue = findViewById(R.id.nnumber2);
                        nvalue.setVisibility(View.VISIBLE);
                        nvaluedel = findViewById(R.id.nnumber);
                        nvaluedel.setVisibility(View.INVISIBLE);
                        nvalue.getText().clear();
                        nvalue.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {


                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                String nvaluestr = nvalue.getText().toString(); //obtener cantidad de filas
                                int nvalueint;
                                try {
                                    nvalueint = Integer.parseInt(nvaluestr);
                                }
                                catch(NumberFormatException ex) {
                                    return;
                                }
                                    double[][] b = new double[nvalueint][2];
                                    double[] ym = new double[nvalueint];
                                    if (nvalueint >= 4){
                                        Toast.makeText(MainActivity.this, "2var", Toast.LENGTH_SHORT).show();
                                        LinearLayout layout = findViewById(R.id.nlayout);
                                        LinearLayout layouty = findViewById(R.id.ylayout);
                                        LinearLayout layoutxa = findViewById(R.id.xalayout);
                                        LinearLayout layoutxb = findViewById(R.id.xblayout);
                                        //serie de try's para evitar eliminar (guarda info en las matrices) el input al añadir mas filas
                                        try {
                                            for (int i=1; i <= nvalueint; i++) {
                                                String butagy = "ytwo"+i;
                                                EditText content = layouty.findViewWithTag(butagy);
                                                String contentstring = content.getText().toString();
                                                try {
                                                    double contentint = Double.parseDouble(contentstring);
                                                    ym[i-1] = contentint;
                                                } catch(Exception ignored) {
                                                }
                                            }
                                        } catch (Exception ignored) {
                                        }
                                        try {
                                            //(xa) for para encontrar cada boton por tag
                                            for (int i=1; i <= nvalueint; i++) {
                                                String butagxa = "xatwo"+i;
                                                EditText content = layoutxa.findViewWithTag(butagxa);
                                                String contentstring = content.getText().toString();
                                                try {
                                                    double contentint = Double.parseDouble(contentstring);
                                                    b[i-1][0] = contentint;
                                                } catch(Exception ignored) {
                                                }
                                            }
                                        } catch (Exception ignored) {
                                        }
                                        try {
                                            //(xb) for para encontrar cada boton por tag
                                            for (int i=1; i <= nvalueint; i++) {
                                                String butagxa = "xbtwo"+i;
                                                EditText content = layoutxb.findViewWithTag(butagxa);
                                                String contentstring = content.getText().toString();
                                                try {
                                                    double contentint = Double.parseDouble(contentstring);
                                                    b[i-1][1] = contentint;
                                                } catch(Exception ignored) {
                                                }
                                            }
                                        } catch (Exception ignored) {
                                        }
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
                                            TextView btn = new TextView(MainActivity.this);
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
                                            EditText btn = new EditText(MainActivity.this);
                                            btn.setTextSize(textsize);
                                            String pastextt = String.valueOf(ym[i-1]);
                                            if(!pastextt.equals("0.0")){
                                                btn.setText(pastextt);
                                            }
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
                                            EditText btn = new EditText(MainActivity.this);
                                            btn.setTextSize(textsize);
                                            String pastextt = String.valueOf(b[i-1][0]);
                                            if(!pastextt.equals("0.0")){
                                                btn.setText(pastextt);
                                            }
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
                                            EditText btn = new EditText(MainActivity.this);
                                            btn.setTextSize(textsize);
                                            String pastextt = String.valueOf(b[i-1][1]);
                                            if(!pastextt.equals("0.0")){
                                                btn.setText(pastextt);
                                            }
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
                                        Button tebtn = findViewById(R.id.button2);
                                        tebtn.setOnClickListener(v12 -> {
                                            //(y) for para encontrar cada boton por tag
                                            for (int i=1; i <= nvalueint; i++) {
                                                String butagy = "ytwo"+i;
                                                EditText content = layouty.findViewWithTag(butagy);
                                                String contentstring = content.getText().toString();
                                                try {
                                                    double contentint = Double.parseDouble(contentstring);
                                                    ym[i-1] = contentint;
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
                                                    b[i-1][0] = contentint;
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
                                                } catch(NumberFormatException ex) {
                                                    return;
                                                }
                                            }
                                            OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
                                            DecimalFormat df = new DecimalFormat("#.####; - #");
                                            df.setRoundingMode(RoundingMode.CEILING);
                                            DecimalFormat df2 = new DecimalFormat("+ #.####;- #");
                                            df2.setRoundingMode(RoundingMode.CEILING);
                                            regression.newSampleData(ym, b);
                                            double[] coef = regression.estimateRegressionParameters();
                                            String b0 = (df.format(coef[0]));
                                            String b1 = (df2.format(coef[1]));
                                            String b2 = (df2.format(coef[2]));
                                            String modelstr = "y= " + b0 + " " + b1 +
                                                    Html.fromHtml("X<sub><small>1</small></sub> ") + b2 + Html.fromHtml("X<sub><small>2</small></sub>");
                                            Intent j = new Intent(MainActivity.this, solved.class);
                                            j.putExtra("modelstr", modelstr);
                                            startActivity(j);
                                        });
                                    }
                            }
                        });
                        break;
                }
            }
        });
    }
    /*
    public void save(View v){
        LinearLayout layoutxa = findViewById(R.id.xalayout);
        String butagxa = "xatwo1";
        EditText content = layoutxa.findViewWithTag(butagxa);
        String contentstring = content.getText().toString();
        Toast.makeText(MainActivity.this, contentstring, Toast.LENGTH_LONG).show();
        EditText nnumberx = findViewById(R.id.nnumber);
        choicesv2 = findViewById(R.id.vargroup);
        choicesv2.check(R.id.onevar);
        nnumberx.setText("4");
    }
    */

}



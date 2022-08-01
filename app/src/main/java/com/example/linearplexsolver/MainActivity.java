package com.example.linearplexsolver;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.hipparchus.stat.regression.OLSMultipleLinearRegression;
import org.hipparchus.stat.regression.SimpleRegression;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity  {


    EditText nvalue;
    EditText nvaluedel;
    RadioGroup choicesv2;
    ImageButton toggle;
    DrawerLayout mdrawer;
    TextView xbhead;
    RadioGroup state;
    RadioButton saveradio;
    RadioButton loadradio;
    RadioButton onevar;
    RadioButton twovar;
    RadioButton del;
    CheckBox icmean;
    CheckBox icpred;
    EditText mean1;
    EditText mean2;
    EditText pre1;
    EditText pre2;
    Gson gson = new GsonBuilder().create();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Genera los botones de guardar/cargar que el usuario añadió previamente
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        //sp.edit().clear().apply();
        int count = sp.getInt("count", 0);

        for (int i = 0; i < count; i++) {
            LinearLayout layout = findViewById(R.id.saveloadlayout);
            int width = (int) getResources().getDimension(R.dimen.widthstate);
            int height = (int) getResources().getDimension(R.dimen.heightstate);
            int topmargin = (int) getResources().getDimension(R.dimen.marginstate); //top margin
            int textsize = (int) getResources().getDimension(R.dimen.textstate);
            Button btn = new Button(MainActivity.this);
            btn.setTextSize(textsize);
            btn.setText(String.valueOf(i+1));
            btn.setId(i+1);
            btn.setTag("state"+i+1);
            btn.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            btn.setTextColor(Color.BLACK);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            params.setMargins(0, 0, 0, topmargin);
            btn.setLayoutParams(params);
            layout.addView(btn);
            int ivalue = i + 1;
            //cuando realmente se guarda algo de información q se cambie el color del boton
            int loadn = sp.getInt("nvalueone"+ivalue, 0);
            int loadn2 = sp.getInt("nvaluetwo"+ivalue, 0);
            int loadvar = sp.getInt("varvalueone"+ivalue, 0);
            int loadvar2 = sp.getInt("varvaluetwo"+ivalue,0);
            if ((loadn >= 4 || loadn2 >= 4) && (loadvar > 0 || loadvar2 > 0)){
                btn.setBackgroundResource(R.drawable.rect4);
            } else  {
                btn.setBackgroundResource(R.drawable.rect5);
            }
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    state = findViewById(R.id.saveorload);
                    saveradio = findViewById(R.id.save);
                    loadradio = findViewById(R.id.load);
                    choicesv2 = findViewById(R.id.vargroup);
                    onevar = findViewById(R.id.onevar);
                    twovar = findViewById(R.id.twovar);
                    del = findViewById(R.id.eraseslot);
                    LinearLayout layouty = findViewById(R.id.ylayout);
                    LinearLayout layoutxa = findViewById(R.id.xalayout);
                    LinearLayout layoutxb = findViewById(R.id.xblayout);
                    if(loadradio.isChecked()){
                        int loadn = sp.getInt("nvalueone"+ivalue, 0);
                        int loadn2 = sp.getInt("nvaluetwo"+ivalue,0);
                        int loadvar2 = sp.getInt("varvaluetwo"+ivalue, 0);
                        int loadvar = sp.getInt("varvalueone"+ivalue, 0);
                        EditText nnumberx = findViewById(R.id.nnumber);
                        EditText nnumberx2 = findViewById(R.id.nnumber2);
                        //1 o 2 para respetar lo que haya guardado el usuario, son almacenados en sp cuando el usuario guarda
                        if(loadvar == 1){
                            layouty.removeAllViews();
                            layoutxa.removeAllViews();
                            choicesv2.check(R.id.onevar);
                            nnumberx.setText(String.valueOf(loadn));
                            String array = sp.getString("arrayonev"+ivalue,null);
                            double[][] a = gson.fromJson(array, double[][].class);
                            //aqui se obtiene el array en forma y con los siguientes for se introduce toda la información guardada
                            try {
                                //(y) for para encontrar cada boton por tag
                                for (int i=1; i <= loadn; i++) {
                                    String butagy = "y"+i;
                                    EditText content = layouty.findViewWithTag(butagy);
                                    String restoredata = String.valueOf(a[i-1][1]);
                                    if(!restoredata.equals("0.0")){
                                        content.setText(restoredata);
                                    }
                                }
                            } catch (Exception ignored) {
                            }
                            try {
                                //(xa) for para encontrar cada boton por tag
                                for (int i=1; i <= loadn; i++) {
                                    String butagxa = "xa"+i;
                                    EditText content = layoutxa.findViewWithTag(butagxa);
                                    String restoredata = String.valueOf(a[i-1][0]);
                                    if(!restoredata.equals("0.0")){
                                        content.setText(restoredata);
                                    }
                                }
                            } catch (Exception ignored) {
                            }
                            Toast.makeText(MainActivity.this, ivalue + " cargar", Toast.LENGTH_SHORT).show();
                        }else if(loadvar2 == 2){
                            layouty.removeAllViews();
                            layoutxa.removeAllViews();
                            layoutxb.removeAllViews();
                            choicesv2.check(R.id.twovar);
                            nnumberx2.setText(String.valueOf(loadn2));
                            String arrayy = sp.getString("arrayY"+ivalue,null);
                            String arrayx = sp.getString("arrayX"+ivalue,null);
                            double[] ym = gson.fromJson(arrayy, double[].class);
                            double[][] b = gson.fromJson(arrayx, double[][].class);
                            try {
                                for (int i=1; i <= loadn2; i++) {
                                    String butagy = "ytwo"+i;
                                    EditText content = layouty.findViewWithTag(butagy);
                                    String restoredata = String.valueOf(ym[i-1]);
                                    if(!restoredata.equals("0.0")){
                                        content.setText(restoredata);
                                    }
                                }
                            } catch (Exception ignored) {
                            }
                            try {
                                //(xa) for para encontrar cada boton por tag
                                for (int i=1; i <= loadn2; i++) {
                                    String butagxa = "xatwo"+i;
                                    EditText content = layoutxa.findViewWithTag(butagxa);
                                    String restoredata = String.valueOf(b[i-1][0]);
                                    if(!restoredata.equals("0.0")){
                                        content.setText(restoredata);
                                    }
                                }
                            } catch (Exception ignored) {
                            }
                            try {
                                //(xb) for para encontrar cada boton por tag
                                for (int i=1; i <= loadn2; i++) {
                                    String butagxa = "xbtwo"+i;
                                    EditText content = layoutxb.findViewWithTag(butagxa);
                                    String restoredata = String.valueOf(b[i-1][1]);
                                    if(!restoredata.equals("0.0")){
                                        content.setText(restoredata);
                                    }
                                }
                            } catch (Exception ignored) {
                            }
                            Toast.makeText(MainActivity.this, ivalue + " cargar", Toast.LENGTH_SHORT).show();
                        }
                    } else if(saveradio.isChecked()){
                        if(onevar.isChecked()){
                            String nvaluestr = nvalue.getText().toString(); //obtener cantidad de filas
                            int nvalueint;
                            try {
                                nvalueint = Integer.parseInt(nvaluestr);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putInt("nvalueone"+ivalue, nvalueint);
                                editor.putInt("varvalueone"+ivalue, 1);
                                editor.apply();
                                int loadn = sp.getInt("nvalueone"+ivalue, 0);
                                int loadvar = sp.getInt("varvalueone"+ivalue, 0);
                                if (loadn >= 4 && loadvar > 0){
                                    btn.setBackgroundResource(R.drawable.rect4);
                                }
                            }
                            catch(NumberFormatException ex) {
                                return;
                            }
                            double[][] a = new double[nvalueint][2];
                            //serie de try's para evitar eliminar (guarda info en las matrices) el input al añadir mas filas
                            try {
                                //(y) for para encontrar cada boton por tag
                                for (int i=1; i <= nvalueint; i++) {
                                    String butagy = "y"+i;
                                    EditText content = layouty.findViewWithTag(butagy);
                                    String contentstring = content.getText().toString();
                                    try {
                                        double contentint = Double.parseDouble(contentstring);
                                        a[i-1][1] = contentint;
                                    } catch(Exception ignored) {
                                    }
                                }
                            } catch (Exception ignored) {
                            }
                            try {
                                //(xa) for para encontrar cada boton por tag
                                for (int i=1; i <= nvalueint; i++) {
                                    String butagxa = "xa"+i;
                                    EditText content = layoutxa.findViewWithTag(butagxa);
                                    String contentstring = content.getText().toString();
                                    try {
                                        double contentint = Double.parseDouble(contentstring);
                                        a[i-1][0] = contentint;
                                    } catch(Exception ignored) {
                                    }
                                }
                            } catch (Exception ignored) {
                            }
                            //Transforma el array en string para almacenarlo en sharedpreferences.
                            String arraytostring = gson.toJson(a);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("arrayonev"+ivalue, arraytostring);
                            editor.apply();
                            Toast.makeText(MainActivity.this, ivalue + " guardar", Toast.LENGTH_SHORT).show();
                        } else if(twovar.isChecked()){
                            String nvaluestr = nvalue.getText().toString(); //obtener cantidad de filas
                            int nvalueint;
                            try {
                                nvalueint = Integer.parseInt(nvaluestr);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.remove("varvalueone"+ivalue);
                                editor.putInt("nvaluetwo"+ivalue, nvalueint);
                                editor.putInt("varvaluetwo"+ivalue, 2);
                                editor.apply();
                                int loadn = sp.getInt("nvaluetwo"+ivalue, 0);
                                int loadvar = sp.getInt("varvaluetwo"+ivalue, 0);
                                if (loadn >= 4 && (loadvar > 0 || loadvar2 > 0)){
                                    btn.setBackgroundResource(R.drawable.rect4);
                                }
                            }
                            catch(NumberFormatException ex) {
                                return;
                            }
                            double[][] b = new double[nvalueint][2];
                            double[] ym = new double[nvalueint];
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
                            //Transforma el array en string para almacenarlo en sharedpreferences.
                            String arrayY = gson.toJson(ym);
                            String arrayX = gson.toJson(b);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("arrayY"+ivalue, arrayY);
                            editor.putString("arrayX"+ivalue, arrayX);
                            editor.apply();
                            Toast.makeText(MainActivity.this, ivalue + " guardar", Toast.LENGTH_SHORT).show();
                        }
                    } else if(del.isChecked()){
                        SharedPreferences.Editor editor = sp.edit();
                        editor.remove("arrayonev"+ivalue);
                        editor.remove("arrayY"+ivalue);
                        editor.remove("arrayX"+ivalue);
                        editor.remove("nvalueone"+ivalue);
                        editor.remove("nvaluetwo"+ivalue);
                        editor.remove("varvalueone"+ivalue);
                        editor.remove("varvaluetwo"+ivalue);
                        editor.apply();
                        btn.setBackgroundResource(R.drawable.rect5);
                    }
                }
            });
        }



        mdrawer = findViewById(R.id.drawer_layout);
        toggle = findViewById(R.id.savingbutton);
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mdrawer.isDrawerOpen(GravityCompat.START))
                    mdrawer.openDrawer(GravityCompat.START);
                else mdrawer.closeDrawer(GravityCompat.END);
            }

        });


        ((TextView)findViewById(R.id.textView)).setText(Html.fromHtml("y=B<sub><small>o</small></sub>")); //asigna texto a la view donde va el modelo
        (findViewById(R.id.textView)).setEnabled(false);

        mean1 = findViewById(R.id.mean1);
        mean1.setEnabled(false);
        mean1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        mean2 = findViewById(R.id.mean2);
        mean2.setEnabled(false);
        mean2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        pre1 = findViewById(R.id.pre1);
        pre1.setEnabled(false);
        pre1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        pre2 = findViewById(R.id.pre2);
        pre2.setEnabled(false);
        pre2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);

        icmean = findViewById(R.id.respuesta);
        icpred = findViewById(R.id.prediccion);

        //listener del checkbox ic media para abilitar/desabilitar el input
        icmean.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mean1.setEnabled(true);
                mean2.setEnabled(true);
                mean1.setBackgroundResource(R.drawable.rect);
                mean2.setBackgroundResource(R.drawable.rect);
                if (!isChecked){
                    mean1.getText().clear();
                    mean2.getText().clear();
                    mean1.setEnabled(false);
                    mean2.setEnabled(false);
                    mean1.setBackgroundResource(R.drawable.rectblocked);
                    mean2.setBackgroundResource(R.drawable.rectblocked);
                }
            }
        });

        //listener del checkbox ic predicción para abilitar/desabilitar el input
        icpred.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pre1.setEnabled(true);
                pre2.setEnabled(true);
                pre1.setBackgroundResource(R.drawable.rect);
                pre2.setBackgroundResource(R.drawable.rect);
                if (!isChecked){
                    pre1.getText().clear();
                    pre2.getText().clear();
                    pre1.setEnabled(false);
                    pre2.setEnabled(false);
                    pre1.setBackgroundResource(R.drawable.rectblocked);
                    pre2.setBackgroundResource(R.drawable.rectblocked);
                }
            }
        });

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
                        xbhead = findViewById(R.id.xbcol);
                        xbhead.setVisibility(View.INVISIBLE);
                        ((TextView)findViewById(R.id.textView)).setText(Html.fromHtml("y=B<sub><small>o</small></sub> + B<sub><small>1</small></sub>" +
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
                                        //serie de try's para evitar eliminar (guarda info en las matrices) el input al añadir mas filas
                                        try {
                                            //(y) for para encontrar cada boton por tag
                                            for (int i=1; i <= nvalueint; i++) {
                                                String butagy = "y"+i;
                                                EditText content = layouty.findViewWithTag(butagy);
                                                String contentstring = content.getText().toString();
                                                try {
                                                    double contentint = Double.parseDouble(contentstring);
                                                    a[i-1][1] = contentint;
                                                } catch(Exception ignored) {
                                                }
                                            }
                                        } catch (Exception ignored) {
                                        }
                                        try {
                                            //(xa) for para encontrar cada boton por tag
                                            for (int i=1; i <= nvalueint; i++) {
                                                String butagxa = "xa"+i;
                                                EditText content = layoutxa.findViewWithTag(butagxa);
                                                String contentstring = content.getText().toString();
                                                try {
                                                    double contentint = Double.parseDouble(contentstring);
                                                    a[i-1][0] = contentint;
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
                                            String pastextt = String.valueOf(a[i-1][1]);
                                            if(!pastextt.equals("0.0")){
                                                btn.setText(pastextt);
                                            }
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
                                            String pastextt = String.valueOf(a[i-1][0]);
                                            if(!pastextt.equals("0.0")){
                                                btn.setText(pastextt);
                                            }
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
                                            String xone = "X1";
                                            String fullstr = "y= " + b0 + " " + b1 + xone;
                                            Spannable modelstr = new SpannableString(fullstr);
                                            modelstr.setSpan(new SubscriptSpan(),(fullstr.indexOf(xone) + 1), (fullstr.indexOf(xone) + 2), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                            modelstr.setSpan(new RelativeSizeSpan(0.75f), (fullstr.indexOf(xone)) + 1, (fullstr.indexOf(xone) + 2), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                            Intent j = new Intent(MainActivity.this, solvedone.class);
                                            j.putExtra("modelstr", modelstr);
                                            startActivity(j);
                                        });
                                    }
                            }
                        });
                        break;
                    //Para 2 entradas y 1 respuesta
                    case R.id.twovar:
                        xbhead = findViewById(R.id.xbcol);
                        xbhead.setVisibility(View.VISIBLE);
                        ((TextView)findViewById(R.id.textView)).setText(Html.fromHtml("y=B<sub><small>o</small></sub> + B<sub><small>1</small></sub>" +
                                "X<sub><small>1</small></sub> + B<sub><small>2</small></sub>X<sub><small>2</small></sub>"));
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
                                            String xone = "X1 ";
                                            String xtwo = "X2";
                                            String fullstr = "y= " + b0 + " " + b1 + xone + b2 + xtwo;
                                            Spannable modelstr = new SpannableString(fullstr);
                                            modelstr.setSpan(new SubscriptSpan(),(fullstr.indexOf(xone) + 1), (fullstr.indexOf(xone) + 2), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                            modelstr.setSpan(new RelativeSizeSpan(0.75f), (fullstr.indexOf(xone)) + 1, (fullstr.indexOf(xone) + 2), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                            modelstr.setSpan(new SubscriptSpan(),(fullstr.indexOf(xtwo) + 1), (fullstr.indexOf(xtwo) + 2), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                            modelstr.setSpan(new RelativeSizeSpan(0.75f), (fullstr.indexOf(xtwo) + 1), (fullstr.indexOf(xtwo) + 2), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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



    public void newstate(View v){
        LinearLayout layout = findViewById(R.id.saveloadlayout);
        int width = (int) getResources().getDimension(R.dimen.widthstate);
        int height = (int) getResources().getDimension(R.dimen.heightstate);
        int topmargin = (int) getResources().getDimension(R.dimen.marginstate); //top margin
        int textsize = (int) getResources().getDimension(R.dimen.textstate);
        Button btn = new Button(MainActivity.this);
        btn.setTextSize(textsize);
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        //obtiene "count", que es la cantidad de botones ya existentes
        int count = sp.getInt("count", 0);
        SharedPreferences.Editor editor = sp.edit();
        //count+1 pq se agrega un botón
        editor.putInt("count", (count + 1));
        editor.putInt("state" + count, count);
        editor.apply();
        int numbtn = sp.getInt("count", 1);
        btn.setText(String.valueOf(numbtn));
        btn.setId(numbtn);
        btn.setTag("state"+numbtn);
        btn.setBackgroundResource(R.drawable.rect5);
        btn.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        btn.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        params.setMargins(0, 0, 0, topmargin);
        btn.setLayoutParams(params);
        layout.addView(btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state = findViewById(R.id.saveorload);
                saveradio = findViewById(R.id.save);
                loadradio = findViewById(R.id.load);
                choicesv2 = findViewById(R.id.vargroup);
                onevar = findViewById(R.id.onevar);
                twovar = findViewById(R.id.twovar);
                del = findViewById(R.id.eraseslot);
                LinearLayout layouty = findViewById(R.id.ylayout);
                LinearLayout layoutxa = findViewById(R.id.xalayout);
                LinearLayout layoutxb = findViewById(R.id.xblayout);
                if(loadradio.isChecked()){
                    int loadn = sp.getInt("nvalueone"+numbtn, 0);
                    int loadvar = sp.getInt("varvalueone"+numbtn, 0);
                    int loadn2 = sp.getInt("nvaluetwo"+numbtn, 0);
                    int loadvar2 = sp.getInt("varvaluetwo"+numbtn, 0);
                    EditText nnumberx = findViewById(R.id.nnumber);
                    EditText nnumberx2 = findViewById(R.id.nnumber2);
                    //1 o 2 para respetar lo que haya guardado el usuario, son almacenados en sp cuando el usuario guarda
                    if(loadvar == 1){
                        layouty.removeAllViews();
                        layoutxa.removeAllViews();
                        choicesv2.check(R.id.onevar);
                        nnumberx.setText(String.valueOf(loadn));
                        String array = sp.getString("arrayonev"+numbtn,null);
                        double[][] a = gson.fromJson(array, double[][].class);
                        //aqui se obtiene el array en forma y con los siguientes for se introduce toda la información guardada
                        try {
                            //(y) for para encontrar cada boton por tag
                            for (int i=1; i <= loadn; i++) {
                                String butagy = "y"+i;
                                EditText content = layouty.findViewWithTag(butagy);
                                String restoredata = String.valueOf(a[i-1][1]);
                                if(!restoredata.equals("0.0")){
                                    content.setText(restoredata);
                                }
                            }
                        } catch (Exception ignored) {
                        }
                        try {
                            //(xa) for para encontrar cada boton por tag
                            for (int i=1; i <= loadn; i++) {
                                String butagxa = "xa"+i;
                                EditText content = layoutxa.findViewWithTag(butagxa);
                                String restoredata = String.valueOf(a[i-1][0]);
                                if(!restoredata.equals("0.0")){
                                    content.setText(restoredata);
                                }
                            }
                        } catch (Exception ignored) {
                        }
                        Toast.makeText(MainActivity.this, numbtn + " cargar", Toast.LENGTH_SHORT).show();
                    }else if(loadvar2 == 2){
                        layouty.removeAllViews();
                        layoutxa.removeAllViews();
                        layoutxb.removeAllViews();
                        choicesv2.check(R.id.twovar);
                        nnumberx2.setText(String.valueOf(loadn2));
                        String arrayy = sp.getString("arrayY"+numbtn,null);
                        String arrayx = sp.getString("arrayX"+numbtn,null);
                        double[] ym = gson.fromJson(arrayy, double[].class);
                        double[][] b = gson.fromJson(arrayx, double[][].class);
                        try {
                            for (int i=1; i <= loadn2; i++) {
                                String butagy = "ytwo"+i;
                                EditText content = layouty.findViewWithTag(butagy);
                                String restoredata = String.valueOf(ym[i-1]);
                                if(!restoredata.equals("0.0")){
                                    content.setText(restoredata);
                                }
                            }
                        } catch (Exception ignored) {
                        }
                        try {
                            //(xa) for para encontrar cada boton por tag
                            for (int i=1; i <= loadn2; i++) {
                                String butagxa = "xatwo"+i;
                                EditText content = layoutxa.findViewWithTag(butagxa);
                                String restoredata = String.valueOf(b[i-1][0]);
                                if(!restoredata.equals("0.0")){
                                    content.setText(restoredata);
                                }
                            }
                        } catch (Exception ignored) {
                        }
                        try {
                            //(xb) for para encontrar cada boton por tag
                            for (int i=1; i <= loadn2; i++) {
                                String butagxa = "xbtwo"+i;
                                EditText content = layoutxb.findViewWithTag(butagxa);
                                String restoredata = String.valueOf(b[i-1][1]);
                                if(!restoredata.equals("0.0")){
                                    content.setText(restoredata);
                                }
                            }
                        } catch (Exception ignored) {
                        }
                        Toast.makeText(MainActivity.this, numbtn + " cargar", Toast.LENGTH_SHORT).show();
                    }
                } else if(saveradio.isChecked()){
                    if(onevar.isChecked()){
                        String nvaluestr = nvalue.getText().toString(); //obtener cantidad de filas
                        int nvalueint;
                        try {
                            nvalueint = Integer.parseInt(nvaluestr);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putInt("nvalueone"+numbtn, nvalueint);
                            editor.putInt("varvalueone"+numbtn, 1);
                            editor.apply();
                        }
                        catch(NumberFormatException ex) {
                            return;
                        }
                        double[][] a = new double[nvalueint][2];
                        //serie de try's para evitar eliminar (guarda info en las matrices) el input al añadir mas filas
                        try {
                            //(y) for para encontrar cada boton por tag
                            for (int i=1; i <= nvalueint; i++) {
                                String butagy = "y"+i;
                                EditText content = layouty.findViewWithTag(butagy);
                                String contentstring = content.getText().toString();
                                try {
                                    double contentint = Double.parseDouble(contentstring);
                                    a[i-1][1] = contentint;
                                } catch(Exception ignored) {
                                }
                            }
                        } catch (Exception ignored) {
                        }
                        try {
                            //(xa) for para encontrar cada boton por tag
                            for (int i=1; i <= nvalueint; i++) {
                                String butagxa = "xa"+i;
                                EditText content = layoutxa.findViewWithTag(butagxa);
                                String contentstring = content.getText().toString();
                                try {
                                    double contentint = Double.parseDouble(contentstring);
                                    a[i-1][0] = contentint;
                                } catch(Exception ignored) {
                                }
                            }
                        } catch (Exception ignored) {
                        }
                        //Transforma el array en string para almacenarlo en sharedpreferences.
                        String arraytostring = gson.toJson(a);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("arrayonev"+numbtn, arraytostring);
                        editor.apply();
                        int loadn = sp.getInt("nvalueone"+numbtn, 0);
                        int loadvar = sp.getInt("varvalueone"+numbtn, 0);
                        if (loadn >= 4 && loadvar > 0){
                            btn.setBackgroundResource(R.drawable.rect4);
                        }
                        Toast.makeText(MainActivity.this, numbtn + " guardar", Toast.LENGTH_SHORT).show();
                    } else if(twovar.isChecked()){
                        String nvaluestr = nvalue.getText().toString(); //obtener cantidad de filas
                        int nvalueint;
                        try {
                            nvalueint = Integer.parseInt(nvaluestr);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.remove("varvalueone"+numbtn);
                            editor.putInt("nvaluetwo"+numbtn, nvalueint);
                            editor.putInt("varvaluetwo"+numbtn, 2);
                            editor.apply();
                        }
                        catch(NumberFormatException ex) {
                            return;
                        }
                        double[][] b = new double[nvalueint][2];
                        double[] ym = new double[nvalueint];
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
                        //Transforma el array en string para almacenarlo en sharedpreferences.
                        String arrayY = gson.toJson(ym);
                        String arrayX = gson.toJson(b);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("arrayY"+numbtn, arrayY);
                        editor.putString("arrayX"+numbtn, arrayX);
                        editor.apply();
                        int loadn = sp.getInt("nvalueone"+numbtn, 0);
                        int loadn2 = sp.getInt("nvaluetwo"+numbtn, 0);
                        int loadvar = sp.getInt("varvalueone"+numbtn, 0);
                        int loadvar2 = sp.getInt("varvaluetwo"+numbtn, 0);
                        if ((loadn >= 4 || loadn2 >= 4) && (loadvar > 0 || loadvar2 > 0)){
                            btn.setBackgroundResource(R.drawable.rect4);
                        }
                        Toast.makeText(MainActivity.this, numbtn + " guardar", Toast.LENGTH_SHORT).show();
                    }
                } else if(del.isChecked()){
                    SharedPreferences.Editor editor = sp.edit();
                    editor.remove("arrayonev"+numbtn);
                    editor.remove("arrayY"+numbtn);
                    editor.remove("arrayX"+numbtn);
                    editor.remove("nvalueone"+numbtn);
                    editor.remove("nvaluetwo"+numbtn);
                    editor.remove("varvalueone"+numbtn);
                    editor.remove("varvaluetwo"+numbtn);
                    editor.apply();
                    btn.setBackgroundResource(R.drawable.rect5);
                }
            }
        });
    }
}



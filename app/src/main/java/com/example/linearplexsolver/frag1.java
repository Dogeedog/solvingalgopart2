package com.example.linearplexsolver;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import org.hipparchus.distribution.continuous.FDistribution;
import org.hipparchus.distribution.continuous.TDistribution;
import org.hipparchus.linear.Array2DRowRealMatrix;
import org.hipparchus.linear.MatrixUtils;
import org.hipparchus.linear.RealMatrix;
import org.hipparchus.stat.descriptive.moment.StandardDeviation;
import org.hipparchus.stat.descriptive.moment.Variance;
import org.hipparchus.stat.regression.OLSMultipleLinearRegression;
import org.hipparchus.stat.regression.SimpleRegression;
import org.matheclipse.core.expression.S;
import org.matheclipse.core.interfaces.IExpr;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Objects;


public class frag1 extends Fragment {


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
    EditText conf;
    RadioButton choiceonevar;
    RadioButton choicetwovar;
    Button newstatebtn;
    ImageButton deletebtn;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    Gson gson = new GsonBuilder().create();

    public frag1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag1, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        //Genera los botones de guardar/cargar que el usuario añadió previamente
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(requireActivity());
        //sp.edit().clear().apply();
        int count = sp.getInt("count", 0);
        for (int i = 0; i < count; i++) {
            LinearLayout layout = getView().findViewById(R.id.saveloadlayout);
            LinearLayout layout2 = getView().findViewById(R.id.saveloadtv);
            int width2 = (int) getResources().getDimension(R.dimen.widthstate2);
            int width = (int) getResources().getDimension(R.dimen.widthstate);
            int height = (int) getResources().getDimension(R.dimen.heightstate);
            int topmargin = (int) getResources().getDimension(R.dimen.marginstate); //top margin
            int textsize = (int) getResources().getDimension(R.dimen.textstate);
            Button btn = new Button(requireActivity());
            btn.setTextSize(textsize);
            btn.setText(String.valueOf(i + 1));
            int tempid = View.generateViewId();
            btn.setId(tempid);
            btn.setTag("state" + i + 1);
            btn.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            btn.setTextColor(Color.BLACK);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            params.setMargins(0, 0, 0, topmargin);
            btn.setLayoutParams(params);
            layout.addView(btn);


            int ivalue = i + 1;


            EditText tv = new EditText(requireActivity());
            String tvOldStrContent = sp.getString("tvlabel" + ivalue, "");
            tv.setText(tvOldStrContent);
            tv.setLineSpacing(1, 1);
            tv.setTextSize(textsize);
            int tempid2 = View.generateViewId();
            tv.setId(tempid2);
            tv.setTag("statetv" + ivalue);
            tv.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            tv.setTextColor(Color.WHITE);
            tv.setHint(getResources().getString(R.string.tvsave));
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(width2, height);
            params2.setMargins(0, 0, 0, topmargin);
            tv.setLayoutParams(params2);
            layout2.addView(tv);

            //cuando realmente se guarda algo de información q se cambie el color del boton
            int loadn = sp.getInt("nvalueone" + ivalue, 0);
            int loadn2 = sp.getInt("nvaluetwo" + ivalue, 0);
            int loadvar = sp.getInt("varvalueone" + ivalue, 0);
            int loadvar2 = sp.getInt("varvaluetwo" + ivalue, 0);
            if ((loadn >= 4 || loadn2 >= 4) && (loadvar > 0 || loadvar2 > 0)) {
                btn.setBackgroundResource(R.drawable.rect4);
                tv.setBackgroundResource(R.drawable.rect);
                tv.setEnabled(true);
            } else {
                btn.setBackgroundResource(R.drawable.rect5);
                tv.setBackgroundResource(R.drawable.rect3);
                tv.setEnabled(false);
            }

            tv.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {


                }

                @Override
                public void afterTextChanged(Editable s) {
                    String tvcontent = "";
                    try {
                        tvcontent = tv.getText().toString();
                    } catch (Exception ignored) {
                    }
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("tvlabel" + ivalue, tvcontent);
                    editor.apply();

                }
            });


            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    state = getView().findViewById(R.id.saveorload);
                    saveradio = getView().findViewById(R.id.save);
                    loadradio = getView().findViewById(R.id.load);
                    choicesv2 = getView().findViewById(R.id.vargroup);
                    onevar = getView().findViewById(R.id.onevar);
                    twovar = getView().findViewById(R.id.twovar);
                    del = getView().findViewById(R.id.eraseslot);
                    LinearLayout layouty = getView().findViewById(R.id.ylayout);
                    LinearLayout layoutxa = getView().findViewById(R.id.xalayout);
                    LinearLayout layoutxb = getView().findViewById(R.id.xblayout);
                    if (loadradio.isChecked()) {
                        int loadn = sp.getInt("nvalueone" + ivalue, 0);
                        int loadn2 = sp.getInt("nvaluetwo" + ivalue, 0);
                        int loadvar2 = sp.getInt("varvaluetwo" + ivalue, 0);
                        int loadvar = sp.getInt("varvalueone" + ivalue, 0);
                        EditText nnumberx = getView().findViewById(R.id.nnumber);
                        EditText nnumberx2 = getView().findViewById(R.id.nnumber2);
                        //1 o 2 para respetar lo que haya guardado el usuario, son almacenados en sp cuando el usuario guarda
                        if (loadvar == 1) {
                            layouty.removeAllViews();
                            layoutxa.removeAllViews();
                            choicesv2.check(R.id.onevar);
                            nnumberx.setText(String.valueOf(loadn));
                            String array = sp.getString("arrayonev" + ivalue, null);
                            double[][] a = gson.fromJson(array, double[][].class);
                            //aqui se obtiene el array en forma y con los siguientes for se introduce toda la información guardada
                            try {
                                //(y) for para encontrar cada boton por tag
                                for (int i = 1; i <= loadn; i++) {
                                    String butagy = "y" + i;
                                    EditText content = layouty.findViewWithTag(butagy);
                                    String restoredata = String.valueOf(a[i - 1][1]);
                                    if (!restoredata.equals("0.0")) {
                                        content.setText(restoredata);
                                    }
                                }
                            } catch (Exception ignored) {
                            }
                            try {
                                //(xa) for para encontrar cada boton por tag
                                for (int i = 1; i <= loadn; i++) {
                                    String butagxa = "xa" + i;
                                    EditText content = layoutxa.findViewWithTag(butagxa);
                                    String restoredata = String.valueOf(a[i - 1][0]);
                                    if (!restoredata.equals("0.0")) {
                                        content.setText(restoredata);
                                    }
                                }
                            } catch (Exception ignored) {
                            }
                            //aqui iba el toast de cargar
                        } else if (loadvar2 == 2) {
                            layouty.removeAllViews();
                            layoutxa.removeAllViews();
                            layoutxb.removeAllViews();
                            choicesv2.check(R.id.twovar);
                            nnumberx2.setText(String.valueOf(loadn2));
                            String arrayy = sp.getString("arrayY" + ivalue, null);
                            String arrayx = sp.getString("arrayX" + ivalue, null);
                            double[] ym = gson.fromJson(arrayy, double[].class);
                            double[][] b = gson.fromJson(arrayx, double[][].class);
                            try {
                                for (int i = 1; i <= loadn2; i++) {
                                    String butagy = "ytwo" + i;
                                    EditText content = layouty.findViewWithTag(butagy);
                                    String restoredata = String.valueOf(ym[i - 1]);
                                    if (!restoredata.equals("0.0")) {
                                        content.setText(restoredata);
                                    }
                                }
                            } catch (Exception ignored) {
                            }
                            try {
                                //(xa) for para encontrar cada boton por tag
                                for (int i = 1; i <= loadn2; i++) {
                                    String butagxa = "xatwo" + i;
                                    EditText content = layoutxa.findViewWithTag(butagxa);
                                    String restoredata = String.valueOf(b[i - 1][0]);
                                    if (!restoredata.equals("0.0")) {
                                        content.setText(restoredata);
                                    }
                                }
                            } catch (Exception ignored) {
                            }
                            try {
                                //(xb) for para encontrar cada boton por tag
                                for (int i = 1; i <= loadn2; i++) {
                                    String butagxa = "xbtwo" + i;
                                    EditText content = layoutxb.findViewWithTag(butagxa);
                                    String restoredata = String.valueOf(b[i - 1][1]);
                                    if (!restoredata.equals("0.0")) {
                                        content.setText(restoredata);
                                    }
                                }
                            } catch (Exception ignored) {
                            }
                            //aqui iba el toast de cargar
                        }
                    } else if (saveradio.isChecked()) {
                        if (onevar.isChecked()) {
                            String nvaluestr = nvalue.getText().toString(); //obtener cantidad de filas
                            int nvalueint;
                            try {
                                nvalueint = Integer.parseInt(nvaluestr);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putInt("nvalueone" + ivalue, nvalueint);
                                editor.putInt("varvalueone" + ivalue, 1);
                                editor.apply();
                                int loadn = sp.getInt("nvalueone" + ivalue, 0);
                                int loadvar = sp.getInt("varvalueone" + ivalue, 0);
                                if (loadn >= 4 && loadvar > 0) {
                                    btn.setBackgroundResource(R.drawable.rect4);
                                    String tvtag = "statetv" + ivalue;
                                    EditText tv = layout2.findViewWithTag(tvtag);
                                    tv.setBackgroundResource(R.drawable.rect);
                                    tv.setEnabled(true);
                                }
                            } catch (NumberFormatException ex) {
                                return;
                            }
                            double[][] a = new double[nvalueint][2];
                            //serie de try's para evitar eliminar (guarda info en las matrices) el input al añadir mas filas
                            try {
                                //(y) for para encontrar cada boton por tag
                                for (int i = 1; i <= nvalueint; i++) {
                                    String butagy = "y" + i;
                                    EditText content = layouty.findViewWithTag(butagy);
                                    String contentstring = content.getText().toString();
                                    try {
                                        double contentint = Double.parseDouble(contentstring);
                                        a[i - 1][1] = contentint;
                                    } catch (Exception ignored) {
                                    }
                                }
                            } catch (Exception ignored) {
                            }
                            try {
                                //(xa) for para encontrar cada boton por tag
                                for (int i = 1; i <= nvalueint; i++) {
                                    String butagxa = "xa" + i;
                                    EditText content = layoutxa.findViewWithTag(butagxa);
                                    String contentstring = content.getText().toString();
                                    try {
                                        double contentint = Double.parseDouble(contentstring);
                                        a[i - 1][0] = contentint;
                                    } catch (Exception ignored) {
                                    }
                                }
                            } catch (Exception ignored) {
                            }
                            //Transforma el array en string para almacenarlo en sharedpreferences.
                            String arraytostring = gson.toJson(a);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("arrayonev" + ivalue, arraytostring);
                            editor.apply();
                            //aqui iba el toast de guardar
                        } else if (twovar.isChecked()) {
                            String nvaluestr = nvalue.getText().toString(); //obtener cantidad de filas
                            int nvalueint;
                            try {
                                nvalueint = Integer.parseInt(nvaluestr);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.remove("varvalueone" + ivalue);
                                editor.putInt("nvaluetwo" + ivalue, nvalueint);
                                editor.putInt("varvaluetwo" + ivalue, 2);
                                editor.apply();
                                int loadn = sp.getInt("nvaluetwo" + ivalue, 0);
                                int loadvar = sp.getInt("varvaluetwo" + ivalue, 0);
                                if (loadn >= 4 && (loadvar > 0 || loadvar2 > 0)) {
                                    btn.setBackgroundResource(R.drawable.rect4);
                                    String tvtag = "statetv" + ivalue;
                                    EditText tv = layout2.findViewWithTag(tvtag);
                                    tv.setBackgroundResource(R.drawable.rect);
                                    tv.setEnabled(true);
                                }
                            } catch (NumberFormatException ex) {
                                return;
                            }
                            double[][] b = new double[nvalueint][2];
                            double[] ym = new double[nvalueint];
                            //serie de try's para evitar eliminar (guarda info en las matrices) el input al añadir mas filas
                            try {
                                for (int i = 1; i <= nvalueint; i++) {
                                    String butagy = "ytwo" + i;
                                    EditText content = layouty.findViewWithTag(butagy);
                                    String contentstring = content.getText().toString();
                                    try {
                                        double contentint = Double.parseDouble(contentstring);
                                        ym[i - 1] = contentint;
                                    } catch (Exception ignored) {
                                    }
                                }
                            } catch (Exception ignored) {
                            }
                            try {
                                //(xa) for para encontrar cada boton por tag
                                for (int i = 1; i <= nvalueint; i++) {
                                    String butagxa = "xatwo" + i;
                                    EditText content = layoutxa.findViewWithTag(butagxa);
                                    String contentstring = content.getText().toString();
                                    try {
                                        double contentint = Double.parseDouble(contentstring);
                                        b[i - 1][0] = contentint;
                                    } catch (Exception ignored) {
                                    }
                                }
                            } catch (Exception ignored) {
                            }
                            try {
                                //(xb) for para encontrar cada boton por tag
                                for (int i = 1; i <= nvalueint; i++) {
                                    String butagxa = "xbtwo" + i;
                                    EditText content = layoutxb.findViewWithTag(butagxa);
                                    String contentstring = content.getText().toString();
                                    try {
                                        double contentint = Double.parseDouble(contentstring);
                                        b[i - 1][1] = contentint;
                                    } catch (Exception ignored) {
                                    }
                                }
                            } catch (Exception ignored) {
                            }
                            //Transforma el array en string para almacenarlo en sharedpreferences.
                            String arrayY = gson.toJson(ym);
                            String arrayX = gson.toJson(b);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("arrayY" + ivalue, arrayY);
                            editor.putString("arrayX" + ivalue, arrayX);
                            editor.apply();
                            //aqui iba el toast de guardar
                        }
                    } else if (del.isChecked()) {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.remove("arrayonev" + ivalue);
                        editor.remove("arrayY" + ivalue);
                        editor.remove("arrayX" + ivalue);
                        editor.remove("nvalueone" + ivalue);
                        editor.remove("nvaluetwo" + ivalue);
                        editor.remove("varvalueone" + ivalue);
                        editor.remove("varvaluetwo" + ivalue);
                        editor.apply();
                        btn.setBackgroundResource(R.drawable.rect5);
                        String tvtag = "statetv" + ivalue;
                        EditText tv = layout2.findViewWithTag(tvtag);
                        try {
                            tv.getText().clear();
                        } catch (Exception ignored) {
                        }
                        tv.setBackgroundResource(R.drawable.rect3);
                        tv.setEnabled(false);
                    }
                }
            });
        }


        mdrawer = getView().findViewById(R.id.drawer_layout);
        toggle = getView().findViewById(R.id.savingbutton);
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mdrawer.isDrawerOpen(GravityCompat.START))
                    mdrawer.openDrawer(GravityCompat.START);
                else mdrawer.closeDrawer(GravityCompat.END);
            }

        });


        ((TextView) getView().findViewById(R.id.textView)).setText(Html.fromHtml("y=B<sub><small>o</small></sub>")); //asigna texto a la view donde va el modelo
        (getView().findViewById(R.id.textView)).setEnabled(false);

        mean1 = getView().findViewById(R.id.mean1);
        mean1.setEnabled(false);
        mean1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        mean2 = getView().findViewById(R.id.mean2);
        mean2.setEnabled(false);
        mean2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        pre1 = getView().findViewById(R.id.pre1);
        pre1.setEnabled(false);
        pre1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        pre2 = getView().findViewById(R.id.pre2);
        pre2.setEnabled(false);
        pre2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);

        conf = getView().findViewById(R.id.percetagenumber);
        conf.setInputType(InputType.TYPE_CLASS_NUMBER);
        conf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String conflevel = conf.getText().toString();
                int confnumber;
                try {
                    confnumber = Integer.parseInt(conflevel);
                } catch (NumberFormatException ex) {
                    return;
                }
                if (confnumber >= 1 && confnumber <= 100) {
                    conf.setTextColor(Color.WHITE);
                } else {
                    Toast.makeText(requireActivity(), R.string.confalert, Toast.LENGTH_SHORT).show();
                    conf.setTextColor(Color.RED);
                }
            }
        });

        icmean = getView().findViewById(R.id.respuesta);
        choicesv2 = getView().findViewById(R.id.vargroup);
        choiceonevar = getView().findViewById(R.id.onevar);
        choicetwovar = getView().findViewById(R.id.twovar);
        icpred = getView().findViewById(R.id.prediccion);

        //listener del checkbox ic media para habilitar/deshabilitar el input
        icmean.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (choiceonevar.isChecked()) {
                    mean1.setEnabled(true);
                    mean1.setBackgroundResource(R.drawable.rect);
                } else if (choicetwovar.isChecked()) {
                    mean1.setEnabled(true);
                    mean1.setBackgroundResource(R.drawable.rect);
                    mean2.setEnabled(true);
                    mean2.setBackgroundResource(R.drawable.rect);
                }

                if (!isChecked) {
                    mean1.getText().clear();
                    mean2.getText().clear();
                    mean1.setEnabled(false);
                    mean2.setEnabled(false);
                    mean1.setBackgroundResource(R.drawable.rectblocked);
                    mean2.setBackgroundResource(R.drawable.rectblocked);
                }
            }
        });

        //listener del checkbox ic predicción para habilitar/deshabilitar el input
        icpred.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (choiceonevar.isChecked()) {
                    pre1.setEnabled(true);
                    pre1.setBackgroundResource(R.drawable.rect);
                } else if (choicetwovar.isChecked()) {
                    pre1.setEnabled(true);
                    pre1.setBackgroundResource(R.drawable.rect);
                    pre2.setEnabled(true);
                    pre2.setBackgroundResource(R.drawable.rect);
                }
                if (!isChecked) {
                    pre1.getText().clear();
                    pre2.getText().clear();
                    pre1.setEnabled(false);
                    pre2.setEnabled(false);
                    pre1.setBackgroundResource(R.drawable.rectblocked);
                    pre2.setBackgroundResource(R.drawable.rectblocked);
                }
            }
        });

        ((TextView) getView().findViewById(R.id.mean1)).setHint(Html.fromHtml("x<sub><small>1</small></sub>"));
        ((TextView) getView().findViewById(R.id.mean2)).setHint(Html.fromHtml("x<sub><small>2</small></sub>"));
        ((TextView) getView().findViewById(R.id.pre1)).setHint(Html.fromHtml("x<sub><small>1</small></sub>"));
        ((TextView) getView().findViewById(R.id.pre2)).setHint(Html.fromHtml("x<sub><small>2</small></sub>"));
        ((TextView) getView().findViewById(R.id.pre2)).setHint(Html.fromHtml("x<sub><small>2</small></sub>"));
        ((TextView) getView().findViewById(R.id.xacol)).setText(Html.fromHtml("x<sub><small>1</small></sub>"));
        ((TextView) getView().findViewById(R.id.xbcol)).setText(Html.fromHtml("x<sub><small>2</small></sub>"));


        choicesv2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    //Para 1 entrada y 1 de respuesta
                    case R.id.onevar:
                        icmean.setChecked(false);
                        icpred.setChecked(false);
                        xbhead = getView().findViewById(R.id.xbcol);
                        xbhead.setVisibility(View.INVISIBLE);
                        ((TextView) getView().findViewById(R.id.textView)).setText(Html.fromHtml("y=B<sub><small>o</small></sub> + B<sub><small>1</small></sub>" +
                                "X<sub><small>1</small></sub>"));
                        nvalue = getView().findViewById(R.id.nnumber);
                        nvalue.setVisibility(View.VISIBLE);
                        nvaluedel = getView().findViewById(R.id.nnumber2);
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
                                } catch (NumberFormatException ex) {
                                    return;
                                }
                                double[][] a = new double[nvalueint][2];
                                if (nvalueint >= 4) {
                                    LinearLayout layout = getView().findViewById(R.id.nlayout);
                                    LinearLayout layouty = getView().findViewById(R.id.ylayout);
                                    LinearLayout layoutxa = getView().findViewById(R.id.xalayout);
                                    LinearLayout layoutxb = getView().findViewById(R.id.xblayout);
                                    //serie de try's para evitar eliminar (guarda info en las matrices) el input al añadir mas filas
                                    try {
                                        //(y) for para encontrar cada boton por tag
                                        for (int i = 1; i <= nvalueint; i++) {
                                            String butagy = "y" + i;
                                            EditText content = layouty.findViewWithTag(butagy);
                                            String contentstring = content.getText().toString();
                                            try {
                                                double contentint = Double.parseDouble(contentstring);
                                                a[i - 1][1] = contentint;
                                            } catch (Exception ignored) {
                                            }
                                        }
                                    } catch (Exception ignored) {
                                    }
                                    try {
                                        //(xa) for para encontrar cada boton por tag
                                        for (int i = 1; i <= nvalueint; i++) {
                                            String butagxa = "xa" + i;
                                            EditText content = layoutxa.findViewWithTag(butagxa);
                                            String contentstring = content.getText().toString();
                                            try {
                                                double contentint = Double.parseDouble(contentstring);
                                                a[i - 1][0] = contentint;
                                            } catch (Exception ignored) {
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
                                        TextView btn = new TextView(requireActivity());
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
                                        EditText btn = new EditText(requireActivity());
                                        btn.setTextSize(textsize);
                                        String pastextt = String.valueOf(a[i - 1][1]);
                                        if (!pastextt.equals("0.0")) {
                                            btn.setText(pastextt);
                                        }
                                        String butag = "y" + i;
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
                                        EditText btn = new EditText(requireActivity());
                                        btn.setTextSize(textsize);
                                        String pastextt = String.valueOf(a[i - 1][0]);
                                        if (!pastextt.equals("0.0")) {
                                            btn.setText(pastextt);
                                        }
                                        String butag = "xa" + i;
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
                                    Button tebtn = getView().findViewById(R.id.button2);
                                    tebtn.setOnClickListener(v1 -> {
                                        //(y) for para encontrar cada boton por tag
                                        for (int i = 1; i <= nvalueint; i++) {
                                            String butagy = "y" + i;
                                            EditText content = layouty.findViewWithTag(butagy);
                                            String contentstring = content.getText().toString();
                                            try {
                                                double contentint = Double.parseDouble(contentstring);
                                                a[i - 1][1] = contentint;
                                            } catch (NumberFormatException ex) {
                                                return;
                                            }
                                        }
                                        //(xa) for para encontrar cada boton por tag
                                        for (int i = 1; i <= nvalueint; i++) {
                                            String butagxa = "xa" + i;
                                            EditText content = layoutxa.findViewWithTag(butagxa);
                                            String contentstring = content.getText().toString();
                                            try {
                                                double contentint = Double.parseDouble(contentstring);
                                                a[i - 1][0] = contentint;
                                            } catch (NumberFormatException ex) {
                                                return;
                                            }
                                        }

                                        String conflevel = conf.getText().toString();
                                        double confnumber;
                                        try {
                                            confnumber = Double.parseDouble(conflevel);
                                        } catch (NumberFormatException ex) {
                                            Toast.makeText(requireActivity(), R.string.confalert, Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        //obtiene el double del valor del nivel de confianza
                                        if (confnumber >= 1 && confnumber <= 100) {
                                            //obtiene el modelo
                                            SimpleRegression regression = new SimpleRegression();
                                            DecimalFormat df = new DecimalFormat("#.####; - #");
                                            df.setRoundingMode(RoundingMode.HALF_UP);
                                            DecimalFormat df2 = new DecimalFormat("+ #.####;- #");
                                            df2.setRoundingMode(RoundingMode.HALF_UP);
                                            // a array {x, y }
                                            regression.addData(a);
                                            double[] coef = regression.regress().getParameterEstimates();
                                            String b0 = (df.format(coef[0]));
                                            String b1 = (df2.format(coef[1]));
                                            String xone = "X1";
                                            String fullstr = "y= " + b0 + " " + b1 + xone;
                                            Spannable modelstr = new SpannableString(fullstr);
                                            modelstr.setSpan(new SubscriptSpan(), (fullstr.indexOf(xone) + 1), (fullstr.indexOf(xone) + 2), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                            modelstr.setSpan(new RelativeSizeSpan(0.75f), (fullstr.indexOf(xone)) + 1, (fullstr.indexOf(xone) + 2), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                            Intent j = new Intent(requireActivity(), solvedone.class);
                                            j.putExtra("nvalueres", nvalueint);
                                            j.putExtra("modelstr", modelstr);
                                            j.putExtra("b0", coef[0]);
                                            j.putExtra("b1", coef[1]);
                                            String regarraytostring = gson.toJson(a);
                                            j.putExtra("regarray", regarraytostring);


                                            double[][] res = new double[nvalueint][3];
                                            double modres;
                                            double resi;
                                            double totalx = 0;
                                            for (int i = 1; i <= nvalueint; i++) {
                                                //media de variable independiente
                                                totalx = totalx + a[i - 1][0];
                                                //y = b0 + b1*x1
                                                modres = coef[0] + (coef[1]) * (a[i - 1][0]);
                                                resi = (a[i - 1][1]) - modres;
                                                //y data
                                                res[i - 1][0] = a[i - 1][1];
                                                //y modelo
                                                res[i - 1][1] = modres;
                                                //residual
                                                res[i - 1][2] = resi;
                                                if (i == nvalueint) {
                                                    String resarraytostring = gson.toJson(res);
                                                    j.putExtra("res1array", resarraytostring);
                                                }
                                            }

                                            //sacar leverage values
                                            //formula: https://xiangyuw.medium.com/high-leverage-points-in-simple-linear-regression-d7bfed545540
                                            double totalxavg = totalx / nvalueint;
                                            double SSxMeanDifference = 0;
                                            double[] leveragevaluesone = new double[nvalueint];
                                            double ninverse = 1.0 / nvalueint;

                                            for (int i = 1; i <= nvalueint; i++) {
                                                SSxMeanDifference = SSxMeanDifference + Math.pow(a[i - 1][0] - totalxavg, 2);
                                            }

                                            for (int i = 1; i <= nvalueint; i++) {
                                                double op1 = Math.pow(a[i - 1][0] - totalxavg, 2);
                                                double op2 = ninverse + (op1 / SSxMeanDifference);
                                                leveragevaluesone[i - 1] = op2;
                                            }
                                            String leveragestrone = gson.toJson(leveragevaluesone);
                                            j.putExtra("leverageone", leveragestrone);


                                            //anova values
                                            double SSTotalone = regression.regress().getTotalSumSquares();
                                            double SSErrorOne = regression.regress().getErrorSumSquares();
                                            j.putExtra("SSTone", SSTotalone);
                                            j.putExtra("SSEone", SSErrorOne);

                                            //valor de t student
                                            double confnumberpercent = (1 - confnumber / 100) / 2;
                                            TDistribution tdist = new TDistribution(nvalueint - 2);
                                            double tvalue = (tdist.inverseCumulativeProbability(confnumberpercent) * (-1));

                                            //valor fisher
                                            double confnumberpercentf = (confnumber / 100);
                                            FDistribution fdist = new FDistribution(1, nvalueint - 2);
                                            double fvalue = fdist.inverseCumulativeProbability(confnumberpercentf);
                                            j.putExtra("confvalue", confnumberpercentf);
                                            j.putExtra("fvalue", fvalue);

                                            //MSE^2
                                            double variance = regression.regress().getMeanSquareError();
                                            j.putExtra("varianceone", variance);

                                            //error estándar por cada coeficiente
                                            double[] varstderror = regression.regress().getStdErrorOfEstimates();
                                            j.putExtra("var1stdone", varstderror[0]);
                                            j.putExtra("var2stdone", varstderror[1]);

                                            //coef determinacion
                                            double coefreg = regression.getRSquare();
                                            j.putExtra("coefregone", coefreg);

                                            //significancia de regresión
                                            double To = coef[1] / varstderror[1];
                                            j.putExtra("tovalue", To);
                                            if (To > 0) {
                                                j.putExtra("tvalue", tvalue);
                                                if (To > tvalue) {
                                                    j.putExtra("signif", "true");
                                                } else {
                                                    j.putExtra("signif", "false");
                                                }
                                            } else {
                                                double tvalue2 = -tvalue;
                                                j.putExtra("tvalue", tvalue2);
                                                if (To > tvalue2) {
                                                    j.putExtra("signif", "false");
                                                } else {
                                                    j.putExtra("signif", "true");
                                                }
                                            }

                                            //intervalo coef b1
                                            double cb1left = coef[1] - (tvalue * varstderror[1]);
                                            double cb1right = coef[1] + (tvalue * varstderror[1]);
                                            j.putExtra("icb1left", cb1left);
                                            j.putExtra("icb1right", cb1right);

                                            //intervalo coef b0
                                            double cb0left = coef[0] - (tvalue * varstderror[0]);
                                            double cb0right = coef[0] + (tvalue * varstderror[0]);
                                            j.putExtra("icb0left", cb0left);
                                            j.putExtra("icb0right", cb0right);

                                            //intervalo media
                                            if (icmean.isChecked()) {
                                                String mediainput = mean1.getText().toString();
                                                double mediadouble;
                                                try {
                                                    mediadouble = Double.parseDouble(mediainput);
                                                } catch (NumberFormatException ex) {
                                                    return;
                                                }
                                                double myox = coef[0] + coef[1] * mediadouble;
                                                double sxxmedia = regression.getXSumSquares();
                                                double mediapart1 = (Math.pow((mediadouble - totalxavg), 2)) / sxxmedia;
                                                double mediapart2 = variance * ((1.0 / nvalueint) + mediapart1);
                                                double icmedleft = myox - (tvalue) * (Math.sqrt(mediapart2));
                                                double icmedright = myox + (tvalue) * (Math.sqrt(mediapart2));
                                                j.putExtra("icmedleft", icmedleft);
                                                j.putExtra("icmedright", icmedright);
                                            }

                                            //intervalo prediccion
                                            if (icpred.isChecked()) {
                                                String mediainput = pre1.getText().toString();
                                                double mediadouble;
                                                try {
                                                    mediadouble = Double.parseDouble(mediainput);
                                                } catch (NumberFormatException ex) {
                                                    return;
                                                }
                                                double myox = coef[0] + coef[1] * mediadouble;
                                                double sxxmedia = regression.getXSumSquares();
                                                double mediapart1 = (Math.pow((mediadouble - totalxavg), 2)) / sxxmedia;
                                                double mediapart2 = variance * (1 + (1.0 / nvalueint) + mediapart1);
                                                double icmedleft = myox - (tvalue) * (Math.sqrt(mediapart2));
                                                double icmedright = myox + (tvalue) * (Math.sqrt(mediapart2));
                                                j.putExtra("icpredleft", icmedleft);
                                                j.putExtra("icpredright", icmedright);
                                                //((TextView)findViewById(R.id.textView)).setText(String.valueOf(icmedright));
                                            }


                                            startActivity(j);
                                        } else if (confnumber > 100) {
                                            Toast.makeText(requireActivity(), R.string.confalert, Toast.LENGTH_SHORT).show();
                                        }


                                    });
                                }
                            }
                        });
                        break;
                    //Para 2 entradas y 1 respuesta
                    case R.id.twovar:
                        icmean.setChecked(false);
                        icpred.setChecked(false);
                        xbhead = getView().findViewById(R.id.xbcol);
                        xbhead.setVisibility(View.VISIBLE);
                        ((TextView) getView().findViewById(R.id.textView)).setText(Html.fromHtml("y=B<sub><small>o</small></sub> + B<sub><small>1</small></sub>" +
                                "X<sub><small>1</small></sub> + B<sub><small>2</small></sub>X<sub><small>2</small></sub>"));
                        nvalue = getView().findViewById(R.id.nnumber2);
                        nvalue.setVisibility(View.VISIBLE);
                        nvaluedel = getView().findViewById(R.id.nnumber);
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
                                } catch (NumberFormatException ex) {
                                    return;
                                }
                                double[][] b = new double[nvalueint][2];
                                double[] ym = new double[nvalueint];
                                if (nvalueint >= 4) {
                                    LinearLayout layout = getView().findViewById(R.id.nlayout);
                                    LinearLayout layouty = getView().findViewById(R.id.ylayout);
                                    LinearLayout layoutxa = getView().findViewById(R.id.xalayout);
                                    LinearLayout layoutxb = getView().findViewById(R.id.xblayout);
                                    //serie de try's para evitar eliminar (guarda info en las matrices) el input al añadir mas filas
                                    try {
                                        for (int i = 1; i <= nvalueint; i++) {
                                            String butagy = "ytwo" + i;
                                            EditText content = layouty.findViewWithTag(butagy);
                                            String contentstring = content.getText().toString();
                                            try {
                                                double contentint = Double.parseDouble(contentstring);
                                                ym[i - 1] = contentint;
                                            } catch (Exception ignored) {
                                            }
                                        }
                                    } catch (Exception ignored) {
                                    }
                                    try {
                                        //(xa) for para encontrar cada boton por tag
                                        for (int i = 1; i <= nvalueint; i++) {
                                            String butagxa = "xatwo" + i;
                                            EditText content = layoutxa.findViewWithTag(butagxa);
                                            String contentstring = content.getText().toString();
                                            try {
                                                double contentint = Double.parseDouble(contentstring);
                                                b[i - 1][0] = contentint;
                                            } catch (Exception ignored) {
                                            }
                                        }
                                    } catch (Exception ignored) {
                                    }
                                    try {
                                        //(xb) for para encontrar cada boton por tag
                                        for (int i = 1; i <= nvalueint; i++) {
                                            String butagxa = "xbtwo" + i;
                                            EditText content = layoutxb.findViewWithTag(butagxa);
                                            String contentstring = content.getText().toString();
                                            try {
                                                double contentint = Double.parseDouble(contentstring);
                                                b[i - 1][1] = contentint;
                                            } catch (Exception ignored) {
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
                                        TextView btn = new TextView(requireActivity());
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
                                        EditText btn = new EditText(requireActivity());
                                        btn.setTextSize(textsize);
                                        String pastextt = String.valueOf(ym[i - 1]);
                                        if (!pastextt.equals("0.0")) {
                                            btn.setText(pastextt);
                                        }
                                        String butag = "ytwo" + i;
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
                                        EditText btn = new EditText(requireActivity());
                                        btn.setTextSize(textsize);
                                        String pastextt = String.valueOf(b[i - 1][0]);
                                        if (!pastextt.equals("0.0")) {
                                            btn.setText(pastextt);
                                        }
                                        String butag = "xatwo" + i;
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
                                        EditText btn = new EditText(requireActivity());
                                        btn.setTextSize(textsize);
                                        String pastextt = String.valueOf(b[i - 1][1]);
                                        if (!pastextt.equals("0.0")) {
                                            btn.setText(pastextt);
                                        }
                                        String butag = "xbtwo" + i;
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
                                    Button tebtn = getView().findViewById(R.id.button2);
                                    tebtn.setOnClickListener(v12 -> {
                                        //(y) for para encontrar cada boton por tag
                                        for (int i = 1; i <= nvalueint; i++) {
                                            String butagy = "ytwo" + i;
                                            EditText content = layouty.findViewWithTag(butagy);
                                            String contentstring = content.getText().toString();
                                            try {
                                                double contentint = Double.parseDouble(contentstring);
                                                ym[i - 1] = contentint;
                                            } catch (NumberFormatException ex) {
                                                return;
                                            }
                                        }
                                        //(xa) for para encontrar cada boton por tag
                                        for (int i = 1; i <= nvalueint; i++) {
                                            String butagxa = "xatwo" + i;
                                            EditText content = layoutxa.findViewWithTag(butagxa);
                                            String contentstring = content.getText().toString();
                                            try {
                                                double contentint = Double.parseDouble(contentstring);
                                                b[i - 1][0] = contentint;
                                            } catch (NumberFormatException ex) {
                                                return;
                                            }
                                        }
                                        //(xb) for para encontrar cada boton por tag
                                        for (int i = 1; i <= nvalueint; i++) {
                                            String butagxa = "xbtwo" + i;
                                            EditText content = layoutxb.findViewWithTag(butagxa);
                                            String contentstring = content.getText().toString();
                                            try {
                                                double contentint = Double.parseDouble(contentstring);
                                                b[i - 1][1] = contentint;
                                            } catch (NumberFormatException ex) {
                                                return;
                                            }
                                        }


                                        String conflevel = conf.getText().toString();
                                        double confnumber;
                                        try {
                                            confnumber = Double.parseDouble(conflevel);
                                        } catch (NumberFormatException ex) {
                                            Toast.makeText(requireActivity(), R.string.confalert, Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        if (confnumber >= 1 && confnumber <= 100) {
                                            //modelo de regresion
                                            OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
                                            //formato
                                            DecimalFormat df = new DecimalFormat("#.####; - #");
                                            df.setRoundingMode(RoundingMode.HALF_UP);
                                            DecimalFormat df2 = new DecimalFormat("+ #.####;- #");
                                            df2.setRoundingMode(RoundingMode.HALF_UP);
                                            regression.newSampleData(ym, b);
                                            double[] coef = regression.estimateRegressionParameters();
                                            String b0 = (df.format(coef[0]));
                                            String b1 = (df2.format(coef[1]));
                                            String b2 = (df2.format(coef[2]));
                                            String xone = "X1 ";
                                            String xtwo = "X2";
                                            String fullstr = "y= " + b0 + " " + b1 + xone + b2 + xtwo;
                                            Spannable modelstr = new SpannableString(fullstr);
                                            modelstr.setSpan(new SubscriptSpan(), (fullstr.indexOf(xone) + 1), (fullstr.indexOf(xone) + 2), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                            modelstr.setSpan(new RelativeSizeSpan(0.75f), (fullstr.indexOf(xone)) + 1, (fullstr.indexOf(xone) + 2), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                            modelstr.setSpan(new SubscriptSpan(), (fullstr.indexOf(xtwo) + 1), (fullstr.indexOf(xtwo) + 2), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                            modelstr.setSpan(new RelativeSizeSpan(0.75f), (fullstr.indexOf(xtwo) + 1), (fullstr.indexOf(xtwo) + 2), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                            Intent j = new Intent(requireActivity(), solved.class);
                                            j.putExtra("nvalueres", nvalueint);
                                            j.putExtra("modelstr", modelstr);
                                            j.putExtra("b0t", coef[0]);
                                            j.putExtra("b1t", coef[1]);
                                            j.putExtra("b2t", coef[2]);
                                            String regarraytostring = gson.toJson(b);
                                            j.putExtra("regarray", regarraytostring);

                                            double[] leveragevalues = new double[nvalueint];
                                            RealMatrix hatmatrix = regression.calculateHat();
                                            for (int i = 1; i <= nvalueint; i++) {
                                                leveragevalues[i - 1] = hatmatrix.getEntry(i - 1, i - 1);
                                            }
                                            String leveragestr = gson.toJson(leveragevalues);
                                            j.putExtra("leverage", leveragestr);

                                            double[][] res2 = new double[nvalueint][3];
                                            double modres;
                                            double resi;
                                            double totalx1 = 0;
                                            double totalx2 = 0;
                                            double totaly = 0;
                                            for (int i = 1; i <= nvalueint; i++) {
                                                //total values
                                                totalx1 = totalx1 + b[i - 1][0];
                                                totalx2 = totalx2 + b[i - 1][1];
                                                totaly = totaly + ym[i - 1];
                                                //y = b0 + b1x1 + b2x2
                                                modres = coef[0] + coef[1] * b[i - 1][0] + coef[2] * b[i - 1][1];
                                                resi = ym[i - 1] - modres;
                                                // y data
                                                res2[i - 1][0] = ym[i - 1];
                                                //y modelo
                                                res2[i - 1][1] = modres;
                                                //residual
                                                res2[i - 1][2] = resi;
                                                if (i == nvalueint) {
                                                    String res2arraytostring = gson.toJson(res2);
                                                    j.putExtra("res2array", res2arraytostring);
                                                }
                                            }
                                            //correlation formula: https://www.ncbi.nlm.nih.gov/pmc/articles/PMC374386/

                                            double x1avg = totalx1 / nvalueint;
                                            double x2avg = totalx2 / nvalueint;
                                            double yavg = totaly / nvalueint;
                                            double Corrx1op1 = 0;
                                            double Corrx1op2 = 0;
                                            double CorrYop = 0;
                                            double Corrx2op1 = 0;
                                            double Corrx2op2 = 0;

                                            for (int i = 1; i <= nvalueint; i++) {
                                                CorrYop = CorrYop + Math.pow(ym[i - 1] - yavg, 2);

                                                Corrx1op1 = Corrx1op1 + (b[i - 1][0] - x1avg) * (ym[i - 1] - yavg);
                                                Corrx1op2 = Corrx1op2 + Math.pow(b[i - 1][0] - x1avg, 2);

                                                Corrx2op1 = Corrx2op1 + (b[i - 1][1] - x2avg) * (ym[i - 1] - yavg);
                                                Corrx2op2 = Corrx2op2 + Math.pow(b[i - 1][1] - x2avg, 2);
                                            }

                                            double SquaredCorrx1 = Math.pow(Corrx1op1 / Math.sqrt(Corrx1op2 * CorrYop), 2);
                                            double SquaredCorrx2 = Math.pow(Corrx2op1 / Math.sqrt(Corrx2op2 * CorrYop), 2);


                                            //regresión
                                            double reg = regression.calculateRSquared();

                                            j.putExtra("regtwo", reg);

                                            //valor de t student
                                            double confnumberpercent = (1 - confnumber / 100) / 2;
                                            TDistribution tdist = new TDistribution(nvalueint - 3);
                                            double tvalue = (tdist.inverseCumulativeProbability(confnumberpercent) * (-1));

                                            //ic coeficiente 0
                                            double[] stder = regression.estimateRegressionParametersStandardErrors();
                                            double cb0left = coef[0] - (tvalue * stder[0]);
                                            double cb0right = coef[0] + (tvalue * stder[0]);
                                            j.putExtra("ic0left", cb0left);
                                            j.putExtra("ic0right", cb0right);

                                            //ic coeficiente 1
                                            double cb1left = coef[1] - (tvalue * stder[1]);
                                            double cb1right = coef[1] + (tvalue * stder[1]);
                                            j.putExtra("ic1left", cb1left);
                                            j.putExtra("ic1right", cb1right);

                                            //ic coeficiente 2
                                            double cb2left = coef[2] - (tvalue * stder[2]);
                                            double cb2right = coef[2] + (tvalue * stder[2]);
                                            j.putExtra("ic2left", cb2left);
                                            j.putExtra("ic2right", cb2right);

                                            //valor fisher
                                            double confnumberpercentf = (confnumber / 100);
                                            FDistribution fdist = new FDistribution(2, nvalueint - 3);
                                            double fvalue = fdist.inverseCumulativeProbability(confnumberpercentf);
                                            j.putExtra("confvalue", confnumberpercentf);


                                            //significancia de regresión general
                                            double errosq = (regression.estimateErrorVariance());
                                            j.putExtra("mse", errosq);
                                            double totalsq = regression.calculateTotalSumOfSquares();
                                            double totalerror = errosq * (nvalueint - 3);
                                            double totalreg = totalsq - totalerror;

                                            double percentSquaredCorrx1 = SquaredCorrx1 / (SquaredCorrx1 + SquaredCorrx2);
                                            double percentSquaredCorrx2 = SquaredCorrx2 / (SquaredCorrx1 + SquaredCorrx2);

                                            double SSx1 = percentSquaredCorrx1 * totalreg;
                                            double SSx2 = percentSquaredCorrx2 * totalreg;
                                            j.putExtra("SSx1", SSx1);
                                            j.putExtra("SSx2", SSx2);

                                            j.putExtra("totalsum", totalsq);
                                            j.putExtra("mse2", totalerror);
                                            j.putExtra("totalreg", totalreg);
                                            double fountvar = ((totalsq - errosq * (nvalueint - 3)) / 2) / errosq;
                                            j.putExtra("fountvalue", fountvar);
                                            if (fountvar > 0) {
                                                j.putExtra("fvalue", fvalue);
                                                if (fountvar > fvalue) {
                                                    j.putExtra("signifg", "true");
                                                } else {
                                                    j.putExtra("signifg", "false");
                                                }
                                            } else {
                                                double fvalue2 = -fvalue;
                                                j.putExtra("tvalue", fvalue2);
                                                if (fountvar > fvalue2) {
                                                    j.putExtra("signifg", "false");
                                                } else {
                                                    j.putExtra("signifg", "true");
                                                }
                                            }

                                            //significancia de regresión b1
                                            double To1 = coef[1] / stder[1];
                                            j.putExtra("tovalue1", To1);
                                            if (To1 > 0) {
                                                j.putExtra("tvalue", tvalue);
                                                if (To1 > tvalue) {
                                                    j.putExtra("signif1", "true");
                                                } else {
                                                    j.putExtra("signif1", "false");
                                                }
                                            } else {
                                                double tvalue2 = -tvalue;
                                                j.putExtra("tvalue", tvalue2);
                                                if (To1 > tvalue2) {
                                                    j.putExtra("signif1", "false");
                                                } else {
                                                    j.putExtra("signif1", "true");
                                                }
                                            }

                                            //significancia de regresión b2
                                            double To2 = coef[2] / stder[2];
                                            j.putExtra("tovalue2", To2);
                                            if (To2 > 0) {
                                                j.putExtra("tvalue", tvalue);
                                                if (To2 > tvalue) {
                                                    j.putExtra("signif2", "true");
                                                } else {
                                                    j.putExtra("signif2", "false");
                                                }
                                            } else {
                                                double tvalue2 = -tvalue;
                                                j.putExtra("tvalue", tvalue2);
                                                if (To2 > tvalue2) {
                                                    j.putExtra("signif2", "false");
                                                } else {
                                                    j.putExtra("signif2", "true");
                                                }
                                            }

                                            //ic media
                                            if (icmean.isChecked()) {
                                                String mediainput = mean1.getText().toString();
                                                String mediainput2 = mean2.getText().toString();
                                                double mediadouble;
                                                double mediadouble2;
                                                try {
                                                    mediadouble = Double.parseDouble(mediainput);
                                                    mediadouble2 = Double.parseDouble(mediainput2);
                                                } catch (NumberFormatException ex) {
                                                    return;
                                                }
                                                double variance = regression.estimateErrorVariance();

                                                double myox = coef[0] + coef[1] * mediadouble + coef[2] * mediadouble2;
                                                double[][] Xo = new double[][]{
                                                        {1.0},
                                                        {mediadouble},
                                                        {mediadouble2}
                                                };
                                                double totalx1pwr2 = 0;
                                                double totalx2pwr2 = 0;
                                                double totalx1x2 = 0;
                                                RealMatrix Xor = new Array2DRowRealMatrix(Xo);
                                                RealMatrix Xotr = Xor.transpose();
                                                for (int i = 1; i <= nvalueint; i++) {
                                                    totalx1pwr2 = totalx1pwr2 + Math.pow(b[i - 1][0], 2);
                                                    totalx2pwr2 = totalx2pwr2 + Math.pow(b[i - 1][1], 2);
                                                    totalx1x2 = totalx1x2 + b[i - 1][0] * b[i - 1][1];
                                                }
                                                double[][] XtX = new double[][]{
                                                        {nvalueint, totalx1, totalx2},
                                                        {totalx1, totalx1pwr2, totalx1x2},
                                                        {totalx2, totalx1x2, totalx2pwr2}
                                                };
                                                RealMatrix XtXr = new Array2DRowRealMatrix(XtX);
                                                RealMatrix XtXrinverse = MatrixUtils.inverse(XtXr);
                                                RealMatrix hat = Xotr.multiply(XtXrinverse.multiply(Xor));
                                                double hatvalue = hat.getEntry(0, 0);
                                                double v = tvalue * (Math.sqrt(variance * hatvalue));
                                                double icmedleft = myox - v;
                                                double icmedright = myox + v;
                                                j.putExtra("icmedleft", icmedleft);
                                                j.putExtra("icmedright", icmedright);
                                            }

                                            //ic prediccion
                                            if (icpred.isChecked()) {
                                                String mediainput = pre1.getText().toString();
                                                String mediainput2 = pre2.getText().toString();
                                                double mediadouble;
                                                double mediadouble2;
                                                try {
                                                    mediadouble = Double.parseDouble(mediainput);
                                                    mediadouble2 = Double.parseDouble(mediainput2);
                                                } catch (NumberFormatException ex) {
                                                    return;
                                                }
                                                double variance = regression.estimateErrorVariance();

                                                double myox = coef[0] + coef[1] * mediadouble + coef[2] * mediadouble2;
                                                double[][] Xo = new double[][]{
                                                        {1.0},
                                                        {mediadouble},
                                                        {mediadouble2}
                                                };
                                                double totalx1pwr2 = 0;
                                                double totalx2pwr2 = 0;
                                                double totalx1x2 = 0;
                                                RealMatrix Xor = new Array2DRowRealMatrix(Xo);
                                                RealMatrix Xotr = Xor.transpose();
                                                for (int i = 1; i <= nvalueint; i++) {
                                                    totalx1pwr2 = totalx1pwr2 + Math.pow(b[i - 1][0], 2);
                                                    totalx2pwr2 = totalx2pwr2 + Math.pow(b[i - 1][1], 2);
                                                    totalx1x2 = totalx1x2 + b[i - 1][0] * b[i - 1][1];
                                                }
                                                double[][] XtX = new double[][]{
                                                        {nvalueint, totalx1, totalx2},
                                                        {totalx1, totalx1pwr2, totalx1x2},
                                                        {totalx2, totalx1x2, totalx2pwr2}
                                                };
                                                RealMatrix XtXr = new Array2DRowRealMatrix(XtX);
                                                RealMatrix XtXrinverse = MatrixUtils.inverse(XtXr);
                                                RealMatrix hat = Xotr.multiply(XtXrinverse.multiply(Xor));
                                                double hatvalue = hat.getEntry(0, 0);

                                                double u = tvalue * (Math.sqrt(variance * (1 + hatvalue)));
                                                double icpredleft = myox - u;
                                                double icpredright = myox + u;
                                                j.putExtra("icpredleft", icpredleft);
                                                j.putExtra("icpredright", icpredright);
                                            }


                                            startActivity(j);
                                        } else if (confnumber > 100) {
                                            Toast.makeText(requireActivity(), R.string.confalert, Toast.LENGTH_SHORT).show();
                                        }

                                    });
                                }
                            }
                        });
                        break;
                }
            }
        });
        newstatebtn = getView().findViewById(R.id.addstate);
        newstatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout layout = getView().findViewById(R.id.saveloadlayout);
                LinearLayout layout2 = getView().findViewById(R.id.saveloadtv);
                int width = (int) getResources().getDimension(R.dimen.widthstate);
                int width2 = (int) getResources().getDimension(R.dimen.widthstate2);
                int height = (int) getResources().getDimension(R.dimen.heightstate);
                int topmargin = (int) getResources().getDimension(R.dimen.marginstate); //top margin
                int textsize = (int) getResources().getDimension(R.dimen.textstate);
                Button btn = new Button(requireActivity());
                btn.setTextSize(textsize);
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(requireActivity());
                //obtiene "count", que es la cantidad de botones ya existentes
                int count = sp.getInt("count", 0);
                SharedPreferences.Editor editor = sp.edit();
                //count+1 pq se agrega un botón
                editor.putInt("count", (count + 1));
                editor.putInt("state" + count, count);
                editor.apply();
                int numbtn = sp.getInt("count", 1);
                btn.setText(String.valueOf(numbtn));
                int tempid = View.generateViewId();
                btn.setId(tempid);
                btn.setTag("state"+numbtn);
                btn.setBackgroundResource(R.drawable.rect5);
                btn.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                btn.setTextColor(Color.BLACK);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
                params.setMargins(0, 0, 0, topmargin);
                btn.setLayoutParams(params);
                layout.addView(btn);

                EditText tv = new EditText(requireActivity());
                tv.setTextSize(textsize);
                int tempid2 = View.generateViewId();
                tv.setId(tempid2);
                tv.setTag("statetv"+numbtn);
                tv.setBackgroundResource(R.drawable.rect3);
                tv.setHint(getResources().getString(R.string.tvsave));
                tv.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                tv.setTextColor(Color.WHITE);
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(width2, height);
                params2.setMargins(0, 0, 0, topmargin);
                tv.setLayoutParams(params2);
                tv.setEnabled(false);
                layout2.addView(tv);

                tv.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {


                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String tvcontent = "";
                        try {
                            tvcontent = tv.getText().toString();
                        }catch (Exception ignored) {
                        }
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("tvlabel"+numbtn, tvcontent);
                        editor.apply();

                    }
                });



                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        state = getView().findViewById(R.id.saveorload);
                        saveradio = getView().findViewById(R.id.save);
                        loadradio = getView().findViewById(R.id.load);
                        choicesv2 = getView().findViewById(R.id.vargroup);
                        onevar = getView().findViewById(R.id.onevar);
                        twovar = getView().findViewById(R.id.twovar);
                        del = getView().findViewById(R.id.eraseslot);
                        LinearLayout layouty = getView().findViewById(R.id.ylayout);
                        LinearLayout layoutxa = getView().findViewById(R.id.xalayout);
                        LinearLayout layoutxb = getView().findViewById(R.id.xblayout);
                        if(loadradio.isChecked()){
                            int loadn = sp.getInt("nvalueone"+numbtn, 0);
                            int loadvar = sp.getInt("varvalueone"+numbtn, 0);
                            int loadn2 = sp.getInt("nvaluetwo"+numbtn, 0);
                            int loadvar2 = sp.getInt("varvaluetwo"+numbtn, 0);
                            EditText nnumberx = getView().findViewById(R.id.nnumber);
                            EditText nnumberx2 = getView().findViewById(R.id.nnumber2);
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
                                //aqui iba el toast de cargar
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
                                //aqui iba el toast de cargar
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
                                    String tvtag = "statetv"+numbtn;
                                    EditText tv = layout2.findViewWithTag(tvtag);
                                    tv.setBackgroundResource(R.drawable.rect);
                                    tv.setEnabled(true);
                                }
                                //aqui iba el toast de guardar
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
                                    String tvtag = "statetv"+numbtn;
                                    EditText tv = layout2.findViewWithTag(tvtag);
                                    tv.setBackgroundResource(R.drawable.rect);
                                    tv.setEnabled(true);
                                }
                                //aqui  iba el toast de guardar
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
                            String tvtag = "statetv"+numbtn;
                            EditText tv = layout2.findViewWithTag(tvtag);
                            try {
                                tv.getText().clear();
                            }catch (Exception ignored) {
                            }
                            tv.setBackgroundResource(R.drawable.rect4);
                            tv.setEnabled(false);
                        }
                    }
                });
            }
        });

        deletebtn = getView().findViewById(R.id.deletebtn);
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle(getString(R.string.warntitle));
                builder.setMessage(getString(R.string.warntext));

                builder.setPositiveButton(getString(R.string.positivebtn), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            nvalue.getText().clear();
                            nvaluedel.getText().clear();
                            LinearLayout layout = getView().findViewById(R.id.nlayout);
                            LinearLayout layouty = getView().findViewById(R.id.ylayout);
                            LinearLayout layoutxa = getView().findViewById(R.id.xalayout);
                            LinearLayout layoutxb = getView().findViewById(R.id.xblayout);
                            layout.removeAllViews();
                            layouty.removeAllViews();
                            layoutxa.removeAllViews();
                            layoutxb.removeAllViews();
                        }catch (Exception ignored) {
                        }
                    }
                });

                builder.setNegativeButton(getString(R.string.negativebtn), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dialog = builder.create();
                dialog.show();

                Button PositiveBtn = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                PositiveBtn.setTextColor(Color.BLACK);
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)PositiveBtn.getLayoutParams();
                params.topMargin = (int) getResources().getDimension(R.dimen.margin);
                params.leftMargin = (int) getResources().getDimension(R.dimen.margin);
                params.rightMargin = (int) getResources().getDimension(R.dimen.margin);
                PositiveBtn.setLayoutParams(params);

                Button NegativeBtn = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                NegativeBtn.setBackgroundColor(Color.parseColor("#5865F2"));
                NegativeBtn.setTextColor(Color.BLACK);
                ViewGroup.MarginLayoutParams params2 = (ViewGroup.MarginLayoutParams)NegativeBtn.getLayoutParams();
                params2.topMargin = (int) getResources().getDimension(R.dimen.margin);
                params2.leftMargin = (int) getResources().getDimension(R.dimen.margin);
                params2.rightMargin = (int) getResources().getDimension(R.dimen.margin);
                NegativeBtn.setLayoutParams(params2);
            }
        });
    }
}



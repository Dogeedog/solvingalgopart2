package com.example.linearplexsolver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.hipparchus.distribution.continuous.FDistribution;

public class frag2 extends Fragment {

    EditText nvalue;
    EditText avalue;
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


    public frag2() {
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
        return inflater.inflate(R.layout.fragment_frag2, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //Genera los botones de guardar/cargar que el usuario añadió previamente
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(requireActivity());
        //sp.edit().clear().apply();

        mdrawer = getView().findViewById(R.id.drawer_layoutDOE);
        toggle = getView().findViewById(R.id.savingbuttonDOE);
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mdrawer.isDrawerOpen(GravityCompat.START))
                    mdrawer.openDrawer(GravityCompat.START);
                else mdrawer.closeDrawer(GravityCompat.END);
            }

        });

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

        final int[] avalueint = {0};
        final int[] nvalueint = {0};
        avalue = getView().findViewById(R.id.anumberDOE);
        nvalue = getView().findViewById(R.id.nnumberDOE);
        nvalue.setEnabled(false);
        nvalue.setHint("");
        nvalue.setBackgroundResource(R.drawable.rect3);
        avalue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String avaluestr = avalue.getText().toString(); //obtener cantidad de filas
                try {
                    avalueint[0] = Integer.parseInt(avaluestr);
                } catch (NumberFormatException ex) {
                    nvalue.setEnabled(false);
                    nvalue.getText().clear();
                    nvalue.setHint("");
                    nvalue.setBackgroundResource(R.drawable.rect3);
                    return;
                }
                if (avalueint[0] >= 2){
                    nvalue.setEnabled(true);
                    nvalue.setBackgroundResource(R.drawable.rect);
                    nvalue.setHint("3");
                } else if (avaluestr.equals("")){
                    nvalue.setEnabled(false);
                    nvalue.getText().clear();
                    nvalue.setHint("");
                    nvalue.setBackgroundResource(R.drawable.rect3);
                }else{
                    nvalue.setEnabled(false);
                    nvalue.getText().clear();
                    nvalue.setHint("");
                    nvalue.setBackgroundResource(R.drawable.rect3);
                }


            }
        });

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
                try {
                    nvalueint[0] = Integer.parseInt(nvaluestr);
                } catch (NumberFormatException ex) {
                    return;
                }
                if (nvalueint[0] >= 2 && avalueint[0] >= 2){
                    LinearLayout baselayout = getView().findViewById(R.id.starterlayout);
                    int width = (int) getResources().getDimension(R.dimen.widthinput); //obtiene manualmente el width 58 de dimens
                    int width2 = (int) getResources().getDimension(R.dimen.width); //obtiene manualmente el width 58 de dimens
                    int height = (int) getResources().getDimension(R.dimen.height); //obtiene manualmente el height 35 de dimens
                    int height2 = (int) getResources().getDimension(R.dimen.height2);
                    int topmargin = (int) getResources().getDimension(R.dimen.margin); //top margin
                    int textsize = (int) getResources().getDimension(R.dimen.text);
                    baselayout.removeAllViews();
                    //i = columnas; j = filas
                    for (int i = 0; i <= avalueint[0]; i++) {
                        //if para hacer columna de n (solo textviews)
                        if (i == 0){
                            LinearLayout aCol = new LinearLayout(requireActivity());
                            int tempid = View.generateViewId();
                            aCol.setId(tempid);
                            aCol.setTag("aCol"+i);
                            aCol.setOrientation(LinearLayout.VERTICAL);
                            aCol.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width2, ViewGroup.LayoutParams.WRAP_CONTENT); //58, 35
                            params.setMargins(topmargin, 0, topmargin, 0);
                            aCol.setLayoutParams(params);
                            baselayout.addView(aCol);

                            for (int j = 0; j <= nvalueint[0]; j++) {
                                String AddedLayoutTag = "aCol" + i;
                                LinearLayout aFoundCol = baselayout.findViewWithTag(AddedLayoutTag);
                                TextView tv = new TextView(getActivity());
                                tv.setTextSize(textsize);
                                tv.setTypeface(Typeface.DEFAULT_BOLD);
                                if (j == 0){
                                    tv.setText("n");
                                    int tempid2 = View.generateViewId();
                                    tv.setId(tempid2);
                                    tv.setTextColor(Color.WHITE);
                                    tv.setBackgroundResource(R.drawable.rect3);
                                    tv.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                                    LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(width2, height); //58, 35
                                    params2.setMargins(0, 0, 0, topmargin);
                                    tv.setLayoutParams(params2);
                                }else {
                                    tv.setText((String.valueOf(j)));
                                    int tempid2 = View.generateViewId();
                                    tv.setId(tempid2);
                                    tv.setTextColor(Color.WHITE);
                                    tv.setBackgroundResource(R.drawable.rect3);
                                    tv.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                                    LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(width2, height); //58, 35
                                    params2.setMargins(0, 0, 0, topmargin);
                                    tv.setLayoutParams(params2);
                                }
                                aFoundCol.addView(tv);
                            }

                        } else{
                            LinearLayout aCol = new LinearLayout(requireActivity());
                            int tempid = View.generateViewId();
                            aCol.setId(tempid);
                            aCol.setTag("aCol"+i);
                            aCol.setOrientation(LinearLayout.VERTICAL);
                            aCol.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT); //58, 35
                            params.setMargins(topmargin, 0, topmargin, 0);
                            aCol.setLayoutParams(params);
                            baselayout.addView(aCol);

                            for (int j = 0; j <= nvalueint[0]; j++) {
                                String AddedLayoutTag = "aCol"+i;
                                LinearLayout aFoundCol = baselayout.findViewWithTag(AddedLayoutTag);
                                if (j == 0){
                                    TextView tv = new TextView(getActivity());
                                    tv.setTypeface(Typeface.DEFAULT_BOLD);
                                    tv.setTextSize(textsize);
                                    tv.setText(Html.fromHtml("a<sub><small>" + i + "</small></sub>"));
                                    int tempid2 = View.generateViewId();
                                    tv.setId(tempid2);
                                    tv.setTextColor(Color.WHITE);
                                    tv.setBackgroundResource(R.drawable.rect3);
                                    tv.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                                    LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(width, height); //58, 35
                                    params2.setMargins(0, 0, 0, topmargin);
                                    tv.setLayoutParams(params2);
                                    aFoundCol.addView(tv);
                                } else{
                                    EditText tv = new EditText(getActivity());
                                    String valuetags = "vt" + i + j;
                                    tv.setTextSize(textsize);
                                    int tempid2 = View.generateViewId();
                                    tv.setId(tempid2);
                                    tv.setTag(valuetags);
                                    tv.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                                    tv.setTextColor(Color.WHITE);
                                    tv.setBackgroundResource(R.drawable.rect);
                                    tv.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                                    LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(width, height); //58, 35
                                    params2.setMargins(0, 0, 0, topmargin);
                                    tv.setLayoutParams(params2);
                                    aFoundCol.addView(tv);
                                }

                            }
                        }



                    }

                }

            }
        });

        Button tebtn = getView().findViewById(R.id.button2);
        tebtn.setOnClickListener(v1 -> {
            int tempAint = avalueint[0];
            int tempNint = nvalueint[0];
            double[][] a = new double[tempNint][tempAint];
            //(y) for para encontrar cada boton por tag
            for (int i = 1; i <= avalueint[0]; i++) {
                LinearLayout baselayout = getView().findViewById(R.id.starterlayout);
                String AddedLayoutTag = "aCol" + i;
                LinearLayout aFoundCol = baselayout.findViewWithTag(AddedLayoutTag);
                for (int j = 1; j <= nvalueint[0]; j++){
                    String valuetags = "vt" + i + j;
                    EditText content = aFoundCol.findViewWithTag(valuetags);
                    String contentstring = content.getText().toString();
                    try {
                        double contentint = Double.parseDouble(contentstring);
                        a[j - 1][i - 1] = contentint;
                    } catch (NumberFormatException ex) {
                        return;
                    }
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
                Intent x = new Intent(requireActivity(), solvedDOEoneFactor.class);
                double totalsumSq = 0;
                double total = 0;
                double totalSumSqPerTreat = 0;
                double[] averagePerTreat = new double[tempAint];
                double temptotalperTreat = 0;
                //i = tratamientos; j = réplicas
                for (int i = 0; i < avalueint[0]; i++){

                    for (int j = 0; j < nvalueint[0]; j++){
                        temptotalperTreat += a[j][i];
                        total += a[j][i];
                        totalsumSq += a[j][i]*a[j][i];
                        if (j == nvalueint[0] - 1){
                            averagePerTreat[i] = temptotalperTreat/tempNint;
                            temptotalperTreat = temptotalperTreat*temptotalperTreat;
                            totalSumSqPerTreat += temptotalperTreat;
                            temptotalperTreat = 0;
                        }
                    }
                }

                double totalAvg = total/(avalueint[0]*nvalueint[0]);
                double v = Math.pow(total, 2) / (tempAint * tempNint);
                double SSt = totalsumSq - v;
                double SStrat = (totalSumSqPerTreat/tempNint) - v;
                double SSe = SSt - SStrat;
                double confnumberpercentf = (confnumber / 100);
                FDistribution fdist = new FDistribution(tempAint-1, tempAint*(tempNint-1));
                double fvalue = fdist.inverseCumulativeProbability(confnumberpercentf);
                String averagePerTreatArrayStr = gson.toJson(averagePerTreat);
                String dataArrayStr = gson.toJson(a);
                x.putExtra("totalAvg", totalAvg);
                x.putExtra("dataArrayStr", dataArrayStr);
                x.putExtra("averagePerTreatArrayStr", averagePerTreatArrayStr);
                x.putExtra("confvalue", confnumberpercentf);
                x.putExtra("fvalue", fvalue);
                x.putExtra("SSt", SSt);
                x.putExtra("SStrat", SStrat);
                x.putExtra("SSe", SSe);
                x.putExtra("avalue", tempAint);
                x.putExtra("nvalue", tempNint);
                startActivity(x);






            }
        });





    }


}
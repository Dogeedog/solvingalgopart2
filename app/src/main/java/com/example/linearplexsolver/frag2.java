package com.example.linearplexsolver;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

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
    public void onSaveInstanceState(Bundle outState) {
        // Save state to the savedInstanceState
        avalue = getView().findViewById(R.id.anumberDOE);
        nvalue = getView().findViewById(R.id.nnumberDOE);
        final int[] avalueint = {0};
        final int[] nvalueint = {0};
        String avaluestr = avalue.getText().toString(); //obtener cantidad de filas
        outState.putString("avalueState", avaluestr);
        try {
            avalueint[0] = Integer.parseInt(avaluestr);
        } catch (Exception ignored) {
        }
        String nvaluestr = nvalue.getText().toString(); //obtener cantidad de filas
        outState.putString("nvalueState", nvaluestr);
        try {
            nvalueint[0] = Integer.parseInt(nvaluestr);
        } catch (Exception ignored) {
        }
        //(y) for para encontrar cada boton por tag
        for (int i = 1; i <= avalueint[0]; i++) {
            LinearLayout baselayout = getView().findViewById(R.id.starterlayout);
            String AddedLayoutTag = "aCol" + i;
            LinearLayout aFoundCol = baselayout.findViewWithTag(AddedLayoutTag);
            for (int j = 1; j <= nvalueint[0]; j++){
                String valuetags = "vt" + i + j;
                try{
                    EditText content = aFoundCol.findViewWithTag(valuetags);
                    String contentstring = content.getText().toString();
                    outState.putString("contentstrState"+i+j, contentstring);
                }catch (Exception e){
                    return;
                }

            }
        }


        super.onSaveInstanceState(outState);
        //savedInstanceState.putString("MyString", );

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
    public void onViewCreated(View view, Bundle outState) {

        //Genera los botones de guardar/cargar que el usuario añadió previamente
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(requireActivity());
        //sp.edit().clear().apply();

        int count = sp.getInt("countD1", 0);

        for (int i = 0; i < count; i++) {

            LinearLayout layout = getView().findViewById(R.id.saveloadlayout);
            LinearLayout layout2 = getView().findViewById(R.id.saveloadtv);
            int width = (int) getResources().getDimension(R.dimen.widthstate);
            int width2 = (int) getResources().getDimension(R.dimen.widthstate2);
            int height = (int) getResources().getDimension(R.dimen.heightstate);
            int topmargin = (int) getResources().getDimension(R.dimen.marginstate); //top margin
            int textsize = (int) getResources().getDimension(R.dimen.textstate);
            Button btn = new Button(requireActivity());
            btn.setTextSize(textsize);
            btn.setText(String.valueOf(i+1));
            int tempid = View.generateViewId();
            btn.setId(tempid);
            btn.setTag("stateD1"+i+1);
            btn.setBackgroundResource(R.drawable.rect5);
            btn.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            btn.setTextColor(Color.BLACK);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            params.setMargins(0, 0, 0, topmargin);
            btn.setLayoutParams(params);
            layout.addView(btn);

            int ivalue = i + 1;

            EditText tv = new EditText(requireActivity());
            String tvOldStrContent = sp.getString("tvlabelD1" + ivalue, "");
            tv.setText(tvOldStrContent);
            tv.setLineSpacing(1, 1);
            tv.setTextSize(textsize);
            int tempid2 = View.generateViewId();
            tv.setId(tempid2);
            tv.setTag("statetvD1"+ivalue);
            tv.setHint(getResources().getString(R.string.tvsave));
            tv.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            tv.setTextColor(Color.WHITE);
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(width2, height);
            params2.setMargins(0, 0, 0, topmargin);
            tv.setLayoutParams(params2);
            tv.setEnabled(false);
            layout2.addView(tv);

            //cuando realmente se guarda algo de información q se cambie el color del boton
            int loadn = sp.getInt("nvalueoneD1" + ivalue, 0);
            int loada = sp.getInt("avalueD1" + ivalue, 0);
            if (loadn >= 2 || loada >= 2){
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
                    }catch (Exception ignored) {
                    }
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("tvlabelD1"+ivalue, tvcontent);
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
                    LinearLayout baselayout = getView().findViewById(R.id.starterlayout);
                    if(loadradio.isChecked()){
                        int loada = sp.getInt("avalueD1"+ivalue, 0);
                        int loadn = sp.getInt("nvalueoneD1"+ivalue, 0);
                        avalue = getView().findViewById(R.id.anumberDOE);
                        nvalue = getView().findViewById(R.id.nnumberDOE);
                        //1 o 2 para respetar lo que haya guardado el usuario, son almacenados en sp cuando el usuario guarda
                        baselayout.removeAllViews();
                        avalue.setText(String.valueOf(loada));
                        nvalue.setText(String.valueOf(loadn));
                        String array = sp.getString("arrayonevD1"+ivalue,null);
                        double[][] a = gson.fromJson(array, double[][].class);
                        //aqui se obtiene el array en forma y con los siguientes for se introduce toda la información guardada
                        for (int i = 1; i <= loada; i++) {
                            String AddedLayoutTag = "aCol" + i;
                            LinearLayout aFoundCol = baselayout.findViewWithTag(AddedLayoutTag);
                            for (int j = 1; j <= loadn; j++){
                                String valuetags = "vt" + i + j;
                                EditText content = aFoundCol.findViewWithTag(valuetags);
                                String restoredata = String.valueOf(a[j-1][i-1]);
                                if(!restoredata.equals("0.0")){
                                    content.setText(restoredata);
                                }
                            }
                        }
                        //aqui iba el toast de cargar

                    } else if(saveradio.isChecked()){

                        String nvaluestr = nvalue.getText().toString(); //obtener cantidad de filas
                        String avaluestr = avalue.getText().toString();
                        int nvalueint;
                        int avalueint2;
                        try {
                            nvalueint = Integer.parseInt(nvaluestr);
                            avalueint2 = Integer.parseInt(avaluestr);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putInt("nvalueoneD1"+ivalue, nvalueint);
                            editor.putInt("avalueD1"+ivalue, avalueint2);
                            editor.apply();
                        }
                        catch(NumberFormatException ex) {
                            return;
                        }
                        double[][] a = new double[nvalueint][avalueint2];
                        //serie de try's para evitar eliminar (guarda info en las matrices) el input al añadir mas filas

                        for (int i = 1; i <= avalueint2; i++) {
                            String AddedLayoutTag = "aCol" + i;
                            LinearLayout aFoundCol = baselayout.findViewWithTag(AddedLayoutTag);
                            for (int j = 1; j <= nvalueint; j++){
                                String valuetags = "vt" + i + j;
                                EditText content = aFoundCol.findViewWithTag(valuetags);
                                String contentstring = content.getText().toString();
                                try {
                                    double contentint = Double.parseDouble(contentstring);
                                    a[j - 1][i - 1] = contentint;
                                }catch (Exception ignored) {
                                }
                            }
                        }

                        //Transforma el array en string para almacenarlo en sharedpreferences.
                        String arraytostring = gson.toJson(a);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("arrayonevD1"+ivalue, arraytostring);
                        editor.apply();
                        int loadn = sp.getInt("nvalueoneD1"+ivalue, 0);
                        int loada = sp.getInt("avalueD1"+ivalue, 0);
                        if (loadn >= 2 && loada >= 2){
                            btn.setBackgroundResource(R.drawable.rect4);
                            String tvtag = "statetvD1"+ivalue;
                            EditText tv = layout2.findViewWithTag(tvtag);
                            tv.setBackgroundResource(R.drawable.rect);
                            tv.setEnabled(true);
                        }
                        //aqui iba el toast de guardar

                    } else if(del.isChecked()){
                        SharedPreferences.Editor editor = sp.edit();
                        editor.remove("arrayonevD1"+ivalue);
                        editor.remove("nvalueoneD1"+ivalue);
                        editor.remove("avalueD1"+ivalue);
                        editor.apply();
                        btn.setBackgroundResource(R.drawable.rect5);
                        String tvtag = "statetvD1"+ivalue;
                        EditText tv = layout2.findViewWithTag(tvtag);
                        try {
                            tv.getText().clear();
                        }catch (Exception ignored) {
                        }
                        tv.setBackgroundResource(R.drawable.rect3);
                        tv.setEnabled(false);
                    }
                }
            });

        }

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
                    try{
                        EditText content = aFoundCol.findViewWithTag(valuetags);
                        String contentstring = content.getText().toString();

                        double contentint = Double.parseDouble(contentstring);
                        a[j - 1][i - 1] = contentint;

                    }catch (Exception e){
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
                int count = sp.getInt("countD1", 0);
                SharedPreferences.Editor editor = sp.edit();
                //count+1 pq se agrega un botón
                editor.putInt("countD1", (count + 1));
                editor.putInt("stateD1" + count, count);
                editor.apply();
                int numbtn = sp.getInt("countD1", 1);
                btn.setText(String.valueOf(numbtn));
                int tempid = View.generateViewId();
                btn.setId(tempid);
                btn.setTag("stateD1"+numbtn);
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
                tv.setTag("statetvD1"+numbtn);
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
                        editor.putString("tvlabelD1"+numbtn, tvcontent);
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
                        LinearLayout baselayout = getView().findViewById(R.id.starterlayout);
                        if(loadradio.isChecked()){
                            int loada = sp.getInt("avalueD1"+numbtn, 0);
                            int loadn = sp.getInt("nvalueoneD1"+numbtn, 0);
                            avalue = getView().findViewById(R.id.anumberDOE);
                            nvalue = getView().findViewById(R.id.nnumberDOE);
                            //1 o 2 para respetar lo que haya guardado el usuario, son almacenados en sp cuando el usuario guarda
                            baselayout.removeAllViews();
                            avalue.setText(String.valueOf(loada));
                            nvalue.setText(String.valueOf(loadn));
                            String array = sp.getString("arrayonevD1"+numbtn,null);
                            double[][] a = gson.fromJson(array, double[][].class);
                            //aqui se obtiene el array en forma y con los siguientes for se introduce toda la información guardada
                            for (int i = 1; i <= loada; i++) {
                                String AddedLayoutTag = "aCol" + i;
                                LinearLayout aFoundCol = baselayout.findViewWithTag(AddedLayoutTag);
                                for (int j = 1; j <= loadn; j++){
                                    String valuetags = "vt" + i + j;
                                    EditText content = aFoundCol.findViewWithTag(valuetags);
                                    String restoredata = String.valueOf(a[j-1][i-1]);
                                    if(!restoredata.equals("0.0")){
                                        content.setText(restoredata);
                                    }
                                }
                            }
                            //aqui iba el toast de cargar

                        } else if(saveradio.isChecked()){

                            String nvaluestr = nvalue.getText().toString(); //obtener cantidad de filas
                            String avaluestr = avalue.getText().toString();
                            int nvalueint;
                            int avalueint2;
                            try {
                                nvalueint = Integer.parseInt(nvaluestr);
                                avalueint2 = Integer.parseInt(avaluestr);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putInt("nvalueoneD1"+numbtn, nvalueint);
                                editor.putInt("avalueD1"+numbtn, avalueint2);
                                editor.apply();
                            }
                            catch(NumberFormatException ex) {
                                return;
                            }
                            double[][] a = new double[nvalueint][avalueint2];
                            //serie de try's para evitar eliminar (guarda info en las matrices) el input al añadir mas filas

                            for (int i = 1; i <= avalueint2; i++) {
                                String AddedLayoutTag = "aCol" + i;
                                LinearLayout aFoundCol = baselayout.findViewWithTag(AddedLayoutTag);
                                for (int j = 1; j <= nvalueint; j++){
                                    String valuetags = "vt" + i + j;
                                    EditText content = aFoundCol.findViewWithTag(valuetags);
                                    String contentstring = content.getText().toString();
                                    try {
                                        double contentint = Double.parseDouble(contentstring);
                                        a[j - 1][i - 1] = contentint;
                                    }catch (Exception ignored) {
                                    }
                                }
                            }

                            //Transforma el array en string para almacenarlo en sharedpreferences.
                            String arraytostring = gson.toJson(a);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("arrayonevD1"+numbtn, arraytostring);
                            editor.apply();
                            int loadn = sp.getInt("nvalueoneD1"+numbtn, 0);
                            int loada = sp.getInt("avalueD1"+numbtn, 0);
                            if (loadn >= 2 && loada >= 2){
                                btn.setBackgroundResource(R.drawable.rect4);
                                String tvtag = "statetvD1"+numbtn;
                                EditText tv = layout2.findViewWithTag(tvtag);
                                tv.setBackgroundResource(R.drawable.rect);
                                tv.setEnabled(true);
                            }
                            //aqui iba el toast de guardar

                        } else if(del.isChecked()){
                            SharedPreferences.Editor editor = sp.edit();
                            editor.remove("arrayonevD1"+numbtn);
                            editor.remove("nvalueoneD1"+numbtn);
                            editor.remove("avalueD1"+numbtn);
                            editor.apply();
                            btn.setBackgroundResource(R.drawable.rect5);
                            String tvtag = "statetvD1"+numbtn;
                            EditText tv = layout2.findViewWithTag(tvtag);
                            try {
                                tv.getText().clear();
                            }catch (Exception ignored) {
                            }
                            tv.setBackgroundResource(R.drawable.rect3);
                            tv.setEnabled(false);
                        }
                    }
                });
            }
        });

        deletebtn = getView().findViewById(R.id.deletebtnDOE);
        deletebtn.setOnClickListener(v -> {
            builder = new AlertDialog.Builder(requireActivity());
            builder.setTitle(getString(R.string.warntitle));
            builder.setMessage(getString(R.string.warntext));

            builder.setPositiveButton(getString(R.string.positivebtn), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        nvalue.getText().clear();
                        avalue.getText().clear();
                        LinearLayout layout = getView().findViewById(R.id.starterlayout);
                        layout.removeAllViews();
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
        });

        super.onCreate(outState);
        if (outState == null) {
            int axs = 1;
        }
        else {
            String astr = outState.getString("avalueState");
            String nstr = outState.getString("nvalueState");
            avalue.setText(astr);
            nvalue.setText(nstr);
            String avaluestr = avalue.getText().toString(); //obtener cantidad de filas
            try {
                avalueint[0] = Integer.parseInt(avaluestr);
            } catch (Exception ignored) {
            }
            String nvaluestr = nvalue.getText().toString(); //obtener cantidad de filas
            try {
                nvalueint[0] = Integer.parseInt(nvaluestr);
            } catch (Exception ignored) {
            }

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
                    try{
                        EditText content = aFoundCol.findViewWithTag(valuetags);
                        String contentstring = outState.getString("contentstrState"+i+j);
                        content.setText(contentstring);
                    }catch (Exception e){
                        return;
                    }

                }
            }
        }



    }


}
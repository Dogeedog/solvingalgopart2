package com.example.linearplexsolver;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.hipparchus.distribution.continuous.TDistribution;
import org.hipparchus.util.Combinations;
import org.jgrapht.util.MathUtil;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class solvedDOEoneFactor extends AppCompatActivity {

    TextView anovareg1;
    TextView anovareg2;
    TextView anovareg3;
    TextView anovagl1;
    TextView anovagl2;
    TextView anovagl3;
    TextView anovamc1;
    TextView anovamc2;
    TextView anovaf1;
    TextView fishersign;
    TextView conftv;
    Button sel1;
    Button sel2;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    AlertDialog dialog2;
    AlertDialog.Builder builder2;
    TextView icMeanDifValue;
    Gson gson = new GsonBuilder().create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solved_doeone_factor);

        anovareg1 = findViewById(R.id.anovareg1oneDOE);
        anovareg2 = findViewById(R.id.anovareg2oneDOE);
        anovareg3 = findViewById(R.id.anovareg3oneDOE);
        anovagl1 = findViewById(R.id.anovagl1oneDOE);
        anovagl2 = findViewById(R.id.anovagl2oneDOE);
        anovagl3 = findViewById(R.id.anovagl3oneDOE);
        anovamc1 = findViewById(R.id.anovamc1oneDOE);
        anovamc2 = findViewById(R.id.anovamc2oneDOE);
        anovaf1 = findViewById(R.id.anovaf1oneDOE);
        fishersign = findViewById(R.id.fishervalueoneDOE);
        conftv = findViewById(R.id.fisherinputsoneDOE);

        DecimalFormat df = new DecimalFormat("#.####; - #");
        df.setRoundingMode(RoundingMode.HALF_UP);
        DecimalFormat df2 = new DecimalFormat("#.##;");

        Intent x = getIntent();

        double totalerror = x.getDoubleExtra("SSe", 0);
        int avalueint = x.getIntExtra("avalue", 0);
        int nvalueint = x.getIntExtra("nvalue", 0);
        double totalreg = x.getDoubleExtra("SSt",0);
        double totaltrat = x.getDoubleExtra("SStrat",0);
        double confvalue = 1 - x.getDoubleExtra("confvalue",0);
        double fvalue = x.getDoubleExtra("fvalue",0);

        anovareg1.setText(df.format(totaltrat));
        anovareg2.setText(df.format(totalerror));
        anovareg3.setText(df.format(totalreg));
        anovagl1.setText(df.format(avalueint-1));
        anovagl2.setText(df.format((long) avalueint *(nvalueint-1)));
        anovagl3.setText(df.format(((long) nvalueint * avalueint)-1));

        double mc1 = totaltrat/((long) avalueint-1);
        double mc2 = totalerror/(((long) avalueint *(nvalueint-1)));


        anovamc1.setText(df.format(mc1));
        anovamc2.setText(df.format(mc2));

        anovaf1.setText(df.format(mc1/mc2));

        anovareg1.setTextIsSelectable(true);
        anovareg2.setTextIsSelectable(true);
        anovareg3.setTextIsSelectable(true);
        anovagl1.setTextIsSelectable(true);
        anovagl2.setTextIsSelectable(true);
        anovagl3.setTextIsSelectable(true);
        anovamc1.setTextIsSelectable(true);
        anovamc2.setTextIsSelectable(true);
        anovaf1.setTextIsSelectable(true);

        String signiffinaltext = df.format(fvalue);
        fishersign.setText(signiffinaltext);
        fishersign.setTextIsSelectable(true);
        conftv.setText(Html.fromHtml("F<sub><small>" + df2.format(confvalue) + ", " + df2.format(avalueint-1) +", " + df2.format((long) avalueint *(nvalueint-1)) + "</small></sub> = "));

        String averagePerTreatArrayStr = x.getStringExtra("averagePerTreatArrayStr");
        double[] averagePerTreatArray = gson.fromJson(averagePerTreatArrayStr, double[].class);
        double confnumberpercent = (confvalue) / 2;
        TDistribution tdist = new TDistribution((long) avalueint *(nvalueint-1));
        double tvalue = (tdist.inverseCumulativeProbability(confnumberpercent) * (-1));
        double icop1 = tvalue * Math.sqrt(mc2/nvalueint);


        LinearLayout baselayout = findViewById(R.id.starterlayoutDOE);
        int width = (int) getResources().getDimension(R.dimen.widthstate3); //obtiene manualmente el width 58 de dimens
        int width3 = (int) getResources().getDimension(R.dimen.widthinput);
        int width2 = (int) getResources().getDimension(R.dimen.width); //obtiene manualmente el width 58 de dimens
        int height = (int) getResources().getDimension(R.dimen.height); //obtiene manualmente el height 35 de dimens
        int height2 = (int) getResources().getDimension(R.dimen.height2);
        int topmargin = (int) getResources().getDimension(R.dimen.margin); //top margin
        int textsize = (int) getResources().getDimension(R.dimen.text);
        baselayout.removeAllViews();
        //i = columnas; j = filas
        for (int i = 0; i <= 1; i++) {
            //if para hacer columna de n (solo textviews)
            if (i == 0){
                LinearLayout aCol = new LinearLayout(solvedDOEoneFactor.this);
                aCol.setId(i);
                aCol.setTag("aColr"+i);
                aCol.setOrientation(LinearLayout.VERTICAL);
                aCol.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width2, ViewGroup.LayoutParams.WRAP_CONTENT); //58, 35
                params.setMargins(topmargin, 0, topmargin, 0);
                aCol.setLayoutParams(params);
                baselayout.addView(aCol);

                for (int j = 1; j <= avalueint; j++) {
                    String AddedLayoutTag = "aColr" + i;
                    LinearLayout aFoundCol = baselayout.findViewWithTag(AddedLayoutTag);
                    TextView tv = new TextView(solvedDOEoneFactor.this);
                    tv.setTextSize(textsize);
                    tv.setTypeface(Typeface.DEFAULT_BOLD);
                    tv.setText(Html.fromHtml("a<sub><small>" + j + "</small></sub>"));
                    tv.setId(j);
                    tv.setTextColor(Color.WHITE);
                    tv.setBackgroundResource(R.drawable.rect3);
                    tv.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                    LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(width2, height); //58, 35
                    params2.setMargins(0, 0, 0, topmargin);
                    tv.setLayoutParams(params2);
                    aFoundCol.addView(tv);
                }

            } else{
                LinearLayout aCol = new LinearLayout(solvedDOEoneFactor.this);
                aCol.setId(i);
                aCol.setTag("aColr"+i);
                aCol.setOrientation(LinearLayout.VERTICAL);
                aCol.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT); //58, 35
                params.setMargins(topmargin, 0, topmargin, 0);
                aCol.setLayoutParams(params);
                baselayout.addView(aCol);

                for (int j = 1; j <= avalueint; j++) {
                    String AddedLayoutTag = "aColr"+i;
                    LinearLayout aFoundCol = baselayout.findViewWithTag(AddedLayoutTag);
                    TextView tv = new TextView(solvedDOEoneFactor.this);
                    tv.setTypeface(Typeface.DEFAULT_BOLD);
                    tv.setTextSize(textsize);
                    tv.setText(Html.fromHtml(
                            df.format(averagePerTreatArray[j-1]-icop1) + " &#8804; " + "&#956;<sub><small>" + j + "</small></sub>" + " &#8804; " + df.format(averagePerTreatArray[j-1]+icop1)
                    ));
                    tv.setTextIsSelectable(true);
                    tv.setId(j);
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

        LinearLayout baselayout2 = findViewById(R.id.LSDlinearlayout);
        LinearLayout baselayout3 = findViewById(R.id.LSDlinearlayout2);
        Combinations c = new Combinations(avalueint,2);
        List<int[]> al = new ArrayList<>();
        for(int[] iterate : c){
            al.add(iterate);
        }

        for (int i = 0 ; i < al.size(); i++){
            int[] comb = al.get(i);
            int v1 = comb[0];
            int v2 = comb[1];
            double op = averagePerTreatArray[v1] - averagePerTreatArray[v2];
            TextView tv = new TextView(solvedDOEoneFactor.this);
            tv.setTextSize(textsize);
            tv.setTypeface(Typeface.DEFAULT_BOLD);
            String plchldr = df.format(averagePerTreatArray[v1]) + " - " +  df.format(averagePerTreatArray[v2]) + " = " + df.format(op);
            tv.setText(plchldr);
            tv.setId(i);
            tv.setTextColor(Color.WHITE);
            tv.setBackgroundResource(R.drawable.rect);
            tv.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(width3, height); //58, 35
            params2.setMargins(0, 0, 0, topmargin);
            tv.setLayoutParams(params2);
            baselayout3.addView(tv);

            TextView tv2 = new TextView(solvedDOEoneFactor.this);
            tv2.setTextSize(textsize);
            tv2.setTypeface(Typeface.DEFAULT_BOLD);
            tv2.setText(Html.fromHtml("a<sub><small>" + v1 + "</small></sub> vs a<sub><small>" + v2 + "</small></sub>"));
            tv2.setId(i);
            tv2.setTextColor(Color.WHITE);
            tv2.setBackgroundResource(R.drawable.rect);
            tv2.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(width3, height); //58, 35
            params3.setMargins(0, 0, 0, topmargin);
            tv2.setLayoutParams(params3);
            baselayout2.addView(tv2);
        }






        icMeanDifValue = findViewById(R.id.tvICMeanDifValue);
        sel1 = findViewById(R.id.treatSel1);
        sel2 = findViewById(R.id.treatSel2);
        String pos = "";
        sel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sel2.setEnabled(false);
                sel2.setText(R.string.sel);
                icMeanDifValue.setText(R.string.icmeandifTEMP);
                builder = new AlertDialog.Builder(solvedDOEoneFactor.this);
                builder.setTitle(getString(R.string.seltitle));

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        solvedDOEoneFactor.this,
                        R.layout.listpicker);

                for (int i = 0; i < avalueint; i++) {
                    arrayAdapter.add(String.valueOf(i+1));
                }

                builder.setNegativeButton(getString(R.string.cancelkey), (dialog, which) -> dialog.dismiss());

                builder.setAdapter(arrayAdapter,
                        (dialog, which) -> {

                            String pos = arrayAdapter.getItem(which);
                            sel1.setText(pos);
                            sel2.setEnabled(true);
                            sel2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    builder2 = new AlertDialog.Builder(solvedDOEoneFactor.this);
                                    builder2.setTitle(getString(R.string.seltitle));
                                    builder2.setNegativeButton(getString(R.string.cancelkey), (dialog, which) -> dialog.dismiss());
                                    arrayAdapter.remove(pos);
                                    builder2.setAdapter(arrayAdapter,
                                            (dialog, which) -> {

                                                String pos = arrayAdapter.getItem(which);
                                                sel2.setText(pos);
                                                sel2.setEnabled(true);

                                                String selstr1 = sel1.getText().toString();
                                                String selstr2 = sel2.getText().toString();
                                                int sel1int = Integer.parseInt(selstr1) - 1;
                                                int sel2int = Integer.parseInt(selstr2) - 1;
                                                double v1 = tvalue * Math.sqrt((2 * mc2) / nvalueint);
                                                double icop1 = averagePerTreatArray[sel1int] - averagePerTreatArray[sel2int] - v1;
                                                double icop2 = averagePerTreatArray[sel1int] - averagePerTreatArray[sel2int] + v1;

                                                icMeanDifValue.setText(Html.fromHtml(df.format(icop1) + " &#8804; &#956;<sub><small>" + selstr1 +"</small></sub> - &#956;<sub><small>"+ selstr2 + "</small></sub> &#8804; " + df.format(icop2)));




                                                dialog.dismiss();
                                            });
                                    dialog2 = builder2.create();
                                    dialog2.show();

                                    Button NegativeBtn2 = dialog2.getButton(DialogInterface.BUTTON_NEGATIVE);
                                    NegativeBtn2.setBackgroundColor(Color.parseColor("#5865F2"));
                                    NegativeBtn2.setTextColor(Color.BLACK);
                                    ViewGroup.MarginLayoutParams params2 = (ViewGroup.MarginLayoutParams)NegativeBtn2.getLayoutParams();
                                    params2.topMargin = (int) getResources().getDimension(R.dimen.margin);
                                    params2.leftMargin = (int) getResources().getDimension(R.dimen.margin);
                                    params2.rightMargin = (int) getResources().getDimension(R.dimen.margin);
                                    NegativeBtn2.setLayoutParams(params2);

                                }


                            });
                            dialog.dismiss();
                        });

                dialog = builder.create();
                dialog.show();

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
package com.example.linearplexsolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.hipparchus.distribution.continuous.TDistribution;
import org.jgrapht.util.MathUtil;

import java.math.RoundingMode;
import java.text.DecimalFormat;

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




    }
}
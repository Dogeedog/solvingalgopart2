package com.example.linearplexsolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import org.hipparchus.distribution.continuous.FDistribution;
import org.w3c.dom.Text;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class anova extends AppCompatActivity {

    TextView anovareg1;
    TextView anovareg1_1;
    TextView anovareg1_2;
    TextView anovareg2;
    TextView anovareg3;
    TextView anovagl1;
    TextView anovagl1_1;
    TextView anovagl1_2;
    TextView anovagl2;
    TextView anovagl3;
    TextView anovamc1;
    TextView anovamc1_1;
    TextView anovamc1_2;
    TextView anovamc2;
    TextView anovaf1;
    TextView anovaf1_1;
    TextView anovaf1_2;
    TextView fishersign;
    TextView fishersign2;
    TextView conftv;
    TextView conftv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anova);

        anovareg1 = findViewById(R.id.anovareg1);
        anovareg1_1 = findViewById(R.id.anovareg1_1);
        anovareg1_2 = findViewById(R.id.anovareg1_2);
        anovareg2 = findViewById(R.id.anovareg2);
        anovareg3 = findViewById(R.id.anovareg3);
        anovagl1 = findViewById(R.id.anovagl1);
        anovagl1_1 = findViewById(R.id.anovagl1_1);
        anovagl1_2 = findViewById(R.id.anovagl1_2);
        anovagl2 = findViewById(R.id.anovagl2);
        anovagl3 = findViewById(R.id.anovagl3);
        anovamc1 = findViewById(R.id.anovamc1);
        anovamc1_1 = findViewById(R.id.anovamc1_1);
        anovamc1_2 = findViewById(R.id.anovamc1_2);
        anovamc2 = findViewById(R.id.anovamc2);
        anovaf1 = findViewById(R.id.anovaf1);
        anovaf1_1 = findViewById(R.id.anovaf1_1);
        anovaf1_2 = findViewById(R.id.anovaf1_2);
        fishersign = findViewById(R.id.fishervalue);
        fishersign2 = findViewById(R.id.fishervalue2);
        conftv = findViewById(R.id.fisherinputs);
        conftv2 = findViewById(R.id.fisherinputs2);

        DecimalFormat df = new DecimalFormat("#.####; - #");
        df.setRoundingMode(RoundingMode.HALF_UP);
        DecimalFormat df2 = new DecimalFormat("#.##;");

        Intent x = getIntent();

        double totalerror = x.getDoubleExtra("mse2", 0);
        int nvalueint = x.getIntExtra("nvalueres", 0);
        double totalsq = x.getDoubleExtra("totalsum",0);
        double totalreg = x.getDoubleExtra("totalreg",0);
        double SSx1 = x.getDoubleExtra("SSx1", 0);
        double SSx2 = x.getDoubleExtra("SSx2", 0);
        double confvalue = 1 - x.getDoubleExtra("confvalue",0);
        double confvaluef = x.getDoubleExtra("confvalue", 0);

        FDistribution fdist = new FDistribution(1,nvalueint-3);
        double fvalue2 = fdist.inverseCumulativeProbability(confvaluef);

        anovareg1.setText(df.format(totalreg));
        anovareg1_1.setText(df.format(SSx1));
        anovareg1_2.setText(df.format(SSx2));
        anovareg2.setText(df.format(totalerror));
        anovareg3.setText(df.format(totalsq));
        anovagl1.setText(df.format(2));
        anovagl1_1.setText(df.format(1));
        anovagl1_2.setText(df.format(1));
        anovagl2.setText(df.format(nvalueint-3));
        anovagl3.setText(df.format(nvalueint-1));

        double mc1 = totalreg/2;
        double mc2 = totalerror/(nvalueint-3);


        anovamc1.setText(df.format(mc1));
        anovamc1_1.setText(df.format(SSx1));
        anovamc1_2.setText(df.format(SSx2));
        anovamc2.setText(df.format(mc2));

        anovaf1.setText(df.format(mc1/mc2));
        anovaf1_1.setText(df.format(SSx1/mc2));
        anovaf1_2.setText(df.format(SSx2/mc2));

        anovareg1.setTextIsSelectable(true);
        anovareg1_1.setTextIsSelectable(true);
        anovareg1_2.setTextIsSelectable(true);
        anovareg2.setTextIsSelectable(true);
        anovareg3.setTextIsSelectable(true);
        anovagl1.setTextIsSelectable(true);
        anovagl1_1.setTextIsSelectable(true);
        anovagl1_2.setTextIsSelectable(true);
        anovagl2.setTextIsSelectable(true);
        anovagl3.setTextIsSelectable(true);
        anovamc1.setTextIsSelectable(true);
        anovamc1_1.setTextIsSelectable(true);
        anovamc1_2.setTextIsSelectable(true);
        anovamc2.setTextIsSelectable(true);
        anovaf1.setTextIsSelectable(true);
        anovaf1_1.setTextIsSelectable(true);
        anovaf1_2.setTextIsSelectable(true);

        double fountvalue = x.getDoubleExtra("fountvalue", 0);
        double fvalue = x.getDoubleExtra("fvalue", 0);
        if(fountvalue > 0){
            String signiffinaltext = df.format(fvalue);
            String signiffinaltext2 = df.format(fvalue2);
            fishersign.setText(signiffinaltext);
            fishersign.setTextIsSelectable(true);
            fishersign2.setText(signiffinaltext2);
            fishersign2.setTextIsSelectable(true);
        }else{
            String signiffinaltext = df.format(fvalue);
            String signiffinaltext2 = df.format(fvalue2);
            fishersign.setText(signiffinaltext);
            fishersign.setTextIsSelectable(true);
            fishersign2.setText(signiffinaltext2);
            fishersign2.setTextIsSelectable(true);
        }

        conftv.setText(Html.fromHtml("R: F<sub><small>" + df2.format(confvalue) + ", 2, " + (nvalueint-3) + "</small></sub> = "));
        conftv2.setText(Html.fromHtml("X<sub><small>1</small></sub>, X<sub><small>2</small></sub>: F<sub><small>" + df2.format(confvalue) + ", 1, " + (nvalueint-3) + "</small></sub> = "));

    }
}
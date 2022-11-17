package com.example.linearplexsolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class anovaone extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anovaone);

        anovareg1 = findViewById(R.id.anovareg1one);
        anovareg2 = findViewById(R.id.anovareg2one);
        anovareg3 = findViewById(R.id.anovareg3one);
        anovagl1 = findViewById(R.id.anovagl1one);
        anovagl2 = findViewById(R.id.anovagl2one);
        anovagl3 = findViewById(R.id.anovagl3one);
        anovamc1 = findViewById(R.id.anovamc1one);
        anovamc2 = findViewById(R.id.anovamc2one);
        anovaf1 = findViewById(R.id.anovaf1one);
        fishersign = findViewById(R.id.fishervalueone);
        conftv = findViewById(R.id.fisherinputsone);

        DecimalFormat df = new DecimalFormat("#.####; - #");
        df.setRoundingMode(RoundingMode.HALF_UP);
        DecimalFormat df2 = new DecimalFormat("#.##;");

        Intent x = getIntent();

        double totalerror = x.getDoubleExtra("SSEone", 0);
        int nvalueint = x.getIntExtra("nvalueres", 0);
        double totalreg = x.getDoubleExtra("totalreg",0);
        double confvalue = 1 - x.getDoubleExtra("confvalue",0);
        String averagePerTreatArrayStr = x.getStringExtra("averagePerTreatArrayStr");

        anovareg1.setText(df.format(totalreg-totalerror));
        anovareg2.setText(df.format(totalerror));
        anovareg3.setText(df.format(totalreg));
        anovagl1.setText(df.format(1));
        anovagl2.setText(df.format(nvalueint-2));
        anovagl3.setText(df.format(nvalueint-1));

        double mc2 = totalerror/(nvalueint-2);


        anovamc1.setText(df.format(totalreg-totalerror));
        anovamc2.setText(df.format(mc2));

        anovaf1.setText(df.format((totalreg-totalerror)/mc2));

        anovareg1.setTextIsSelectable(true);
        anovareg2.setTextIsSelectable(true);
        anovareg3.setTextIsSelectable(true);
        anovagl1.setTextIsSelectable(true);
        anovagl2.setTextIsSelectable(true);
        anovagl3.setTextIsSelectable(true);
        anovamc1.setTextIsSelectable(true);
        anovamc2.setTextIsSelectable(true);
        anovaf1.setTextIsSelectable(true);

        double fountvalue = x.getDoubleExtra("fountvalue", 0);
        double fvalue = x.getDoubleExtra("fvalue", 0);
        if(fountvalue > 0){
            String signiffinaltext = df.format(fvalue);
            fishersign.setText(signiffinaltext);
            fishersign.setTextIsSelectable(true);
        }else{
            String signiffinaltext = df.format(fvalue);
            fishersign.setText(signiffinaltext);
            fishersign.setTextIsSelectable(true);
        }

        conftv.setText(Html.fromHtml("F<sub><small>" + df2.format(confvalue) + ", 1, " + (nvalueint-2) + "</small></sub> = "));

    }
}
package com.example.linearplexsolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class anova extends AppCompatActivity {

    TextView anovareg1;
    TextView anovareg2;
    TextView anovareg3;
    TextView anovagl1;
    TextView anovagl2;
    TextView anovagl3;
    TextView anovamc1;
    TextView anovamc2;
    TextView anovaf1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anova);

        anovareg1 = findViewById(R.id.anovareg1);
        anovareg2 = findViewById(R.id.anovareg2);
        anovareg3 = findViewById(R.id.anovareg3);
        anovagl1 = findViewById(R.id.anovagl1);
        anovagl2 = findViewById(R.id.anovagl2);
        anovagl3 = findViewById(R.id.anovagl3);
        anovamc1 = findViewById(R.id.anovamc1);
        anovamc2 = findViewById(R.id.anovamc2);
        anovaf1 = findViewById(R.id.anovaf1);

        DecimalFormat df = new DecimalFormat("#.####; - #");
        df.setRoundingMode(RoundingMode.CEILING);

        Intent x = getIntent();

        double totalerror = x.getDoubleExtra("mse2", 0);
        int nvalueint = x.getIntExtra("nvalueres", 0);
        double totalsq = x.getDoubleExtra("totalsum",0);
        double totalreg = x.getDoubleExtra("totalreg",0);

        anovareg1.setText(df.format(totalreg));
        anovareg2.setText(df.format(totalerror));
        anovareg3.setText(df.format(totalsq));
        anovagl1.setText(df.format(2));
        anovagl2.setText(df.format(nvalueint-3));
        anovagl3.setText(df.format(nvalueint-1));

        double mc1 = totalreg/2;
        double mc2 = totalerror/(nvalueint-3);


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


    }
}
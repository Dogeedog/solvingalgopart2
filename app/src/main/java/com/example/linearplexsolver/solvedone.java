package com.example.linearplexsolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.SubscriptSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class solvedone extends AppCompatActivity {

    EditText input;
    TextView givenresult;
    TextView sqerror;
    TextView errorb0;
    TextView errorb1;
    TextView icb1;
    TextView coefreg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solvedone);
        TextView modelbox = (findViewById(R.id.textView2one)); //.setText(Html.fromHtml("y=B<sub><small>o</small></sub>+B<sub><small>1</small></sub>X<sub><small>1</small></sub>+B<sub><small>2</small></sub>X<sub><small>2</small></sub>"));
        Intent j = getIntent();
        CharSequence modeltext = j.getCharSequenceExtra("modelstr");
        modelbox.setText(modeltext);
        modelbox.setTextIsSelectable(true);
        input = findViewById(R.id.meanone);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);

        sqerror = findViewById(R.id.given3one);
        errorb0 = findViewById(R.id.given4one);
        errorb1 = findViewById(R.id.given5one);
        icb1 = findViewById(R.id.given10one);
        coefreg = findViewById(R.id.given14one);

        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        DecimalFormat df2 = new DecimalFormat("#.##%");
        df2.setRoundingMode(RoundingMode.CEILING);

        double sqerrorvalue = j.getDoubleExtra("varianceone", 0);
        double errorb0value = j.getDoubleExtra("var1stdone", 0);
        double errorb1value = j.getDoubleExtra("var2stdone",0);
        double icb1valueleft = j.getDoubleExtra("icb1left", 0);
        double icb1valueright = j.getDoubleExtra("icb1right", 0);
        double coefregvalue = j.getDoubleExtra("coefregone", 0);

        String sqerrorstr = df.format(sqerrorvalue);
        String errorb0str = df.format(errorb0value);
        String errorb1str = df.format(errorb1value);
        String b1sub = "B1";
        String icb1strbs = df.format(icb1valueleft) + " < " + b1sub + " < " + df.format(icb1valueright);
        Spannable icb1str = new SpannableString(icb1strbs);
        icb1str.setSpan(new SubscriptSpan(),(icb1strbs.indexOf(b1sub) + 1), (icb1strbs.indexOf(b1sub) + 2), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        icb1str.setSpan(new RelativeSizeSpan(0.75f), (icb1strbs.indexOf(b1sub)) + 1, (icb1strbs.indexOf(b1sub) + 2), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        String coefregstr = df2.format(coefregvalue);

        sqerror.setText(sqerrorstr);
        sqerror.setTextIsSelectable(true);
        errorb0.setText(errorb0str);
        errorb0.setTextIsSelectable(true);
        errorb1.setText(errorb1str);
        errorb1.setTextIsSelectable(true);
        icb1.setText(icb1str);
        icb1.setTextIsSelectable(true);
        coefreg.setText(coefregstr);
        coefreg.setTextIsSelectable(true);

    }

    public void eval(View h) {
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        Intent j = getIntent();
        double b0 = j.getDoubleExtra("b0",0);
        double b1 = j.getDoubleExtra("b1", 0);

        input = findViewById(R.id.meanone);
        givenresult = findViewById(R.id.given2one);

        String inputstr = input.getText().toString();
        double inputvalue;
        try {
            inputvalue = Double.parseDouble(inputstr);

        }
        catch(NumberFormatException ex) {
            return;
        }
        double evaluatedvalue = b0 + b1*inputvalue;
        String evaluatedvaluestr = df.format(evaluatedvalue);
        givenresult.setText(evaluatedvaluestr);
        givenresult.setTextIsSelectable(true);
    }

    public void graphx(View h) {
        Intent x = new Intent(this, graphx.class);
        startActivity(x);
    }
}
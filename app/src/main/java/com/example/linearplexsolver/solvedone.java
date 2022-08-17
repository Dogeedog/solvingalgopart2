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
import android.widget.ImageView;
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
    TextView icb0;
    TextView icbmed;
    TextView icpred;
    TextView coefreg;
    TextView signif;
    ImageView signifpng;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solvedone);

        //modelo
        TextView modelbox = (findViewById(R.id.textView2one)); //.setText(Html.fromHtml("y=B<sub><small>o</small></sub>+B<sub><small>1</small></sub>X<sub><small>1</small></sub>+B<sub><small>2</small></sub>X<sub><small>2</small></sub>"));
        Intent j = getIntent();
        CharSequence modeltext = j.getCharSequenceExtra("modelstr");
        modelbox.setText(modeltext);
        modelbox.setTextIsSelectable(true);

        //eval
        input = findViewById(R.id.meanone);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);






        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        DecimalFormat df2 = new DecimalFormat("#.##%");
        df2.setRoundingMode(RoundingMode.CEILING);

        //MSE^2 y errores de los coeficientes
        sqerror = findViewById(R.id.given3one);
        errorb0 = findViewById(R.id.given4one);
        errorb1 = findViewById(R.id.given5one);
        double sqerrorvalue = j.getDoubleExtra("varianceone", 0);
        double errorb0value = j.getDoubleExtra("var1stdone", 0);
        double errorb1value = j.getDoubleExtra("var2stdone",0);
        String sqerrorstr = df.format(sqerrorvalue);
        String errorb0str = df.format(errorb0value);
        String errorb1str = df.format(errorb1value);
        sqerror.setText(sqerrorstr);
        sqerror.setTextIsSelectable(true);
        errorb0.setText(errorb0str);
        errorb0.setTextIsSelectable(true);
        errorb1.setText(errorb1str);
        errorb1.setTextIsSelectable(true);

        //coef regresion
        coefreg = findViewById(R.id.given14one);
        double coefregvalue = j.getDoubleExtra("coefregone", 0);
        String coefregstr = df2.format(coefregvalue);
        coefreg.setText(coefregstr);
        coefreg.setTextIsSelectable(true);

        //significance
        signifpng = findViewById(R.id.imageView3one);
        signif = findViewById(R.id.given6one);
        String operator = j.getStringExtra("signif");
        Double tovalue = j.getDoubleExtra("tovalue", 0);
        Double tvalue = j.getDoubleExtra("tvalue", 0);
        if(tovalue > 0){
            String signiffinaltext = df.format(tovalue) +  " > " + df.format(tvalue);
            signif.setText(signiffinaltext);
            signif.setTextIsSelectable(true);
        }else{
            String signiffinaltext = df.format(tovalue) +  " < " + df.format(tvalue);
            signif.setText(signiffinaltext);
            signif.setTextIsSelectable(true);
        }
        if(operator.equals("true")){
            signifpng.setBackgroundResource(R.mipmap.check);
        }else{
            signifpng.setBackgroundResource(R.mipmap.checknt);
        }

        //intervalo coeficiente b1
        icb1 = findViewById(R.id.given10one);
        double icb1valueleft = j.getDoubleExtra("icb1left", 0);
        double icb1valueright = j.getDoubleExtra("icb1right", 0);
        String b1sub = "B1";
        String icb1strbs = df.format(icb1valueleft) + " < " + b1sub + " < " + df.format(icb1valueright);
        Spannable icb1str = new SpannableString(icb1strbs);
        icb1str.setSpan(new SubscriptSpan(),(icb1strbs.indexOf(b1sub) + 1), (icb1strbs.indexOf(b1sub) + 2), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        icb1str.setSpan(new RelativeSizeSpan(0.75f), (icb1strbs.indexOf(b1sub)) + 1, (icb1strbs.indexOf(b1sub) + 2), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        icb1.setText(icb1str);
        icb1.setTextIsSelectable(true);

        //intervalo coeficiente b0
        icb0 = findViewById(R.id.given9one);
        double icb0valueleft = j.getDoubleExtra("icb0left", 0);
        double icb0valueright = j.getDoubleExtra("icb0right", 0);
        String b0sub = "B0";
        String icb0strbs = df.format(icb0valueleft) + " < " + b0sub + " < " + df.format(icb0valueright);
        Spannable icb0str = new SpannableString(icb0strbs);
        icb0str.setSpan(new SubscriptSpan(),(icb0strbs.indexOf(b0sub) + 1), (icb0strbs.indexOf(b0sub) + 2), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        icb0str.setSpan(new RelativeSizeSpan(0.75f), (icb0strbs.indexOf(b0sub)) + 1, (icb0strbs.indexOf(b0sub) + 2), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        icb0.setText(icb0str);
        icb0.setTextIsSelectable(true);

        //intervalo media
        icbmed = findViewById(R.id.given11one);
        double icbmedleft = j.getDoubleExtra("icmedleft", 0);
        double icbmedright = j.getDoubleExtra("icmedright", 0);
        String Myox = "MY|Xo";
        String icbmedstrbf = df.format(icbmedleft) + " < " + Myox + " < " + df.format(icbmedright);
        Spannable icbmedstr = new SpannableString(icbmedstrbf);
        icbmedstr.setSpan(new SubscriptSpan(),(icbmedstrbf.indexOf(Myox) + 1), (icbmedstrbf.indexOf(Myox) + 5), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        icbmedstr.setSpan(new RelativeSizeSpan(0.75f), (icbmedstrbf.indexOf(Myox)) + 1, (icbmedstrbf.indexOf(Myox) + 5), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        icbmed.setText(icbmedstr);
        icbmed.setTextIsSelectable(true);

        //intervalo prediccion
        icpred = findViewById(R.id.given13one);
        double icpredleft = j.getDoubleExtra("icpredleft", 0);
        double icpredright = j.getDoubleExtra("icpredright", 0);
        String Yo = "Yo";
        String icpredstrbf = df.format(icpredleft) + " < " + Yo + " < " + df.format(icpredright);
        Spannable icbpredstr = new SpannableString(icpredstrbf);
        icbpredstr.setSpan(new SubscriptSpan(),(icpredstrbf.indexOf(Yo) + 1), (icpredstrbf.indexOf(Yo) + 2), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        icbpredstr.setSpan(new RelativeSizeSpan(0.75f), (icpredstrbf.indexOf(Yo)) + 1, (icpredstrbf.indexOf(Yo) + 2), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        icpred.setText(icbpredstr);
        icpred.setTextIsSelectable(true);


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
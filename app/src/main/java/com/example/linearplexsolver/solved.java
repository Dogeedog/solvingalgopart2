package com.example.linearplexsolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class solved extends AppCompatActivity {

    EditText input1;
    EditText input2;
    TextView givenresult;
    TextView coefv;
    TextView ic0;
    TextView ic1;
    TextView ic2;
    TextView sigb1;
    TextView sigb2;
    TextView sigg;
    ImageView sigb1png;
    ImageView sigb2png;
    ImageView siggpng;
    TextView icmed;
    TextView icpred;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solved);
        TextView modelbox = (findViewById(R.id.textView2)); //.setText(Html.fromHtml("y=B<sub><small>o</small></sub>+B<sub><small>1</small></sub>X<sub><small>1</small></sub>+B<sub><small>2</small></sub>X<sub><small>2</small></sub>"));
        Intent j = getIntent();
        CharSequence modeltext = j.getCharSequenceExtra("modelstr");
        modelbox.setText(modeltext);
        modelbox.setTextIsSelectable(true);
        input1 = findViewById(R.id.mean);
        input1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        input2 = findViewById(R.id.mean3);
        input2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);

        //formato
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        DecimalFormat df2 = new DecimalFormat("#.##%");
        df2.setRoundingMode(RoundingMode.CEILING);

        //coef regresion
        coefv = findViewById(R.id.coefvaluetwo);
        double coefregvalue = j.getDoubleExtra("regtwo",0);
        String coefregstr = df2.format(coefregvalue);
        coefv.setText(coefregstr);
        coefv.setTextIsSelectable(true);

        //ic coeficiente 0
        ic0 = findViewById(R.id.icvar1valuetwo);
        double ic0left = j.getDoubleExtra("ic0left", 0);
        double ic0right = j.getDoubleExtra("ic0right", 0);
        String b0sub = "B0";
        String icb0strbs = df.format(ic0left) + " < " + b0sub + " < " + df.format(ic0right);
        Spannable icb0str = new SpannableString(icb0strbs);
        icb0str.setSpan(new SubscriptSpan(),(icb0strbs.indexOf(b0sub) + 1), (icb0strbs.indexOf(b0sub) + 2), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        icb0str.setSpan(new RelativeSizeSpan(0.75f), (icb0strbs.indexOf(b0sub)) + 1, (icb0strbs.indexOf(b0sub) + 2), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ic0.setText(icb0str);
        ic0.setTextIsSelectable(true);

        //ic coeficiente 1
        ic1 = findViewById(R.id.icvar2valuetwo);
        double ic1left = j.getDoubleExtra("ic1left", 0);
        double ic1right = j.getDoubleExtra("ic1right", 0);
        String b1sub = "B1";
        String icb1strbs = df.format(ic1left) + " < " + b1sub + " < " + df.format(ic1right);
        Spannable icb1str = new SpannableString(icb1strbs);
        icb1str.setSpan(new SubscriptSpan(),(icb1strbs.indexOf(b1sub) + 1), (icb1strbs.indexOf(b1sub) + 2), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        icb1str.setSpan(new RelativeSizeSpan(0.75f), (icb1strbs.indexOf(b1sub)) + 1, (icb1strbs.indexOf(b1sub) + 2), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ic1.setText(icb1str);
        ic1.setTextIsSelectable(true);

        //ic coeficiente 2
        ic2 = findViewById(R.id.icvar3valuetwo);
        double ic2left = j.getDoubleExtra("ic2left", 0);
        double ic2right = j.getDoubleExtra("ic2right", 0);
        String b2sub = "B2";
        String icb2strbs = df.format(ic2left) + " < " + b2sub + " < " + df.format(ic2right);
        Spannable icb2str = new SpannableString(icb2strbs);
        icb2str.setSpan(new SubscriptSpan(),(icb2strbs.indexOf(b2sub) + 1), (icb2strbs.indexOf(b2sub) + 2), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        icb2str.setSpan(new RelativeSizeSpan(0.75f), (icb2strbs.indexOf(b2sub)) + 1, (icb2strbs.indexOf(b2sub) + 2), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ic2.setText(icb2str);

        //ic media
        icmed = findViewById(R.id.icresvaluetwo);
        double icmedleft = j.getDoubleExtra("icmedleft", 0);
        double icmedright = j.getDoubleExtra("icmedright", 0);
        String Myox = "Y|Xo";
        Spanned mu = Html.fromHtml("&#956;");
        String icbmedstrbf = df.format(icmedleft) + " < ";
        String icbmedstrbf2 = " < " + df.format(icmedright);
        Spannable icbmedstr = new SpannableString(icbmedstrbf + mu + Myox + icbmedstrbf2);
        icbmedstr.setSpan(new SubscriptSpan(),(icbmedstrbf.length() + mu.length() + icbmedstrbf2.indexOf(Myox)) + 1, (icbmedstrbf.length() + mu.length() + icbmedstrbf2.indexOf(Myox) + 5), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        icbmedstr.setSpan(new RelativeSizeSpan(0.75f), (icbmedstrbf.length() + mu.length() + icbmedstrbf2.indexOf(Myox) + 1), (icbmedstrbf.length() + mu.length() + icbmedstrbf2.indexOf(Myox) + 5), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        icmed.setText(icbmedstr);
        icmed.setTextIsSelectable(true);

        //ic prediccion
        icpred = findViewById(R.id.icprevaluetwo);
        double icpredleft = j.getDoubleExtra("icpredleft", 0);
        double icpredright = j.getDoubleExtra("icpredright", 0);
        String Yo = "Yo";
        String icpredstrbf = df.format(icpredleft) + " < " + Yo + " < " + df.format(icpredright);
        Spannable icbpredstr = new SpannableString(icpredstrbf);
        icbpredstr.setSpan(new SubscriptSpan(),(icpredstrbf.indexOf(Yo) + 1), (icpredstrbf.indexOf(Yo) + 2), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        icbpredstr.setSpan(new RelativeSizeSpan(0.75f), (icpredstrbf.indexOf(Yo)) + 1, (icpredstrbf.indexOf(Yo) + 2), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        icpred.setText(icbpredstr);
        icpred.setTextIsSelectable(true);

        //significancia general
        siggpng = findViewById(R.id.imageView);
        sigg = findViewById(R.id.signifvaltwo);
        String operatorg = j.getStringExtra("signifg");
        Double fountvalue = j.getDoubleExtra("fountvalue", 0);
        Double fvalue = j.getDoubleExtra("fvalue", 0);
        if(fountvalue > 0){
            String signiffinaltext = df.format(fountvalue) +  " > " + df.format(fvalue);
            sigg.setText(signiffinaltext);
            sigg.setTextIsSelectable(true);
        }else{
            String signiffinaltext = df.format(fountvalue) +  " < " + df.format(fvalue);
            sigg.setText(signiffinaltext);
            sigg.setTextIsSelectable(true);
        }
        if(operatorg.equals("true")){
            siggpng.setBackgroundResource(R.mipmap.check);
        }else{
            siggpng.setBackgroundResource(R.mipmap.checknt);
        }

        //significancia b1
        sigb1png = findViewById(R.id.imageView2);
        sigb1 = findViewById(R.id.signifvar1valtwo);
        String operator = j.getStringExtra("signif1");
        Double tovalue = j.getDoubleExtra("tovalue1", 0);
        Double tvalue = j.getDoubleExtra("tvalue", 0);
        if(tovalue > 0){
            String signiffinaltext = df.format(tovalue) +  " > " + df.format(tvalue);
            sigb1.setText(signiffinaltext);
            sigb1.setTextIsSelectable(true);
        }else{
            String signiffinaltext = df.format(tovalue) +  " < " + df.format(tvalue);
            sigb1.setText(signiffinaltext);
            sigb1.setTextIsSelectable(true);
        }
        if(operator.equals("true")){
            sigb1png.setBackgroundResource(R.mipmap.check);
        }else{
            sigb1png.setBackgroundResource(R.mipmap.checknt);
        }

        //significancia b2
        sigb2png = findViewById(R.id.imageView3);
        sigb2 = findViewById(R.id.signifvar2valtwo);
        String operator2 = j.getStringExtra("signif2");
        Double tovalue2 = j.getDoubleExtra("tovalue2", 0);
        if(tovalue2 > 0){
            String signiffinaltext = df.format(tovalue2) +  " > " + df.format(tvalue);
            sigb2.setText(signiffinaltext);
            sigb2.setTextIsSelectable(true);
        }else{
            String signiffinaltext = df.format(tovalue2) +  " < " + df.format(tvalue);
            sigb2.setText(signiffinaltext);
            sigb2.setTextIsSelectable(true);
        }
        if(operator2.equals("true")){
            sigb2png.setBackgroundResource(R.mipmap.check);
        }else{
            sigb2png.setBackgroundResource(R.mipmap.checknt);
        }
    }

    public void eval2(View h) {
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        Intent j = getIntent();
        double b0 = j.getDoubleExtra("b0t",0);
        double b1 = j.getDoubleExtra("b1t", 0);
        double b2 = j.getDoubleExtra("b2t", 0);

        input1 = findViewById(R.id.mean);
        input2 = findViewById(R.id.mean3);
        givenresult = findViewById(R.id.given2);

        String input1str = input1.getText().toString();
        double inputvalue1;
        try {
            inputvalue1 = Double.parseDouble(input1str);

        }
        catch(NumberFormatException ex) {
            return;
        }

        String input2str = input2.getText().toString();
        double inputvalue2;
        try {
            inputvalue2 = Double.parseDouble(input2str);

        }
        catch(NumberFormatException ex) {
            return;
        }

        double evaluatedvalue = b0 + b1*inputvalue1 + b2*inputvalue2;
        String evaluatedvaluestr = df.format(evaluatedvalue);
        givenresult.setText(evaluatedvaluestr);
        givenresult.setTextIsSelectable(true);
    }

    public void graph(View h) {
        Intent j = new Intent(this, graphx.class);
        Intent x = getIntent();
        double b0 = x.getDoubleExtra("b0t",0);
        double b1 = x.getDoubleExtra("b1t", 0);
        double b2 = x.getDoubleExtra("b2t", 0);
        String array1 = x.getStringExtra("res2array");
        String array2 = x.getStringExtra("regarray");
        double mse = x.getDoubleExtra("mse", 0);
        int nvalueint = x.getIntExtra("nvalueres", 0);
        String array3 = x.getStringExtra("leverage");
        j.putExtra("res1array", array1);
        j.putExtra("regarray", array2);
        j.putExtra("nvalueres", nvalueint);
        j.putExtra("b0t", b0);
        j.putExtra("b1t", b1);
        j.putExtra("b2t", b2);
        j.putExtra("leverage", array3);
        j.putExtra("mse", mse);
        startActivity(j);
    }

    public void residuales(View h){
        Intent j = new Intent(solved.this, residuales.class);
        Intent x = getIntent();
        int nvalueint = x.getIntExtra("nvalueres", 0);
        j.putExtra("nvalueres", nvalueint);
        String array1 = x.getStringExtra("res1array");
        j.putExtra("res1array", array1);
        String array2 = x.getStringExtra("res2array");
        j.putExtra("res2array", array2);
        j.putExtra("vares", "two");
        startActivity(j);
    }

    public void anovaf(View h){
        Intent j = new Intent(this, anova.class);
        Intent x = getIntent();

        double mse = x.getDoubleExtra("mse2", 0);
        int nvalueint = x.getIntExtra("nvalueres", 0);
        double totalsq = x.getDoubleExtra("totalsum",0);
        double totalreg = x.getDoubleExtra("totalreg",0);
        j.putExtra("mse2", mse);
        j.putExtra("nvalueres", nvalueint);
        j.putExtra("totalsum", totalsq);
        j.putExtra("totalreg", totalreg);
        startActivity(j);


    }

}

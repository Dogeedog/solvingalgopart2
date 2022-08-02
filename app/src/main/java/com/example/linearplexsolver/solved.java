package com.example.linearplexsolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class solved extends AppCompatActivity {

    EditText input1;
    EditText input2;
    TextView givenresult;
    TextView coefv;

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

        coefv = findViewById(R.id.coefvaluetwo);

        DecimalFormat df2 = new DecimalFormat("#.##%");
        df2.setRoundingMode(RoundingMode.CEILING);

        double coefregvalue = j.getDoubleExtra("regtwo",0);
        String coefregstr = df2.format(coefregvalue);

        coefv.setText(coefregstr);
        coefv.setTextIsSelectable(true);
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
        startActivity(j);
    }

}

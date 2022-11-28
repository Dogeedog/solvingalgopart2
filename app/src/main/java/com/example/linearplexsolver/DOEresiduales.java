package com.example.linearplexsolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class DOEresiduales extends AppCompatActivity {

    Gson gson = new GsonBuilder().create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doeresiduales);

        DecimalFormat df = new DecimalFormat("#.####; - #");
        df.setRoundingMode(RoundingMode.HALF_UP);
        Intent x = getIntent();
        int avalueint = x.getIntExtra("avalue", 0);
        int nvalueint = x.getIntExtra("nvalue", 0);
        String averagePerTreatArrayStr = x.getStringExtra("averagePerTreatArrayStr");
        String dataArrayStr = x.getStringExtra("dataArrayStr");
        double[] averagePerTreatArray = gson.fromJson(averagePerTreatArrayStr, double[].class);
        double[][] dataArray = gson.fromJson(dataArrayStr, double[][].class);

            LinearLayout baselayout = findViewById(R.id.starterlayoutRes);
            int width = (int) getResources().getDimension(R.dimen.widthinput); //obtiene manualmente el width 58 de dimens
            int width2 = (int) getResources().getDimension(R.dimen.width); //obtiene manualmente el width 58 de dimens
            int height = (int) getResources().getDimension(R.dimen.height); //obtiene manualmente el height 35 de dimens
            int height2 = (int) getResources().getDimension(R.dimen.height2);
            int topmargin = (int) getResources().getDimension(R.dimen.margin); //top margin
            int textsize = (int) getResources().getDimension(R.dimen.text);
            baselayout.removeAllViews();
            //i = columnas; j = filas
            for (int i = 0; i <= avalueint; i++) {
                //if para hacer columna de n (solo textviews)
                if (i == 0){
                    LinearLayout aCol = new LinearLayout(DOEresiduales.this);
                    int tempid = View.generateViewId();
                    aCol.setId(tempid);
                    aCol.setTag("aCol"+i);
                    aCol.setOrientation(LinearLayout.VERTICAL);
                    aCol.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width2, ViewGroup.LayoutParams.WRAP_CONTENT); //58, 35
                    params.setMargins(topmargin, 0, topmargin, 0);
                    aCol.setLayoutParams(params);
                    baselayout.addView(aCol);

                    for (int j = 0; j <= nvalueint; j++) {
                        String AddedLayoutTag = "aCol" + i;
                        LinearLayout aFoundCol = baselayout.findViewWithTag(AddedLayoutTag);
                        TextView tv = new TextView(DOEresiduales.this);
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
                    LinearLayout aCol = new LinearLayout(DOEresiduales.this);
                    int tempid = View.generateViewId();
                    aCol.setId(tempid);
                    aCol.setTag("aCol"+i);
                    aCol.setOrientation(LinearLayout.VERTICAL);
                    aCol.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT); //58, 35
                    params.setMargins(topmargin, 0, topmargin, 0);
                    aCol.setLayoutParams(params);
                    baselayout.addView(aCol);

                    for (int j = 0; j <= nvalueint; j++) {
                        String AddedLayoutTag = "aCol"+i;
                        LinearLayout aFoundCol = baselayout.findViewWithTag(AddedLayoutTag);
                        if (j == 0){
                            TextView tv = new TextView(DOEresiduales.this);
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
                            TextView tv = new TextView(DOEresiduales.this);
                            String valuetags = "vt" + i + j;
                            tv.setTextSize(textsize);
                            int tempid2 = View.generateViewId();
                            double op = dataArray[j-1][i-1] - averagePerTreatArray[i-1];
                            tv.setText(df.format(op));
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
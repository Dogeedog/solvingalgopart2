package com.example.linearplexsolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class residuales extends AppCompatActivity {
    Gson gson = new GsonBuilder().create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_residuales);

        Intent j = getIntent();
        int nvalueint = j.getIntExtra("nvalueres", 0);
        String var = j.getStringExtra("vares");
        String array1 = j.getStringExtra("res1array");
        double[][] res1 = gson.fromJson(array1, double[][].class);
        String array2 = j.getStringExtra("res2array");
        double[][] res2 = gson.fromJson(array2, double[][].class);
        DecimalFormat df = new DecimalFormat("#.####; - #");
        df.setRoundingMode(RoundingMode.HALF_UP);

        LinearLayout layout = findViewById(R.id.res2nlayout);
        LinearLayout layouty = findViewById(R.id.res2ylayout);
        LinearLayout layoutxa = findViewById(R.id.res2xalayout);
        LinearLayout layoutxb = findViewById(R.id.res2xblayout);

        layout.removeAllViews();
        layouty.removeAllViews();
        layoutxa.removeAllViews();
        layoutxb.removeAllViews();

        //for para n textviews
        for (int i = 1; i <= nvalueint; i++) {
            int width = (int) getResources().getDimension(R.dimen.width); //obtiene manualmente el width 58 de dimens
            int height = (int) getResources().getDimension(R.dimen.height); //obtiene manualmente el height 35 de dimens
            int topmargin = (int) getResources().getDimension(R.dimen.margin); //top margin
            int textsize = (int) getResources().getDimension(R.dimen.text);
            TextView btn = new TextView(residuales.this);
            btn.setTextSize(textsize);
            btn.setText((String.valueOf(i)));
            btn.setId(i);
            btn.setTextColor(Color.WHITE);
            btn.setBackgroundResource(R.drawable.rect3);
            btn.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height); //58, 35
            params.setMargins(0, 0, 0, topmargin);
            btn.setLayoutParams(params);
            layout.addView(btn);
        }

        //for para n textviews(y data)
        for (int i = 1; i <= nvalueint; i++) {
            int width = (int) getResources().getDimension(R.dimen.widthinput); //obtiene manualmente el width 58 de dimens
            int height = (int) getResources().getDimension(R.dimen.height); //obtiene manualmente el height 35 de dimens
            int topmargin = (int) getResources().getDimension(R.dimen.margin); //top margin
            int textsize = (int) getResources().getDimension(R.dimen.text); //text size 15dp
            TextView btn = new TextView(residuales.this);
            btn.setTextSize(textsize);
            if(var.equals("one")){
                String array1str = df.format(res1[i-1][0]);
                btn.setText(array1str);
            } else{
                String array2str = df.format(res2[i-1][0]);
                btn.setText(array2str);
            }
            String butag = "ry"+i;
            btn.setTag(butag);
            btn.setId(i);
            btn.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            btn.setBackgroundResource(R.drawable.rect);
            btn.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height); //58, 35
            params.setMargins(0, 0, 0, topmargin);
            btn.setLayoutParams(params);
            layouty.addView(btn);
        }
        //for para n textviews(y modelo)
        for (int i = 1; i <= nvalueint; i++) {
            int width = (int) getResources().getDimension(R.dimen.widthinput); //obtiene manualmente el width 58 de dimens
            int height = (int) getResources().getDimension(R.dimen.height); //obtiene manualmente el height 35 de dimens
            int topmargin = (int) getResources().getDimension(R.dimen.margin); //top margin
            int textsize = (int) getResources().getDimension(R.dimen.text); //text size 15dp
            TextView btn = new TextView(residuales.this);
            btn.setTextSize(textsize);
            if(var.equals("one")){
                String array1str = df.format(res1[i-1][1]);
                btn.setText(array1str);
            } else{
                String array2str = df.format(res2[i-1][1]);
                btn.setText(array2str);
            }
            String butag = "rxa"+i;
            btn.setTag(butag);
            btn.setId(i);
            btn.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            btn.setBackgroundResource(R.drawable.rect);
            btn.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height); //58, 35
            params.setMargins(0, 0, 0, topmargin);
            btn.setLayoutParams(params);
            layoutxa.addView(btn);
        }
        //for para n textviews(residual)
        for (int i = 1; i <= nvalueint; i++) {
            int width = (int) getResources().getDimension(R.dimen.widthinput); //obtiene manualmente el width 58 de dimens
            int height = (int) getResources().getDimension(R.dimen.height); //obtiene manualmente el height 35 de dimens
            int topmargin = (int) getResources().getDimension(R.dimen.margin); //top margin
            int textsize = (int) getResources().getDimension(R.dimen.text); //text size 15dp
            TextView btn = new TextView(residuales.this);
            btn.setTextSize(textsize);
            if(var.equals("one")){
                String array1str = df.format(res1[i-1][2]);
                btn.setText(array1str);
            } else{
                String array2str = df.format(res2[i-1][2]);
                btn.setText(array2str);
            }
            btn.setTextColor(Color.WHITE);
            String butag = "xbtwo"+i;
            btn.setTag(butag);
            btn.setId(i);
            btn.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            btn.setBackgroundResource(R.drawable.rect);
            btn.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height); //58, 35
            params.setMargins(0, 0, 0, topmargin);
            btn.setLayoutParams(params);
            layoutxb.addView(btn);
        }

    }
}
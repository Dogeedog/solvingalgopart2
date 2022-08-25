package com.example.linearplexsolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class graphxone extends AppCompatActivity {

    LineChart test;
    Button b1;
    Button b2;
    Button b3;
    Gson gson = new GsonBuilder().create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphxone);

        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        Intent j = getIntent();
        String array1 = j.getStringExtra("res1array");
        String array2 = j.getStringExtra("regarray");
        int nvalueint = j.getIntExtra("nvalueres", 0);
        double b0t = j.getDoubleExtra("b0",0);
        float b0 = (float) b0t;
        double b1rt = j.getDoubleExtra("b1", 0);
        float b1r = (float) b1rt;
        double[][] regarray = gson.fromJson(array2, double[][].class);
        double[][] res1 = gson.fromJson(array1, double[][].class);
        float[] xvalues = new float[nvalueint];
        float[] resvalues = new float[nvalueint];
        float[][] lineardataset = new float[nvalueint][2];

        for (int i = 1; i <= nvalueint; i++){
            xvalues[i-1] = (float) regarray[i-1][0];
            resvalues[i-1] = (float) res1[i-1][0];
        }

        for (int i = 1; i <= nvalueint; i++){
            lineardataset[i-1][0] = b0 + b1r*( (float) regarray[i-1][0]);
            lineardataset[i-1][1] = (float) regarray[i-1][0];
        }
        java.util.Arrays.sort(lineardataset, (a, b) -> Float.compare(a[1], b[1]));
        java.util.Arrays.sort(xvalues);
        java.util.Arrays.sort(resvalues);
        ArrayUtils.reverse(resvalues);

        List<Float> b = Arrays.asList(ArrayUtils.toObject(xvalues));

        float xmin = Collections.min(b) - (Collections.min(b)/4);
        float xmax = Collections.max(b) + (Collections.max(b)/4);


        test = (LineChart) findViewById(R.id.mf1one);
        b1 = findViewById(R.id.graph1one);
        b2 = findViewById(R.id.graph2one);
        b3 = findViewById(R.id.graph3one);

        b1.setOnClickListener(v -> {
            test.setVisibility(View.VISIBLE);
        });

        b2.setOnClickListener(v -> {
            test.setVisibility(View.GONE);
        });

        b3.setOnClickListener(v -> {
            test.setVisibility(View.GONE);
        });

        //dataset para plottear el modelo lineal
        ArrayList<Entry> entrieslist = new ArrayList<>();
        ArrayList<Entry> entrieslist2 = new ArrayList<>();

        for(int i = 0; i < lineardataset.length; i++){
            entrieslist.add(new Entry(xvalues[i], resvalues[i]));
            entrieslist2.add(new Entry(lineardataset[i][1], lineardataset[i][0]));
        }


        //dataset de los valores reales de y


        ArrayList<ILineDataSet> linedatasets = new ArrayList<>();

        LineDataSet data = new LineDataSet(entrieslist, "Y real");
        data.enableDashedLine(0f, 1f, 0f);
        data.setColor(Color.parseColor("#5865F2"));
        data.setValueTextColor(Color.parseColor("#5865F2"));
        data.setCircleColor(Color.parseColor("#5865F2"));
        data.setCircleHoleColor(Color.parseColor("#5865F2"));
        data.setDrawValues(false);

        LineDataSet data2 = new LineDataSet(entrieslist2, "Y modelo");
        data2.setDrawCircles(false);
        data2.setColor(Color.WHITE);
        data2.setDrawValues(false);


        linedatasets.add(data);
        linedatasets.add(data2);

        LineData xtds = new LineData(linedatasets);
        test.setBorderColor(Color.WHITE);
        test.getLegend().setTextColor(Color.WHITE);
        test.getDescription().setTextColor(Color.WHITE);
        test.getDescription().setText("Residuales del modelo");
        test.getAxisLeft().setDrawGridLines(false);
        test.getXAxis().setDrawGridLines(false);
        test.setData(xtds);

        XAxis xaxis = test.getXAxis();
        xaxis.setTextColor(Color.WHITE);

        YAxis yaxis = test.getAxisLeft();
        yaxis.setTextColor(Color.WHITE);

        YAxis yaxisr = test.getAxisRight();
        yaxisr.setTextColor(Color.WHITE);

        test.invalidate();
    }
}
package com.example.linearplexsolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.lang3.ArrayUtils;
import org.hipparchus.linear.Array2DRowRealMatrix;
import org.hipparchus.linear.RealMatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class graphx extends AppCompatActivity {

    LineChart test;
    TextView g1tv1;
    TextView g1tv2;
    Button btn1;
    Button btn2;
    Button btn3;
    Gson gson = new GsonBuilder().create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphx);

        btn1 = findViewById(R.id.graph1);
        btn2 = findViewById(R.id.graph2);
        btn3 = findViewById(R.id.graph3);
        test = findViewById(R.id.mf1);
        g1tv1 = findViewById(R.id.fittedv2);
        g1tv2 = findViewById(R.id.actualv2);

        g1tv1.setVisibility(View.GONE);
        g1tv2.setVisibility(View.GONE);
        test.setVisibility(View.GONE);

        Intent j = getIntent();
        double b0t = j.getDoubleExtra("b0t",0);
        float b0 = (float) b0t;
        double b1t = j.getDoubleExtra("b1t", 0);
        float b1 = (float) b1t;
        double b2 = j.getDoubleExtra("b2t", 0);
        String array1 = j.getStringExtra("res1array");
        String array2 = j.getStringExtra("regarray");
        int nvalueint = j.getIntExtra("nvalueres", 0);
        double[][] regarray = gson.fromJson(array2, double[][].class);
        double[][] res1 = gson.fromJson(array1, double[][].class);
        float[][] lineardataset = new float[nvalueint][3];
        float[] h1 = new float[nvalueint];
        float[] h2 = new float[nvalueint];

        //plot predicted vs actual
        btn1.setOnClickListener(v -> {
            for (int i = 1; i <= nvalueint; i++){
                //predicted
                lineardataset[i-1][0] = (float) res1[i-1][1];
                //x value
                lineardataset[i-1][1] = (float) regarray[i-1][0];
                //actual
                lineardataset[i-1][2] = (float) res1[i-1][0];
                //lazy code
                h1[i-1] = (float) res1[i-1][1];
                h2[i-1] = (float) res1[i-1][0];

            }
            //ordena de menor a mayor col 0
            java.util.Arrays.sort(lineardataset, (a, b) -> Float.compare(a[0], b[0]));

            //bloque de codigo para hacer la linea de tendencia de esta gráfica
            List<Float> pred = Arrays.asList(ArrayUtils.toObject(h1));
            List<Float> actual = Arrays.asList(ArrayUtils.toObject(h2));
            List<Float> values = new ArrayList<>();

            float maxvaluepred = Collections.max(pred);
            float maxvalueactual = Collections.max(actual);
            float minvaluepred = Collections.min(pred);
            float minvalueactual = Collections.min(actual);
            values.add(maxvaluepred);
            values.add(maxvalueactual);
            values.add(minvalueactual);
            values.add(minvaluepred);
            float maxvaluemod = Collections.max(values);
            float minvaluemod = Collections.min(values);



            //listas para el dataset para plottear
            ArrayList<Entry> entrieslist = new ArrayList<>();
            ArrayList<Entry> entrieslist2 = new ArrayList<>();

            for(int i = 0; i < lineardataset.length; i++){
                //pred vs actual (x pred, y actual)
                entrieslist.add(new Entry(lineardataset[i][0], lineardataset[i][2]));
            }

            //dataset para la linea de tendencia
            entrieslist2.add(new Entry(minvaluemod, minvaluemod));
            entrieslist2.add(new Entry(maxvaluemod, maxvaluemod));



            //fusión de datasets
            ArrayList<ILineDataSet> linedatasets = new ArrayList<>();

            //dataset pred vs actual
            LineDataSet data = new LineDataSet(entrieslist, "");
            data.enableDashedLine(0f, 1f, 0f);
            data.setColor(Color.parseColor("#5865F2"));
            data.setValueTextColor(Color.parseColor("#5865F2"));
            data.setCircleColor(Color.parseColor("#5865F2"));
            data.setCircleHoleColor(Color.parseColor("#5865F2"));
            data.setDrawValues(false);

            //dataset linea de tendencia
            LineDataSet data2 = new LineDataSet(entrieslist2, "");
            data2.setDrawCircles(false);
            data2.setColor(Color.WHITE);
            data2.setDrawValues(false);

            linedatasets.add(data);
            linedatasets.add(data2);

            //chart style
            LineData xtds = new LineData(linedatasets);
            test.setBorderColor(Color.WHITE);
            test.getLegend().setEnabled(false);
            test.getDescription().setTextColor(Color.WHITE);
            test.getDescription().setText("");
            test.getDescription().setTextSize(12);
            test.getAxisLeft().setDrawGridLines(false);
            test.getXAxis().setDrawGridLines(false);
            test.setTouchEnabled(true);
            IMarker mv = new YourMarkerView(getApplicationContext(), R.layout.marker1var2);
            test.setMarker(mv);
            test.setData(xtds);

            XAxis xaxis = test.getXAxis();
            xaxis.setTextColor(Color.WHITE);
            xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xaxis.setAxisMaximum(maxvaluemod);
            xaxis.setAxisMinimum(minvaluemod);

            YAxis yaxis = test.getAxisLeft();
            yaxis.setTextColor(Color.WHITE);
            yaxis.setAxisMaximum(maxvaluemod);
            yaxis.setAxisMinimum(minvaluemod);

            YAxis yaxisr = test.getAxisRight();
            yaxisr.setEnabled(false);

            test.invalidate();

            test.setVisibility(View.VISIBLE);
            g1tv1.setVisibility(View.VISIBLE);
            g1tv2.setVisibility(View.VISIBLE);
        });

        btn2.setOnClickListener(v -> {

            test.setVisibility(View.GONE);
            g1tv1.setVisibility(View.GONE);
            g1tv2.setVisibility(View.GONE);
        });

        btn3.setOnClickListener(v -> {

            test.setVisibility(View.GONE);
            g1tv1.setVisibility(View.GONE);
            g1tv2.setVisibility(View.GONE);
        });




    }
}
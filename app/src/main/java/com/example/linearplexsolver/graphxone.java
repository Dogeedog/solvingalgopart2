package com.example.linearplexsolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.LimitLine;
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
    LineChart g2;
    TextView g2tv1;
    TextView g2tv2;
    Button b1;
    Button b2;
    Button b3;
    TextView g1tv1;
    TextView g1tv2;
    Gson gson = new GsonBuilder().create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphxone);

        b1 = findViewById(R.id.graph1one);
        b2 = findViewById(R.id.graph2one);
        b3 = findViewById(R.id.graph3one);
        test = findViewById(R.id.mf1one);
        g1tv1 = findViewById(R.id.actualv2one);
        g1tv2 = findViewById(R.id.fittedv2one);
        g2 = findViewById(R.id.mf2one);
        g2tv1 = findViewById(R.id.fittedone);
        g2tv2 = findViewById(R.id.residualone);

        g2.setVisibility(View.GONE);
        g2tv1.setVisibility(View.GONE);
        g2tv2.setVisibility(View.GONE);

        g1tv1.setVisibility(View.GONE);
        g1tv2.setVisibility(View.GONE);
        test.setVisibility(View.GONE);

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
        float[][] lineardataset = new float[nvalueint][3];


        //Botón para la gráfica de residuales ordinaria
        b1.setOnClickListener(v -> {

            for (int i = 1; i <= nvalueint; i++){
                lineardataset[i-1][0] = (float) res1[i-1][1];
                lineardataset[i-1][1] = (float) regarray[i-1][0];
                lineardataset[i-1][2] = (float) res1[i-1][0];
            }
            java.util.Arrays.sort(lineardataset, (a, b) -> Float.compare(a[1], b[1]));



            //dataset para plottear el modelo lineal
            ArrayList<Entry> entrieslist = new ArrayList<>();
            ArrayList<Entry> entrieslist2 = new ArrayList<>();

            for(int i = 0; i < lineardataset.length; i++){
                //valores reales (x, y real)
                entrieslist.add(new Entry(lineardataset[i][1], lineardataset[i][2]));
                //valores previstos (x, y previsto)
                entrieslist2.add(new Entry(lineardataset[i][1], lineardataset[i][0]));
            }


            //dataset de los valores reales de y


            ArrayList<ILineDataSet> linedatasets = new ArrayList<>();

            LineDataSet data = new LineDataSet(entrieslist, getString(R.string.actualv2));
            data.enableDashedLine(0f, 1f, 0f);
            data.setColor(Color.parseColor("#5865F2"));
            data.setValueTextColor(Color.parseColor("#5865F2"));
            data.setCircleColor(Color.parseColor("#5865F2"));
            data.setCircleHoleColor(Color.parseColor("#5865F2"));
            data.setDrawValues(false);

            LineDataSet data2 = new LineDataSet(entrieslist2, getString(R.string.fittedv2));
            data2.setDrawCircles(false);
            data2.setColor(Color.WHITE);
            data2.setDrawValues(false);


            linedatasets.add(data);
            linedatasets.add(data2);

            LineData xtds = new LineData(linedatasets);
            test.setBorderColor(Color.WHITE);
            test.getLegend().setTextColor(Color.WHITE);
            test.getDescription().setTextColor(Color.WHITE);
            test.getDescription().setText(getString(R.string.graphlin));
            test.getAxisLeft().setDrawGridLines(false);
            test.getXAxis().setDrawGridLines(false);
            test.setTouchEnabled(true);
            IMarker mv = new YourMarkerView(getApplicationContext(), R.layout.marker1var2);
            test.setMarker(mv);
            test.setData(xtds);

            XAxis xaxis = test.getXAxis();
            xaxis.setTextColor(Color.WHITE);
            xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            

            YAxis yaxis = test.getAxisLeft();
            yaxis.setTextColor(Color.WHITE);

            YAxis yaxisr = test.getAxisRight();
            yaxisr.setEnabled(false);

            test.invalidate();
            g2.setVisibility(View.GONE);
            g2tv1.setVisibility(View.GONE);
            g2tv2.setVisibility(View.GONE);

            test.setVisibility(View.VISIBLE);
            g1tv1.setVisibility(View.VISIBLE);
            g1tv2.setVisibility(View.VISIBLE);
        });

        b2.setOnClickListener(v -> {
            for (int i = 1; i <= nvalueint; i++){
                //y fitted value
                lineardataset[i-1][0] = (float) res1[i-1][1];
                //residual
                lineardataset[i-1][2] = (float) res1[i-1][2];
            }
            java.util.Arrays.sort(lineardataset, (a, b) -> Float.compare(a[0], b[0]));



            //dataset para plottear el modelo lineal
            ArrayList<Entry> entrieslist = new ArrayList<>();

            for(int i = 0; i < lineardataset.length; i++){
                //(x: fitted, y: residual)
                entrieslist.add(new Entry(lineardataset[i][0], lineardataset[i][2]));
            }


            //dataset de los valores reales de y


            ArrayList<ILineDataSet> linedatasets = new ArrayList<>();

            LineDataSet data = new LineDataSet(entrieslist, "");
            data.enableDashedLine(0f, 1f, 0f);
            data.setColor(Color.parseColor("#5865F2"));
            data.setValueTextColor(Color.parseColor("#5865F2"));
            data.setCircleColor(Color.parseColor("#5865F2"));
            data.setCircleHoleColor(Color.parseColor("#5865F2"));
            data.setDrawValues(false);





            linedatasets.add(data);

            LineData xtds = new LineData(linedatasets);
            g2.setBorderColor(Color.WHITE);
            g2.getLegend().setEnabled(false);
            g2.getDescription().setTextColor(Color.WHITE);
            g2.getDescription().setText(getString(R.string.ordinaryres));
            g2.getAxisLeft().setDrawGridLines(false);
            g2.getXAxis().setDrawGridLines(false);
            g2.setTouchEnabled(true);
            IMarker mv = new YourMarkerView(getApplicationContext(), R.layout.marker1var2);
            g2.setMarker(mv);
            g2.setData(xtds);

            XAxis xaxis = g2.getXAxis();
            xaxis.setTextColor(Color.WHITE);
            xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);


            YAxis yaxis = g2.getAxisLeft();
            yaxis.setTextColor(Color.WHITE);

            YAxis yaxisr = g2.getAxisRight();
            yaxisr.setEnabled(false);

            LimitLine ld = new LimitLine(0f);
            ld.setLineColor(Color.WHITE);
            ld.setLineWidth(0.25f);
            yaxis.addLimitLine(ld);

            g2.invalidate();

            test.setVisibility(View.GONE);
            g1tv1.setVisibility(View.GONE);
            g1tv2.setVisibility(View.GONE);

            g2.setVisibility(View.VISIBLE);
            g2tv1.setVisibility(View.VISIBLE);
            g2tv2.setVisibility(View.VISIBLE);
        });

        b3.setOnClickListener(v -> {
            test.setVisibility(View.GONE);
            g1tv1.setVisibility(View.GONE);
            g1tv2.setVisibility(View.GONE);
        });


    }
}
package com.example.linearplexsolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class DOEuniGraf extends AppCompatActivity {

    Button P1btn;
    Button P2btn;
    Button P3btn;
    LineChart Plot1;
    TextView P1x;
    TextView P1y;
    LineChart Plot2;
    TextView P2x;
    TextView P2y;
    LineChart Plot3;
    TextView P3x;
    TextView P3y;
    Gson gson = new GsonBuilder().create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doeuni_graf);

        P1btn = findViewById(R.id.DOEPlot1btn);
        P2btn = findViewById(R.id.DOEPlot2btn);
        P3btn = findViewById(R.id.DOEPlot3btn);

        Plot1 = findViewById(R.id.DOEPlot1);
        P1x = findViewById(R.id.DOEPlot1tvX);
        P1y = findViewById(R.id.DOEPlot1tvY);

        Plot2 = findViewById(R.id.DOEPlot2);
        P2x = findViewById(R.id.DOEPlot2tvX);
        P2y = findViewById(R.id.DOEPlot2tvY);

        Plot3 = findViewById(R.id.DOEPlot3);
        P3x = findViewById(R.id.DOEPlot3tvX);
        P3y = findViewById(R.id.DOEPlot3tvY);

        Plot1.setVisibility(View.GONE);
        P1x.setVisibility(View.GONE);
        P1y.setVisibility(View.GONE);

        Plot2.setVisibility(View.GONE);
        P2x.setVisibility(View.GONE);
        P2y.setVisibility(View.GONE);

        Plot3.setVisibility(View.GONE);
        P3x.setVisibility(View.GONE);
        P3y.setVisibility(View.GONE);

        DecimalFormat df = new DecimalFormat("#.####; - #");
        df.setRoundingMode(RoundingMode.HALF_UP);
        Intent x = getIntent();
        int avalueint = x.getIntExtra("avalue", 0);
        int nvalueint = x.getIntExtra("nvalue", 0);
        double totalAvg = x.getDoubleExtra("totalAvg", 0);
        String CIperTreatStr = x.getStringExtra("CIperTreatStr");
        String averagePerTreatArrayStr = x.getStringExtra("averagePerTreatArrayStr");
        String dataArrayStr = x.getStringExtra("dataArrayStr");
        double[] averagePerTreatArray = gson.fromJson(averagePerTreatArrayStr, double[].class);
        double[][] dataArray = gson.fromJson(dataArrayStr, double[][].class);
        double[][] CIperTreatArray = gson.fromJson(CIperTreatStr, double[][].class);

        // ---------- Plot 1: Respuesta media con intervalos

        //listas para el dataset para plottear
        ArrayList<Entry> entrieslist = new ArrayList<>();
        ArrayList<ArrayList<Entry>> entriesloop = new ArrayList<>();

        //fusión de datasets
        ArrayList<ILineDataSet> linedatasets = new ArrayList<>();

        for(int i = 0; i < avalueint; i++){
            //pred vs actual (x pred, y actual)
            entrieslist.add(new Entry(i+1, (float) averagePerTreatArray[i]));

            ArrayList<Entry> templist = new ArrayList<>();
            templist.add(new Entry(i + 1 - .33f, (float) CIperTreatArray[i][0]));
            templist.add(new Entry(i + 1 + .33f, (float) CIperTreatArray[i][0]));
            entriesloop.add(templist);
            LineDataSet data2 = new LineDataSet(entriesloop.get(3*i), "");
            data2.setColor(Color.parseColor("#5865F2"));
            data2.setDrawCircles(false);
            data2.setLineWidth(.5f);
            data2.setDrawValues(false);
            linedatasets.add(data2);

            ArrayList<Entry> templist2 = new ArrayList<>();
            templist2.add(new Entry(i + 1 - .33f, (float) CIperTreatArray[i][1]));
            templist2.add(new Entry(i + 1 + .33f, (float) CIperTreatArray[i][1]));
            entriesloop.add(templist2);
            LineDataSet data3 = new LineDataSet(entriesloop.get(1+3*i), "");
            data3.setColor(Color.parseColor("#5865F2"));
            data3.setDrawCircles(false);
            data3.setLineWidth(.5f);
            data3.setDrawValues(false);
            linedatasets.add(data3);

            ArrayList<Entry> templist3 = new ArrayList<>();
            templist3.add(new Entry(i + 1, (float) CIperTreatArray[i][0]));
            templist3.add(new Entry(i + 1, (float) CIperTreatArray[i][1]));
            entriesloop.add(templist3);
            LineDataSet data4 = new LineDataSet(entriesloop.get(2+3*i), "");
            data4.setColor(Color.parseColor("#5865F2"));
            data4.enableDashedLine(10f, 10f, 0f);
            data4.setDrawCircles(false);
            data4.setLineWidth(.5f);
            data4.setDrawValues(false);
            linedatasets.add(data4);
        }

        //dataset pred vs actual
        LineDataSet data = new LineDataSet(entrieslist, "");
        data.enableDashedLine(0f, 1f, 0f);
        data.setColor(Color.parseColor("#5865F2"));
        data.setValueTextColor(Color.parseColor("#5865F2"));
        data.setCircleColor(Color.parseColor("#5865F2"));
        data.setCircleHoleColor(Color.parseColor("#5865F2"));
        data.setDrawValues(false);
        linedatasets.add(data);

        //chart style
        LineData xtds = new LineData(linedatasets);
        Plot1.setBorderColor(Color.WHITE);
        Plot1.getLegend().setEnabled(false);
        Plot1.getDescription().setTextColor(Color.WHITE);
        Plot1.getDescription().setText("");
        Plot1.getDescription().setTextSize(12);
        Plot1.getAxisLeft().setDrawGridLines(false);
        Plot1.getXAxis().setDrawGridLines(false);
        Plot1.setTouchEnabled(true);
        IMarker mv = new YourMarkerView(getApplicationContext(), R.layout.marker1var2);
        Plot1.setMarker(mv);
        Plot1.setData(xtds);

        XAxis xaxis = Plot1.getXAxis();
        xaxis.setTextColor(Color.WHITE);
        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxis.setAxisMinimum(0f);
        xaxis.setAxisMaximum(avalueint+1);
        xaxis.setGranularity(1f);

        YAxis yaxis = Plot1.getAxisLeft();
        yaxis.setTextColor(Color.WHITE);
        LimitLine ld = new LimitLine(0f);
        ld.setLineColor(Color.WHITE);
        ld.setLineWidth(0.25f);
        yaxis.addLimitLine(ld);

        YAxis yaxisr = Plot1.getAxisRight();
        yaxisr.setEnabled(false);

        Plot1.invalidate();


        // ---------- Plot 2: Respuesta media con intervalos
        ArrayList<Entry> entrieslistp2 = new ArrayList<>();
        ArrayList<Entry> entrieslistp2_1 = new ArrayList<>();

        //fusión de datasets
        ArrayList<ILineDataSet> linedatasetsp2 = new ArrayList<>();

        for(int i = 0; i < avalueint; i++){
            //pred vs actual (x pred, y actual)
            entrieslistp2.add(new Entry(i+1, (float) averagePerTreatArray[i]));
        }
        entrieslistp2_1.add(new Entry(0, (float)totalAvg));
        entrieslistp2_1.add(new Entry(avalueint+1, (float)totalAvg));

        //dataset promedio por traatamiento
        LineDataSet datap2 = new LineDataSet(entrieslistp2, getResources().getString(R.string.avgtreat));
        datap2.enableDashedLine(0f, 1f, 0f);
        datap2.setColor(Color.parseColor("#5865F2"));
        datap2.setValueTextColor(Color.parseColor("#5865F2"));
        datap2.setCircleColor(Color.parseColor("#5865F2"));
        datap2.setCircleHoleColor(Color.parseColor("#5865F2"));
        datap2.setDrawValues(false);
        linedatasetsp2.add(datap2);


        //dataset paara promedio general
        LineDataSet datap2_1 = new LineDataSet(entrieslistp2_1, getResources().getString(R.string.granavg));
        datap2_1.enableDashedLine(10f, 10f, 0f);
        datap2_1.setColor(Color.WHITE);
        datap2_1.setValueTextColor(Color.WHITE);
        datap2_1.setCircleColor(Color.WHITE);
        datap2_1.setCircleHoleColor(Color.WHITE);
        datap2_1.setDrawValues(false);
        datap2_1.setDrawCircles(false);
        linedatasetsp2.add(datap2_1);




        LineData LDPlot2 = new LineData(linedatasetsp2);
        Plot2.setBorderColor(Color.WHITE);
        Plot2.getLegend().setEnabled(true);
        Plot2.getLegend().setTextColor(Color.WHITE);
        Plot2.getDescription().setTextColor(Color.WHITE);
        Plot2.getDescription().setText("");
        Plot2.getDescription().setTextSize(12);
        Plot2.getAxisLeft().setDrawGridLines(false);
        Plot2.getXAxis().setDrawGridLines(false);
        Plot2.setTouchEnabled(true);
        Plot2.setMarker(mv);
        Plot2.setData(LDPlot2);

        XAxis xaxisPlot2 = Plot2.getXAxis();
        xaxisPlot2.setTextColor(Color.WHITE);
        xaxisPlot2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxisPlot2.setAxisMinimum(0f);
        xaxisPlot2.setAxisMaximum(avalueint+1);
        xaxisPlot2.setGranularity(1f);

        YAxis yaxisPlot2 = Plot2.getAxisLeft();
        yaxisPlot2.setTextColor(Color.WHITE);
        LimitLine ldPlot2 = new LimitLine(0f);
        ldPlot2.setLineColor(Color.WHITE);
        ldPlot2.setLineWidth(0.25f);
        yaxisPlot2.addLimitLine(ld);

        YAxis yaxisrPlot2 = Plot2.getAxisRight();
        yaxisrPlot2.setEnabled(false);

        Plot2.invalidate();


        // ---------- Plot 3: Valores individuales
        ArrayList<Entry> entrieslistp3 = new ArrayList<>();

        //fusión de datasets
        ArrayList<ILineDataSet> linedatasetsp3 = new ArrayList<>();


        for(int i = 0; i < avalueint; i++){
            for (int j = 0; j < nvalueint; j++){
                entrieslistp3.add(new Entry(i+1, (float)dataArray[j][i]));
            }

        }

        //dataset promedio por traatamiento
        LineDataSet datap3 = new LineDataSet(entrieslistp3, "");
        datap3.enableDashedLine(0f, 1f, 0f);
        datap3.setColor(Color.parseColor("#5865F2"));
        datap3.setValueTextColor(Color.parseColor("#5865F2"));
        datap3.setCircleColor(Color.parseColor("#5865F2"));
        datap3.setCircleHoleColor(Color.parseColor("#5865F2"));
        datap3.setDrawValues(false);
        linedatasetsp3.add(datap3);




        LineData LDPlot3 = new LineData(linedatasetsp3);
        Plot3.setBorderColor(Color.WHITE);
        Plot3.getLegend().setEnabled(false);
        Plot3.getDescription().setTextColor(Color.WHITE);
        Plot3.getDescription().setText("");
        Plot3.getDescription().setTextSize(12);
        Plot3.getAxisLeft().setDrawGridLines(false);
        Plot3.getXAxis().setDrawGridLines(false);
        Plot3.setTouchEnabled(true);
        Plot3.setMarker(mv);
        Plot3.setData(LDPlot3);

        XAxis xaxisPlot3 = Plot3.getXAxis();
        xaxisPlot3.setTextColor(Color.WHITE);
        xaxisPlot3.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxisPlot3.setAxisMinimum(0f);
        xaxisPlot3.setAxisMaximum(avalueint+1);
        xaxisPlot3.setGranularity(1f);

        YAxis yaxisPlot3 = Plot3.getAxisLeft();
        yaxisPlot3.setTextColor(Color.WHITE);
        LimitLine ldPlot3 = new LimitLine(0f);
        ldPlot3.setLineColor(Color.WHITE);
        ldPlot3.setLineWidth(0.25f);
        yaxisPlot3.addLimitLine(ld);

        YAxis yaxisrPlot3 = Plot3.getAxisRight();
        yaxisrPlot3.setEnabled(false);

        Plot3.invalidate();


        //show/hide
        P1btn.setOnClickListener(v -> {
            P3y.setVisibility(View.GONE);
            P3x.setVisibility(View.GONE);
            Plot3.setVisibility(View.GONE);

            P2y.setVisibility(View.GONE);
            P2x.setVisibility(View.GONE);
            Plot2.setVisibility(View.GONE);

            P1y.setVisibility(View.VISIBLE);
            P1x.setVisibility(View.VISIBLE);
            Plot1.setVisibility(View.VISIBLE);
        });

        P2btn.setOnClickListener(v -> {
            P3y.setVisibility(View.GONE);
            P3x.setVisibility(View.GONE);
            Plot3.setVisibility(View.GONE);

            P2y.setVisibility(View.VISIBLE);
            P2x.setVisibility(View.VISIBLE);
            Plot2.setVisibility(View.VISIBLE);

            P1y.setVisibility(View.GONE);
            P1x.setVisibility(View.GONE);
            Plot1.setVisibility(View.GONE);
        });

        P3btn.setOnClickListener(v -> {
            P3y.setVisibility(View.VISIBLE);
            P3x.setVisibility(View.VISIBLE);
            Plot3.setVisibility(View.VISIBLE);

            P2y.setVisibility(View.GONE);
            P2x.setVisibility(View.GONE);
            Plot2.setVisibility(View.GONE);

            P1y.setVisibility(View.GONE);
            P1x.setVisibility(View.GONE);
            Plot1.setVisibility(View.GONE);
        });
    }
}
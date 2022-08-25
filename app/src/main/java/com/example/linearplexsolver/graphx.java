package com.example.linearplexsolver;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.List;

public class graphx extends AppCompatActivity {

    LineChart test;
    Button b1;
    Button b2;
    Button b3;
    LinearLayout mflayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphx);

        mflayout = findViewById(R.id.maineffects);
        test = findViewById(R.id.mf1);
        b1 = findViewById(R.id.graph1);
        b2 = findViewById(R.id.graph2);
        b3 = findViewById(R.id.graph3);

        b1.setOnClickListener(v -> {
            mflayout.setVisibility(View.VISIBLE);
        });

        b2.setOnClickListener(v -> {
            mflayout.setVisibility(View.GONE);
        });

        b3.setOnClickListener(v -> {
            mflayout.setVisibility(View.GONE);
        });

        float[][] entries = new float[][]{
                {1, 2},
                {4, 4},
                {6, 18}
        };

        List<Entry> entrieslist = new ArrayList<>();

        for(int i = 0; i < entries.length; i++){
            entrieslist.add(new Entry(entries[i][1], entries[i][0]));
        }

        LineDataSet data = new LineDataSet(entrieslist, "test");
        data.setColor(Color.WHITE);
        data.setValueTextColor(Color.WHITE);

        LineData linetest = new LineData(data);
        test.setData(linetest);
        test.setBorderColor(Color.WHITE);
        test.getLegend().setTextColor(Color.WHITE);
        test.getDescription().setTextColor(Color.WHITE);

        XAxis xaxis = test.getXAxis();
        xaxis.setAxisMinimum(0);
        xaxis.setAxisMaximum(20);
        xaxis.setTextColor(Color.WHITE);

        YAxis yaxis = test.getAxisLeft();
        yaxis.setTextColor(Color.WHITE);

        YAxis yaxisr = test.getAxisRight();
        yaxisr.setTextColor(Color.WHITE);

        test.setDragEnabled(true);
        test.invalidate();
        mflayout.setVisibility(View.GONE);


    }
}
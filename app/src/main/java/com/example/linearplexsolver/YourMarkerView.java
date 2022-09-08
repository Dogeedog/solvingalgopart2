package com.example.linearplexsolver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.math.RoundingMode;
import java.text.DecimalFormat;

//https://weeklycoding.com/mpandroidchart-documentation/markerview/

@SuppressLint("ViewConstructor")
public class YourMarkerView extends MarkerView implements IMarker {

    private TextView tvContent;

    public YourMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        DecimalFormat df = new DecimalFormat("#.##; -#");
        df.setRoundingMode(RoundingMode.CEILING);
        String finaltext = "x: " + df.format(e.getX()) + " y: " + df.format(e.getY());
        tvContent.setText(finaltext);
        super.refreshContent(e, highlight);
    }

    private MPPointF mOffset;

    @Override
    public MPPointF getOffset() {

        if(mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = new MPPointF(-(getWidth() / 1.5f), -getHeight());
        }

        return mOffset;
    }

}


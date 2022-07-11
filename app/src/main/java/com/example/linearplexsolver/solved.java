package com.example.linearplexsolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class solved extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solved);
        TextView modelbox = (findViewById(R.id.textView2)); //.setText(Html.fromHtml("y=B<sub><small>o</small></sub>+B<sub><small>1</small></sub>X<sub><small>1</small></sub>+B<sub><small>2</small></sub>X<sub><small>2</small></sub>"));
        Intent j = getIntent();
        String modeltext = j.getStringExtra("modelstr");
        modelbox.setText(modeltext);
        modelbox.setTextIsSelectable(true);
    }
    public void graph(View h) {
        Intent j = new Intent(this, graphx.class);
        startActivity(j);
    }

}

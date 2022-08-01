package com.example.linearplexsolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class solvedone extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solvedone);
        TextView modelbox = (findViewById(R.id.textView2one)); //.setText(Html.fromHtml("y=B<sub><small>o</small></sub>+B<sub><small>1</small></sub>X<sub><small>1</small></sub>+B<sub><small>2</small></sub>X<sub><small>2</small></sub>"));
        Intent j = getIntent();
        CharSequence modeltext = j.getCharSequenceExtra("modelstr");
        modelbox.setText(modeltext);
        modelbox.setTextIsSelectable(true);
    }
    public void graphx(View h) {
        Intent j = new Intent(this, graphx.class);
        startActivity(j);
    }
}
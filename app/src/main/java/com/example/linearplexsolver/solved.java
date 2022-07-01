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
        ((TextView)findViewById(R.id.textView2)).setText(Html.fromHtml("y=B<sub><small>o</small></sub>+B<sub><small>1</small></sub>X<sub><small>1</small></sub>+B<sub><small>2</small></sub>X<sub><small>2</small></sub>"));
    }
    public void graph(View h) {
        Intent j = new Intent(this, graphx.class);
        startActivity(j);
    }

    public static void main(String[] args) {
        long sum = 0;
        int dimension = 3;
        int i, j;
        int matrix[][] = new int[dimension][dimension];

        for (i = 0; i < dimension; i++) {
            for (j = 0; j < dimension; j++) {
                matrix[i][j] = 232; // Number you want to store, could be a scanner input
                sum = matrix[i][j];
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }


    }


}

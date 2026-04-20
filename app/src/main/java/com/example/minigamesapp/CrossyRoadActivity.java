package com.example.minigamesapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CrossyRoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crossy_road);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        GridLayout grid = findViewById(R.id.grid);

        int rows = 11;
        int cols = 7;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {

                View cell = new View(this);

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.rowSpec = GridLayout.spec(r, 1f);
                params.columnSpec = GridLayout.spec(c, 1f);
                params.width = 0;
                params.height = 0;

                cell.setLayoutParams(params);
                cell.setBackgroundResource(R.drawable.cell_border);
                grid.addView(cell);
            }
        }
        ImageView chicken = findViewById(R.id.chicken);
        final int[] playerRow = {10};

        grid.setOnClickListener(v -> {
            if (playerRow[0] > 0) {
                playerRow[0]--;

                GridLayout.LayoutParams params = (GridLayout.LayoutParams) chicken.getLayoutParams();
                params.rowSpec = GridLayout.spec(playerRow[0], 1f);
                chicken.setLayoutParams(params);
            }
        });


    }
}
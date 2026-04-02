package com.example.minigamesapp;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class BattleshipsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_battleships);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        GridLayout grid = findViewById(R.id.grid);
        for (int i = 0; i < 100; i = i + 1) {
            ImageView vakje = new ImageView(this);
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();

            float density = getResources().getDisplayMetrics().density;
            int sizeInPx = (int) (100 * density);

            layoutParams.width = sizeInPx;
            layoutParams.height = sizeInPx;

            vakje.setLayoutParams(layoutParams);
            vakje.setBackgroundColor(Color.RED);
            grid.addView(vakje);
        }
    }
}
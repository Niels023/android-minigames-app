package com.example.minigamesapp;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChessActivity extends AppCompatActivity {

    public int squares = 64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chess);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        GridLayout gridLayout = findViewById(R.id.grid);

        int rows = 8;
        int columns = 8;
        boolean colorIsBlack = true;

        // setup squares

        for (int row = 1; row <= rows; row++) {
            Log.e("row", Integer.toString(row));
            for (int column = 1; column <= columns; column++) {
                Log.e("column", Integer.toString(column));
                ImageView square = new ImageView(this);
                if (colorIsBlack) {
                    square.setBackgroundColor(Color.rgb(78, 51, 21));
                } else {
                    square.setBackgroundColor(Color.rgb(234, 192, 144));
                }

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();

                int size = (int) (50 * getResources().getDisplayMetrics().density);

                params.width = size;
                params.height = size;

                square.setLayoutParams(params);
                String chessNotation = ColumnToLetter(column) + row;
                square.setTag(chessNotation);
                square.setId(ImageView.generateViewId());
                square.setOnClickListener(v -> markSpecificChessSquare(chessNotation));
                Log.e("Chess Notation:", chessNotation);
                gridLayout.addView(square);
                colorIsBlack = !colorIsBlack;
            }
            colorIsBlack = !colorIsBlack;
        }

        
    }

    private static String ColumnToLetter(int columnNumber) {
        String columnLetter = "A";
        switch (columnNumber) {
            case 1:
                columnLetter = "A";
                break;
            case 2:
                columnLetter = "B";
                break;
            case 3:
                columnLetter = "C";
                break;
            case 4:
                columnLetter = "D";
                break;
            case 5:
                columnLetter = "E";
                break;
            case 6:
                columnLetter = "F";
                break;
            case 7:
                columnLetter = "G";
                break;
            case 8:
                columnLetter = "H";
                break;
            default:
                throw new IllegalArgumentException("Invalid column number: " + columnNumber);
        }
        return columnLetter;
    }

    private void markSpecificChessSquare(String targetNotation){

        GridLayout gridLayout = findViewById(R.id.grid);

        View targetSquare = null;
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View child = gridLayout.getChildAt(i);
            if (targetNotation.equals(child.getTag())) {
                targetSquare = child;
                break;
            }
        }

        if (targetSquare != null) {
            targetSquare.setBackgroundColor(Color.RED);
        }
    }
}
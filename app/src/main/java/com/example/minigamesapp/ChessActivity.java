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

        // 1️⃣ Create the board model (pieces)
        String[][] board = new String[rows][columns];

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) board[row][column] = "";
        }

        board[0] = new String[]{"black_rook","black_knight","black_bishop","black_queen","black_king","black_bishop","black_knight","black_rook"};
        for (int column = 0; column < columns; column++) board[1][column] = "black_pawn";

        board[7] = new String[]{"white_rook","white_knight","white_bishop","white_queen","white_king","white_bishop","white_knight","white_rook"};
        for (int column = 0; column < columns; column++) board[6][column] = "white_pawn";

        boolean colorIsBlack = false;
        int squareSize = (int)(50 * getResources().getDisplayMetrics().density); // turning 50dp into pixels cuz the layout params only accept pixels!!

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                ImageView square = new ImageView(this);

                square.setBackgroundColor(colorIsBlack ? Color.rgb(78,51,21) : Color.rgb(234,192,144));

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = squareSize;
                params.height = squareSize;
                params.rowSpec = GridLayout.spec(row);
                params.columnSpec = GridLayout.spec(col);
                square.setLayoutParams(params);

                square.setTag(R.id.tag_coords, row + "," + col);
                square.setTag(R.id.tag_piece, board[row][col]);

                String piece = board[row][col];
                if (!piece.isEmpty()) {
                    int resId = getResources().getIdentifier(piece, "drawable", getPackageName());
                    if (resId != 0) {
                        square.setImageResource(resId); // dynamically set the image
                    }
                }

                square.setId(ImageView.generateViewId());
                square.setOnClickListener(v -> selectPiece(square));

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

    private void selectPiece(ImageView selectedPiece) {
        if (selectedPiece != null) {
            selectedPiece.setBackgroundColor(Color.BLUE);
            showLegalMoves((selectedPiece));
        }
    }

    private void showLegalMoves(ImageView selectedPiece) {
        String pieceType = selectedPiece.getTag(R.id.tag_piece).toString();
//        if (pieceType == "")


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
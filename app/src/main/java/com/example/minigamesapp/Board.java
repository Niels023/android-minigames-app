package com.example.minigamesapp;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import java.util.ArrayList;

public class Board{
    public GridLayout gridLayout;
    public ArrayList<Piece> pieces;
    public Board(GridLayout grid) {
        gridLayout = grid;
    }
    public void setup(Context context) {
        Log.d("ChessGame", "--// Setting up the board.");
        boolean colorIsBlack = false;
        int squareSize = (int)(46.875 * context.getResources().getDisplayMetrics().density); // turning 50dp into pixels cuz the layout params only accept pixels!!
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ImageView square = new ImageView(context);

                square.setBackgroundColor(colorIsBlack ? Color.rgb(182, 137, 99) : Color.rgb(240, 218, 182));
                square.setTag(R.id.color, colorIsBlack);

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = squareSize;
                params.height = squareSize;
                params.rowSpec = GridLayout.spec(row);
                params.columnSpec = GridLayout.spec(col);
                square.setLayoutParams(params);

                square.setId(ImageView.generateViewId());

                gridLayout.addView(square);

                colorIsBlack = !colorIsBlack;
            }
            colorIsBlack = !colorIsBlack;
        }
        Log.d("ChessGame", "--// Board has been setup!");
    }

    public void clean() {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            boolean colorIsBlack = Boolean.parseBoolean(gridLayout.getChildAt(i).getTag(R.id.color).toString());
            gridLayout.getChildAt(i).setBackgroundColor(colorIsBlack ? Color.rgb(182, 137, 99) : Color.rgb(240, 218, 182));
        }
    }
    public Piece getPieceFromPosition(Position pos) {
        for (Piece piece : this.pieces) {
            if (piece.position.row == pos.row && piece.position.column == pos.column) {
                return piece;
            }
        }
        return null;
    }
}

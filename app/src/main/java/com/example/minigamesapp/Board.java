package com.example.minigamesapp;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;

public class Board{

    public GridLayout boardGrid;

    public void addPiece(Piece chessPiece) {
        Position pos = chessPiece.position;
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                GridLayout.spec(pos.row),
                GridLayout.spec(pos.column)
        );
        boardGrid.addView(chessPiece.chessImage, params);
        Log.e("Placed", "Piece has been added to: " + pos.row + "," + pos.column);
    }
}

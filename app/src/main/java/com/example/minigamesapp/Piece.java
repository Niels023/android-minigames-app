package com.example.minigamesapp;

import android.util.Log;
import android.widget.GridLayout;
import android.widget.ImageView;

public class Piece implements IPiece{
    public boolean isWhite;
    public Position position;
    public ImageView chessImage;
    public Position getPositionFromIndex(int index) {
        int moduloRow = 0;
        while (index >= 8) {
            index -= 8;
            moduloRow++;
        }
        int moduloCol = index; // left-overs
        return new Position(moduloRow, moduloCol);
    }
    public boolean isMoveLegal(Position targetPosition){
        Log.e("OH NO", "no work");
        return false;
    };
    public void move(Position targetPosition){
    };
    public Position[] getLegalMoves(Board board, Piece piece) {
        Log.e("OH NO", "no work");
        return null;
    };
}

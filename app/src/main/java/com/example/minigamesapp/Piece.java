package com.example.minigamesapp;

import android.util.Log;
import android.widget.GridLayout;
import android.widget.ImageView;

public class Piece implements IPiece{

    public int pictureLocation = R.drawable.white_pawn;
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
    public boolean isMoveLegal(Board board, Position targetPosition){
        Log.e("OH NO", "no work");
        return false;
    };
    public boolean move(Board board, int index){
        Log.e("OH NO", "OH GOD");
        return false;
    };
    public Position[] getLegalMoves(Board board) {
        Log.e("OH NO", "no work");
        return new Position[]{};
    };

    public boolean isAttackMove(Board board, Position targetPos) {
        return isMoveLegal(board, targetPos);
    }
}

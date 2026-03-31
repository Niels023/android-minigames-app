package com.example.minigamesapp;

import android.widget.ImageView;

import org.intellij.lang.annotations.Identifier;

public class Pawn extends Piece implements IPiece {

    public int pictureLocation = R.drawable.white_pawn;

    public Pawn (boolean isWhite, Position position, ImageView chessImage){
        this.chessImage = chessImage;
        this.isWhite = isWhite;
        this.position = position;
        if (!isWhite) {
            pictureLocation = R.drawable.black_pawn;
        }

        chessImage.setImageResource(pictureLocation);
    }

    public boolean isMoveLegal(Position targetPos){
        int firstRow;
        int secondRow;
        if (this.isWhite) {
            firstRow = 1;
            secondRow = 2;
        } else {
            firstRow = -1;
            secondRow = -2;
        }

        Position pawnPos = this.position;
        // Check for UP moves
        if(targetPos.column == pawnPos.column) {
            // same column
            if (targetPos.row - pawnPos.row == firstRow) {
                // Position is 1 up


            } else if (targetPos.row - pawnPos.row == secondRow) {
                // Position is 2 up
            }
        } else {
            // Check for diagonal takes
            // Check for en passant
        }
        return false;
    };
    public void move(Position targetPosition){

    };
}



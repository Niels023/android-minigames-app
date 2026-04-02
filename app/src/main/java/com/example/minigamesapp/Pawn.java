package com.example.minigamesapp;

import android.content.Context;
import android.widget.GridLayout;
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
        // Check for front moves
        if(targetPos.column == pawnPos.column) {
            // same column
            if (targetPos.row - pawnPos.row == firstRow) {
                // Position is 1 in front

            } else if (targetPos.row - pawnPos.row == secondRow) {
                // Position is 2 in front
                return true;
            }
        } else {
            // Check for diagonal takes
            // Check for en passant
        }
        return false;
    };

    public Position[] getLegalMoves(GridLayout board, Piece piece) {
        for (int i = 0; i < board.getChildCount(); i++) {
            ImageView
        }
    }
    public void move(Position targetPosition){

    };
}



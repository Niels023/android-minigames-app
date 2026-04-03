package com.example.minigamesapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.ImageView;

import org.intellij.lang.annotations.Identifier;

import java.util.ArrayList;
import java.util.List;

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
    public boolean isMoveLegal(Board board, Position targetPos){
        int firstRow;
        int secondRow;
        int startRow;
        if (this.isWhite) {
            firstRow = -1;
            secondRow = -2;
            startRow = 6;
        } else {
            firstRow = 1;
            secondRow = 2;
            startRow = 1;
        }
        Position pawnPos = this.position;
        // Check for front moves
        if(targetPos.column == pawnPos.column) {
            // same column
            if (targetPos.row - pawnPos.row == firstRow) {
                // Position is 1 in front
                ImageView square = (ImageView) board.gridLayout.getChildAt(targetPos.row * 8 + targetPos.column);
                Drawable drawable = square.getDrawable();
                boolean hasImage = (drawable != null);

                return !hasImage;
            } else if (targetPos.row - pawnPos.row == secondRow) {
                // Position is 2 in front

                if (pawnPos.row != startRow) {
                    return false;
                }

                int frontRow = targetPos.row - firstRow;

                ImageView frontSquare = (ImageView) board.gridLayout.getChildAt(frontRow * 8 + targetPos.column);
                Drawable frontDrawable = frontSquare.getDrawable();
                boolean hasImageFront = (frontDrawable != null);

                if (hasImageFront) {
                    return false;
                }

                ImageView square = (ImageView) board.gridLayout.getChildAt(targetPos.row * 8 + targetPos.column);
                Drawable drawable = square.getDrawable();
                boolean hasImage = (drawable != null);

                if (!hasImage) {
                    return true;
                }

                Piece piece = board.getPieceFromPosition(targetPos);
                if (piece == null) {
                    return true;
                }

                if (piece.isWhite != this.isWhite) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            // not same column
            if (targetPos.row - pawnPos.row == firstRow) {
                // Position is 1 in front
                if (targetPos.column - 1 == pawnPos.column || targetPos.column + 1 == pawnPos.column){
                    ImageView square = (ImageView) board.gridLayout.getChildAt(targetPos.row * 8 + targetPos.column);
                    Drawable drawable = square.getDrawable();
                    boolean hasImage = (drawable != null);

                    if (!hasImage) {
                        return false;
                    }

                    Piece piece = board.getPieceFromPosition(targetPos);

                    if (piece.isWhite != this.isWhite) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }

            // Check for diagonal takes
            // Check for en passant
        }
        return false;
    };
    public Position[] getLegalMoves(Board board, Piece piece) {
        List<Position> list = new ArrayList<>();
        for (int index = 0; index < board.gridLayout.getChildCount(); index++) {
            if (isMoveLegal(board, getPositionFromIndex(index))) {
                list.add(getPositionFromIndex(index));
                Log.e("IsLegal", getPositionFromIndex(index).row + "," + getPositionFromIndex(index).column + " is a legal move!");
            } else {
                Log.e("IsNOTLegal", getPositionFromIndex(index).row + "," + getPositionFromIndex(index).column + " is NOT a legal move!");
            }
        }
        return list.toArray(new Position[0]);
    }
    public void move(Position targetPosition){

    };
}



package com.example.minigamesapp;

import android.content.Context;
import android.graphics.Color;
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
        int firstRow = isWhite ? -1 : 1;
        int secondRow = isWhite ? -2 : 2;
        int startRow = isWhite ? 6 : 1;
        Position pawnPos = this.position;

        if (position.checkEquals(targetPos)) {
            return false;
        }

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
                    Log.e("e", "has image(piece)");
                    return false;
                } else {
                    Log.e("e", "DOESNT image(piece)");
                }

                ImageView square = (ImageView) board.gridLayout.getChildAt(targetPos.row * 8 + targetPos.column);
                Drawable drawable = square.getDrawable();
                boolean hasImage = (drawable != null);

                if (!hasImage) {
                    return true;
                }

                Piece piece = board.getPieceFromPosition(targetPos);
                if (piece != null) {
                    return false;
                }

                return true;
            }
        } else {
            isAttackMove(board, targetPos, false);
        }

        if (!isAttackMove(board, targetPos, false)) {
            return false;
        }

        ImageView square = (ImageView) board.gridLayout.getChildAt(targetPos.row * 8 + targetPos.column);
        Drawable drawable = square.getDrawable();
        boolean hasImage = (drawable != null);
        if (hasImage) {
            return (isWhite != board.getPieceFromPosition(targetPos).isWhite);
        } else {
            return false;
        }
    }

    public boolean isAttackMove(Board board, Position targetPos, Boolean turn) {
        Log.e("eeee", "eeee");
        Position pawnPos = this.position;
        int firstRow = isWhite ? -1 : 1;
        int secondRow = isWhite ? -2 : 2;
        int startRow = isWhite ? 6 : 1;
        if (targetPos.row - pawnPos.row == firstRow) {
            // Position is 1 in front
            if (targetPos.column - 1 == pawnPos.column || targetPos.column + 1 == pawnPos.column) {
                Log.e("bbbbb", "bbbbb");
                return true;
            }
        }
        Log.e("aaaa", "aaaa");
        return false;
    }

    public Position[] getLegalMoves(Board board) {
        List<Position> list = new ArrayList<>();

        int row = position.row;

        int firstRow = isWhite ? -1 : 1;
        int secondRow = isWhite ? -2 : 2;

        for (int r = 0; r < 8; r++) {
            if (r - row == firstRow) {
                for (int c = 0; c < 8; c++) {
                    if (isMoveLegal(board, new Position(r, c))) {
                        list.add(new Position(r, c));
                    }
                }
            }
        }

        int startRow = isWhite ? 6 : 1;
        if (row == startRow) {
            for (int r = 0; r < 8; r++) {
                if (r - row == secondRow) {
                    for (int c = 0; c < 8; c++) {
                        if (isMoveLegal(board, new Position(r, c))) {
                            list.add(new Position(r, c));
                        }
                    }
                }
            }
        }

        return list.toArray(new Position[0]);
    }
    public boolean move(Board board,int index){
        if (isMoveLegal(board, getPositionFromIndex(index))) {
            Position pos = getPositionFromIndex(index);
            ImageView otherView = (ImageView) board.gridLayout.getChildAt(index);
            ImageView currentView = this.chessImage;
            currentView.setImageResource(0);
            otherView.setImageResource(this.pictureLocation);
            if (board.getPieceFromPosition(pos) != null) {
                Piece otherPiece = board.getPieceFromPosition(pos);
                board.pieces.remove(otherPiece);
            }
            this.chessImage = otherView;
            this.position = pos;
            board.clean();
            return true;
        } else {
            Position pos = getPositionFromIndex(index);
            Log.e("Pawn", "Illegal Move, " + Integer.toString(this.position.row) + "," + Integer.toString(this.position.column) + " > " + Integer.toString(pos.row) + "," + Integer.toString(pos.column));
            board.clean();
            return false;
        }
    };
}



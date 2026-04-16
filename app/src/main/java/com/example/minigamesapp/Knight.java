package com.example.minigamesapp;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece implements IPiece {
    public int pictureLocation = R.drawable.white_knight;

    public Knight (boolean isWhite, Position position, ImageView chessImage){
        this.chessImage = chessImage;
        this.isWhite = isWhite;
        this.position = position;
        if (!isWhite) {
            pictureLocation = R.drawable.black_knight;
        }
        chessImage.setImageResource(pictureLocation);
    }

    public boolean isMoveLegal(Board board, Position targetPos) {
        int currentRow = position.row;
        int currentColumn = position.column;
        int targetRow = targetPos.row;
        int targetColumn = targetPos.column;

        if (position.checkEquals(targetPos)) {
            return false;
        }

        int highestRow = Math.max(currentRow, targetRow);
        int lowestRow = Math.min(currentRow, targetRow);

        int highestColumn = Math.max(currentColumn, targetColumn);
        int lowestColumn = Math.min(currentColumn, targetColumn);

        if (highestRow >= 8 || highestColumn >= 8 || lowestRow < 0 || lowestColumn < 0) {
            return false;
        }

        if (highestRow - lowestRow != 2 || highestColumn - lowestColumn != 1 ) {
            if (highestRow - lowestRow != 1 || highestColumn - lowestColumn != 2 ) {
                return false;
            }
        }

        Piece targetPiece = board.getPieceFromPosition(targetPos);

        return targetPiece == null || targetPiece.isWhite != this.isWhite;
    };
    public Position[] getLegalMoves(Board board) {
        List<Position> list = new ArrayList<>();
        int row = position.row;
        int col = position.column;

        int[][] directions = {
                {2,1},
                {2,-1},
                {-2,1},
                {-2,-1},
                {1,2},
                {1,-2},
                {-1,2},
                {-1,-2}
        };

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            Position newPos = new Position(newRow, newCol);

            if (isMoveLegal(board, newPos)) {
                list.add(newPos);
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
            Log.e("Knight", "Illegal Move, " + Integer.toString(this.position.row) + "," + Integer.toString(this.position.column) + " > " + Integer.toString(pos.row) + "," + Integer.toString(pos.column));
            board.clean();
            return false;
        }
    };
}

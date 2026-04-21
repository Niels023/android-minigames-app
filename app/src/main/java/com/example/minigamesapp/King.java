package com.example.minigamesapp;

import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece implements IPiece {
    public int pictureLocation = R.drawable.white_king;

    public King (boolean isWhite, Position position, ImageView chessImage){
        this.chessImage = chessImage;
        this.isWhite = isWhite;
        this.position = position;
        if (!isWhite) {
            pictureLocation = R.drawable.black_king;
        }
        chessImage.setImageResource(pictureLocation);
    }

    public boolean isMoveLegal(Board board, Position targetPos) {
        int currentRow = position.row;
        int currentColumn = position.column;
        int targetRow = targetPos.row;
        int targetColumn = targetPos.column;

        int rowDiff = Math.abs(currentRow - targetRow);
        int colDiff = Math.abs(currentColumn - targetColumn);

        // Must move exactly one square in any direction
        if ((rowDiff <= 1 && colDiff <= 1) && (rowDiff + colDiff != 0)) {
            Piece targetPiece = board.getPieceFromPosition(targetPos);

            if (targetPiece == null || targetPiece.isWhite != this.isWhite) {

                // 🚨 NEW: prevent moving into check
                return !board.isSquareUnderAttack(targetPos, this.isWhite);
            }
        }

        return false;
    }

    public Position[] getLegalMoves(Board board) {
        List<Position> moves = new ArrayList<>();

        int row = position.row;
        int col = position.column;

        int[] directionRows = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] directionCols = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            int newRow = row + directionRows[i];
            int newCol = col + directionCols[i];

            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {

                Position newPos = new Position(newRow, newCol);
                Piece targetPiece = board.getPieceFromPosition(newPos);

                if (isMoveLegal(board, newPos)) {
                    if (!board.isSquareUnderAttack(newPos, this.isWhite)) {
                        moves.add(newPos);
                    }
                }
            }
        }

        return moves.toArray(new Position[0]);
    }

    public boolean move(Board board, int index){
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
            Log.e("King", "Illegal Move, " + Integer.toString(this.position.row) + "," + Integer.toString(this.position.column) + " > " + Integer.toString(pos.row) + "," + Integer.toString(pos.column));
            board.clean();
            return false;
        }
    };

    public boolean isAttackMove(Board board, Position targetPos) {
        return isMoveLegal(board, targetPos);
    }
}

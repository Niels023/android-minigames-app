package com.example.minigamesapp;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece implements IPiece {
    public int pictureLocation = R.drawable.white_rook;
    public Rook (boolean isWhite, Position position, ImageView chessImage){
        this.chessImage = chessImage;
        this.isWhite = isWhite;
        this.position = position;
        if (!isWhite) {
            pictureLocation = R.drawable.black_rook;
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

        if (currentRow == targetRow) {
            // on same row
            int step = (targetColumn > currentColumn) ? 1 : -1; // switch 1 and -1 if target column number is higher
            for (int col = currentColumn + step; col != targetColumn; col += step) {
                if (board.getPieceFromPosition(new Position(currentRow, col)) != null) {
                    return false; // piece is in the way
                }
            }
        }
        else {
            int step = (targetRow > currentRow) ? 1 : -1;  // switch 1 and -1 if target row number is higher

            for (int row = currentRow + step; row != targetRow; row += step) {
                if (board.getPieceFromPosition(new Position(row, currentColumn)) != null) {
                    return false; // piece is in the way
                }
            }
        }

        ImageView square = (ImageView) board.gridLayout.getChildAt(targetRow * 8 + targetColumn);
        Drawable drawSquare = square.getDrawable();
        boolean HasImage = (drawSquare != null);
        if (!HasImage) {
            return true;
        }

        Piece targetPiece = board.getPieceFromPosition(targetPos);

        return isWhite != targetPiece.isWhite;
    }

    public Position[] getLegalMoves(Board board) {
        List<Position> moves = new ArrayList<>();

        int row = position.row;
        int col = position.column;

        for (int r = row - 1; r >= 0; r--) {
            if (!addMoveToList(board, moves, r, col)) {
                break;
            }
        }

        for (int r = row + 1; r < 8; r++) {
            if (!addMoveToList(board, moves, r, col)) {
                break;
            }
        }

        for (int c = col - 1; c >= 0; c--) {
            if (!addMoveToList(board, moves, row, c)) {
                break;
            }
        }

        for (int c = col + 1; c < 8; c++) {
            if (!addMoveToList(board, moves, row, c)) {
                break;
            }
        }

        return moves.toArray(new Position[0]);
    }

    public boolean move(Board board, int index) {
        Log.e("test", "test");
        if (isMoveLegal(board, getPositionFromIndex(index))) {
            Log.e("I", "LEGAL");
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
            Log.e("Rook", "Illegal Move, " + Integer.toString(this.position.row) + "," + Integer.toString(this.position.column) + " > " + Integer.toString(pos.row) + "," + Integer.toString(pos.column));
            board.clean();
            return false;
        }
    }

    private boolean addMoveToList(Board board, List<Position> moves, int r, int c) {
        Position pos = new Position(r, c);
        Piece piece = board.getPieceFromPosition(pos);

        if (piece == null) {
            moves.add(pos);
            return true; // keep going
        } else {
            if (piece.isWhite != this.isWhite) {
                moves.add(pos); // can capture
            }
            return false; // stop here (blocked)
        }
    }
}
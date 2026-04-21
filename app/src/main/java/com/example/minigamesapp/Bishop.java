package com.example.minigamesapp;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece implements IPiece {
    public int pictureLocation = R.drawable.white_bishop;

    public Bishop (boolean isWhite, Position position, ImageView chessImage){
        this.chessImage = chessImage;
        this.isWhite = isWhite;
        this.position = position;
        if (!isWhite) {
            pictureLocation = R.drawable.black_bishop;
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

        if (Math.abs(currentRow - targetRow) != Math.abs(currentColumn - targetColumn)) {
            return false;
        }

        int rowStep = (targetRow > currentRow) ? 1 : -1;
        int colStep = (targetColumn > currentColumn) ? 1 : -1;

        int r = currentRow + rowStep;
        int c = currentColumn + colStep;

        while (r != targetRow && c != targetColumn) {
            if (board.getPieceFromPosition(new Position(r, c)) != null) {
                return false;
            }
            r += rowStep;
            c += colStep;
        }

        Piece targetPiece = board.getPieceFromPosition(targetPos);

        return targetPiece == null || targetPiece.isWhite != this.isWhite;
    }

    public Position[] getLegalMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int row = position.row;
        int col = position.column;

        // 1 row up and 1 column left
        for (int r = row - 1, c = col - 1; r >= 0 && c >= 0; r--, c--) {
            if (!addMoveToList(board, moves, r, c)) break;
        }

        // 1 row up and 1 column right
        for (int r = row - 1, c = col + 1; r >= 0 && c < 8; r--, c++) {
            if (!addMoveToList(board, moves, r, c)) break;
        }

        // 1 row down and 1 column left
        for (int r = row + 1, c = col - 1; r < 8 && c >= 0; r++, c--) {
            if (!addMoveToList(board, moves, r, c)) break;
        }

        // 1 row down and 1 column right
        for (int r = row + 1, c = col + 1; r < 8 && c < 8; r++, c++) {
            if (!addMoveToList(board, moves, r, c)) break;
        }

        return moves.toArray(new Position[0]);
    }

    public boolean move(Board board, int index) {
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
            Log.e("Bishop", "Illegal Move, " + Integer.toString(this.position.row) + "," + Integer.toString(this.position.column) + " > " + Integer.toString(pos.row) + "," + Integer.toString(pos.column));
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

    public boolean isAttackMove(Board board, Position targetPos) {
        return isMoveLegal(board, targetPos);
    }
}
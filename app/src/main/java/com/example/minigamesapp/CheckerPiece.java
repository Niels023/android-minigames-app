package com.example.minigamesapp;

import android.content.Context;
import android.widget.ImageView;
import java.util.ArrayList;

public class CheckerPiece {
    public CheckerPosition position;
    public boolean isBlack; // true = black piece, false = white piece
    public boolean isKing = false;
    public ImageView imageView;

    public CheckerPiece(CheckerPosition position, boolean isBlack) {
        this.position = position;
        this.isBlack = isBlack;
    }

    public void place(Checkerboard board, Context context) {
        int index = position.row * 8 + position.column;
        ImageView square = (ImageView) board.gridLayout.getChildAt(index);
        square.setImageResource(isBlack ? R.drawable.piece_black : R.drawable.piece_white);
        this.imageView = square;
    }

    public void remove(Checkerboard board) {
        int index = position.row * 8 + position.column;
        ImageView square = (ImageView) board.gridLayout.getChildAt(index);
        square.setImageDrawable(null);
        board.pieces.remove(this);
    }

    // Returns all valid moves (respects capture priority)
    public ArrayList<CheckerPosition> getValidMoves(Checkerboard board) {
        if (board.hasAnyCaptures(isBlack)) {
            return getCaptureMoves(board);
        }
        return getNormalMoves(board);
    }

    public ArrayList<CheckerPosition> getNormalMoves(Checkerboard board) {
        ArrayList<CheckerPosition> moves = new ArrayList<>();
        int[][] dirs = getMoveDirections();

        for (int[] dir : dirs) {
            CheckerPosition target = new CheckerPosition(position.row + dir[0], position.column + dir[1]);
            if (board.isEmpty(target)) {
                moves.add(target);
            }
        }
        return moves;
    }

    public ArrayList<CheckerPosition> getCaptureMoves(Checkerboard board) {
        ArrayList<CheckerPosition> captures = new ArrayList<>();
        int[][] dirs = getMoveDirections();

        for (int[] dir : dirs) {
            CheckerPosition middle = new CheckerPosition(position.row + dir[0], position.column + dir[1]);
            CheckerPosition landing = new CheckerPosition(position.row + dir[0] * 2, position.column + dir[1] * 2);

            if (!board.isInsideBoard(middle) || !board.isInsideBoard(landing)) continue;

            CheckerPiece middlePiece = board.getPieceFromPosition(middle);
            if (middlePiece != null && middlePiece.isBlack != this.isBlack && board.isEmpty(landing)) {
                captures.add(landing);
            }
        }
        return captures;
    }

    public boolean canCaptureTo(Checkerboard board, CheckerPosition target) {
        return getCaptureMoves(board).stream().anyMatch(p -> p.equals(target));
    }

    public CheckerPosition getCapturedPosition(CheckerPosition target) {
        int midRow = (position.row + target.row) / 2;
        int midCol = (position.column + target.column) / 2;
        return new CheckerPosition(midRow, midCol);
    }

    public void promoteToKing(Checkerboard board, Context context) {
        isKing = true;
        int index = position.row * 8 + position.column;
        ImageView square = (ImageView) board.gridLayout.getChildAt(index);
        square.setImageResource(isBlack ? R.drawable.piece_black_king : R.drawable.piece_white_king);
    }

    private int[][] getMoveDirections() {
        if (isKing) {
            return new int[][]{{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        }
        // Black moves down (increasing row), white moves up (decreasing row)
        return isBlack ? new int[][]{{1, -1}, {1, 1}} : new int[][]{{-1, -1}, {-1, 1}};
    }
}
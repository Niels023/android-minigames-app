package com.example.minigamesapp;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.ImageView;
import java.util.ArrayList;

public class Checkerboard {
    public GridLayout gridLayout;
    public boolean isBlackTurn = true;
    public ArrayList<CheckerPiece> pieces;

    public Checkerboard(GridLayout grid) {
        gridLayout = grid;
        pieces = new ArrayList<>();
    }

    public void setup(Context context) {
        Log.d("CheckersGame", "--// Setting up the board.");

        boolean colorIsDark = false;
        int squareSize = (int)(46.875 * context.getResources().getDisplayMetrics().density);

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ImageView square = new ImageView(context);
                square.setBackgroundColor(colorIsDark ? Color.rgb(139, 69, 19) : Color.rgb(240, 217, 181));
                square.setTag(R.id.color, colorIsDark);

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = squareSize;
                params.height = squareSize;
                params.rowSpec = GridLayout.spec(row);
                params.columnSpec = GridLayout.spec(col);
                square.setLayoutParams(params);
                square.setId(ImageView.generateViewId());
                gridLayout.addView(square);

                colorIsDark = !colorIsDark;
            }
            colorIsDark = !colorIsDark;
        }

        Log.d("CheckersGame", "--// Board has been setup!");
    }

    public void clean() {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            boolean colorIsDark = Boolean.parseBoolean(gridLayout.getChildAt(i).getTag(R.id.color).toString());
            gridLayout.getChildAt(i).setBackgroundColor(colorIsDark ? Color.rgb(139, 69, 19) : Color.rgb(240, 217, 181));
        }
    }

    public CheckerPiece getPieceFromPosition(CheckerPosition pos) {
        for (CheckerPiece piece : pieces) {
            if (piece.position.row == pos.row && piece.position.column == pos.column) {
                return piece;
            }
        }
        return null;
    }

    public boolean isSquareUnderAttack(CheckerPosition pos, boolean isBlack) {
        for (CheckerPiece piece : pieces) {
            if (piece.isBlack != isBlack) {
                if (piece.canCaptureTo(this, pos)) {
                    Log.e("SQUARE", "Square is under attack");
                    return true;
                } else {
                    Log.e("SQUARE", "Square is NOT under attack");
                }
            }
        }
        Log.e("SQUARE", "Square wasn't under attack.");
        return false;
    }

    public boolean hasAnyMoves(boolean isBlack) {
        for (CheckerPiece piece : pieces) {
            if (piece.isBlack == isBlack) {
                if (!piece.getValidMoves(this).isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasAnyCaptures(boolean isBlack) {
        for (CheckerPiece piece : pieces) {
            if (piece.isBlack == isBlack) {
                if (!piece.getCaptureMoves(this).isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isInsideBoard(CheckerPosition pos) {
        return pos.row >= 0 && pos.row < 8 && pos.column >= 0 && pos.column < 8;
    }

    public boolean isEmpty(CheckerPosition pos) {
        return isInsideBoard(pos) && getPieceFromPosition(pos) == null;
    }
}
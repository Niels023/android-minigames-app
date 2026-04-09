package com.example.minigamesapp;

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

    public boolean isMoveLegal(Board board, Position targetPost) {
        return false;
    }

    public Position[] getLegalMoves(Board board) {
        List<Position> list = new ArrayList<>();
        for (int index = 0; index < board.gridLayout.getChildCount(); index++) {
            if (isMoveLegal(board, getPositionFromIndex(index))) {
                list.add(getPositionFromIndex(index));
            }
        }
        return list.toArray(new Position[0]);
    }

    public boolean move(Board board, int index) {
        return false;
    }
}

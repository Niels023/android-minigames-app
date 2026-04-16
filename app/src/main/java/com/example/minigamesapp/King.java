package com.example.minigamesapp;

import android.util.Log;
import android.widget.ImageView;

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
        return super.isMoveLegal(targetPos);
    }

    public Position[] getLegalMoves(Board board) {
        return super.getLegalMoves(board);
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
}

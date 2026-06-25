package com.example.minigamesapp;

import android.content.Context;

public interface IPiece {
    boolean isMoveLegal(Board board, Position targetPosition);
    boolean move(Context context, Board board, int index);
    Position[] getLegalMoves(Board board);

    boolean isAttackMove(Board board, Position targetPos, Boolean turn);
}

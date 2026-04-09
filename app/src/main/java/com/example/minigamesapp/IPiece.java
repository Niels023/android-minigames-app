package com.example.minigamesapp;

public interface IPiece {
    boolean isMoveLegal(Position targetPosition);
    boolean move(Board board, int index);
    Position[] getLegalMoves(Board board);

}

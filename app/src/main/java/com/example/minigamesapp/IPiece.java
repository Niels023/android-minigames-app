package com.example.minigamesapp;

public interface IPiece {
    boolean isMoveLegal(Board board, Position targetPosition);
    boolean move(Board board, int index);
    Position[] getLegalMoves(Board board);

}

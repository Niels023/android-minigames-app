package com.example.minigamesapp;

public interface IPiece {
    boolean isMoveLegal(Position targetPosition);
    void move(Position targetPosition);

}

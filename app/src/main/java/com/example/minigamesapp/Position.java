package com.example.minigamesapp;

public class Position {
    public int row;
    public int column;

    public Position (int row, int column) {
        this.row = row;
        this.column = column;
    }

    public boolean checkEquals(Position pos) {
        if (row == pos.row && this.column == pos.column) {
            return true;
        } else {
            return false;
        }
    }
}

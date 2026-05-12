package com.example.minigamesapp;

class CheckerPosition {
    public int row;
    public int column;

    public CheckerPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public boolean equals(CheckerPosition other) {
        return this.row == other.row && this.column == other.column;
    }
}
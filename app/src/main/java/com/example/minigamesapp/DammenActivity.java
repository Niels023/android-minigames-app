package com.example.minigamesapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class DammenActivity extends AppCompatActivity {

    private Checkerboard board;
    private CheckerPiece selectedPiece = null;
    private ArrayList<CheckerPosition> highlightedMoves = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dammen);

        GridLayout gridLayout = findViewById(R.id.Checkersbord);
        board = new Checkerboard(gridLayout);
        board.setup(this);
        setupPieces();
        setupClickListeners();
    }

    private void setupPieces() {
        board.pieces = new ArrayList<>();

        // White pieces on rows 0-2, dark squares only
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 == 1) {
                    CheckerPiece piece = new CheckerPiece(new CheckerPosition(row, col), false); // false = white
                    board.pieces.add(piece);
                    piece.place(board, this);
                }
            }
        }

        // Black pieces on rows 5-7, dark squares only
        for (int row = 5; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 == 1) {
                    CheckerPiece piece = new CheckerPiece(new CheckerPosition(row, col), true); // true = black
                    board.pieces.add(piece);
                    piece.place(board, this);
                }
            }
        }

        Log.d("CheckersGame", "--// Pieces placed: " + board.pieces.size());
    }

    private void setupClickListeners() {
        for (int i = 0; i < board.gridLayout.getChildCount(); i++) {
            ImageView square = (ImageView) board.gridLayout.getChildAt(i);
            final int index = i;
            square.setOnClickListener(v -> {
                int row = index / 8;
                int col = index % 8;
                handleSquareClick(row, col);
            });
        }
    }

    private void handleSquareClick(int row, int col) {
        CheckerPosition clicked = new CheckerPosition(row, col);
        CheckerPiece clickedPiece = board.getPieceFromPosition(clicked);

        // Clicked own piece — select it
        if (clickedPiece != null && clickedPiece.isBlack == board.isBlackTurn) {
            selectPiece(clickedPiece);
            return;
        }

        // Clicked a highlighted move — execute it
        if (selectedPiece != null && isHighlighted(clicked)) {
            movePiece(selectedPiece, clicked);
            return;
        }

        // Clicked elsewhere — deselect
        clearSelection();
    }

    private void selectPiece(CheckerPiece piece) {
        clearSelection();
        ArrayList<CheckerPosition> moves = piece.getValidMoves(board);
        if (moves.isEmpty()) {
            Toast.makeText(this, "Dit stuk kan niet bewegen.", Toast.LENGTH_SHORT).show();
            return;
        }
        selectedPiece = piece;
        highlightedMoves = moves;
        highlightMoves(moves);
        Log.d("CheckersGame", "--// Selected piece at " + piece.position.row + "," + piece.position.column);
    }

    private void movePiece(CheckerPiece piece, CheckerPosition target) {
        boolean isCapture = Math.abs(piece.position.row - target.row) == 2;

        // Remove captured piece
        if (isCapture) {
            CheckerPosition captured = piece.getCapturedPosition(target);
            CheckerPiece capturedPiece = board.getPieceFromPosition(captured);
            if (capturedPiece != null) {
                capturedPiece.remove(board);
                Log.d("CheckersGame", "--// Captured piece at " + captured.row + "," + captured.column);
            }
        }

        // Move the piece visually
        int oldIndex = piece.position.row * 8 + piece.position.column;
        ImageView oldSquare = (ImageView) board.gridLayout.getChildAt(oldIndex);
        oldSquare.setImageDrawable(null);

        piece.position = target;

        int newIndex = target.row * 8 + target.column;
        ImageView newSquare = (ImageView) board.gridLayout.getChildAt(newIndex);
        newSquare.setImageResource(piece.isBlack ? R.drawable.piece_black : R.drawable.piece_white);

        // Check for promotion to king
        if ((piece.isBlack && target.row == 7) || (!piece.isBlack && target.row == 0)) {
            piece.promoteToKing(board, this);
            Log.d("CheckersGame", "--// Piece promoted to king!");
        }

        clearSelection();

        // Multi-capture: if capture and can capture again, same player continues
        if (isCapture && !piece.getCaptureMoves(board).isEmpty()) {
            Log.d("CheckersGame", "--// Multi-capture available, same player continues.");
            selectPiece(piece);
            return;
        }

        // Switch turn
        board.isBlackTurn = !board.isBlackTurn;
        Log.d("CheckersGame", "--// Turn: " + (board.isBlackTurn ? "Zwart" : "Wit"));

        // Check for win condition
        checkWinCondition();
    }

    private void checkWinCondition() {
        long blackCount = board.pieces.stream().filter(p -> p.isBlack).count();
        long whiteCount = board.pieces.stream().filter(p -> !p.isBlack).count();

        if (blackCount == 0) {
            Toast.makeText(this, "Wit wint! 🎉", Toast.LENGTH_LONG).show();
        } else if (whiteCount == 0) {
            Toast.makeText(this, "Zwart wint! 🎉", Toast.LENGTH_LONG).show();
        } else if (!board.hasAnyMoves(board.isBlackTurn)) {
            Toast.makeText(this, (board.isBlackTurn ? "Zwart" : "Wit") + " heeft geen zetten meer. Tegenstander wint!", Toast.LENGTH_LONG).show();
        }
    }

    private void highlightMoves(ArrayList<CheckerPosition> moves) {
        for (CheckerPosition pos : moves) {
            int index = pos.row * 8 + pos.column;
            board.gridLayout.getChildAt(index).setBackgroundColor(0xFF5A9B5A);
        }
    }

    private void clearSelection() {
        board.clean();
        selectedPiece = null;
        highlightedMoves.clear();
    }

    private boolean isHighlighted(CheckerPosition pos) {
        for (CheckerPosition m : highlightedMoves) {
            if (m.equals(pos)) return true;
        }
        return false;
    }
}
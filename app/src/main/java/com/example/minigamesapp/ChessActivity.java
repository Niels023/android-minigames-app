package com.example.minigamesapp;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class ChessActivity extends AppCompatActivity {
    public Piece currentSelectedPiece;
    public Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chess);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        board = new Board(findViewById(R.id.board));
        board.setup(this);

        Log.d("ChessGame", "--// Setting up the pieces.");

        // Rooks
        Log.d("ChessGame", "--// Placing Rooks");
        for (int row = 0; row < 8; row++){
            for (int col = 0; col < 8; col++) {
                if (row == 0 && (col == 0 || col == 7)) {
                    ImageView square = (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(row,col)));
                    square.setImageResource(R.drawable.black_rook);
                } else if (row == 7 && (col == 0 || col == 7)) {
                    ImageView square = (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(row,col)));
                    square.setImageResource(R.drawable.white_rook);
                }
            }
        }
        Log.d("ChessGame", "--// Placed Rooks");

//        // Knights
//        Log.d("ChessGame", "--// Placing Knights");
//        for (int row = 0; row < 8; row++){
//            for (int col = 0; col < 8; col++) {
//                if (row == 0 && (col == 1 || col == 6)) {
//                    ImageView square = (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(row,col)));
//                    square.setImageResource(R.drawable.black_knight);
//                } else if (row == 7 && (col == 1 || col == 6)) {
//                    ImageView square = (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(row,col)));
//                    square.setImageResource(R.drawable.white_knight);
//                }
//            }
//        }
//        Log.d("ChessGame", "--// Placed Knights");

        // Bishops
        Log.d("ChessGame", "--// Placing Bishops");
        for (int row = 0; row < 8; row++){
            for (int col = 0; col < 8; col++) {
                if (row == 0 && (col == 2 || col == 5)) {
                    ImageView square = (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(row,col)));
                    square.setImageResource(R.drawable.black_bishop);
                } else if (row == 7 && (col == 2 || col == 5)) {
                    ImageView square = (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(row,col)));
                    square.setImageResource(R.drawable.white_bishop);
                }
            }
        }
        Log.d("ChessGame", "--// Placed Bishops");

//        // Queens
//        Log.d("ChessGame", "--// Placing Queens");
//        for (int row = 0; row < 8; row++){
//            for (int col = 0; col < 8; col++) {
//                if (row == 0 && col == 3) {
//                    ImageView square = (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(row,col)));
//                    square.setImageResource(R.drawable.black_queen);
//                } else if (row == 7 && col == 3) {
//                    ImageView square = (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(row,col)));
//                    square.setImageResource(R.drawable.white_queen);
//                }
//            }
//        }
//        Log.d("ChessGame", "--// Placed Queens");
//
//        // Kings
//        Log.d("ChessGame", "--// Placing Kings");
//        for (int row = 0; row < 8; row++){
//            for (int col = 0; col < 8; col++) {
//                if (row == 0 && col == 4) {
//                    ImageView square = (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(row,col)));
//                    square.setImageResource(R.drawable.black_king);
//                } else if (row == 7 && col == 4) {
//                    ImageView square = (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(row,col)));
//                    square.setImageResource(R.drawable.white_king);
//                }
//            }
//        }
//        Log.d("ChessGame", "--// Placed Kings");

        // Black Pawns
        Log.d("ChessGame", "--// Placing Black Pawns");
        for (int col = 0; col < 8; col++) {
            int index = getIndexFromPosition(new Position(1,col));
            ImageView square = (ImageView) board.gridLayout.getChildAt(index);
            square.setImageResource(R.drawable.black_pawn);
        }
        Log.d("ChessGame", "--// Placed Black Pawns");

        // White Pawns
        Log.d("ChessGame", "--// Placing White Pawns");
        for (int col = 0; col < 8; col++) {
            int index = getIndexFromPosition(new Position(6,col));
            ImageView square = (ImageView) board.gridLayout.getChildAt(index);
            square.setImageResource(R.drawable.white_pawn);
        }
        Log.d("ChessGame", "--// Placed White Pawns");

        board.pieces = new ArrayList<>(Arrays.asList(
                // Black Bishops
                new Bishop(false, new Position(0, 2), (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(0,2)))),
                new Bishop(false, new Position(0, 5), (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(0,5)))),

                // White Bishops
                new Bishop(true, new Position(7, 2), (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(7,2)))),
                new Bishop(true, new Position(7, 5), (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(7,5)))),

                // Black Rooks
                new Rook(false, new Position(0,0), (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(0,0)))),
                new Rook(false, new Position(0,7), (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(0,7)))),

                // White Rooks
                new Rook(true, new Position(7,0), (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(7,0)))),
                new Rook(true, new Position(7,7), (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(7,7)))),

                // Extras
//                new Pawn(true, new Position(5,2), (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(5,2)))),
//                new Rook(true, new Position(5,5), (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(5,5)))),
//                new Bishop(true, new Position(5,6), (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(5,6)))),

                // Black Pawns
                new Pawn(false,new Position(1,0), (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(1,0)))),
                new Pawn(false,new Position(1,1), (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(1,1)))),
                new Pawn(false,new Position(1,2), (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(1,2)))),
                new Pawn(false,new Position(1,3), (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(1,3)))),
                new Pawn(false,new Position(1,4), (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(1,4)))),
                new Pawn(false,new Position(1,5), (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(1,5)))),
                new Pawn(false,new Position(1,6), (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(1,6)))),
                new Pawn(false,new Position(1,7), (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(1,7)))),
                // White Pawns
                new Pawn(true,new Position(6,0), (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(6,0)))),
                new Pawn(true,new Position(6,1), (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(6,1)))),
                new Pawn(true,new Position(6,2), (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(6,2)))),
                new Pawn(true,new Position(6,3), (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(6,3)))),
                new Pawn(true,new Position(6,4), (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(6,4)))),
                new Pawn(true,new Position(6,5), (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(6,5)))),
                new Pawn(true,new Position(6,6), (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(6,6)))),
                new Pawn(true,new Position(6,7), (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(6,7))))
        ));

        Log.d("ChessGame", "--// Adding OnClickListeners");
        for (int i = 0; i < board.gridLayout.getChildCount(); i++){
            ImageView child = (ImageView) board.gridLayout.getChildAt(i);
            child.setOnClickListener(v -> selectPiece(child));
        }
        Log.d("ChessGame", "--// OnClickListeners have been connected!");
    }
    private void selectPiece(ImageView square) {
        if (currentSelectedPiece == null) {
            int i = board.gridLayout.indexOfChild(square);
            Drawable drawable = square.getDrawable();
            boolean hasImage = (drawable != null);
            if (hasImage) {
                Log.d("Piece", "Selected Piece");
                Piece piece = board.getPieceFromPosition(getPositionFromIndex(i));
                if (currentSelectedPiece != null) {
                    currentSelectedPiece.move(board, getIndexFromPosition(piece.position));
                } else {
                    if (piece == null) {
                        return;
                    }
                    Position[] listOfPositions = piece.getLegalMoves(board);
                    if (listOfPositions.length != 0) {
                        square.setBackgroundColor(Color.BLUE);
                        currentSelectedPiece = piece;
                        for (int x = 0; x < listOfPositions.length; x++) {
                            ImageView legalSquare = (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(listOfPositions[x]));
                            Drawable legalDrawable = legalSquare.getDrawable();
                            boolean legalHasImage = (legalDrawable != null);
                            if (legalHasImage) {
                                legalSquare.setBackgroundColor(Color.RED);
                            } else {
                                legalSquare.setBackgroundColor(Color.GREEN);
                            }
                        }
                    } else {
                        Log.e("no", "no positions returned");
                    }
                }
            }
        }
        else {
            for(int i = 0; i < board.gridLayout.getChildCount(); i++) {
                if (board.gridLayout.getChildAt(i).getId() == square.getId()) {
                    Log.d("Square", "Selected Square");
                    boolean status = currentSelectedPiece.move(board, i);
                    if (status) {
                        Log.d("Piece", "Moved Piece");
                    }
                    currentSelectedPiece = null;
                    Log.d("Piece", "Unselected Piece");
                }
            }
        }
    }
    private int getIndexFromPosition(Position position) {
        return position.row * 8 + position.column;
    }
    private Position getPositionFromIndex(int index) {
        int moduloRow = 0;
        while (index >= 8) {
            index -= 8;
            moduloRow++;
        }
        int moduloCol = index; // left-overs
        return new Position(moduloRow, moduloCol);
    }
    private String columnToLetter(int columnNumber) {
        String columnLetter = "A";
        switch (columnNumber) {
            case 1:
                columnLetter = "A";
                break;
            case 2:
                columnLetter = "B";
                break;
            case 3:
                columnLetter = "C";
                break;
            case 4:
                columnLetter = "D";
                break;
            case 5:
                columnLetter = "E";
                break;
            case 6:
                columnLetter = "F";
                break;
            case 7:
                columnLetter = "G";
                break;
            case 8:
                columnLetter = "H";
                break;
            default:
                throw new IllegalArgumentException("Invalid column number: " + columnNumber);
        }
        return columnLetter;
    }
}
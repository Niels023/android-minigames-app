package com.example.minigamesapp;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
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

        // Rooks
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
        // Knights
        for (int row = 0; row < 8; row++){
            for (int col = 0; col < 8; col++) {
                if (row == 0 && (col == 1 || col == 6)) {
                    ImageView square = (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(row,col)));
                    square.setImageResource(R.drawable.black_knight);
                } else if (row == 7 && (col == 1 || col == 6)) {
                    ImageView square = (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(row,col)));
                    square.setImageResource(R.drawable.white_knight);
                }
            }
        }
        // Bishops
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
        // Queens
        for (int row = 0; row < 8; row++){
            for (int col = 0; col < 8; col++) {
                if (row == 0 && col == 3) {
                    ImageView square = (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(row,col)));
                    square.setImageResource(R.drawable.black_queen);
                } else if (row == 7 && col == 3) {
                    ImageView square = (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(row,col)));
                    square.setImageResource(R.drawable.white_queen);
                }
            }
        }
        // Kings
        for (int row = 0; row < 8; row++){
            for (int col = 0; col < 8; col++) {
                if (row == 0 && col == 4) {
                    ImageView square = (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(row,col)));
                    square.setImageResource(R.drawable.black_king);
                } else if (row == 7 && col == 4) {
                    ImageView square = (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(row,col)));
                    square.setImageResource(R.drawable.white_king);
                }
            }
        }
        // Black Pawns
        for (int col = 0; col < 8; col++) {
            Log.e("e", Integer.toString(col));
            int index = getIndexFromPosition(new Position(1,col));
            ImageView square = (ImageView) board.gridLayout.getChildAt(index);
            square.setImageResource(R.drawable.black_pawn);
        }
        // White Pawns
        for (int col = 0; col < 8; col++) {
            Log.e("e", Integer.toString(col));
            int index = getIndexFromPosition(new Position(6,col));
            ImageView square = (ImageView) board.gridLayout.getChildAt(index);
            square.setImageResource(R.drawable.white_pawn);
        }

        board.pieces = new Piece[]{
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
                new Pawn(true,new Position(6,7), (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(6,7)))),
                new Pawn(false,new Position(5,5), (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(new Position(5,5)))),

        };

        for (int i = 0; i < board.gridLayout.getChildCount(); i++){
            ImageView child = (ImageView) board.gridLayout.getChildAt(i);
            child.setOnClickListener(v -> selectPiece(child));
        }
    }
    private void selectPiece(ImageView square) {
        for(int i = 0; i < board.gridLayout.getChildCount(); i++) {
            if (board.gridLayout.getChildAt(i).getId() == square.getId()) {
                Drawable drawable = square.getDrawable();
                boolean hasImage = (drawable != null);
                if (hasImage) {
                    square.setBackgroundColor(Color.BLUE);
                    Piece piece = board.getPieceFromPosition(getPositionFromIndex(i));
                    Position[] listOfPositions = piece.getLegalMoves(board, piece);
                    for (int x = 0; x < listOfPositions.length; x++) {
                        Log.e("PositionInList", listOfPositions[x].row + "," + listOfPositions[x].column);
                        ImageView legalSquare = (ImageView) board.gridLayout.getChildAt(getIndexFromPosition(listOfPositions[x]));

                        Drawable legalDrawable = legalSquare.getDrawable();
                        boolean legalHasImage = (legalDrawable != null);
                        if (legalHasImage) {
                            legalSquare.setBackgroundColor(Color.RED);
                        } else {
                            legalSquare.setBackgroundColor(Color.GREEN);
                        }
                    }
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

    //    private void selectPiece(ImageView selectedPiece) {
    //        if (currentSelectedPiece != selectedPiece) {
    //            int pieceRow = Integer.parseInt(selectedPiece.getTag(R.id.tag_coords_row).toString());
    //            int pieceColumn = Integer.parseInt(selectedPiece.getTag(R.id.tag_coords_column).toString());
    //                currentSelectedPiece = selectedPiece;
    //                selectedPiece.setBackgroundColor(Color.BLUE);
    //                showLegalMoves((selectedPiece));
    //        } else if (selectedPiece != null) {
    //            int pieceRow = Integer.parseInt(selectedPiece.getTag(R.id.tag_coords_row).toString());
    //            int pieceColumn = Integer.parseInt(selectedPiece.getTag(R.id.tag_coords_column).toString());
    //            if (!board[pieceRow][pieceColumn].isEmpty()) {
    //                currentSelectedPiece = selectedPiece;
    //                selectedPiece.setBackgroundColor(Color.BLUE);
    //                showLegalMoves((selectedPiece));
    //            }
    //        }
    //    }

    //    private boolean isMoveLegal(ImageView selectedPiece, View selectedSquare){
    //
    //    };

    //    private void showLegalMoves(ImageView selectedPiece) {
    //        int pieceRow = Integer.parseInt(selectedPiece.getTag(R.id.tag_coords_row).toString());
    //        int pieceColumn = Integer.parseInt(selectedPiece.getTag(R.id.tag_coords_column).toString());
    //
    //        String pieceType = selectedPiece.getTag(R.id.tag_piece).toString();
    //        Log.e("Piece", pieceType + " Is at: " + pieceRow + "," + pieceColumn);
    //        switch (pieceType){
    //            case "white_pawn":
    //                if (board[pieceRow - 1][pieceColumn].isEmpty()) {
    //                    View space = getViewFromCoords(pieceRow - 1, pieceColumn);
    //                    space.setBackgroundColor(Color.GREEN);
    //                }
    //                if (pieceRow == 6 && board[pieceRow - 2][pieceColumn].isEmpty()) {
    //                    View space = getViewFromCoords(pieceRow - 2, pieceColumn);
    //                    space.setBackgroundColor(Color.GREEN);
    //                }
    //                if (board[pieceRow - 1][pieceColumn - 1].contains("black_")) {
    //                    View space = getViewFromCoords(pieceRow - 1, pieceColumn - 1);
    //                    space.setBackgroundColor(Color.RED);
    //                }
    //                if (board[pieceRow - 1][pieceColumn + 1].contains("black_")) {
    //                    View space = getViewFromCoords(pieceRow - 1, pieceColumn + 1);
    //                    space.setBackgroundColor(Color.RED);
    //                }
    //        }
    //
    //    }

//    private View getViewFromCoords(int row, int column) {
//        GridLayout gridLayout = findViewById(R.id.grid);
//        for (int i = 0; i < gridLayout.getChildCount(); i++){
//            View child = gridLayout.getChildAt(i);
//            int childRow = Integer.parseInt(child.getTag(R.id.tag_coords_row).toString());
//            int childColumn = Integer.parseInt(child.getTag(R.id.tag_coords_column).toString());
//            if (childRow == row && childColumn == column) {
//                return child;
//            }
//        }
//        return null;
//    }
//    private void markSpecificChessSquare(String targetNotation){
//
//        GridLayout gridLayout = findViewById(R.id.grid);
//
//        View targetSquare = null;
//        for (int i = 0; i < gridLayout.getChildCount(); i++) {
//            View child = gridLayout.getChildAt(i);
//            if (targetNotation.equals(child.getTag())) {
//                targetSquare = child;
//                break;
//            }
//        }
//
//        if (targetSquare != null) {
//            targetSquare.setBackgroundColor(Color.RED);
//        }
//    }
}
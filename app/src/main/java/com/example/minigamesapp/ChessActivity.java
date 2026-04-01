package com.example.minigamesapp;

import android.graphics.Color;
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
        ConstraintLayout main = findViewById(R.id.main);

        GridLayout gridBoard = findViewById(R.id.board);
        Board chessBoard = new Board();
        chessBoard.boardGrid = gridBoard;

        boolean colorIsBlack = false;
        int squareSize = (int)(50 * getResources().getDisplayMetrics().density); // turning 50dp into pixels cuz the layout params only accept pixels!!
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ImageView square = new ImageView(this);

                square.setBackgroundColor(colorIsBlack ? Color.rgb(182, 137, 99) : Color.rgb(240, 218, 182));

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = squareSize;
                params.height = squareSize;
                params.rowSpec = GridLayout.spec(row);
                params.columnSpec = GridLayout.spec(col);
                square.setLayoutParams(params);

                square.setId(ImageView.generateViewId());

                gridBoard.addView(square);

                colorIsBlack = !colorIsBlack;
            }
            colorIsBlack = !colorIsBlack;
        }

        // Extra Method
//        for (int row = 0; row < 8; row++){
//            for (int col = 0; col < 8; col++) {
//                if (row == 0 && (col == 0 || col == 7)) {
//                    ImageView square = (ImageView) gridBoard.getChildAt(row * 8 + col);
//                    square.setImageResource(R.drawable.black_rook);
//                } else if (row == 7 && (col == 0 || col == 7)) {
//                    ImageView square = (ImageView) gridBoard.getChildAt(row * 8 + col);
//                    square.setImageResource(R.drawable.white_rook);
//                }
//            }
//        }

        ImageView blackrook1 = (ImageView) gridBoard.getChildAt(0);
        blackrook1.setImageResource(R.drawable.black_rook);

        ImageView blackrook2 = (ImageView) gridBoard.getChildAt(7);
        blackrook2.setImageResource(R.drawable.black_rook);

        ImageView blackknight1 = (ImageView) gridBoard.getChildAt(1);
        blackknight1.setImageResource(R.drawable.black_knight);

        ImageView blackknight2 = (ImageView) gridBoard.getChildAt(6);
        blackknight2.setImageResource(R.drawable.black_knight);

        ImageView blackbishop1 = (ImageView) gridBoard.getChildAt(2);
        blackbishop1.setImageResource(R.drawable.black_bishop);

        ImageView blackbishop2 = (ImageView) gridBoard.getChildAt(5);
        blackbishop2.setImageResource(R.drawable.black_bishop);

        ImageView whiterook1 = (ImageView) gridBoard.getChildAt(56);
        whiterook1.setImageResource(R.drawable.white_rook);

        ImageView whiterook2 = (ImageView) gridBoard.getChildAt(63);
        whiterook2.setImageResource(R.drawable.white_rook);

        ImageView whiteknight1 = (ImageView) gridBoard.getChildAt(57);
        whiteknight1.setImageResource(R.drawable.white_knight);

        ImageView whiteknight2 = (ImageView) gridBoard.getChildAt(62);
        whiteknight2.setImageResource(R.drawable.white_knight);

        ImageView whitebishop1 = (ImageView) gridBoard.getChildAt(58);
        whitebishop1.setImageResource(R.drawable.white_bishop);

        ImageView whitebishop2 = (ImageView) gridBoard.getChildAt(61);
        whitebishop2.setImageResource(R.drawable.white_bishop);

        ImageView whitequeen = (ImageView) gridBoard.getChildAt(59);
        whitequeen.setImageResource(R.drawable.white_queen);

        ImageView whiteking = (ImageView) gridBoard.getChildAt(60);
        whiteking.setImageResource(R.drawable.white_king);

        ImageView blackqueen = (ImageView) gridBoard.getChildAt(3);
        blackqueen.setImageResource(R.drawable.black_queen);

        ImageView blackking = (ImageView) gridBoard.getChildAt(4);
        blackking.setImageResource(R.drawable.black_king);

        for (int col = 0; col < 8; col++) {
            Log.e("e", Integer.toString(col));
            int index = getIndexFromPosition(new Position(1,col));
            ImageView square = (ImageView) gridBoard.getChildAt(index);
            square.setImageResource(R.drawable.black_pawn);
        }

        for (int col = 0; col < 8; col++) {
            Log.e("e", Integer.toString(col));
            int index = getIndexFromPosition(new Position(6,col));
            ImageView square = (ImageView) gridBoard.getChildAt(index);
            square.setImageResource(R.drawable.white_pawn);
        }

//        ImageView imageView = new ImageView(this);
//        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
//        params.width = size;
//        params.height = size;
//        imageView.setLayoutParams(params);
//        imageView.setBackgroundColor(Color.RED);
//        Pawn pawn = new Pawn(false, new Position(7, 5), imageView);
//        chessBoard.addPiece(pawn);
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

    private static String ColumnToLetter(int columnNumber) {
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
package com.example.minigamesapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SudokuActivity extends AppCompatActivity {

    // UI Elements
    private GridLayout sudokuGrid;
    private TextView selectedCell = null;
    private TextView livesText;

    // Game State
    private int lives = 3;
    private int[][] solutionGrid = new int[9][9];
    private boolean isGameOver = false;
    private int cellsRemaining = 0;

    // Menu Overlay Elements
    private LinearLayout menuOverlay;
    private TextView menuTitle;
    private Button btnMenuPlay;
    private Button btnMenuBack;
    private Button btnGameMenu;
    private Button btnMenuViewBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);

        // Initialize game UI
        sudokuGrid = findViewById(R.id.sudoku_grid);
        livesText = findViewById(R.id.tv_lives);

        // Initialize menu UI
        menuOverlay = findViewById(R.id.menu_overlay);
        menuTitle = findViewById(R.id.tv_menu_title);
        btnMenuPlay = findViewById(R.id.btn_menu_play);
        btnMenuBack = findViewById(R.id.btn_menu_back);
        btnGameMenu = findViewById(R.id.btn_game_menu);
        btnMenuViewBoard = findViewById(R.id.btn_menu_view_board);

        setupNumberPad();
        setupMenu();
    }

    // Configures the overlay menu and in-game menu buttons
    private void setupMenu() {
        btnMenuPlay.setOnClickListener(v -> {
            menuOverlay.setVisibility(View.GONE);
            lives = 3;
            isGameOver = false;
            livesText.setText("❤️❤️❤️");
            sudokuGrid.removeAllViews();
            fetchSudokuPuzzle();
        });

        btnMenuBack.setOnClickListener(v -> finish());

        btnMenuViewBoard.setOnClickListener(v -> {
            menuOverlay.setVisibility(View.GONE);
        });

        btnGameMenu.setOnClickListener(v -> {
            menuOverlay.setVisibility(View.VISIBLE);

            // Adjust menu styling if the game is merely paused
            if (!isGameOver) {
                menuTitle.setText("Paused");
                menuTitle.setTextColor(Color.BLACK);
                btnMenuPlay.setText("Resume Game");
                btnMenuViewBoard.setVisibility(View.GONE);
            }
        });
    }

    // Fetches puzzle data from Dosuku API on a background thread
    private void fetchSudokuPuzzle() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                URL url = new URL("https://sudoku-api.vercel.app/api/dosuku");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                JSONObject jsonObject = new JSONObject(result.toString());
                JSONArray grids = jsonObject.getJSONObject("newboard").getJSONArray("grids");

                JSONArray puzzleValues = grids.getJSONObject(0).getJSONArray("value");
                JSONArray solutionValues = grids.getJSONObject(0).getJSONArray("solution");

                // Switch back to main thread to update UI
                runOnUiThread(() -> buildGrid(puzzleValues, solutionValues));

            } catch (Exception e) {
                Log.e("SudokuAPI", "Error fetching puzzle.", e);
            }
        });
    }

    // Dynamically generates the 9x9 grid and applies 3x3 styling
    private void buildGrid(JSONArray puzzleValues, JSONArray solutionValues) {
        sudokuGrid.removeAllViews();
        cellsRemaining = 0;

        float density = getResources().getDisplayMetrics().density;
        int cellSize = (int) (38 * density);
        int thinLine = (int) (1 * density);
        int thickLine = (int) (4 * density);

        try {
            for (int row = 0; row < 9; row++) {
                JSONArray rowArray = puzzleValues.getJSONArray(row);
                JSONArray solutionRowArray = solutionValues.getJSONArray(row);

                for (int col = 0; col < 9; col++) {
                    int cellValue = rowArray.getInt(col);
                    solutionGrid[row][col] = solutionRowArray.getInt(col);

                    TextView cell = new TextView(this);
                    cell.setTag(row + "," + col); // Store coordinates for validation

                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                    params.width = cellSize;
                    params.height = cellSize;
                    params.rowSpec = GridLayout.spec(row);
                    params.columnSpec = GridLayout.spec(col);

                    // Create thicker borders for the standard 3x3 Sudoku subgrids
                    int rightMargin = (col == 2 || col == 5) ? thickLine : thinLine;
                    int bottomMargin = (row == 2 || row == 5) ? thickLine : thinLine;
                    params.setMargins(thinLine, thinLine, rightMargin, bottomMargin);

                    cell.setLayoutParams(params);
                    cell.setGravity(Gravity.CENTER);
                    cell.setBackgroundColor(Color.WHITE);
                    cell.setTextSize(20f);

                    if (cellValue != 0) {
                        cell.setText(String.valueOf(cellValue));
                        cell.setTextColor(Color.BLACK);
                    } else {
                        cellsRemaining++;
                        cell.setTextColor(Color.BLUE);

                        // Handle cell selection
                        cell.setOnClickListener(v -> {
                            if (isGameOver) return;

                            if (selectedCell != null) {
                                selectedCell.setBackgroundColor(Color.WHITE);
                            }
                            selectedCell = (TextView) v;
                            selectedCell.setBackgroundColor(Color.parseColor("#BBDEFB"));
                        });
                    }
                    sudokuGrid.addView(cell);
                }
            }
        } catch (Exception e) {
            Log.e("SudokuAPI", "Error building grid layout", e);
        }
    }

    // Configures the custom input pad and handles win/loss validation
    private void setupNumberPad() {
        int[] buttonIds = {R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5,
                R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9};

        for (int i = 0; i < buttonIds.length; i++) {
            int number = i + 1;
            Button btn = findViewById(buttonIds[i]);

            btn.setOnClickListener(v -> {
                if (selectedCell != null && !isGameOver) {

                    String[] coordinates = selectedCell.getTag().toString().split(",");
                    int r = Integer.parseInt(coordinates[0]);
                    int c = Integer.parseInt(coordinates[1]);

                    if (number == solutionGrid[r][c]) {
                        // Correct input
                        selectedCell.setText(String.valueOf(number));
                        selectedCell.setTextColor(Color.BLUE);
                        selectedCell.setBackgroundColor(Color.WHITE);
                        selectedCell.setOnClickListener(null); // Lock cell
                        selectedCell = null;

                        cellsRemaining--;
                        if (cellsRemaining == 0) {
                            triggerWin();
                        }
                    } else {
                        // Incorrect input
                        lives--;
                        triggerVibration();
                        updateLivesUI();
                    }
                }
            });
        }

        Button btnErase = findViewById(R.id.btn_erase);
        btnErase.setOnClickListener(v -> {
            if (selectedCell != null && !isGameOver) {
                selectedCell.setText("");
            }
        });
    }

    private void triggerWin() {
        isGameOver = true;
        menuTitle.setText("You Win!");
        menuTitle.setTextColor(Color.parseColor("#4CAF50"));
        btnMenuPlay.setText("Play Again");
        btnMenuViewBoard.setVisibility(View.VISIBLE);
        menuOverlay.setVisibility(View.VISIBLE);
    }

    private void triggerVibration() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            vibrator.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
        }
    }

    private void updateLivesUI() {
        if (lives == 2) {
            livesText.setText("❤️❤️🖤");
        } else if (lives == 1) {
            livesText.setText("❤️🖤🖤");
        } else if (lives <= 0) {
            livesText.setText("🖤🖤🖤");
            isGameOver = true;

            if (selectedCell != null) {
                selectedCell.setBackgroundColor(Color.WHITE);
            }

            menuTitle.setText("Game Over!");
            menuTitle.setTextColor(Color.RED);
            btnMenuPlay.setText("Try Again");
            btnMenuViewBoard.setVisibility(View.VISIBLE);
            menuOverlay.setVisibility(View.VISIBLE);
        }
    }
}
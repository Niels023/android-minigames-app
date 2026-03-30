package com.example.minigamesapp;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
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

    private GridLayout sudokuGrid;

    // This variable remembers which cell the player has currently clicked
    private TextView selectedCell = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);

        sudokuGrid = findViewById(R.id.sudoku_grid);

        fetchSudokuPuzzle();
        setupNumberPad(); // Turn on the bottom buttons
    }

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

                runOnUiThread(() -> buildGrid(puzzleValues));

            } catch (Exception e) {
                Log.e("SudokuAPI", "Error fetching puzzle.", e);
            }
        });
    }

    private void buildGrid(JSONArray puzzleValues) {
        sudokuGrid.removeAllViews();
        int cellSize = (int) (38 * getResources().getDisplayMetrics().density); // Slightly bigger cell

        try {
            for (int row = 0; row < 9; row++) {
                JSONArray rowArray = puzzleValues.getJSONArray(row);
                for (int col = 0; col < 9; col++) {
                    int cellValue = rowArray.getInt(col);

                    // CHANGED: Using TextView instead of EditText to prevent keyboard pop-up
                    TextView cell = new TextView(this);

                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                    params.width = cellSize;
                    params.height = cellSize;
                    params.rowSpec = GridLayout.spec(row);
                    params.columnSpec = GridLayout.spec(col);
                    params.setMargins(1, 1, 1, 1); // 1dp margin creates thin black borders
                    cell.setLayoutParams(params);

                    cell.setGravity(Gravity.CENTER);
                    cell.setBackgroundColor(Color.WHITE);
                    cell.setTextSize(20f); // Bigger text

                    if (cellValue != 0) {
                        cell.setText(String.valueOf(cellValue));
                        cell.setTextColor(Color.BLACK);
                    } else {
                        // This is an empty cell the player can interact with
                        cell.setTextColor(Color.BLUE);

                        // What happens when the player taps an empty square:
                        cell.setOnClickListener(v -> {
                            // 1. Reset the previously selected cell back to white
                            if (selectedCell != null) {
                                selectedCell.setBackgroundColor(Color.WHITE);
                            }
                            // 2. Set this new cell as the 'selected' one
                            selectedCell = (TextView) v;

                            // 3. Highlight it light blue so the player knows it's active
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

    private void setupNumberPad() {
        // List of all the button IDs from your XML
        int[] buttonIds = {R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5,
                R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9};

        // Loop through 1 to 9 and attach a click listener to each button
        for (int i = 0; i < buttonIds.length; i++) {
            int number = i + 1; // i is 0-8, so number is 1-9
            Button btn = findViewById(buttonIds[i]);

            btn.setOnClickListener(v -> {
                // If the player has selected a cell, put the number in it
                if (selectedCell != null) {
                    selectedCell.setText(String.valueOf(number));
                }
            });
        }

        // Setup the Erase (X) button
        Button btnErase = findViewById(R.id.btn_erase);
        btnErase.setOnClickListener(v -> {
            if (selectedCell != null) {
                selectedCell.setText(""); // Clear the text
            }
        });
    }
}
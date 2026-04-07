package com.example.minigamesapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.SoundPool;
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

    // User interface elements
    private GridLayout sudokuGrid;
    private TextView selectedCell = null;
    private TextView livesText;
    private Button btnMenuEasy, btnMenuMedium, btnMenuHard;

    // Audio playback variables
    private SoundPool soundPool;
    private int soundPlaceId, soundWinId, soundLoseId, soundIncorrectId;

    // Game state tracking
    private int lives = 3;
    private int[][] solutionGrid = new int[9][9];
    private boolean isGameOver = false;
    private int cellsRemaining = 0;

    // Menu overlay elements
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

        sudokuGrid = findViewById(R.id.sudoku_grid);
        livesText = findViewById(R.id.tv_lives);

        menuOverlay = findViewById(R.id.menu_overlay);
        menuTitle = findViewById(R.id.tv_menu_title);
        btnMenuEasy = findViewById(R.id.btn_menu_easy);
        btnMenuMedium = findViewById(R.id.btn_menu_medium);
        btnMenuHard = findViewById(R.id.btn_menu_hard);
        btnMenuBack = findViewById(R.id.btn_menu_back);
        btnGameMenu = findViewById(R.id.btn_game_menu);
        btnMenuViewBoard = findViewById(R.id.btn_menu_view_board);

        setupSoundPool();
        setupNumberPad();
        setupMenu();
    }

    // Pre-loads all sound files into memory for low-latency playback
    private void setupSoundPool() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(audioAttributes)
                .build();

        soundPlaceId = soundPool.load(this, R.raw.sound_place, 1);
        soundWinId = soundPool.load(this, R.raw.sound_win, 1);
        soundLoseId = soundPool.load(this, R.raw.sound_lose, 1);
        soundIncorrectId = soundPool.load(this, R.raw.sound_placeincorrect, 1);
    }

    // Configures the overlay menu and in-game menu buttons
    private void setupMenu() {
        View.OnClickListener difficultyListener = v -> {
            Button clickedBtn = (Button) v;
            String selectedDifficulty = clickedBtn.getText().toString();

            menuOverlay.setVisibility(View.GONE);
            lives = 3;
            isGameOver = false;
            livesText.setText("❤️❤️❤️");
            sudokuGrid.removeAllViews();

            fetchSudokuPuzzle(selectedDifficulty);
        };

        btnMenuEasy.setOnClickListener(difficultyListener);
        btnMenuMedium.setOnClickListener(difficultyListener);
        btnMenuHard.setOnClickListener(difficultyListener);

        btnMenuBack.setOnClickListener(v -> finish());

        btnMenuViewBoard.setOnClickListener(v -> {
            menuOverlay.setVisibility(View.GONE);
        });

        btnGameMenu.setOnClickListener(v -> {
            menuOverlay.setVisibility(View.VISIBLE);
            if (!isGameOver) {
                menuTitle.setText("Paused");
                menuTitle.setTextColor(Color.BLACK);
                btnMenuViewBoard.setVisibility(View.GONE);
            }
        });
    }

    // Fetches puzzle data from API Ninjas on a background thread
    private void fetchSudokuPuzzle(String targetDifficulty) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                URL url = new URL("https://api.api-ninjas.com/v1/sudokugenerate?difficulty=" + targetDifficulty.toLowerCase());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // Note: insert personal api-ninjas.com api key here to run and test the app locally
                connection.setRequestProperty("X-Api-Key", "insert_your_api_key_here");
                connection.setRequestProperty("Accept", "application/json");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                JSONObject jsonObject = new JSONObject(result.toString());

                JSONArray puzzleValues = jsonObject.getJSONArray("puzzle");
                JSONArray solutionValues = jsonObject.getJSONArray("solution");

                runOnUiThread(() -> buildGrid(puzzleValues, solutionValues));

            } catch (Exception e) {
                Log.e("SudokuAPI", "Error fetching API Ninjas puzzle.", e);

                runOnUiThread(() -> {
                    Toast.makeText(SudokuActivity.this, "Error fetching puzzle. Check your connection or API key.", Toast.LENGTH_LONG).show();
                    menuOverlay.setVisibility(View.VISIBLE);
                });
            }
        });
    }

    // Dynamically generates the grid and applies layout styling
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
                    int cellValue = rowArray.optInt(col, 0);
                    solutionGrid[row][col] = solutionRowArray.optInt(col, 0);

                    TextView cell = new TextView(this);
                    cell.setTag(row + "," + col);

                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                    params.width = cellSize;
                    params.height = cellSize;
                    params.rowSpec = GridLayout.spec(row);
                    params.columnSpec = GridLayout.spec(col);

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

                        cell.setOnClickListener(v -> {
                            if (isGameOver) return;

                            if (selectedCell != null) {
                                selectedCell.setBackgroundColor(Color.WHITE);
                            }
                            selectedCell = (TextView) v;
                            selectedCell.setBackgroundColor(Color.parseColor("#bbdefb"));
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
                        selectedCell.setText(String.valueOf(number));
                        selectedCell.setTextColor(Color.BLUE);
                        selectedCell.setBackgroundColor(Color.WHITE);
                        selectedCell.setOnClickListener(null);
                        selectedCell = null;

                        playSound(soundPlaceId);

                        cellsRemaining--;
                        if (cellsRemaining == 0) {
                            triggerWin();
                        }
                    } else {
                        lives--;
                        playSound(soundIncorrectId);
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
                playSound(soundPlaceId);
            }
        });
    }

    // Plays a pre-loaded sound effect
    private void playSound(int soundId) {
        if (soundPool != null) {
            soundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
        }
    }

    // Saves a statistic to the local storage
    private void saveStat(String statName) {
        SharedPreferences sharedPref = getSharedPreferences("minigames_stats", Context.MODE_PRIVATE);
        int currentScore = sharedPref.getInt(statName, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(statName, currentScore + 1);
        editor.apply();
    }

    private void triggerWin() {
        playSound(soundWinId);

        saveStat("sudoku_wins");

        isGameOver = true;
        menuTitle.setText("You Win!");
        menuTitle.setTextColor(Color.parseColor("#4caf50"));

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

            playSound(soundLoseId);

            saveStat("sudoku_losses");

            if (selectedCell != null) {
                selectedCell.setBackgroundColor(Color.WHITE);
            }

            menuTitle.setText("Game Over!");
            menuTitle.setTextColor(Color.RED);
            btnMenuViewBoard.setVisibility(View.VISIBLE);
            menuOverlay.setVisibility(View.VISIBLE);
        }
    }

    // Cleans up memory when the activity is destroyed
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}
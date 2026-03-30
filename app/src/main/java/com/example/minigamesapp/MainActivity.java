package com.example.minigamesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Find the button by the ID we just gave it in the XML
        Button startSudokuBtn = findViewById(R.id.btn_start_sudoku);

        // 2. Set an OnClickListener to trigger when the button is tapped
        startSudokuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 3. Create an Intent to navigate from this activity to the SudokuActivity
                Intent intent = new Intent(MainActivity.this, SudokuActivity.class);

                // 4. Start the new activity
                startActivity(intent);
            }
        });
    }
}
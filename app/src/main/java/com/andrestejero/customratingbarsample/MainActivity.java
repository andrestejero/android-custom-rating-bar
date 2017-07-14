package com.andrestejero.customratingbarsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.andrestejero.customratingbar.CustomRatingBar;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example
        TextView question = (TextView) findViewById(R.id.question);
        question.setText("¿Qué tan fácil e intuitivo te resultó el uso de la APP?");
        CustomRatingBar rating = (CustomRatingBar) findViewById(R.id.rating);
        rating.setOnStarChangeListener(new CustomRatingBar.OnStarChangeListener() {
            @Override
            public void onStarChange(int stars) {
                Log.d(LOG_TAG, "star: " + stars);
            }
        });
    }
}

package com.andrestejero.customratingbarsample;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.andrestejero.customratingbar.CustomRatingBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private int mRating = 0;
    private CustomRatingBar mRatingBar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRatingBar1 = (CustomRatingBar) findViewById(R.id.rating1);

        List<Integer> colors = new ArrayList<>();
        colors.add(R.color.yellow20);
        colors.add(R.color.yellow40);
        colors.add(R.color.yellow60);
        colors.add(R.color.yellow80);
        colors.add(R.color.yellow100);
        mRatingBar1.setColorStarActive(colors);

        mRatingBar1.setOnStarChangeListener(new CustomRatingBar.OnStarChangeListener() {
            @Override
            public void onStarChange(int stars) {
                mRating = stars;
            }
        });

        Button buttonContinue = (Button) findViewById(R.id.buttonContinue);
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRating == 0) {
                    mRatingBar1.showErrorStars();
                    Snackbar.make(findViewById(R.id.content), getString(R.string.error), Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(findViewById(R.id.content), getString(R.string.result, mRating), Snackbar.LENGTH_LONG).show();
                }
            }
        });

        CustomRatingBar ratingBar2 = (CustomRatingBar) findViewById(R.id.rating2);
        ratingBar2.setOnStarChangeListener(new CustomRatingBar.OnStarChangeListener() {
            @Override
            public void onStarChange(int stars) {
                Snackbar.make(findViewById(R.id.content), getString(R.string.result, stars), Snackbar.LENGTH_LONG).show();
            }
        });
    }
}

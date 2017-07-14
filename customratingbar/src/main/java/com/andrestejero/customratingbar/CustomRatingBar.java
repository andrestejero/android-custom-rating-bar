package com.andrestejero.customratingbar;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class CustomRatingBar extends FrameLayout {

    @Nullable
    private OnStarChangeListener mListener;

    private ImageView[] starInactive;
    private View[] starActive;

    private Animation mBounceAnimation;

    int mStars;

    public CustomRatingBar(Context context) {
        super(context);
        init(context);
    }

    public CustomRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomRatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomRatingBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mStars = 0;
        inflate(context, R.layout.custom_rating_bar, this);
        View star1 = findViewById(R.id.star1);
        View star2 = findViewById(R.id.star2);
        View star3 = findViewById(R.id.star3);
        View star4 = findViewById(R.id.star4);
        View star5 = findViewById(R.id.star5);

        starInactive = new ImageView[]{
                (ImageView) findViewById(R.id.starInactive1),
                (ImageView) findViewById(R.id.starInactive2),
                (ImageView) findViewById(R.id.starInactive3),
                (ImageView) findViewById(R.id.starInactive4),
                (ImageView) findViewById(R.id.starInactive5)
        };

        starActive = new View[]{
                findViewById(R.id.starActive1),
                findViewById(R.id.starActive2),
                findViewById(R.id.starActive3),
                findViewById(R.id.starActive4),
                findViewById(R.id.starActive5)
        };

        star1.setOnClickListener(onClick(1));
        star2.setOnClickListener(onClick(2));
        star3.setOnClickListener(onClick(3));
        star4.setOnClickListener(onClick(4));
        star5.setOnClickListener(onClick(5));

        mBounceAnimation = AnimationUtils.loadAnimation(context, R.anim.bounce);
        refreshStars();
    }

    private OnClickListener onClick(final int position) {
        return new OnClickListener() {
            @Override
            public void onClick(View view) {
                mStars = position;
                refreshStars();
                if (mListener != null) {
                    mListener.onStarChange(mStars);
                }
            }
        };
    }

    private void refreshStars() {
        for (int i = 0; i < starActive.length; i++) {
            starActive[i].clearAnimation();
            if (i < mStars) {
                starInactive[i].setVisibility(INVISIBLE);
                starActive[i].setVisibility(VISIBLE);
                starActive[i].startAnimation(mBounceAnimation);
            } else {
                starInactive[i].setVisibility(VISIBLE);
                starActive[i].setVisibility(INVISIBLE);
            }
            starInactive[i].setColorFilter(ContextCompat.getColor(getContext(),R.color.grey20));
        }
    }

    public void showErrorStars() {
        for (int i = 0; i < starActive.length; i++) {
            starInactive[i].setVisibility(VISIBLE);
            starInactive[i].setColorFilter(ContextCompat.getColor(getContext(),R.color.red100));
            starActive[i].setVisibility(INVISIBLE);
        }
    }

    public interface OnStarChangeListener {
        void onStarChange(int stars);
    }

    public void setOnStarChangeListener(@Nullable OnStarChangeListener starChangeListener) {
        this.mListener = starChangeListener;
    }
}
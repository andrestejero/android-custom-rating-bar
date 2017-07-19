package com.andrestejero.customratingbar;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.List;

public class CustomRatingBar extends FrameLayout {

    private static final String LOG_TAG = CustomRatingBar.class.getSimpleName();
    private static final int DEFAULT_STARS = 5;

    @Nullable
    private OnStarChangeListener mListener;

    private ImageView[] mStarsInactive;
    private ImageView[] mStarsActive;

    private Animation mBounceAnimation;

    private int mStarSelected;

    public CustomRatingBar(Context context) {
        super(context);
        init(context, null);
    }

    public CustomRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomRatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomRatingBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        inflate(context, R.layout.custom_rating_bar, this);

        mStarSelected = 0;
        int starSize = (int) getResources().getDimension(R.dimen.star);
        int stars = DEFAULT_STARS;
        int starActiveColor = ContextCompat.getColor(context, R.color.yellow100);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomRatingBarView, 0, 0);
            stars = a.getInt(R.styleable.CustomRatingBarView_stars, stars);
            starSize = a.getDimensionPixelSize(R.styleable.CustomRatingBarView_starSize, starSize);
            starActiveColor = a.getInt(R.styleable.CustomRatingBarView_starActiveColor, starActiveColor);
            a.recycle();
        }

        ViewGroup root = (ViewGroup) findViewById(R.id.root);

        View[] starContainer = new View[stars];
        mStarsInactive = new ImageView[stars];
        mStarsActive = new ImageView[stars];

        for (int i = 0; i < stars; i++) {
            View starLayout = LayoutInflater.from(context).inflate(R.layout.custom_rating_bar_item, null);
            starContainer[i] = starLayout.findViewById(R.id.starContainer);
            starContainer[i].setOnClickListener(onClick(i + 1));
            mStarsInactive[i] = (ImageView) starLayout.findViewById(R.id.starInactive);
            mStarsActive[i] = (ImageView) starLayout.findViewById(R.id.starActive);
            setStarSize(i, starSize);
            setStarActiveColor(i, starActiveColor);
            root.addView(starLayout);
        }

        mBounceAnimation = AnimationUtils.loadAnimation(context, R.anim.bounce);
        refreshStars();
    }

    private OnClickListener onClick(final int position) {
        return new OnClickListener() {
            @Override
            public void onClick(View view) {
                mStarSelected = position;
                refreshStars();
                if (mListener != null) {
                    mListener.onStarChange(mStarSelected);
                }
            }
        };
    }

    private void refreshStars() {
        for (int i = 0; i < mStarsActive.length; i++) {
            mStarsActive[i].clearAnimation();
            if (i < mStarSelected) {
                mStarsInactive[i].setVisibility(INVISIBLE);
                mStarsActive[i].setVisibility(VISIBLE);
                mStarsActive[i].startAnimation(mBounceAnimation);
            } else {
                mStarsInactive[i].setVisibility(VISIBLE);
                mStarsActive[i].setVisibility(INVISIBLE);
            }
            mStarsInactive[i].setColorFilter(ContextCompat.getColor(getContext(), R.color.grey20));
        }
    }

    public void setColorStarActive(@NonNull List<Integer> colors) {
        for (int i = 0; i < mStarsActive.length; i++) {
            if (colors.size() > i) {
                mStarsActive[i].setColorFilter(ContextCompat.getColor(getContext(), colors.get(i)));
            }
        }
    }

    public void showErrorStars() {
        for (int i = 0; i < mStarsActive.length; i++) {
            mStarsInactive[i].setVisibility(VISIBLE);
            mStarsInactive[i].setColorFilter(ContextCompat.getColor(getContext(), R.color.red100));
            mStarsActive[i].setVisibility(INVISIBLE);
        }
    }

    private void setStarSize(int i, int size) {
        mStarsActive[i].getLayoutParams().height = size;
        mStarsActive[i].getLayoutParams().width = size;
        mStarsInactive[i].getLayoutParams().height = size;
        mStarsInactive[i].getLayoutParams().width = size;
    }

    private void setStarActiveColor(int i, int color) {
        mStarsActive[i].setColorFilter(color);
    }

    public interface OnStarChangeListener {
        void onStarChange(int stars);
    }

    public void setOnStarChangeListener(@Nullable OnStarChangeListener starChangeListener) {
        this.mListener = starChangeListener;
    }
}
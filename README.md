# Android Custom Rating Bar

## Demonstration

// Gif

## Installation and usage

Add it as dependency in Gradle as:
```
compile 'com.github.andrestejero:customratingbar:1.1.0'
```

Add the following xml code into your layout/something.xml:

```
   <com.andrestejero.customratingbar.CustomRatingBar
        android:id="@+id/ratingBar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:starActiveColor="@color/colorPrimary"
        app:starSize="26dp"
        app:stars="7" />
```
Define active color for each star

```
CustomRatingBar ratingBar = (CustomRatingBar) findViewById(R.id.ratingBar);
ratingBar.setColorStarActive(R.color.star1, R.color.star2, R.color.star3, R.color.star4, R.color.star5);
```

Show error

```
ratingBar.showErrorStars();
```

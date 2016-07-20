package com.romanus.cardiotracker.ui.workout;

import com.romanus.cardiotracker.R;

/**
 * Created by rursu on 20.07.16.
 */
public enum WorkoutParameter {
    MIN_HR("Min HR", R.drawable.heart_24),
    MAX_HR("Max HR", R.drawable.heart_thunder_24),
    TARGET_DURATION("Duration", R.drawable.time_24),
    TRAGET_DISTANCE("Distance", R.drawable.length_24);

    private String title;
    private int icon;

    WorkoutParameter(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }
}

package com.romanus.cardiotracker.util;

import com.romanus.cardiotracker.R;

/**
 * Created by roman on 7/22/16.
 */
public enum WorkoutType {
    RUNNING("Running", R.drawable.heart_24),
    BOXING("Running", R.drawable.heart_24),
    HIKING("Running", R.drawable.heart_24),
    WALKING("Running", R.drawable.heart_24);

    private String title;
    private int icon;

    WorkoutType(String title, int icon) {
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

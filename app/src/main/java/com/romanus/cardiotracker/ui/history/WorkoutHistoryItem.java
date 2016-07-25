package com.romanus.cardiotracker.ui.history;

import com.romanus.cardiotracker.util.WorkoutType;

/**
 * Created by roman on 7/22/16.
 */
public class WorkoutHistoryItem {
    private String description;
    private String date;
    private WorkoutType type;

    public WorkoutHistoryItem() { }

    public WorkoutHistoryItem(String description, String date, WorkoutType type) {
        this.description = description;
        this.date = date;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public WorkoutType getType() {
        return type;
    }

    public void setType(WorkoutType type) {
        this.type = type;
    }
}

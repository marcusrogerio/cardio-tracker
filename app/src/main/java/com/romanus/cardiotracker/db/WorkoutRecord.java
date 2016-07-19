package com.romanus.cardiotracker.db;

/**
 * Created by roman on 6/12/16.
 */
public class WorkoutRecord {
    private String type;
    private String note;
    private int id;
    private int maxRH;
    private int minRH;
    private int averageRH;
    private double destance;
    private double averageSpeed;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaxRH() {
        return maxRH;
    }

    public void setMaxRH(int maxRH) {
        this.maxRH = maxRH;
    }

    public int getMinRH() {
        return minRH;
    }

    public void setMinRH(int minRH) {
        this.minRH = minRH;
    }

    public int getAverageRH() {
        return averageRH;
    }

    public void setAverageRH(int averageRH) {
        this.averageRH = averageRH;
    }

    public double getDestance() {
        return destance;
    }

    public void setDestance(double destance) {
        this.destance = destance;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }
}
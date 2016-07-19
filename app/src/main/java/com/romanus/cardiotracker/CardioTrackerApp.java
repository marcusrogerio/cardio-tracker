package com.romanus.cardiotracker;

import android.app.Application;

import com.romanus.cardiotracker.domain.AppModule;

/**
 * Created by roman on 6/12/16.
 */
public class CardioTrackerApp extends Application {
    private static DiComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerDiComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static DiComponent getAppComponent() {
        return appComponent;
    }
}

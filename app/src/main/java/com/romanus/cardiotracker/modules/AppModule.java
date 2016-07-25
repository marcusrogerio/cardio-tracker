package com.romanus.cardiotracker.modules;

import android.content.Context;

import com.romanus.cardiotracker.CardioTrackerApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by roman on 6/12/16.
 */
@Module
public class AppModule {
    private CardioTrackerApp app;

    public AppModule(CardioTrackerApp app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return app.getApplicationContext();
    }
}

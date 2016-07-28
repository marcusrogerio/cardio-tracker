package com.romanus.cardiotracker;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.romanus.cardiotracker.db.DataBaseHelper;
import com.romanus.cardiotracker.modules.AppModule;
import com.romanus.cardiotracker.modules.DBModule;

/**
 * Created by roman on 6/12/16.
 */
public class CardioTrackerApp extends Application {
    private static DiComponent appComponent;
    private DataBaseHelper databaseHelper = null;
    private int activeActivitiesNumber = 0;

    @Override
    public void onCreate() {
        super.onCreate();

        databaseHelper = getDataBaseHelper();
        appComponent = DaggerDiComponent.builder()
                .appModule(new AppModule(this))
                .dBModule(new DBModule(databaseHelper))
                .build();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activeActivitiesNumber++;
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                activeActivitiesNumber--;
                if (activeActivitiesNumber == 0 && !activity.isChangingConfigurations()) {

                    releaseDBHelper();
                }
            }
        });
    }

    private DataBaseHelper getDataBaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DataBaseHelper.class);
        }

        return databaseHelper;
    }

    private void releaseDBHelper() {
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    public static DiComponent getAppComponent() {
        return appComponent;
    }
}

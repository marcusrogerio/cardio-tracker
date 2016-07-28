package com.romanus.cardiotracker.modules;

import com.romanus.cardiotracker.db.DBManager;
import com.romanus.cardiotracker.db.DataBaseHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rursu on 28.07.16.
 */
@Module
public class DBModule {
    private DataBaseHelper dataBaseHelper;

    public DBModule(DataBaseHelper dataBaseHelper) {
        this.dataBaseHelper = dataBaseHelper;
    }

    @Provides
    @Singleton
    public DBManager provideDataBaseHelper() {
        return this.dataBaseHelper;
    }
}

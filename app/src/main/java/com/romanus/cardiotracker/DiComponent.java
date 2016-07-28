package com.romanus.cardiotracker;

import com.romanus.cardiotracker.modules.AppModule;
import com.romanus.cardiotracker.modules.BluetoothModule;
import com.romanus.cardiotracker.modules.DBModule;
import com.romanus.cardiotracker.modules.SettingsModule;
import com.romanus.cardiotracker.modules.UtilsModule;
import com.romanus.cardiotracker.ui.MainActivity;
import com.romanus.cardiotracker.ui.settings.SettingsActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by roman on 6/12/16.
 */
@Singleton
@Component(modules = {AppModule.class, UtilsModule.class, BluetoothModule.class, SettingsModule.class, DBModule.class})
public interface DiComponent {
    void inject(MainActivity mainActivity);
    void inject(SettingsActivity settingsActivity);
}

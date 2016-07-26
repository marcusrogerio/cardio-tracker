package com.romanus.cardiotracker.modules;

import com.romanus.cardiotracker.bluetooth.BluetoothAPI;
import com.romanus.cardiotracker.ui.settings.SettingsPresenter;
import com.romanus.cardiotracker.ui.settings.SettingsPresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by roman on 7/26/16.
 */
@Module(includes = {AppModule.class, BluetoothModule.class})
public class SettingsModule {

    @Provides
    @Singleton
    public SettingsPresenter provideSettingsPresenter(BluetoothAPI bluetoothAPI) {
        return new SettingsPresenterImpl(bluetoothAPI);
    }


}

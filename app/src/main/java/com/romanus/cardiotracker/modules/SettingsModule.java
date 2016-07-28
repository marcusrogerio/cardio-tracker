package com.romanus.cardiotracker.modules;

import com.romanus.cardiotracker.bluetooth.BluetoothDeviceManager;
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
    public SettingsPresenter provideSettingsPresenter(BluetoothDeviceManager bluetoothDeviceManager) {
        return new SettingsPresenterImpl(bluetoothDeviceManager);
    }


}

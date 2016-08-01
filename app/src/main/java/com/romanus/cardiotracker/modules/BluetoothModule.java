package com.romanus.cardiotracker.modules;

import android.content.Context;

import com.romanus.cardiotracker.bluetooth.BluetoothAPI;
import com.romanus.cardiotracker.bluetooth.BluetoothAPIImpl;
import com.romanus.cardiotracker.bluetooth.BluetoothDeviceManager;
import com.romanus.cardiotracker.db.DBManager;
import com.romanus.cardiotracker.db.DataBaseHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by roman on 7/26/16.
 */
@Module(includes = {AppModule.class, DBModule.class})
public class BluetoothModule {

    @Singleton
    @Provides
    public BluetoothAPI providesBluetoothAPI(Context context) {
        return new BluetoothAPIImpl(context);
    }

    @Singleton
    @Provides
    public BluetoothDeviceManager providesBluetoothDeviceManager(BluetoothAPI bluetoothAPI, DBManager dbManager) {
        return new BluetoothDeviceManager(bluetoothAPI, dbManager);
    }
}

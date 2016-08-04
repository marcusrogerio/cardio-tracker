package com.romanus.cardiotracker.modules;

import android.content.Context;

import com.romanus.cardiotracker.bluetooth.BLEDeviceManager;
import com.romanus.cardiotracker.bluetooth.BluetoothScanner;
import com.romanus.cardiotracker.bluetooth.BluetoothScannerImpl;
import com.romanus.cardiotracker.bluetooth.BluetoothDeviceManager;
import com.romanus.cardiotracker.db.DBManager;

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
    public BluetoothScanner providesBluetoothAPI(Context context) {
        return new BluetoothScannerImpl(context);
    }

    @Singleton
    @Provides
    public BLEDeviceManager providesBluetoothDeviceManager(BluetoothScanner bluetoothScanner, DBManager dbManager, Context context) {
        return new BluetoothDeviceManager(bluetoothScanner, dbManager, context);
    }
}

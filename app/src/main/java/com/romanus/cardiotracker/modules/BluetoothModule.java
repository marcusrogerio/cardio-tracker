package com.romanus.cardiotracker.modules;

import android.content.Context;

import com.romanus.cardiotracker.bluetooth.BluetoothAPI;
import com.romanus.cardiotracker.bluetooth.BluetoothAPIImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by roman on 7/26/16.
 */
@Module(includes = {AppModule.class})
public class BluetoothModule {

    @Singleton
    @Provides
    public BluetoothAPI providesBluetoothAPI(Context context) {
        return new BluetoothAPIImpl(context);
    }
}

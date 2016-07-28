package com.romanus.cardiotracker.ui.settings;

import android.bluetooth.BluetoothDevice;

import java.util.Set;

/**
 * Created by roman on 7/26/16.
 */
public interface SettingsView {
    void onDevicesDetected(Set<BluetoothDevice> deviceName);

}

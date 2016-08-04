package com.romanus.cardiotracker.bluetooth;

import android.bluetooth.BluetoothDevice;

import java.util.Set;

/**
 * Created by roman on 7/26/16.
 */
public interface BluetoothScanner {

    interface ScanCallback {
        void onDevicesFound(Set<BluetoothDevice> devices);
    }

    void startScanLeDevices();
    void stopScanLeDevices();
    void setScanCallback(ScanCallback scanCallback);

}

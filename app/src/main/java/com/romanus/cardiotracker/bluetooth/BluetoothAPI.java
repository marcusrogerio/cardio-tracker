package com.romanus.cardiotracker.bluetooth;

import android.bluetooth.BluetoothDevice;

/**
 * Created by roman on 7/26/16.
 */
public interface BluetoothAPI {

    interface ScanCallback {
        void onDeviceFound(BluetoothDevice device);
    }

    void startScanLeDevices();
    void stopScanLeDevices();
    void setScanCallback(ScanCallback scanCallback);

}

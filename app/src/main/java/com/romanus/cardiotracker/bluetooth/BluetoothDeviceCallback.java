package com.romanus.cardiotracker.bluetooth;

/**
 * Created by rursu on 03.08.16.
 */
public interface BluetoothDeviceCallback {
    void onDeviceConnected(String address);
    void onDeviceDisconnected(String address);
    void onDataUpdated(Object data);
}

package com.romanus.cardiotracker.bluetooth;

import android.support.v4.content.LocalBroadcastManager;

import com.romanus.cardiotracker.db.beans.SavedBluetoothDevice;

import java.util.List;

import rx.Observable;

/**
 * Created by rursu on 03.08.16.
 */
public interface BLEDeviceManager {
    Observable<List<SavedBluetoothDevice>> scanForBLEDevices();
    Observable<List<SavedBluetoothDevice>> getBLEDevicesFromDB();
    void stopScan();
    void setBluetoothDeviceCallback(BluetoothDeviceCallback bluetoothDeviceCallback);
    void disconnect();
    void release();
    void startListenDataUpdate();
    void stopListenDataUpdate();
    void connectToDevice(SavedBluetoothDevice device);
}

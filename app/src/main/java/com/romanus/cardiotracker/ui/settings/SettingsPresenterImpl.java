package com.romanus.cardiotracker.ui.settings;

import android.bluetooth.BluetoothDevice;

import com.romanus.cardiotracker.bluetooth.BluetoothAPI;

/**
 * Created by roman on 7/26/16.
 */
public class SettingsPresenterImpl implements SettingsPresenter {

    private BluetoothAPI bluetoothAPI;
    private SettingsView settingsView;

    public SettingsPresenterImpl(BluetoothAPI bluetoothAPI) {
        this.bluetoothAPI = bluetoothAPI;
    }

    @Override
    public void setView(SettingsView settingsView) {
        this.settingsView = settingsView;
        startLookingForDevices();
    }

    private void startLookingForDevices() {
        bluetoothAPI.setScanCallback(new BluetoothAPI.ScanCallback() {
            @Override
            public void onDeviceFound(BluetoothDevice device) {
                if (settingsView != null) {
                    settingsView.onNewDeviceDetected(device.getName());
                }
            }
        });
        bluetoothAPI.startScanLeDevices();
    }
}

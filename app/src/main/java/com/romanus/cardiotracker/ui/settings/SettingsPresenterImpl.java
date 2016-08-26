package com.romanus.cardiotracker.ui.settings;

import android.util.Log;

import com.romanus.cardiotracker.bluetooth.BLEDeviceManager;
import com.romanus.cardiotracker.bluetooth.BluetoothDeviceCallback;
import com.romanus.cardiotracker.db.beans.SavedBluetoothDevice;

import java.util.List;

import rx.Observer;
import rx.Subscription;

/**
 * Created by roman on 7/26/16.
 */
public class SettingsPresenterImpl implements SettingsPresenter {

    private static final String TAG = SettingsPresenterImpl.class.getSimpleName();
    private BLEDeviceManager bleDeviceManager;
    private SettingsView settingsView;
    private Subscription scanSubscription;
    private Subscription loadSubscription;
    private BluetoothDeviceCallback bluetoothDeviceCallback = new BluetoothDeviceCallback() {
        @Override
        public void onDeviceConnected(String address) {
            if (settingsView != null) {
                settingsView.showDeviceConnected(address);
            }
        }

        @Override
        public void onDeviceConnecting(String address) {
            if (settingsView != null) {
                settingsView.showDeviceConnecting(address);
            }
        }

        @Override
        public void onDeviceDisconnected(String address) {
            if (settingsView != null) {
                settingsView.showDeviceDisconnected(address);
            }
        }

        @Override
        public void onDataUpdated(Object data, String address) {
            if (settingsView != null && (data instanceof String)) {
                try {
                    int heartRate = Integer.parseInt((String)data);
                    settingsView.heartRateUpdated(heartRate, address);
                } catch (NumberFormatException e) {
                    Log.e(TAG, "Wrong Data format", e);
                }
            }
        }
    };

    public SettingsPresenterImpl(BLEDeviceManager bleDeviceManager) {
        this.bleDeviceManager = bleDeviceManager;
        this.bleDeviceManager.setBluetoothDeviceCallback(bluetoothDeviceCallback);
    }

    @Override
    public void setView(SettingsView settingsView) {
        this.settingsView = settingsView;
    }

    @Override
    public void startScanForDevices() {
        if (settingsView != null) {
            settingsView.showScanProgress(true);
        }

        scanSubscription = bleDeviceManager.scanForBLEDevices().subscribe(new Observer<List<SavedBluetoothDevice>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Error occurred while scanning devices", e);
            }

            @Override
            public void onNext(List<SavedBluetoothDevice> savedBluetoothDevices) {
                if (settingsView != null) {
                    settingsView.onScannedDevicesDetected(savedBluetoothDevices);
                    settingsView.showScanProgress(false);
                }
            }
        });
    }

    @Override
    public void loadSavedDevices() {
        loadSubscription = bleDeviceManager.getBLEDevicesFromDB().subscribe(new Observer<List<SavedBluetoothDevice>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Error occurred while loading devices from DB", e);
            }

            @Override
            public void onNext(List<SavedBluetoothDevice> savedBluetoothDevices) {
                if (settingsView != null) {
                    settingsView.onSavedDevicesLoaded(savedBluetoothDevices);
                }
            }
        });
    }

    @Override
    public void stopScanForDevices() {
        if (scanSubscription != null) {
            scanSubscription.unsubscribe();
            scanSubscription = null;
        }
        bleDeviceManager.stopScan();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onDeviceSelected(SavedBluetoothDevice device) {
        bleDeviceManager.connectToDevice(device);
    }
}

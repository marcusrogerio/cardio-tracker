package com.romanus.cardiotracker.ui.settings;

import android.util.Log;

import com.romanus.cardiotracker.bluetooth.BLEDeviceManager;
import com.romanus.cardiotracker.bluetooth.BluetoothDeviceManager;
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

    public SettingsPresenterImpl(BLEDeviceManager bleDeviceManager) {
        this.bleDeviceManager = bleDeviceManager;
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

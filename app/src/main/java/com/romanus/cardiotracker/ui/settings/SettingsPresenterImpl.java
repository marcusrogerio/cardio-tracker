package com.romanus.cardiotracker.ui.settings;

import android.bluetooth.BluetoothDevice;

import com.romanus.cardiotracker.bluetooth.BluetoothDeviceManager;

import java.util.Set;

import rx.Observer;
import rx.Subscription;

/**
 * Created by roman on 7/26/16.
 */
public class SettingsPresenterImpl implements SettingsPresenter {

    private BluetoothDeviceManager bluetoothDeviceManager;
    private SettingsView settingsView;
    private Subscription subscription;

    public SettingsPresenterImpl(BluetoothDeviceManager bluetoothDeviceManager) {
        this.bluetoothDeviceManager = bluetoothDeviceManager;
    }

    @Override
    public void setView(SettingsView settingsView) {
        this.settingsView = settingsView;
    }

    @Override
    public void startScanForDevices() {
        subscription = bluetoothDeviceManager.scanForBLEDevices().subscribe(new Observer<Set<BluetoothDevice>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Set<BluetoothDevice> bluetoothDevices) {
                if (settingsView != null) {
                    settingsView.onDevicesDetected(bluetoothDevices);
                }
            }
        });
    }

    @Override
    public void stopScanForDevices() {
        if (subscription != null) {
            subscription.unsubscribe();
            subscription = null;
        }
        bluetoothDeviceManager.stopScan();
    }

    @Override
    public void onDestroy() {

    }
}

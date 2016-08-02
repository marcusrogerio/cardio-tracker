package com.romanus.cardiotracker.ui.settings;

import com.romanus.cardiotracker.db.beans.SavedBluetoothDevice;

/**
 * Created by roman on 7/26/16.
 */
public interface SettingsPresenter {
    void setView(SettingsView settingsView);
    void startScanForDevices();
    void loadSavedDevices();
    void stopScanForDevices();
    void onDestroy();
    void onDeviceSelected(SavedBluetoothDevice device);
}

package com.romanus.cardiotracker.ui.settings;

/**
 * Created by roman on 7/26/16.
 */
public interface SettingsPresenter {
    void setView(SettingsView settingsView);
    void startScanForDevices();
    void loadSavedDevices();
    void stopScanForDevices();
    void onDestroy();
}

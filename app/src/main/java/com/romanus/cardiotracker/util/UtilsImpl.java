package com.romanus.cardiotracker.util;

import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by roman on 7/25/16.
 */
public class UtilsImpl implements Utils {

    private Context context;

    public UtilsImpl(Context context) {
        this.context = context;
    }

    @Override
    public boolean isBluetoothSupported() {
        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothAPIImpl
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);

        // Checks if Bluetooth is supported on the device.
        return bluetoothManager.getAdapter() != null;
    }

    @Override
    public boolean isBLESupported() {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }

    @Override
    public boolean isBluetoothEnabled() {
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        if (bluetoothManager.getAdapter() != null) {
            return bluetoothManager.getAdapter().isEnabled();
        } else {
            return false;
        }
    }


}

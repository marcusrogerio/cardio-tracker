package com.romanus.cardiotracker.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Handler;

/**
 * Created by roman on 6/12/16.
 */
public class BluetoothAPIImpl implements BluetoothAPI {
    private boolean isScanning;
    private Handler handler = new Handler();
    private BluetoothAdapter bluetoothAdapter;
    private static final long SCAN_PERIOD = 20000;
    private BluetoothAPI.ScanCallback scanCallback;
    private Context context;

    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            if (scanCallback != null) {
                scanCallback.onDeviceFound(device);
            }
        }
    };

    public BluetoothAPIImpl(Context context) {
        this.context = context;
    }

    @Override
    public void startScanLeDevices() {
        if (bluetoothAdapter == null) {
            initBluetoothAdapter();
        }

        // Stops scanning after a pre-defined scan period.
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isScanning = false;
                bluetoothAdapter.stopLeScan(leScanCallback);
            }
        }, SCAN_PERIOD);

        isScanning = true;
        bluetoothAdapter.startLeScan(leScanCallback);
    }

    private void initBluetoothAdapter() {
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
    }

    @Override
    public void stopScanLeDevices() {
        isScanning = false;
        bluetoothAdapter.stopLeScan(leScanCallback);
    }

    @Override
    public void setScanCallback(ScanCallback scanCallback) {
        this.scanCallback = scanCallback;
    }
}

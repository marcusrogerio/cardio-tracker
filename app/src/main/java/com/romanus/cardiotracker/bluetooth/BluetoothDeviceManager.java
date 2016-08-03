package com.romanus.cardiotracker.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.romanus.cardiotracker.db.DBManager;
import com.romanus.cardiotracker.db.beans.SavedBluetoothDevice;
import com.romanus.cardiotracker.util.BluetoothLeService;
import com.romanus.cardiotracker.util.RxHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by rursu on 27.07.16.
 */
public class BluetoothDeviceManager implements BLEDeviceManager {

    private static final String TAG = BluetoothDeviceManager.class.getSimpleName();
    private BluetoothAPI bluetoothAPI;
    private DBManager dbManager;
    private Context context;
    private String deviceAddress;
    private BluetoothLeService bluetoothLeService;
    private BluetoothDeviceCallback bluetoothDeviceCallback;
    private IntentFilter intentFilter;

    private final ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            bluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!bluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
            } else {
                // Automatically connects to the device upon successful start-up initialization.
                bluetoothLeService.connect(deviceAddress);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            bluetoothLeService = null;
        }
    };

    private final BroadcastReceiver bleUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                if (bluetoothDeviceCallback != null) {
                    bluetoothDeviceCallback.onDeviceConnected(deviceAddress);
                }
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                if (bluetoothDeviceCallback != null) {
                    bluetoothDeviceCallback.onDeviceDisconnected(deviceAddress);
                }
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                String data = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
                if (bluetoothDeviceCallback != null) {
                    bluetoothDeviceCallback.onDataUpdated(data);
                }
            }
        }
    };

    public BluetoothDeviceManager(BluetoothAPI bluetoothAPI, DBManager dbManager, Context context) {
        this.bluetoothAPI = bluetoothAPI;
        this.dbManager = dbManager;
        this.context = context;

        intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
    }

    @Override
    public Observable<List<SavedBluetoothDevice>> scanForBLEDevices() {
        return Observable.create(new Observable.OnSubscribe<List<SavedBluetoothDevice>>() {
            @Override
            public void call(final Subscriber<? super List<SavedBluetoothDevice>> subscriber) {
                if (subscriber != null && !subscriber.isUnsubscribed()) {

                    bluetoothAPI.setScanCallback(new BluetoothAPI.ScanCallback() {
                        @Override
                        public void onDevicesFound(Set<BluetoothDevice> devices) {
                            updateDB(devices);
                            subscriber.onNext(convertData(devices));
                            subscriber.onCompleted();
                        }
                    });
                    bluetoothAPI.startScanLeDevices();
                }
            }
        }).compose(RxHelper.<List<SavedBluetoothDevice>>getSchedulers());
    }

    @Override
    public void stopScan() {
        bluetoothAPI.stopScanLeDevices();
    }

    @Override
    public Observable<List<SavedBluetoothDevice>> getBLEDevicesFromDB() {
        return Observable.create(new Observable.OnSubscribe<List<SavedBluetoothDevice>>() {
            @Override
            public void call(final Subscriber<? super List<SavedBluetoothDevice>> subscriber) {
                try {
                    List<SavedBluetoothDevice> savedBluetoothDevices = dbManager.getBluetoothDeviceDao().queryForAll();

                    if (subscriber != null && !subscriber.isUnsubscribed()) {
                        subscriber.onNext(savedBluetoothDevices);
                        subscriber.onCompleted();
                    }
                } catch (SQLException e) {
                    if (subscriber != null && !subscriber.isUnsubscribed()) {
                        subscriber.onError(e);
                    }
                }
                bluetoothAPI.startScanLeDevices();

            }
        }).compose(RxHelper.<List<SavedBluetoothDevice>>getSchedulers());
    }

    @Override
    public void connectToDevice(SavedBluetoothDevice device) {
        deviceAddress = device.getAddress();
        Intent gattServiceIntent = new Intent(context, BluetoothLeService.class);
        context.bindService(gattServiceIntent, serviceConnection, context.BIND_AUTO_CREATE);
    }

    @Override
    public void setBluetoothDeviceCallback(BluetoothDeviceCallback bluetoothDeviceCallback) {
        this.bluetoothDeviceCallback = bluetoothDeviceCallback;
    }

    @Override
    public void disconnect() {
        deviceAddress = null;
        if (bluetoothLeService != null) {
            bluetoothLeService.disconnect();
        }
    }

    @Override
    public void release() {
        context.unbindService(serviceConnection);
        bluetoothLeService = null;
    }

    @Override
    public void startListenDataUpdate() {
        LocalBroadcastManager.getInstance(context).registerReceiver(bleUpdateReceiver, intentFilter);
    }

    @Override
    public void stopListenDataUpdate() {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(bleUpdateReceiver);
    }

    private List<SavedBluetoothDevice> convertData(Set<BluetoothDevice> devices) {
        List<SavedBluetoothDevice> list = new ArrayList<>();

        if (devices.size() > 0) {
            Iterator<BluetoothDevice> iterator = devices.iterator();
            while (iterator.hasNext()) {
                BluetoothDevice bluetoothDevice = iterator.next();
                SavedBluetoothDevice savedBluetoothDevice = new SavedBluetoothDevice();
                savedBluetoothDevice.setAddress(bluetoothDevice.getAddress());
                savedBluetoothDevice.setName(bluetoothDevice.getName());
                savedBluetoothDevice.setType(bluetoothDevice.getType());

                list.add(savedBluetoothDevice);
            }
        }
        return list;
    }

    private void updateDB(Set<BluetoothDevice> scannedDevices) {
        try {
            for (SavedBluetoothDevice newDevice : convertData(scannedDevices)) {
                dbManager.getBluetoothDeviceDao().createIfNotExists(newDevice);
            }

        } catch (SQLException e) {
            Log.e(TAG, "Error updating bluetooth devices DB");
        }
    }
}

package com.romanus.cardiotracker.ui.settings;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.romanus.cardiotracker.CardioTrackerApp;
import com.romanus.cardiotracker.R;
import com.romanus.cardiotracker.db.beans.SavedBluetoothDevice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by roman on 7/26/16.
 */
public class SettingsActivity extends Activity implements SettingsView {

    @BindView(R.id.rv_scanned_devices_list)
    RecyclerView scannedDevicesList;

    @BindView(R.id.rv_saved_devices_list)
    RecyclerView savedDevicesList;

    @BindView(R.id.bt_scan)
    Button scanButton;

    @BindView(R.id.pb_progress)
    ProgressBar progressBar;

    @Inject
    SettingsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        CardioTrackerApp.getAppComponent().inject(this);
        presenter.setView(this);

        scannedDevicesList.setLayoutManager(new LinearLayoutManager(this));
        scannedDevicesList.setAdapter(new DeviceInfoAdapter());

        savedDevicesList.setLayoutManager(new LinearLayoutManager(this));
        savedDevicesList.setAdapter(new DeviceInfoAdapter());
    }

    @Override
    protected void onStop() {
        presenter.stopScanForDevices();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @OnClick(R.id.bt_scan)
    public void onScanClicked(View view) {
        presenter.startScanForDevices();
    }

    @Override
    public void onScannedDevicesDetected(List<SavedBluetoothDevice> devices) {
        updateDeviceList((DeviceInfoAdapter) scannedDevicesList.getAdapter(), devices);
    }

    @Override
    public void onSavedDevicesLoaded(List<SavedBluetoothDevice> devices) {
        updateDeviceList((DeviceInfoAdapter) savedDevicesList.getAdapter(), devices);
    }

    private void updateDeviceList(DeviceInfoAdapter adapter, List<SavedBluetoothDevice> devices) {
        adapter.getDevices().clear();
        adapter.getDevices().addAll(devices);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showScanProgress(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            scanButton.setText(R.string.scanning);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            scanButton.setText(R.string.scan);
        }
    }

    @Override
    public void showDeviceConnected(String address) {
        DeviceInfoAdapter adapter = ((DeviceInfoAdapter)scannedDevicesList.getAdapter());
        DeviceInfoViewHolder viewHolder = adapter.getDeviceInfoViewHolder(address);

        if (viewHolder != null) {
            viewHolder.deviceStatusTextView.setText("Connected");
        }
    }

    @Override
    public void showDeviceConnecting(String address) {
        DeviceInfoAdapter adapter = ((DeviceInfoAdapter)scannedDevicesList.getAdapter());
        DeviceInfoViewHolder viewHolder = adapter.getDeviceInfoViewHolder(address);

        if (viewHolder != null) {
            viewHolder.deviceStatusTextView.setText("Connecting");
        }
    }

    @Override
    public void showDeviceDisconnected(String address) {
        DeviceInfoAdapter adapter = ((DeviceInfoAdapter)scannedDevicesList.getAdapter());
        DeviceInfoViewHolder viewHolder = adapter.getDeviceInfoViewHolder(address);

        if (viewHolder != null) {
            viewHolder.deviceStatusTextView.setText("Disconnected");
        }
    }

    @Override
    public void heartRateUpdated(int data, String address) {
        DeviceInfoAdapter adapter = ((DeviceInfoAdapter)scannedDevicesList.getAdapter());
        DeviceInfoViewHolder viewHolder = adapter.getDeviceInfoViewHolder(address);

        if (viewHolder != null) {
            viewHolder.deviceData.setText("Heart rate: " + data);
        }
    }

    private void onDeviceClicked(SavedBluetoothDevice device) {
        presenter.onDeviceSelected(device);
    }

    class DeviceInfoAdapter extends RecyclerView.Adapter<DeviceInfoViewHolder> {

        private List<SavedBluetoothDevice> devices = new ArrayList<>();

        public List<SavedBluetoothDevice> getDevices() {
            return devices;
        }
        private Map<SavedBluetoothDevice, DeviceInfoViewHolder> itemViewMap = new HashMap<>();

        @Override
        public DeviceInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_item, parent, false);
            return new DeviceInfoViewHolder(view);
        }

        public DeviceInfoViewHolder getDeviceInfoViewHolder(String address) {
            Iterator<Map.Entry<SavedBluetoothDevice, DeviceInfoViewHolder>> iterator = itemViewMap.entrySet().iterator();

            while(iterator.hasNext()) {
                if (iterator.next().getKey().getAddress().equalsIgnoreCase(address)) {
                    return iterator.next().getValue();
                }
            }

            return null;
        }

        @Override
        public void onBindViewHolder(final DeviceInfoViewHolder holder, int position) {
            final SavedBluetoothDevice device = devices.get(position);

            holder.deviceNameTextView.setText(device.getName());
            holder.deviceStatusTextView.setText("Disconnected");
            holder.menuImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeviceDialog(device);
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeviceClicked(device);
                    holder.deviceStatusTextView.setText("Connecting...");
                }
            });

            itemViewMap.put(device, holder);
        }

        @Override
        public int getItemCount() {
            return devices.size();
        }

        @Override
        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
            itemViewMap.clear();
            super.onDetachedFromRecyclerView(recyclerView);
        }

        @Override
        public void onViewDetachedFromWindow(DeviceInfoViewHolder holder) {
            super.onViewDetachedFromWindow(holder);
            removeItemFromList(holder);
        }

        @Override
        public void onViewRecycled(DeviceInfoViewHolder holder) {
            super.onViewRecycled(holder);
            removeItemFromList(holder);
        }

        private void removeItemFromList(DeviceInfoViewHolder holder) {
            Iterator<Map.Entry<SavedBluetoothDevice, DeviceInfoViewHolder>> iterator = itemViewMap.entrySet().iterator();

            while(iterator.hasNext()) {
                if (iterator.next() == holder) {
                    iterator.remove();
                }
            }
        }
    }

    class DeviceInfoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_device_name)
        TextView deviceNameTextView;

        @BindView(R.id.tv_device_status)
        TextView deviceStatusTextView;

        @BindView(R.id.iv_menu)
        ImageView menuImageButton;

        @BindView(R.id.tv_data)
        TextView deviceData;

        public DeviceInfoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void showDeviceDialog(SavedBluetoothDevice device) {
        Toast.makeText(this, "Device Dialog for " + device.getName(), Toast.LENGTH_SHORT).show();
    }
}

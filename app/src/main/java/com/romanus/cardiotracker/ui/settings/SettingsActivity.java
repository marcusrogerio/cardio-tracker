package com.romanus.cardiotracker.ui.settings;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.romanus.cardiotracker.CardioTrackerApp;
import com.romanus.cardiotracker.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by roman on 7/26/16.
 */
public class SettingsActivity extends Activity implements SettingsView {

    @BindView(R.id.rv_devices_list)
    RecyclerView devicesList;

    @Inject
    SettingsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        CardioTrackerApp.getAppComponent().inject(this);
        presenter.setView(this);

        devicesList.setLayoutManager(new LinearLayoutManager(this));
        devicesList.setAdapter(new DeviceInfoAdapter());
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.startScanForDevices();
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

    @Override
    public void onDevicesDetected(Set<BluetoothDevice> devices) {
        DeviceInfoAdapter adapter = (DeviceInfoAdapter) devicesList.getAdapter();

        List<String> devicesName = new ArrayList<>();
        Iterator<BluetoothDevice> iterator = devices.iterator();
        while (iterator.hasNext()) {
            devicesName.add(iterator.next().getName());
        }

        adapter.getDevices().addAll(devicesName);
        adapter.notifyDataSetChanged();
    }

    class DeviceInfoAdapter extends RecyclerView.Adapter<DeviceInfoViewHolder> {

        private List<String> devices = new ArrayList<>();

        public List<String> getDevices() {
            return devices;
        }

        @Override
        public DeviceInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_item, parent, false);
            return new DeviceInfoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DeviceInfoViewHolder holder, int position) {
            String deviceName = devices.get(position);
            holder.textView.setText(deviceName);
        }

        @Override
        public int getItemCount() {
            return devices.size();
        }
    }

    class DeviceInfoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_text)
        TextView textView;

        public DeviceInfoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

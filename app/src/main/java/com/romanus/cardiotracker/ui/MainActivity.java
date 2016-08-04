package com.romanus.cardiotracker.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.romanus.cardiotracker.CardioTrackerApp;
import com.romanus.cardiotracker.R;
import com.romanus.cardiotracker.ui.history.HistoryFragment;
import com.romanus.cardiotracker.ui.settings.SettingsActivity;
import com.romanus.cardiotracker.ui.workout.WorkoutFragment;
import com.romanus.cardiotracker.ui.zones.HRZonesDetialsFragment;
import com.romanus.cardiotracker.util.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 007;
    private static final int REQUEST_ENABLE_BT = 001;
    private static final int REQUEST_ENABLE_LOCATION = 002;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.tl_tabs)
    TabLayout tabLayout;

    @Inject
    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        CardioTrackerApp.getAppComponent().inject(this);

        viewPager.setAdapter(new CTFragmentPageAdapter(getSupportFragmentManager(), this));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkFeatures();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermissions();
        checkBluetoothEnabled();
        checkBluetoothLocationEnabled();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_settings : {
                launchSettingsScreen();
                return true;
            }
            default : return super.onOptionsItemSelected(item);
        }
    }

    private void launchSettingsScreen() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void checkBluetoothEnabled() {
        if (!utils.isBluetoothEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    private void checkBluetoothLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean locationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!locationEnabled) {
            Intent enableLocationIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(enableLocationIntent, REQUEST_ENABLE_LOCATION);
        }
    }

    private void checkFeatures() {
        if (!utils.isBluetoothSupported()) {
            showErrorAndQuit(R.string.bluetooth_not_supported);
        }

        if (!utils.isBLESupported()) {
            showErrorAndQuit(R.string.ble_not_supported);
        }
    }

    private void showErrorAndQuit(int messageResourceId) {
        Toast.makeText(this, messageResourceId, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android M Permission check 
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    this.checkSelfPermission(Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED ||
                    this.checkSelfPermission(Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Permissions");
                builder.setMessage("Please grant location and bluetooth access so this app can detect beacons.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @TargetApi(Build.VERSION_CODES.M)
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.BLUETOOTH,
                                Manifest.permission.BLUETOOTH_ADMIN
                        }, PERMISSION_REQUEST_CODE);
                    }
                });
                builder.show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT : {
                if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(this, "Bluetooth is needed.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } return;

            case REQUEST_ENABLE_LOCATION : {
                if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(this, "Location service is needed.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            for (int i = 0; i < grantResults.length; i ++) {
                if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "All permissions are needed for app to work properly", Toast.LENGTH_SHORT).show();
                    checkPermissions();
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    class CTFragmentPageAdapter extends FragmentPagerAdapter {

        private final int PAGES_COUNT = 3;
        private int tabTitles[] = new int[] { R.string.workout, R.string.history, R.string.hr_details};
        private Context context;

        public CTFragmentPageAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return context.getResources().getString(tabTitles[position]);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch(position) {
                case 0 : {
                    fragment = WorkoutFragment.newInstance();
                } break;
                case 1 : {
                    fragment = HistoryFragment.newInstance();
                } break;
                case 2 : {
                    fragment = HRZonesDetialsFragment.newInstance();
                } break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return PAGES_COUNT;
        }
    }
}

package com.romanus.cardiotracker.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.romanus.cardiotracker.db.beans.SavedBluetoothDevice;

import java.sql.SQLException;

/**
 * Created by rursu on 28.07.16.
 */
public class DataBaseHelper extends OrmLiteSqliteOpenHelper implements DBManager {

    private final static String TAG = DataBaseHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "cardiotracker";
    private static final int DATABASE_VERSION = 1;
    private Dao<SavedBluetoothDevice, String> devicesDao = null;

    @Override
    public Dao<SavedBluetoothDevice, String> getBluetoothDeviceDao() throws SQLException {
        if (devicesDao == null) {
            devicesDao = getDao(SavedBluetoothDevice.class);
        }

        return devicesDao;
    }

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.i(TAG, "DB creation");
            TableUtils.createTable(connectionSource, SavedBluetoothDevice.class);

        } catch (SQLException e) {
            Log.e(TAG, "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(TAG, "DB upgrade");
            TableUtils.dropTable(connectionSource, SavedBluetoothDevice.class, true);

            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e(TAG, "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        super.close();
        devicesDao = null;
    }
}

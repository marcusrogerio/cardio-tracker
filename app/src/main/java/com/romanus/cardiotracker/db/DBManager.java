package com.romanus.cardiotracker.db;

import com.j256.ormlite.dao.Dao;
import com.romanus.cardiotracker.db.beans.SavedBluetoothDevice;

import java.sql.SQLException;

/**
 * Created by rursu on 28.07.16.
 */
public interface DBManager {
    Dao<SavedBluetoothDevice, String> getBluetoothDeviceDao() throws SQLException;
}

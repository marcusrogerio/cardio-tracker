package com.romanus.cardiotracker.db.beans;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by rursu on 28.07.16.
 */
@DatabaseTable(tableName = "bluetooth_device")
public class SavedBluetoothDevice {

    public static final String ADDRESS_FIELD = "address";
    public static final String NAME_FIELD = "name";
    public static final String TYPE_FIELD = "type";

    @DatabaseField(id = true, columnName = ADDRESS_FIELD)
    private String address;

    @DatabaseField(columnName = NAME_FIELD)
    private String name;

    @DatabaseField(columnName = TYPE_FIELD)
    private int type;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SavedBluetoothDevice) {
            SavedBluetoothDevice device = (SavedBluetoothDevice) o;
            return device.getAddress().equalsIgnoreCase(getAddress());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return getAddress().hashCode();
    }
}

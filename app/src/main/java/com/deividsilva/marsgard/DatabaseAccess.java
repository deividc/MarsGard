package com.deividsilva.marsgard;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseAccess {
    private AssetDatabaseOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;

    public DatabaseAccess(Context context){
        this.openHelper = new AssetDatabaseOpenHelper(context);
        db = openHelper.openDatabase();
    }

    private static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public String get_plant_registry_key_by_plant_name(String plant_name) {
        Cursor c = db.rawQuery("SELECT Registry_Number from Plant_Index where Common_Name = '" + plant_name + "'", new String[]{});
        StringBuffer buffer = new StringBuffer();
        while (c.moveToNext()) {
            String plant_registry_key = c.getString(0);
            buffer.append(""+plant_registry_key);

        }
        return buffer.toString();
    }

    public String get_garden_key_by_serial_number(String serial_number) {
        Cursor c = db.rawQuery("SELECT Garden_key from Garden_Register where Serial_Number = '" + serial_number + "'" , new String[]{});
        StringBuffer buffer = new StringBuffer();
        while (c.moveToNext()) {
            String plant_key = c.getString(0);
            buffer.append(""+plant_key);

        }
        return buffer.toString();
    }
}

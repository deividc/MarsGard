package com.deividsilva.marsgard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class AssetDatabaseOpenHelper {
    private static String DB_PATH = "";
    private static final String DB_NAME = "marsgarddb.db";

    private Context context;

    public AssetDatabaseOpenHelper(Context context) {
        this.context = context;
        DB_PATH = context.getFilesDir().getAbsolutePath() + "/databases/";
    }

    public SQLiteDatabase openDatabase() {
        File dbFile = context.getDatabasePath(DB_NAME);
        dbFile.getParentFile();
        Toast.makeText(context, "I am here", Toast.LENGTH_LONG).show();
        if (!dbFile.exists()) {
            try {
                copyDataBase(dbFile);
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }
        Toast.makeText(context, dbFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READONLY);
    }

    private void copyDataBase(File dbFile) throws IOException {

        Log.i("Database",
                "New database is being copied to device!");
        byte[] buffer = new byte[1024];
        OutputStream myOutput = null;
        int length;
        // Open your local db as the input stream
        InputStream myInput = null;

        try {
            myInput = context.getAssets().open(DB_NAME);
            // transfer bytes from the inputfile to the
            // outputfile
            myOutput = new FileOutputStream(DB_PATH + DB_NAME);
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.close();
            myOutput.flush();
            myInput.close();
            Log.i("Database",
                    "New database has been copied to device!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
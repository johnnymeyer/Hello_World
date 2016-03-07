package com.cs246.johnmeyer.hello;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.Locale;

/**
 * Created by edoyle on 2/29/16.
 */
public class DBInfo extends SQLiteOpenHelper {
    private static String Database_path = null;
    private static String DATABASE_NAME = "DBPages.db";
    private static final int SCHEMA_VERSION = 1;
    public SQLiteDatabase dbSqlite;
    private final Context myContext;

    public DBInfo(Context context){
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    public void createDatabase() {
        createDB();
    }

    private void createDB() {
        boolean dbExist = DBExists();

        if (!dbExist) {

            // this method will create a empty database
            // we need this so we can override our database with this empty one
            this.getReadableDatabase();

            // copy over the database
            copyDBFromResource();
        }
    }

    private boolean DBExists() {

        SQLiteDatabase db = null;

        try {
            String databasePath = Database_path + DATABASE_NAME;
            db = SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE);
            db.setLocale(Locale.getDefault());
            //db.setLockingEnabled(true);
            db.setVersion(1);
        }
        catch (SQLiteException e) {
            Log.e("SqlHelper", "database not found");
        }

        if (db != null) {
            db.close();
        }

        return db != null ? true : false;
    }

    private void copyDBFromResource() {

        InputStream inputStream = null;
        OutputStream outputStream = null;
        String dbFilePath = Database_path + DATABASE_NAME;

        try {
            inputStream = myContext.getAssets().open(DATABASE_NAME);

            outputStream = new FileOutputStream(dbFilePath);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0 , length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }
        catch (IOException e) {
            throw new Error("Problem copying database from resource file");
        }
    }
}

package com.cs246.johnmeyer.hello;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.net.MalformedURLException;

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
}

package com.cs246.johnmeyer.hello;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.Touch;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    private static String DB_PATH = "file:///android_asset/raw/sample/DBPages.db";
    private static String DB_NAME ="DBPages.db";
    public static DBInfo dbInfo = null;
    //A good practice is to define database field names as constants
    private static final String TABLE_NAME = "Pages";
    private static final String ID = "_id";
    private static final String TITLE = "Title";
    private static final String NEXT = "Next_Page";
    private static final String PREV = "Prev_Page";
    private static final String INFO =  "Info";
    private static final String PIC = "Picture";


    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ////
        LoadDataBase dbOpenHelper = new LoadDataBase(this, DB_NAME);
        database = dbOpenHelper.openDataBase();
        ////
        setContentView(R.layout.activity_main);
        ((ImageView)findViewById(R.id.imageView)).setImageResource(R.drawable.glewfrontpage);
        fillFreinds();
    //    dbInfo = new DBInfo(this);
     //   dbInfo.createDatabase();


    }

    private void fillFreinds() {
        Log.i(TAG, "We are reaching the database query.");
        Cursor friendCursor = database.query(TABLE_NAME, new String[]{ID,
                TITLE}, null, null, null, null, TITLE);

        if (friendCursor == null || friendCursor.equals(" "))
        {
            Log.e(this.getClass().toString(), "Error: Query Sting is null.");
        }
        friendCursor.moveToFirst();
        Toast.makeText(this, "hey",
                Toast.LENGTH_SHORT);
        if(!friendCursor.isAfterLast()) {
            do {
                String name = friendCursor.getString(1);
                System.out.println("Here: "  + name);
                Toast.makeText(this, name,
                        Toast.LENGTH_SHORT).show();
            } while (friendCursor.moveToNext());
        }
        friendCursor.close();
    }

    private boolean checkDataBase()
    {
        Log.i(TAG, "DataBase is being created.");
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {

            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                System.out.println("Inside!");
                if (Math.abs(deltaX) > MIN_DISTANCE)
                {

                    // Left to Right swipe action
                    if (x2 > x1)
                    {
                        x1 = x2;
                        Toast output = Toast.makeText(this, "Created By:\nWellesley Shumway"
                                        + "\nKlenton Stone"
                                        + "\nJohn Meyer"
                                        + "\nEdward Doyle",
                                Toast.LENGTH_SHORT);
                        output.setGravity(Gravity.CENTER, 0, 200);
                        output.show();
                    }

                    // Right to left swipe action
                    else if (x2 < x1)
                    {
                        x1 = x2;
                        startActivity(new Intent(MainActivity.this, TableOfContents.class));
                    }

                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    public void tableOfContents(View v) {
        startActivity(new Intent(MainActivity.this, TableOfContents.class));
    }
    //////////

 //   private ListView listView;
  //  private ArrayList friends;
//

//


    //Extracting elements from the database





}

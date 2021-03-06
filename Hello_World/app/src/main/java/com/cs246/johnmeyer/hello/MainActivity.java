package com.cs246.johnmeyer.hello;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    private static String DB_NAME ="DBPages.db";
    //A good practice is to define database field names as constants
    public static final String TABLE_NAME = "Pages";
    public static SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ////
        LoadDataBase dbOpenHelper = new LoadDataBase(this, DB_NAME);
        database = dbOpenHelper.openDataBase();
        ////
        setContentView(R.layout.activity_main);
        ((ImageView)findViewById(R.id.imageView)).setImageResource(R.drawable.glewfrontpage);

    }


    /**
     * Function onTouchEvent
     *
     * This function handles the logic for swiping on the screen.
     * This is used for swiping to switch between pages.
     * @param event
     * @return super.onTouchEvent
     */
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
        Log.i(this.getClass().toString(), "Loaded Table of Contents");
    }
}

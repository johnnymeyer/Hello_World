package com.cs246.johnmeyer.hello;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.Touch;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((ImageView)findViewById(R.id.imageView)).setImageResource(R.drawable.glewfrontpage);
/////I changed this

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
    // very very dark gray
    // hello
    // Return fire
    // adding above this
// whatever I want
//Batman for the win!!!
    // Klentons comments
}

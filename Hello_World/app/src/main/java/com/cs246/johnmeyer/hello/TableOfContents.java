package com.cs246.johnmeyer.hello;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableOfContents extends AppCompatActivity {
    final public String [] CONTENT = {"Loops", "\t\t\t\t\t- For Loops", "\t\t\t\t\t- While Loops",
            "\t\t\t\t\t- Do-While Loops", "Functions", "\t\t\t\t\t- Calling a Function",
            "\t\t\t\t\t- Pass By Reference", "\t\t\t\t\t- Pass By Value", "\t\t\t\t\t- Why Functions?"};
    private List <String> layout = new ArrayList<>(Arrays.asList(CONTENT));
    private ArrayAdapter <String> adapter = new ArrayAdapter<>(TableOfContents.this, android.R.layout.simple_list_item_1, layout);
    private int nextPageIndicator = 2;
    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    public List<String> getLayout(){
        return layout;
    }

    public ArrayAdapter <String> getAdapter()
    {
        return adapter;
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
                if (Math.abs(deltaX) > MIN_DISTANCE)
                {

                    // Left to Right swipe action
                    if (x2 > x1)
                    {
                        x1 = x2;
                        navBack();
                    }

                    // Right to left swipe action
                    else if (x2 < x1)
                    {
                        System.out.println(getNextPageIndicator());
                        x1 = x2;
                        //startActivity(new Intent(MainActivity.this, TableOfContents.class));
                    }

                }
                break;
        }
        return super.onTouchEvent(event);
    }
    public int getNextPageIndicator(){
        return nextPageIndicator;
    }

    public void navBack()
    {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_of_contents);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(

                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String listPosition = String.valueOf(parent.getItemAtPosition(position));
                        listPosition = listPosition.replace("\t\t\t\t\t- ", "");
                        (Toast.makeText(getApplicationContext(), listPosition, Toast.LENGTH_SHORT)).show();
                    }
                }
        );
    }

}

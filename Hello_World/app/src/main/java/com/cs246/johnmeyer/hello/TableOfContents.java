package com.cs246.johnmeyer.hello;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableOfContents extends AppCompatActivity {
  private List <String> layout;
    private ArrayAdapter <String> adapter;
    private int nextPageIndicator = 2;
    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    public static String pageNumber = null;

/*
    public List<String> getLayout(){
        return layout;
    }
*/
   /* public ArrayAdapter <String> getAdapter()
    {
        return adapter;
    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId())
        {
            case R.id.action_home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==2){
            finish();
        }
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolss);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.BLACK);
        ListView listView = (ListView) findViewById(R.id.listView);
        Cursor friendCursor = MainActivity.database.query(MainActivity.TABLE_NAME, new String[]
                {"Info"}, "_id='2'", null, null, null, null);
        friendCursor.moveToFirst();
        String content = friendCursor.getString(0);
        layout = new ArrayList<>(Arrays.asList(content.split("\\|")));
        adapter = new ArrayAdapter<>(TableOfContents.this, android.R.layout.simple_list_item_1, layout);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(

                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String listPosition = String.valueOf(parent.getItemAtPosition(position));
                        listPosition = listPosition.trim().replace("- ", "");
                        loadPage(listPosition);
                    }
                }
        );
    }
    void loadPage(String pageName){
        String query = "Title=\"" + pageName.trim() + "\"";
        Cursor friendCursor = MainActivity.database.query(MainActivity.TABLE_NAME, new String[]
                {"_id"}, query, null, null, null, null);
        friendCursor.moveToFirst();
        String pageNum = friendCursor.getString(0);
        pageNumber = pageNum;
        startActivityForResult(new Intent(TableOfContents.this, Page.class), 1);
    }

}

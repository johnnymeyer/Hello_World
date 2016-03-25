package com.cs246.johnmeyer.hello;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
    String content;
    String displayC;

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
        Cursor friendCursor;
        content = "";
        try {
            do {
                friendCursor = MainActivity.database.query(MainActivity.TABLE_NAME, new String[]
                        {"Info"}, "_id='2'", null, null, null, null);
                if (friendCursor != null) {
                    friendCursor.moveToFirst();
                    content = friendCursor.getString(0);
                }
            } while (friendCursor == null);
            friendCursor.close();
        }
        catch (NullPointerException e){
            Log.d("onCreate TOC", "Error Loading");
        }
        displayC = content;
        layout = new ArrayList<>(Arrays.asList((displayC.replace("- ", "\t\t\t\t" )).split("\\|")));
        adapter = new ArrayAdapter<>(TableOfContents.this, android.R.layout.simple_list_item_1, layout);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(

                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String listPosition = String.valueOf(parent.getItemAtPosition(position));
                        listPosition = listPosition.trim().replace("- ", "");
                        if (databaseContains("Pages", listPosition))
                            loadPage(listPosition);
                        else
                            toggleItem(listPosition);
                    }
                }
        );
    }

    public void toggleItem (String item) {
        String temp = content;
        int start;
        int end;
        int prev;
        boolean wordFound = false;
        String word;
        if (item.contains("-")) {
            start = displayC.indexOf(item) + (item.length());
            word = "";
            end = start + 1;
            prev = end;
            while (end < displayC.length()) {
                if (displayC.charAt(end) != '|') {
                    word += displayC.charAt(end);
                }
                else {

                    if (!databaseContains("Pages", (word.trim().replace("- ", "")).replace("+", "-"))) {
                        end = prev;
                        break;
                    }
                    else {
                        wordFound = true;
                        prev = end;
                        word = "";
                    }
                }
                ++end;
            }

            ListView listView = (ListView) findViewById(R.id.listView);
            displayC = displayC.replace(displayC.substring(start + 1, (end == displayC.length() || !wordFound) ? end : end + 1), "");
            displayC = displayC.substring(0, start - 1) + "+" + displayC.substring(start);
            layout = new ArrayList<>(Arrays.asList((displayC.replace("- ", "\t\t\t\t" )).split("\\|")));
            adapter = new ArrayAdapter<>(TableOfContents.this, android.R.layout.simple_list_item_1, layout);
            listView.setAdapter(adapter);
        }
        else if (item.contains("+")) {
            int startO = displayC.indexOf(item) + (item.length());
            displayC = displayC.substring(0, startO - 1) + "-" + displayC.substring(startO);
            start = temp.indexOf(item.replace("+", "-")) + (item.length());
            word = "";
            end = start + 1;

            prev = end;
            while (end < temp.length()) {
                if (temp.charAt(end) != '|') {
                    word += temp.charAt(end);
                }
                else {

                    if (!databaseContains("Pages", (word.trim().replace("- ", "")).replace("+", "-"))){
                        end = prev;
                        break;
                    }
                    else {
                        prev = end;
                        word = "";
                    }
                }
                ++end;
            }
         ListView listView = (ListView) findViewById(R.id.listView);
         displayC = displayC.substring(0, startO) + temp.substring(start, end) + displayC.substring(startO);//(end == temp.length()? end : end + 1)) +
            layout = new ArrayList<>(Arrays.asList((displayC.replace("- ", "\t\t\t\t" )).split("\\|")));
            adapter = new ArrayAdapter<>(TableOfContents.this, android.R.layout.simple_list_item_1, layout);
            listView.setAdapter(adapter);

        }
     //   Toast toast = Toast.makeText(getApplicationContext(), temp.charAt(start) + "-" + temp.charAt(end), Toast.LENGTH_LONG);
     //   toast.show();
    }

    public boolean databaseContains(String tableName, String element){
        String query = "Title=\"" + element.trim() + "\"";
        Cursor friendCursor = MainActivity.database.query(tableName, new String[]
                {"Info"}, query, null, null, null, null);
        if(friendCursor == null)
            return false;
        else if (friendCursor.getCount() < 1) {
            friendCursor.close();
            return false;
        }
        else {
            friendCursor.close();
            return true;
        }
    }

    void loadPage(String pageName){
        String query = "Title=\"" + pageName.trim() + "\"";
        Cursor friendCursor;
            friendCursor = MainActivity.database.query(MainActivity.TABLE_NAME, new String[]
                    {"_id"}, query, null, null, null, null);
            friendCursor.moveToFirst();

        String pageNum = friendCursor.getString(0);
        friendCursor.close();
        pageNumber = pageNum;
        startActivityForResult(new Intent(TableOfContents.this, Page.class), 1);
    }

}

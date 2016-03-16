package com.cs246.johnmeyer.hello;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class Page extends AppCompatActivity {
    private String title;
    private String content;
    private String description;
    private PageData data;
    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = new PageData();
        setContentView(R.layout.activity_page);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);
        loadPage(TableOfContents.pageNumber);
       // TextView contentCont = (TextView) findViewById(R.id.textView3);
       //         ((ScrollView) findViewById(R.id.scrollView)).addView(contentCont);
    }

    public void loadPage(String pageNum){
        if (pageNum.equals("0"))
            finish();
        else {
            String query = "_id=\"" + pageNum.trim() + "\"";
            Cursor friendCursor = MainActivity.database.query(MainActivity.TABLE_NAME, new String[]
                    {"Title", "Info", "Prev_Page", "Next_Page"}, query, null, null, null, null);
            friendCursor.moveToFirst();
            setMyTitle(friendCursor.getString(0));
            setContent(friendCursor.getString(1));
            friendCursor.getString(2);
            setPrevPage(friendCursor.getString(2));
            setNextPage(friendCursor.getString(3));
        }
    }

    public void goBack(View v){
        loadPage(data.getNav_prev());
    }
    public void goNext(View v){
        loadPage(data.getNav_next());
    }
    public void setPrevPage(String prev){
        data.setNav_prev(prev);
    }

    public void setNextPage(String next){
     data.setNav_next(next);
    }
    public void tableOfContents(View v){
        finish();
    }

    public String getMyTitle(){
        return title;
    }

    public void setMyTitle(String myTitle) {
        ((TextView)findViewById(R.id.textView4)).setText(myTitle);
        title = myTitle;

    }

    public String getContent(){
        return content;
    }

    public void setContent(String myContent) {
        ((TextView)findViewById(R.id.textView3)).setText(myContent);
        content = myContent;
    }

    public void fetchContent() {

        if (title != null) {
            String newContent = null;
            //set string to material
            setContent(newContent);
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
                        loadPage(data.getNav_prev());
                        // Toast.makeText(this, "Left to Right swipe [Next]", Toast.LENGTH_SHORT).show ();
                    }

                    // Right to left swipe action
                    else
                    {
                        x1 = x2;
                        loadPage(data.getNav_next());
                        // Toast.makeText(this, "Right to Left swipe [Previous]", Toast.LENGTH_SHORT).show ();
                    }

                }
                else
                {
                    // consider as something else - a screen tap for example
                }
                break;
        }
        return super.onTouchEvent(event);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // Toast.makeText(this, "" + x1 + " " + x2, Toast.LENGTH_SHORT).show();
        onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    };
}

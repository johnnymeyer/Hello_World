package com.cs246.johnmeyer.hello;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class Page extends AppCompatActivity {
    private String title;
    private String content;
    private PageData data;
    private float x1,x2;
    static final int MIN_DISTANCE = 450;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = new PageData();
        setContentView(R.layout.activity_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        loadPage(TableOfContents.pageNumber);
       // TextView contentCont = (TextView) findViewById(R.id.textView3);
       //         ((ScrollView) findViewById(R.id.scrollView)).addView(contentCont);
        Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );

        // button for left arrow
        Button button5 = (Button)findViewById(R.id.button5);
        button5.setTypeface(font);

        // button for table of contents
        Button button4 = (Button)findViewById(R.id.button4);
        button4.setTypeface(font);

        // button for right arrow
        Button button3 = (Button)findViewById(R.id.button3);
        button3.setTypeface(font);

    }

    public void setText(final Spannable span, final int start, final int end){
        if(databaseContains("Definitions", span.toString().substring(start, end))) {
            span.setSpan(new ClickableSpan() {
                public String name = span.toString().substring(start, end);

                @Override
                public void onClick(View v) {
                    popUpDefinition(name);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setColor(Color.BLUE);
                    ds.bgColor = 0xffeeeeee;
                    // ds.setUnderlineText(false);
                }
            }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    public boolean databaseContains(String tableName, String element){
        String query = "Word=\"" + element.trim().toLowerCase() + "\"";
        Cursor friendCursor = MainActivity.database.query(tableName, new String[]
                {"Definition"}, query, null, null, null, null);
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

    public String getDefinition(String tableName, String word){
        String query = "Word=\"" + word.trim().toLowerCase() + "\"";
        Cursor friendCursor = MainActivity.database.query(tableName, new String[]
                {"Definition"}, query, null, null, null, null);
        friendCursor.moveToFirst();
        String def = friendCursor.getString(0);
        friendCursor.close();
        return def;
    }

    public void popUpDefinition(String definition) {
        new AlertDialog.Builder(Page.this)
                .setTitle("Definition")
                .setMessage(getDefinition("Definitions", definition))
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Whatever...
                    }
                }).create().show();
    }

    public void setClickable(String text) {
        TextView tv = (TextView)findViewById(R.id.textView3);
        Spannable span = Spannable.Factory.getInstance().newSpannable(text);
        boolean newWord = true;
        int beginning = 0;
        for (int i = 0; i < text.length(); ++i) {
            if (text.charAt(i) != ' ' && text.charAt(i) != '<' && text.charAt(i) != '>') {
                if (!newWord) {
                    newWord = true;
                    beginning = i;
                }
            }
            else if (newWord) {
                newWord = false;
                setText(span, beginning, i);
            }
        }
        setText(span, beginning, text.length());
        tv.setText(span);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId())
        {
            case R.id.action_home:
               // startActivity(new Intent(this, MainActivity.class));
                setResult(2);
                finish();
                return true;
            case R.id.action_gloss:
                startActivityForResult(new Intent(Page.this, Glossary.class), 1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * LoadPage- This function will load the page content by querying out to the database
     * and pulling into our app the needed material.
     *
     * @param pageNum -This variable will give the page number to the LoadPage function so it
     *                can load the correct page.
     */
    public void loadPage(String pageNum){
        if (pageNum.equals("0"))
            finish();
        else {
            String query = "_id=\"" + pageNum.trim() + "\"";
            Cursor friendCursor = MainActivity.database.query(MainActivity.TABLE_NAME, new String[]
                    {"Title", "Info", "Prev_Page", "Next_Page", "Picture"}, query, null, null, null, null);
            friendCursor.moveToFirst();
            setMyTitle(friendCursor.getString(0));
            setContent(friendCursor.getString(1));
            friendCursor.getString(2);
            setPrevPage(friendCursor.getString(2));
            setNextPage(friendCursor.getString(3));
            String gif = friendCursor.getString(4);
            if (gif != null) {
                try {
                    int lookup = getApplicationContext().getResources().getIdentifier(gif, "drawable",
                            getApplicationContext().getPackageName());
                    // imageView.setImageResource(id);
                    GifDrawable gifDrawable = new GifDrawable(getResources(), lookup); // R.drawable.whileloop);
                    ((GifImageView) findViewById(R.id.gifView)).setImageDrawable(gifDrawable);
                }
                catch (IOException e) {
                    //
                }
            }
            else {
                ((GifImageView) findViewById(R.id.gifView)).setImageDrawable(null);

            }
            friendCursor.close();
        }
    }


    /**
     * calls the load function for the previous page. This is used by the navigate back button.
     * @param v the view that has been clicked.
     */
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

    /**
     * getMyTitle gives the title of the page object
     * @return returns the title of a given page
     */
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
        setClickable(myContent);
        //((TextView)findViewById(R.id.textView3)).setText(myContent);
        content = myContent;
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
              /*  else
                {
                    // consider as something else - a screen tap for example
                }*/
                break;
        }
        return super.onTouchEvent(event);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // Toast.makeText(this, "" + x1 + " " + x2, Toast.LENGTH_SHORT).show();
        onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }
}

package com.cs246.johnmeyer.hello;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Page extends AppCompatActivity {
    private String title;
    private String content;
    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public String getMyTitle(){
        return title;
    }

    public void setMyTitle(String myTitle) {
        title = myTitle;

    }

    public String getContent(){
        return content;
    }

    public void setContent(String myContent) {
        content = myContent;
    }

    public void fetchContent() {

        if (title != null) {
            String newContent = null;
            //set string to material
            setContent(newContent);
        }
    }
}

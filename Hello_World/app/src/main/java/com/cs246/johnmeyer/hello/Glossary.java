package com.cs246.johnmeyer.hello;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Glossary extends AppCompatActivity {
    String[] items;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    ListView listView;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glossary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        //itemListGenerator();
        listView = (ListView)findViewById(R.id.listview);
        editText = (EditText) findViewById(R.id.txtsearch);
        initList();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    //resets listview
                    initList();
                } else {
                    //search
                    searchItems(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gloss, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId())
        {
            case R.id.action_back:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void searchItems (String text){
        for (String item: items){
            if(!item.contains(text.toLowerCase()))
                listItems.remove(item);
            else if (!listItems.contains(item)) {
                listItems.add(item);
                Collections.sort(listItems);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void itemListGenerator() {
        String itemString = "";
        String name;
        Cursor friendCursor = MainActivity.database.rawQuery("SELECT Word FROM Definitions", null);
        friendCursor.moveToFirst();
        while (friendCursor.isAfterLast() == false) {
            name = friendCursor.getString(0);
            friendCursor.moveToNext();
            itemString += name + "|";
        }
       // items =  new String[]{"England", "MA", "Ed"};
       items = itemString.split("\\|");
    }

    public void initList(){
        //items = new String[]{"England", "MA", "Ed"};
        itemListGenerator();
        listItems = new ArrayList<>(Arrays.asList(items));
        Collections.sort(listItems);
        adapter = new ArrayAdapter<>(Glossary.this, R.layout.list_item, R.id.txtitem, listItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(

                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String listPosition = String.valueOf(parent.getItemAtPosition(position));
                        popUpDefinition(listPosition);

                    }
                }
        );
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
        new AlertDialog.Builder(Glossary.this)
                .setTitle(definition)
                .setMessage(getDefinition("Definitions", definition))
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Whatever...
                    }
                }).create().show();
    }


}

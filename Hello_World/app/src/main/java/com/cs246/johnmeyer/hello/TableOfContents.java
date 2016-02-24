package com.cs246.johnmeyer.hello;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
    private List <String> layout;
    public ArrayAdapter <String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_of_contents);
        layout = new ArrayList<>(Arrays.asList(CONTENT));
        adapter = new ArrayAdapter<>(TableOfContents.this, android.R.layout.simple_list_item_1, layout);
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

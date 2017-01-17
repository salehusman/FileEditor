package com.example.zozombie.fileeditor;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final FileEditor2 editor = new FileEditor2("folder", FileEditor2.INTERNAL_STORAGE, this);
        final List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        

        editor.askForPremmisions();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        editor.writeList("test.txt", list, true);

                for (File file : editor.getFileList("")) {

                    Log.d(TAG, "File: " + file.getAbsolutePath());
                    for (String s : editor.readList(file.getName()))
                        Log.d(TAG, "Content: " + s);

                    Log.d(TAG, "File: " + file.getAbsolutePath());

                    Log.d(TAG, "Content: " + editor.readString(file.getName()));

                }

                editor.createEmptyFile("file1.txt");
                editor.createEmptyFile("file2.txt");
                editor.createEmptyFile("file3.txt");

                editor.rename("file1.txt", "file1-copy.txt");
                editor.delete("file3.txt");
            }
        });
    }
}

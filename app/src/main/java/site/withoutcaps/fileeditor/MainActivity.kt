package site.withoutcaps.fileeditor

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toolbar

import java.io.File
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    protected fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)


        val list = ArrayList<String>()
        list.add("a")
        list.add("b")
        list.add("c")
        list.add("d")


        Toast.makeText(this, "Click FAB to see the action and check ADB console", Toast.LENGTH_LONG).show()

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener(View.OnClickListener {
            Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show()
            FileEditor.writeList("test.txt", list, true)

            FileEditor.writeList("writingTest.txt", list, false)
            for (file in FileEditor.getFileList("")) {

                Log.d(TAG, "File: " + file.absolutePath)
                for (s in FileEditor.readList(file.name))
                    Log.d(TAG, "readList: " + s)

                Log.d(TAG, "readString: " + FileEditor.readString(file.name))

            }

            FileEditor.createEmptyFile("file1.txt")
            FileEditor.createEmptyFile("file2.txt")
            FileEditor.createEmptyFile("file3.txt")

            FileEditor.rename("file1.txt", "file1 - renamed.txt")
            FileEditor.delete("file3.txt")
        })
    }

    companion object {
        private val TAG = "MainActivity"
    }
}

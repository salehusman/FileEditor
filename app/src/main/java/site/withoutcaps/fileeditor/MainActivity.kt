package site.withoutcaps.fileeditor

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import site.withoutcaps.lessonsschedule.FileEditor
import java.io.File
import java.util.*

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private var mRootFilesPath = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mRootFilesPath = filesDir.toString() + File.separator
        val list = ArrayList<String>()
        list.add("a")
        list.add("b")
        list.add("c")
        list.add("d")

        Toast.makeText(this, "NOTE: FAB Does stuff. Check ADB console", Toast.LENGTH_LONG).show()

        fab.setOnClickListener(View.OnClickListener {
            Toast.makeText(applicationContext, "Click", Toast.LENGTH_SHORT).show()
            FileEditor.writeList(mRootFilesPath + "test.txt", list, true)

            FileEditor.writeList(mRootFilesPath + "writingTest.txt", list, false)
            for (file in FileEditor.getFileList(mRootFilesPath)) {
                Log.d(TAG, "File: " + file.absolutePath)
                for (s in FileEditor.readList(mRootFilesPath + file.name))
                    Log.d(TAG, "readList: " + s)

                Log.d(TAG, "readString: " + FileEditor.readString(mRootFilesPath + file.name))
            }

            FileEditor.createIfDosentExist(mRootFilesPath + "file1.txt")
            FileEditor.createIfDosentExist(mRootFilesPath + "file2.txt")
            FileEditor.createIfDosentExist(mRootFilesPath + "file3.txt")

            FileEditor.zipFile(mRootFilesPath + "file3.txt")
            FileEditor.rename(mRootFilesPath + "file1.txt", mRootFilesPath + "file1 - renamed.txt")
            FileEditor.delete(mRootFilesPath + "file3.txt")
        })
    }
}

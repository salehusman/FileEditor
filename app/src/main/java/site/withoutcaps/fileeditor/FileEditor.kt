package site.withoutcaps.fileeditor

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileWriter
import java.io.IOException
import java.io.InputStreamReader
import java.util.ArrayList
import java.util.Arrays

class FileEditor(folderName: String, storage: Int, private val mContext: Context) {
    private var mPath: File? = null


    init {
        setPath(folderName, storage)
    }

    /**
     * Method that sets "default path" if you will.

     * @param path    name of the file OR path to the subdir(ex: Pictures/AwsomePic.jpg).
     * *
     * @param storage APP_PRIVATE_STORAGE or INTERNAL_STORAGE or CUSTOM_PATH_STORAGE.
     */
    fun setPath(path: String, storage: Int) {
        when (storage) {
            APP_PRIVATE_STORAGE -> this.mPath = File(mContext.filesDir.toString() + File.separator + path)
            INTERNAL_STORAGE -> this.mPath = Environment.getExternalStoragePublicDirectory(path)
            CUSTOM_PATH_STORAGE -> this.mPath = File(path)
        }
        Log.d(TAG, "mPath: " + mPath!!)
        this.mPath!!.mkdirs()
    }

    /**
     *
     *
     * Simple method that asks for WRITE_EXTERNAL_STORAGE premmision. (Required for Android 6.0 (API Level 23) and up)
     *
     *
     *
     * NOTE: This method runs ASYNCRONOUSLY!!!!!!!!!!!!!! Check link below (2017-01-17 edit: it looks like this is really simple and dirty implementation of premmisions, quite a few extra lines required....)
     *
     * For more information see: [Requesting Permissions at Run Time](https://developer.android.com/training/permissions/requesting.html)
     */
    fun askForPremmisions() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mContext as Activity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE)
        }
    }

    /**
     * Writes List of strings to a file. (Each string in separate line)

     * @param path      location of file.
     * *
     * @param data      List that needs to be saved from this cruel world of RAM. :D
     * *
     * @param overwrite if passed value is 'true' - file will get overwrited with specified data, appends otherwise.
     */
    fun writeList(path: String, data: List<*>, overwrite: Boolean?) {
        try {
            val writer = BufferedWriter(FileWriter(mPath.toString() + File.separator + path, (!overwrite)!!))
            for (line in data)
                writer.write(line.toString() + System.getProperty("line.separator"))
            writer.flush()
            writer.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    /**
     * @param path      location of file.
     * *
     * @param data      String that needs to be saved from this cruel world of RAM. :D
     * *
     * @param overwrite if passed value is 'true' - file will get overwrited with specified data, appends otherwise.
     */
    fun writeString(path: String, overwrite: Boolean?, addNewLine: Boolean?, vararg data: String) {
        try {
            val writer = BufferedWriter(FileWriter(mPath.toString() + File.separator + path, (!overwrite)!!))
            for (s in data)
                writer.write(s + if (addNewLine) System.getProperty("line.separator") else "")
            writer.flush()
            writer.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    /**
     * Reads file and returns List with its contents.

     * @param path location of file.
     * *
     * @return List with file contents.(one line = one list item)
     * *
     * @see File
     */
    fun readList(path: String): List<String> {
        val fileContent = ArrayList<String>()
        try {
            val br = BufferedReader(InputStreamReader(FileInputStream(mPath.toString() + File.separator + path)))
            var strLine: String? = null
            while ((strLine = br.readLine()) != null)
                fileContent.add(strLine)
            br.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return fileContent
    }

    /**
     * Reads file and returns String with its contents.

     * @param path location of file.
     * *
     * @return String with file contents.
     * *
     * @see File
     */
    fun readString(path: String): String {
        val builder = StringBuilder()
        try {
            val br = BufferedReader(InputStreamReader(FileInputStream(mPath.toString() + File.separator + path)))
            var strLine: String? = null
            while ((strLine = br.readLine()) != null)
                builder.append(strLine!! + System.getProperty("line.separator"))
            br.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return builder.toString()
    }

    /**
     * @param name name of file.
     * *
     * @return True if file exists, false otherwise.
     * *
     * @see File
     */
    fun exists(name: String): Boolean {
        return File(mPath, name).exists()
    }

    /**
     * Creates empty file

     * @param path name of a file.
     * *
     * @return true if the file has been created, false if it already exists.
     * *
     * @see File
     */
    fun createEmptyFile(path: String): Boolean {
        if (!exists(path))
            try {
                return File(mPath, path).createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        return false
    }

    fun createIfDosentExist(fileName: String, data: List<String>?) {
        if (!exists(fileName))
            if (data != null)
                writeList(fileName, data, true)
            else
                createEmptyFile(fileName)
    }

    fun createIfDosentExist(fileName: String, vararg data: String) {
        createIfDosentExist(fileName, Arrays.asList(*data))
    }

    /**
     * Renames file

     * @param from old file name.
     * *
     * @param to   new file name.
     * *
     * @return true if the file has been renamed.
     * *
     * @see File
     */
    fun rename(from: String, to: String): Boolean {
        return File(mPath, from).renameTo(File(mPath, to))
    }

    /**
     * Returns a list of File objects.

     * @param path folder name, or empty string for current folder.
     * *
     * @return the file/folder at the specified location.
     * *
     * @see File
     */
    fun getFileList(path: String): Array<File> {
        return File(mPath, path).listFiles()
    }

    /**
     * Returns specified File object.

     * @param path location of file.
     * *
     * @return the file/folder at the specified location.
     * *
     * @see File
     */
    fun getFile(path: String): File {
        return File(mPath, path)
    }

    /**
     * Deletes a File or folder.

     * @param path location of file/folder.
     * *
     * @return true if specified file was deleted, false otherwise.
     * *
     * @see File
     */
    fun delete(path: String): Boolean {
        return File(mPath, path).delete()
    }

    companion object {
        private val TAG = "FileEditor"
        val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0
        val APP_PRIVATE_STORAGE = 1
        val INTERNAL_STORAGE = 2
        val CUSTOM_PATH_STORAGE = 3
    }
}

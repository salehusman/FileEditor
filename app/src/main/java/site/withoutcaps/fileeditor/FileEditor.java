package site.withoutcaps.fileeditor;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class FileEditor {

    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0;

    public static final int APP_PRIVATE_STORAGE = 1;
    public static final int INTERNAL_STORAGE = 2;
    public static final int CUSTOM_PATH_STORAGE = 3;

    private Activity context;
    private File PATH;
    private static final String TAG = "FileEditor";


    public FileEditor(String folderName, int storage, Context context) {
        this.context = (Activity) context;
        setPath(folderName, storage);
    }

    /**
     * Method that sets "default path" if you will.
     * @param path name of the file OR path to the subdir(ex: Pictures/AwsomePic.jpg).
     * @param storage   APP_PRIVATE_STORAGE or INTERNAL_STORAGE or CUSTOM_PATH_STORAGE.
     */
    public void setPath(String path, int storage) {
        switch (storage) {
            case APP_PRIVATE_STORAGE:
                this.PATH = new File(context.getFilesDir() + File.separator + path);

                break;
            case INTERNAL_STORAGE:
                this.PATH = Environment.getExternalStoragePublicDirectory(path);

                break;
            case CUSTOM_PATH_STORAGE:
                this.PATH = new File(path);
                break;
        }
        Log.d(TAG, "PATH: " + PATH);
        this.PATH.mkdirs();
    }

    /**
     * <p>
     * Simple method that asks for WRITE_EXTERNAL_STORAGE premmision. (Required for Android 6.0 (API Level 23) and up)
     * </p>
     * <p>
     * NOTE: This method runs ASYNCRONOUSLY!!!!!!!!!!!!!! Check link below (2017-01-17 edit: it looks like this is really simple and dirty implementation of premmisions, quite a few extra lines required....)
     * </p>
     * For more information see: <a href="https://developer.android.com/training/permissions/requesting.html">Requesting Permissions at Run Time</a>
     */
    public void askForPremmisions() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }

    /**
     * Writes List of strings to a file. (Each string in separate line)
     *
     * @param path  location of file.
     * @param data      List that needs to be saved from this cruel world of RAM. :D
     * @param overwrite if passed value is 'true' - file will get overwrited with specified data, appends otherwise.
     */
    public void writeList(String path, List<?> data, Boolean overwrite) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(PATH + File.separator + path, !overwrite));
            for (Object line : data)
                writer.write(line + System.getProperty("line.separator"));
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param path  location of file.
     * @param data      String that needs to be saved from this cruel world of RAM. :D
     * @param overwrite if passed value is 'true' - file will get overwrited with specified data, appends otherwise.
     */
    public void writeString(String path, Boolean overwrite, String... data) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(PATH + File.separator + path, !overwrite));
            for (String s : data)
                writer.write(s);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads file and returns List with its contents.
     *
     * @param path location of file.
     * @return List with file contents.(one line = one list item)
     * @see File
     */
    public List<String> readList(String path) {
        List<String> fileContent = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(PATH + File.separator + path)));
            String strLine = null;
            while ((strLine = br.readLine()) != null)
                fileContent.add(strLine);
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }

    /**
     * Reads file and returns String with its contents.
     *
     * @param path location of file.
     * @return String with file contents.
     * @see File
     */
    public String readString(String path) {
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(PATH + File.separator + path)));
            String strLine = null;
            while ((strLine = br.readLine()) != null)
//                builder.append(strLine + System.getProperty("line.separator"));
                builder.append(strLine );
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    /**
     * @param name name of file.
     * @return True if file exists, false otherwise.
     * @see File
     */
    public boolean exists(String name) {
        return new File(PATH, name).exists();
    }

    /**
     * Creates empty file
     *
     * @param path name of a file.
     * @return true if the file has been created, false if it already exists.
     * @see File
     */
    public boolean createEmptyFile(String path) {
        try {
            return new File(PATH, path).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Renames file
     *
     * @param from old file name.
     * @param to   new file name.
     * @return true if the file has been renamed.
     * @see File
     */
    public boolean rename(String from, String to) {
        return new File(PATH, from).renameTo(new File(PATH, to));
    }

    /**
     * Returns a list of File objects.
     *
     * @param path folder name, or empty string for current folder.
     * @return the file/folder at the specified location.
     * @see File
     */
    public File[] getFileList(String path) {
        return new File(PATH, path).listFiles();
    }

    /**
     * Returns specified File object.
     *
     * @param path location of file.
     * @return the file/folder at the specified location.
     * @see File
     */
    public File getFile(String path) {
        return new File(PATH, path);
    }

    /**
     * Deletes a File or folder.
     *
     * @param path location of file/folder.
     * @return true if specified file was deleted, false otherwise.
     * @see File
     */
    public boolean delete(String path) {
        return new File(PATH, path).delete();
    }
}

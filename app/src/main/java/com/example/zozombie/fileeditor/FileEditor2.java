package com.example.zozombie.fileeditor;

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


public class FileEditor2 {

    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0;
    public static final int APP_PRIVATE_STORAGE = 1;
    public static final int INTERNAL_STORAGE = 2;

    private Activity context;
    private File PATH;
    private BufferedWriter writer;
    private Boolean enviormentSubdir;
    private static final String TAG = "FileEditor";

//    public static final int EXTERNAL_STORAGE = 3;

    public FileEditor2(String folderName, int storage, Context context) {
        this.context = (Activity) context;
        setPath(folderName, storage);
    }

    private void prepareWriter(String fileName, Boolean overwrite) {        //i have no idea why this method exist. BLAME STONE ME, NOT ME :D
        try {
            writer = new BufferedWriter(new FileWriter(!enviormentSubdir ? PATH + "/" + fileName : PATH.toString(), !overwrite));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPath(String folderName, int storage) {
        enviormentSubdir = false;
        switch (storage) {
            case APP_PRIVATE_STORAGE:
                this.PATH = new File(context.getFilesDir() + "/" + folderName);

                break;
            case INTERNAL_STORAGE:
                this.PATH = Environment.getExternalStoragePublicDirectory(folderName);

                break;
//            case EXTERNAL_STORAGE:
//                break;
        }
        Log.d(TAG, "setPath: " + PATH);
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
//--------------------------------------[2017-01-17: few extra comments :)]-----------------------------//
    /**
     * Writes List of strings to a file. (Each string in separate line)
     *
     * @param fileName  location of file.
     * @param data      List that needs to be saved from this cruel world of RAM. :D
     * @param overwrite if passed value is 'true' - file will get overwrited with specified data, appends otherwise.
     */
    public void writeList(String fileName, List<String> data, Boolean overwrite) {
        try {
            //creates BufferedWriteer that alows us to "export" data to the files. No need to worry about closing "FileWriter", BufferedWriter has that part preimplemented within
            writer = new BufferedWriter(new FileWriter(!enviormentSubdir ? PATH + "/" + fileName : PATH.toString(), !overwrite));

//            prepareWriter(fileName, overwrite);

            for (String line : data) {              //just writes down a list to the file(one line== one list item), changing parameter "List<String> data" to " List<?> data" should make it accept any type of list, im abit too lazy and busy to test stuff right now :(
                writer.write(line + System.getProperty("line.separator"));
            }
            writer.flush();         //if i remember correctly, it waits till all data
            writer.close();         //ULL NEVER GUESS WHAT THIS LINE DOES!!!!!!
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param fileName  location of file.
     * @param data      String that needs to be saved from this cruel world of RAM. :D
     * @param overwrite if passed value is 'true' - file will get overwrited with specified data, appends otherwise.
     */
    public void writeString(String fileName, String data, Boolean overwrite) {
        try {
            prepareWriter(fileName, overwrite);
            writer.write(data);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//--------------------------------------[2017-01-17: few extra comments :)]-----------------------------//

    /**
     * Reads file and returns List with its contents.
     *
     * @param fileName location of file.
     * @return List with file contents.(one line = one list item)
     * @see File
     */
    public List<String> readList(String fileName) {
        List<String> fileContent = new ArrayList<>();       //Creates empty list, ik, what a surprise :D
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(PATH + "/" + fileName)));  //Pretty much the same shit as in line 96, just for reading
            String strLine = null;
            while ((strLine = br.readLine()) != null)               //checks if !strLine.equals(null) :) and adds that line to
                fileContent.add(strLine);                           //adds line to the list, ik unexpected :D
            br.close();                                             //well, and ofcourse, affter "reading" storries you get to the end and have to "close" the book :D
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;                         //ummm, could u explane me this line, i dot really get it :D
    }

    /**
     * Reads file and returns String with its contents.
     *
     * @param fileName location of file.
     * @return String with file contents.
     * @see File
     */
    public String readString(String fileName) {
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(PATH + "/" + fileName)));
            String strLine = null;
            while ((strLine = br.readLine()) != null)
                builder.append(strLine + System.getProperty("line.separator"));
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
     * @param fileName name of a file.
     * @return true if the file has been created, false if it already exists.
     * @see File
     */
    public boolean createEmptyFile(String fileName) {
        try {
            return new File(PATH, fileName).createNewFile();
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

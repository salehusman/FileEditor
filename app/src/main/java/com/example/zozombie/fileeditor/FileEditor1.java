package com.example.zozombie.fileeditor;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class FileEditor1 {
    private File PATH;

    public FileEditor1(String folderName) {
        setFolder(folderName);
    }

    public void setFolder(String folderName) {
        this.PATH = Environment.getExternalStoragePublicDirectory(folderName);
        this.PATH.mkdirs();
    }

    public boolean exists(String fileName) {
        if (new File(PATH, fileName).exists())
            return true;
        return false;
    }

    public boolean createIfDosentExist(String filename, List<String> data) {
        if (!exists(filename)) {
            writeList(filename, data, true);
            return true;
        }
        return false;
    }

    public boolean createIfDosentExist(String filename, String data) {
        if (!exists(filename)) {
            writeString(filename, data, true);
            return true;
        }
        return false;
    }

    public boolean touch(String fileName) {
        try {
            new File(PATH, fileName).createNewFile();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public List<String> readList(String fileName) {
        List<String> fileContent = new ArrayList<>();
        try {
            InputStream inStream = new FileInputStream(PATH + "/" + fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
            String strLine = null;
            while ((strLine = br.readLine()) != null) {
                fileContent.add(strLine);
            }
            br.close();
            inStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }


    public String readString(String fileName) {
        StringBuilder builder = new StringBuilder();
        try {
            if (exists(fileName))
                return "";

            InputStream inStream = new FileInputStream(PATH + "/" + fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
            String strLine = null;

            while ((strLine = br.readLine()) != null) {
                builder.append(strLine);
            }
            br.close();
            inStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }


    public void writeList(String fileName, List<String> data, Boolean overwrite) {
        try {
            FileWriter fw = new FileWriter(PATH + "/" + fileName, !overwrite);
            BufferedWriter writer = new BufferedWriter(fw);
            for (String line : data) {
                writer.write(line + System.getProperty("line.separator"));
            }
            writer.flush();
            writer.close();
            fw.flush();
            fw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeString(String fileName, String data, Boolean overwrite) {
        try {
            FileWriter fw = new FileWriter(PATH + "/" + fileName, !overwrite);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(data + System.getProperty("line.separator"));
            writer.flush();
            writer.close();
            fw.flush();
            fw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void rename(String from, String to) {
        new File(PATH, from).renameTo(new File(PATH, to));
    }

    public boolean delete(String filenName) {
        return new File(PATH, filenName).delete();
    }
}

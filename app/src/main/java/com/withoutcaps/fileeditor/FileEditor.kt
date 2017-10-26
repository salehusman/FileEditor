package com.withoutcaps.fileeditor

import java.io.*
import java.util.*
import kotlin.collections.ArrayList


object FileEditor {

    private const val TAG = "FileEditor"

    /**
     * Reads file and returns String with its contents.
     *
     * @param path location of file.
     * @return String with file contents.
     * @see File
     */
    fun readString(path: String): String {
        if (exists(path)) {
            val br = BufferedReader(InputStreamReader(FileInputStream(path)))
            br.use {
                return br.readText()
            }
        }
        return ""
    }

    /**
     * Reads file and returns List with its contents. ( One line equals one list item )
     *
     * @param path location of file.
     * @return List with file contents.(one line = one list item)
     * @see File
     */
    fun readList(path: String): MutableList<String> {
        if (exists(path)) {
            val br = BufferedReader(InputStreamReader(FileInputStream(path)))
            br.use {
                return br.readLines() as MutableList<String>
            }
        }
        return ArrayList(0)
    }

    /**
     * @param path      location of file.
     * @param data      String that needs to be saved from this cruel world of RAM. :D
     * @param overwrite if passed value is 'true' - file will get overwrited with specified data, appends otherwise.
     */
    fun writeString(path: String, overwrite: Boolean = true, vararg data: String) {
        val writer = BufferedWriter(FileWriter(path, !overwrite))
        for (s in data)
            writer.write(if (s != data.last()) s + System.getProperty("line.separator") else s)
        writer.flush()
        writer.close()
    }

    /**
     * Writes List to a file. (Each string in separate line)
     *
     * @param path      location of file.
     * @param data      List that needs to be saved from this cruel world of RAM. :D
     * @param overwrite if passed value is 'true' - file will get overwrited with specified data, appends otherwise.
     */
    fun writeList(path: String, data: Collection<*>, overwrite: Boolean = true) {
        val writer = BufferedWriter(FileWriter(path, !overwrite))
        for (line in data)
            writer.write(line.toString() + System.getProperty("line.separator"))
        writer.flush()
        writer.close()
    }


    /**
     * Creates file(Or Directory) if it does not exist
     *
     * @param path File path.
     * @param data File content, null if empty
     * @return true if the file has been created, false if it already exists.
     * @see File
     */
    fun createIfDosentExist(path: String, data: Collection<*>? = null): Boolean {
        if (!exists(path)) {
            if (data != null) {
                writeList(path, data)
            } else {
                val file = File(path)
                return if (file.isDirectory)
                    file.mkdirs()
                else
                    file.createNewFile()
            }
            return true
        }
        return false
    }

    fun copyFile(src: File, dst: File) {
        val inChannel = FileInputStream(src).channel
        val outChannel = FileOutputStream(dst).channel

        inChannel.transferTo(0, inChannel.size(), outChannel)
        inChannel.close()
        outChannel.close()
    }

    fun moveFile(src: File, dst: File) {
        copyFile(src, dst)
        src.delete()
    }

    fun exists(vararg paths: String): Boolean = paths.all { File(it).exists() }

    fun createIfDosentExist(path: String, vararg data: String): Boolean = createIfDosentExist(path, Arrays.asList(*data))

    fun delete(vararg paths: String): Boolean = paths.all { File(it).delete() }

    fun rename(from: String, to: String): Boolean = rename(File(from), File(to))

    fun rename(from: File, to: File): Boolean = from.renameTo(to)

    fun getFileList(path: String): Array<File> = getFileList(File(path))

    fun getFileList(directory: File): Array<File> = directory.listFiles()

    fun moveFile(srcPath: String, dstPath: String) = moveFile(File(srcPath), File(dstPath))

    fun copyFile(srcPath: String, dstPath: String) = copyFile(File(srcPath), File(dstPath))


}
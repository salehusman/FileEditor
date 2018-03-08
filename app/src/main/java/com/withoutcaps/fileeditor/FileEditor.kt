@file:Suppress("MemberVisibilityCanBePrivate")

package com.withoutcaps.fileeditor

import java.io.*
import java.util.*
import kotlin.collections.ArrayList


@Suppress("unused", "MemberVisibilityCanPrivate")
object FileEditor {

    /**
     * Reads file and returns String with its contents.
     *
     * @param path location of file.
     * @return String with file contents.
     * @see File
     */
    fun readString(path: String): String {
        if (exists(path)) {
            BufferedReader(InputStreamReader(FileInputStream(path))).use {
                return it.readText()
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
            BufferedReader(InputStreamReader(FileInputStream(path))).use {
                return it.readLines() as MutableList<String>
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
        BufferedWriter(FileWriter(path, !overwrite)).use {
            for (s in data)
                it.write(if (s != data.last()) s + System.getProperty("line.separator") else s)
            it.flush()
        }
    }

    /**
     * Writes List to a file. (Each string in separate line)
     *
     * @param path      location of file.
     * @param data      List that needs to be saved from this cruel world of RAM. :D
     * @param overwrite if passed value is 'true' - file will get overwrited with specified data, appends otherwise.
     */
    fun writeList(path: String, data: Collection<*>, overwrite: Boolean = true) {
        BufferedWriter(FileWriter(path, !overwrite)).use {
            for (line in data)
                it.write(line.toString() + System.getProperty("line.separator"))
            it.flush()
        }
    }

    /**
     * Creates file(Or Directory) if it does not exist
     *
     * @param path File path.
     * @param data File content, null if empty
     * @return true if the file has been created, false if it already exists.
     * @see File
     */
    fun createIfDoesntExist(path: String, data: Collection<*>? = null): Boolean {
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
        FileInputStream(src).channel.use { inChannel ->
            FileOutputStream(dst).channel.use { outChannel ->
                inChannel.transferTo(0, inChannel.size(), outChannel)
            }
        }
    }

    fun moveFile(src: File, dst: File) {
        copyFile(src, dst)
        src.delete()
    }

    fun exists(vararg paths: String): Boolean = paths.all { File(it).exists() }

    fun createIfDoesntExist(path: String, vararg data: String): Boolean = createIfDoesntExist(path, Arrays.asList(*data))

    fun delete(vararg paths: String): Boolean = paths.all { File(it).delete() }

    fun rename(from: String, to: String): Boolean = rename(File(from), File(to))

    fun rename(from: File, to: File): Boolean = from.renameTo(to)

    fun getFileList(path: String): Array<File> = getFileList(File(path))

    fun getFileList(directory: File): Array<File> = directory.listFiles()

    fun moveFile(srcPath: String, dstPath: String) = moveFile(File(srcPath), File(dstPath))

    fun copyFile(srcPath: String, dstPath: String) = copyFile(File(srcPath), File(dstPath))
}
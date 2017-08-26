package site.withoutcaps.lessonsschedule

import java.io.*
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream
import kotlin.collections.ArrayList


object FileEditor {

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
     * @param path      location of file.
     * @param data      String that needs to be saved from this cruel world of RAM. :D
     * @param overwrite if passed value is 'true' - file will get overwrited with specified data, appends otherwise.
     */
    fun writeString(path: String, overwrite: Boolean = true, addNewLine: Boolean = true, vararg data: String) {
        val writer = BufferedWriter(FileWriter(path, !overwrite))
        for (s in data)
            writer.write(s + if (addNewLine) System.getProperty("line.separator") else "")
        writer.flush()
        writer.close()
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
            try {
                return br.readLines() as MutableList<String>
            } finally {
                br.close()
            }
        }
        return ArrayList(0)
    }

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
            try {
                return br.readText()
            } finally {
                br.close()
            }
        }
        return ""
    }

    /**
     * Creates empty file if it does not exist
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
                if (file.isDirectory)
                    return file.mkdirs()
                else
                    return file.createNewFile()
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

    fun exists(path: String): Boolean = File(path).exists()

    fun createIfDosentExist(path: String, vararg data: String): Boolean = createIfDosentExist(path, Arrays.asList(*data))

    fun rename(from: String, to: String): Boolean = File(from).renameTo(File(to))

    fun getFileList(path: String): Array<File> = File(path).listFiles()

    fun getFile(path: String): File = File(path)

    fun delete(path: String): Boolean = File(path).delete()

    fun moveFile(srcPath: String, dstPath: String) = moveFile(File(srcPath), File(dstPath))

    fun copyFile(srcPath: String, dstPath: String) = copyFile(File(srcPath), File(dstPath))

}
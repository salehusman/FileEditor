package site.withoutcaps.fileeditor

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

object FileEditor {

    /**
     * Writes List of strings to a file. (Each string in separate line)
     *
     * @param path      location of file.
     * @param data      List that needs to be saved from this cruel world of RAM. :D
     * @param overwrite if passed value is 'true' - file will get overwrited with specified data, appends otherwise.
     */
    fun writeList(path: String, data: List<*>, overwrite: Boolean) {
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
    fun writeString(path: String, overwrite: Boolean, addNewLine: Boolean, vararg data: String) {
            val writer = BufferedWriter(FileWriter(path, !overwrite))
            for (s in data)
                writer.write(s + if (addNewLine) System.getProperty("line.separator") else "")
            writer.flush()
            writer.close()
    }

    /**
     * Reads file and returns List with its contents.
     * @param path location of file.
     *
     * @return List with file contents.(one line = one list item)
     *
     * @see File
     */
    fun readList(path: String): List<String> {
        val fileContent = ArrayList<String>()
            val br = BufferedReader(InputStreamReader(FileInputStream(path)))
            while (true) {
                val strLine = br.readLine()
                if (strLine == null) break
                fileContent.add(strLine)
            }
            br.close()
        return fileContent
    }

    /**
     * Reads file and returns String with its contents.
     *
     * @param path location of file.
     * @return String with file contents.
     * @see File
     */
    fun readString(path: String): String {
        val builder = StringBuilder()
            val br = BufferedReader(InputStreamReader(FileInputStream(path)))
            while (true) {
                val strLine = br.readLine()
                if (strLine == null) break
                builder.append(strLine)
                builder.append(System.getProperty("line.separator"))
            }
            br.close()
        return builder.toString()
    }

    /**
     * @param name name of file.
     * @return True if file exists, false otherwise.
     * @see File
     */
    fun exists(path: String): Boolean = File(path).exists()

    /**
     * Creates empty file
     *
     * @param path name of a file.
     * @return true if the file has been created, false if it already exists.
     * @see File
     */
    fun createEmptyFile(path: String): Boolean {
        if (!exists(path))
            return File(path).createNewFile()
        return false
    }

    fun createIfDosentExist(path: String, data: List<String>?) {
        if (!exists(path) && data != null)
            writeList(path, data, true)
    }

    fun createIfDosentExist(path: String, vararg data: String) = createIfDosentExist(path, Arrays.asList(*data))


    /**
     * Renames file
     *
     * @param from old file name.
     * @param to   new file name.
     * @return true if the file has been renamed.
     * @see File
     */
    fun rename(from: String, to: String): Boolean = File(from).renameTo(File(to))

    /**
     * Returns a list of File objects.
     *
     * @param path folder name, or empty string for current folder.
     * @return the file/folder at the specified location.
     * @see File
     */
    fun getFileList(path: String): Array<File> = File(path).listFiles()


    /**
     * Returns specified File object.
     *
     * @param path location of file.
     * @return the file/folder at the specified location.
     * @see File
     */
    fun getFile(path: String): File = File(path)


    /**
     * Deletes a File or folder.
     *
     * @param path location of file/folder.
     * @return true if specified file was deleted, false otherwise.
     * @see File
     */
    fun delete(path: String): Boolean = File(path).delete()
}

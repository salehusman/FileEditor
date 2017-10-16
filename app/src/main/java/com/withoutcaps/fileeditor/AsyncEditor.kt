package site.withoutcaps.fileeditor

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import site.withoutcaps.lessonsschedule.FileEditor
import java.io.File

/**
 * Wrapper class for FileEditor.class which adds async calls
 */
object AsyncEditor {    //TODO: Remove unnecessary methods (after performance check)
    private val pool = CommonPool

    fun readString(path: String, onDone: (String) -> Unit) = launch(pool, block = { onDone.invoke(FileEditor.readString(path)) })
    fun readList(path: String, onDone: (MutableList<String>) -> Unit) = launch(pool, block = { onDone.invoke(FileEditor.readList(path)) })

    //TODO: Make it so that it would accept "vararg" as "data" param
    fun writeString(path: String, overwrite: Boolean = true, addNewLine: Boolean = true, data: String, onDone: () -> Unit) {
        launch(pool, block = {
            FileEditor.writeString(path, addNewLine, overwrite, data)
            onDone.invoke()
        })
    }

    fun writeList(path: String, data: Collection<*>, overwrite: Boolean = true, onDone: () -> Unit) {
        launch(pool, block = {
            FileEditor.writeList(path, data, overwrite)
            onDone.invoke()
        })
    }

    fun createIfDosentExist(path: String, onDone: (Boolean) -> Unit, data: Collection<*>? = null) = launch(pool, block = { onDone.invoke(FileEditor.createIfDosentExist(path, data)) })
    fun createIfDosentExist(path: String, onDone: (Boolean) -> Unit, data: String) = launch(pool, block = { onDone.invoke(FileEditor.createIfDosentExist(path, data)) })

    fun copyFile(src: File, dst: File, onDone: () -> Unit) = launch(pool, block = { FileEditor.copyFile(src, dst); onDone.invoke() })
    fun copyFile(src: String, dst: String, onDone: () -> Unit) = launch(pool, block = { FileEditor.copyFile(src, dst); onDone.invoke() })

    fun moveFile(src: File, dst: File, onDone: () -> Unit) = launch(pool, block = { FileEditor.moveFile(src, dst); onDone.invoke() })
    fun moveFile(src: String, dst: String, onDone: () -> Unit) = launch(pool, block = { FileEditor.moveFile(src, dst); onDone.invoke() })

    fun delete(src: String, onDone: (Boolean) -> Unit) = launch(pool, block = { onDone.invoke(FileEditor.delete(src)) })
    fun rename(src: String, dst: String, onDone: (Boolean) -> Unit) = launch(pool, block = { onDone.invoke(FileEditor.rename(src, dst)) })
    fun getFileList(src: String, onDone: (Array<File>) -> Unit) = launch(pool, block = { onDone.invoke(FileEditor.getFileList(src)) })
}
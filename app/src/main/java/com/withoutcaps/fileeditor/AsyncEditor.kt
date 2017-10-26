package site.withoutcaps.fileeditor

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import site.withoutcaps.lessonsschedule.FileEditor
import java.io.File

/**
 * Wrapper class for FileEditor.class which adds async calls
 */
object AsyncEditor {
    private val pool = CommonPool

    fun readString(path: String, onDone: (String) -> Unit) = launch(pool, block = { onDone.invoke(FileEditor.readString(path)) })
    fun readList(path: String, onDone: (MutableList<String>) -> Unit) = launch(pool, block = { onDone.invoke(FileEditor.readList(path)) })

    //TODO: Make it so that it would accept "vararg" as "data" param
    fun writeString(path: String, overwrite: Boolean = true, data: String, onDone: () -> Unit) {
        launch(pool, block = {
            FileEditor.writeString(path, overwrite, data)
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

    fun delete(src: String, onDone: (Boolean) -> Unit) = launch(pool, block = { onDone.invoke(FileEditor.delete(src)) })
}
package com.withoutcaps.fileeditor

import kotlinx.coroutines.experimental.launch

/**
 * Wrapper class for FileEditor.class which adds async calls
 */
@Suppress("unused", "MemberVisibilityCanPrivate")
object AsyncEditor {

    inline fun readString(path: String, crossinline onDone: (String) -> Unit) = launch { onDone.invoke(FileEditor.readString(path)) }
    inline fun readList(path: String, crossinline onDone: (MutableList<String>) -> Unit) = launch { onDone.invoke(FileEditor.readList(path)) }

    //TODO: Make it so that it would accept "vararg" as "data" param
    inline fun writeString(path: String, overwrite: Boolean = true, data: String, crossinline onDone: () -> Unit) {
        launch {
            FileEditor.writeString(path, overwrite, data)
            onDone.invoke()
        }
    }

    inline fun writeList(path: String, data: Collection<*>, overwrite: Boolean = true, crossinline onDone: () -> Unit) {
        launch {
            FileEditor.writeList(path, data, overwrite)
            onDone.invoke()
        }
    }

    inline fun createIfDoesntExist(path: String, crossinline onDone: (Boolean) -> Unit, data: Collection<*>? = null) = launch { onDone.invoke(FileEditor.createIfDoesntExist(path, data)) }
    inline fun createIfDoesntExist(path: String, crossinline onDone: (Boolean) -> Unit, data: String) = launch { onDone.invoke(FileEditor.createIfDoesntExist(path, data)) }

    inline fun delete(src: String, crossinline onDone: (Boolean) -> Unit) = launch { onDone.invoke(FileEditor.delete(src)) }
}
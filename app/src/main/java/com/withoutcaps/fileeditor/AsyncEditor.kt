@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package com.withoutcaps.fileeditor

import kotlinx.coroutines.experimental.launch
import java.io.File

@Suppress("unused", "MemberVisibilityCanPrivate")
object AsyncEditor {

    inline fun readString(path: String, crossinline onDone: (String) -> Unit) = launch { onDone.invoke(FileEditor.readString(path)) }
    inline fun readList(path: String, crossinline onDone: (MutableList<String>) -> Unit) = launch { onDone.invoke(FileEditor.readList(path)) }

    inline fun writeString(path: String, overwrite: Boolean = true, vararg data: String, crossinline onDone: () -> Unit) = launch {
        FileEditor.writeString(path, overwrite, *data)
        onDone.invoke()
    }

    inline fun writeList(path: String, data: Collection<*>, overwrite: Boolean = true, crossinline onDone: () -> Unit) = launch {
        FileEditor.writeList(path, data, overwrite)
        onDone.invoke()
    }

    inline fun createIfDoesntExist(path: String, crossinline onDone: (Boolean) -> Unit, data: Collection<*>? = null) = launch { onDone.invoke(FileEditor.createIfDoesntExist(path, data)) }
    inline fun createIfDoesntExist(path: String, crossinline onDone: (Boolean) -> Unit, data: String) = launch { onDone.invoke(FileEditor.createIfDoesntExist(path, data)) }

    inline fun copy(src: File, dest: File, crossinline onDone: () -> Unit) = launch {
        FileEditor.copy(src, dest)
        onDone()
    }

    inline fun move(src: File, dest: File, crossinline onDone: () -> Unit) = launch {
        FileEditor.move(src, dest)
        onDone()
    }

    inline fun exists(vararg files: File, crossinline onDone: () -> Unit) = launch {
        FileEditor.exists(*files)
        onDone()
    }

    inline fun exists(vararg files: String, crossinline onDone: () -> Unit) = launch {
        FileEditor.exists(*files)
        onDone()
    }

    inline fun readDir(path: String, crossinline result: (Array<File>) -> Unit) = launch { result(FileEditor.readDir(path)) }
    inline fun readDir(path: File, crossinline result: (Array<File>) -> Unit) = launch { result(FileEditor.readDir(path)) }

    inline fun delete(src: String, crossinline onDone: (Boolean) -> Unit) = launch { onDone.invoke(FileEditor.delete(src)) }
    inline fun delete(src: File, crossinline onDone: (Boolean) -> Unit) = launch { onDone.invoke(FileEditor.delete(src)) }

}
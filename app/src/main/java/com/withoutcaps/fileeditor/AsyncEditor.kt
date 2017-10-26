package com.withoutcaps.fileeditor

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import site.withoutcaps.lessonsschedule.FileEditor

/**
 * Wrapper class for FileEditor.class which adds async calls
 */
object AsyncEditor {

    inline fun readString(path: String, crossinline onDone: (String) -> Unit) = launch(CommonPool, block = { onDone.invoke(FileEditor.readString(path)) })
    inline fun readList(path: String, crossinline onDone: (MutableList<String>) -> Unit) = launch(CommonPool, block = { onDone.invoke(FileEditor.readList(path)) })

    //TODO: Make it so that it would accept "vararg" as "data" param
    inline fun writeString(path: String, overwrite: Boolean = true, data: String, crossinline onDone: () -> Unit) {
        launch(CommonPool, block = {
            FileEditor.writeString(path, overwrite, data)
            onDone.invoke()
        })
    }

    inline fun writeList(path: String, data: Collection<*>, overwrite: Boolean = true, crossinline onDone: () -> Unit) {
        launch(CommonPool, block = {
            FileEditor.writeList(path, data, overwrite)
            onDone.invoke()
        })
    }

    inline fun createIfDosentExist(path: String, crossinline onDone: (Boolean) -> Unit, data: Collection<*>? = null) = launch(CommonPool, block = { onDone.invoke(FileEditor.createIfDosentExist(path, data)) })
    inline fun createIfDosentExist(path: String, crossinline onDone: (Boolean) -> Unit, data: String) = launch(CommonPool, block = { onDone.invoke(FileEditor.createIfDosentExist(path, data)) })

    inline fun delete(src: String, crossinline onDone: (Boolean) -> Unit) = launch(CommonPool, block = { onDone.invoke(FileEditor.delete(src)) })
}
@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package com.withoutcaps.fileeditor

import kotlinx.coroutines.experimental.async
import java.io.File

@Suppress("unused", "MemberVisibilityCanPrivate")
object AsyncEditor {

    fun readString(path: String) = async { FileEditor.readString(path) }
    fun readList(path: String) = async { FileEditor.readList(path) }

    fun writeString(path: String, overwrite: Boolean, vararg data: String) = async {
        FileEditor.writeString(path, overwrite, *data)
    }

    fun writeList(path: String, overwrite: Boolean, data: Collection<*>) = async {
        FileEditor.writeList(path, data, overwrite)
    }

    fun createIfDoesntExist(path: String, data: Collection<*>? = null) = async {
        FileEditor.createIfDoesntExist(path, data)
    }

    fun createIfDoesntExist(path: String, data: String) = async {
        FileEditor.createIfDoesntExist(path, data)
    }

    fun copy(src: String, dest: String) = async { FileEditor.copy(src, dest) }
    fun copy(src: File, dest: File) = async { FileEditor.copy(src, dest) }

    fun move(src: String, dest: String) = async { FileEditor.move(src, dest) }
    fun move(src: File, dest: File) = async { FileEditor.move(src, dest) }

    fun exists(vararg files: String) = async { FileEditor.exists(*files) }
    fun exists(vararg files: File) = async { FileEditor.exists(*files) }

    fun delete(src: String) = async { FileEditor.delete(src) }
    fun delete(src: File) = async { FileEditor.delete(src) }

    fun rename(src: String, dest: String) = async { FileEditor.rename(src, dest) }
    fun rename(src: File, dest: File) = async { FileEditor.rename(src, dest) }

    fun readDir(path: String) = async { FileEditor.readDir(path) }
    fun readDir(path: File) = async { FileEditor.readDir(path) }

}
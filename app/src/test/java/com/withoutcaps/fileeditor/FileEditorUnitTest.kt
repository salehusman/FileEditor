package com.withoutcaps.fileeditor

import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import site.withoutcaps.lessonsschedule.FileEditor
import java.io.File


class FileEditorUnitTest {

    val perfTest = true
    val testsCount = 100

    val testFile1 = File(Constants.rootPath + "testText1.txt")
    val testFile2 = File(Constants.rootPath + "testText2.txt")

    fun createTempFiles() {
        testFile1.printWriter().use { out ->
            out.print(Constants.testText1)
        }
        testFile2.printWriter().use { out ->
            out.print(Constants.testText2)
        }
    }

    @Before
    @Throws(Exception::class)
    fun setUp() {
        createTempFiles()
    }

    @Test
    fun readStringTest() {

        assertFalse(FileEditor.readString(testFile1.path) != Constants.testText1)
        assertFalse(FileEditor.readString(testFile2.path) != Constants.testText2)


        if (perfTest) {
            var average: Long = 0

            for (i in 1..testsCount) {
                val currentTime = System.nanoTime()
                FileEditor.readString(testFile2.path)
                average += (System.nanoTime() - currentTime)
            }
            System.out.println("readString testFile2: " + average / testsCount)
        }

    }

    @Test
    fun readListTest() {
        var fileContent = listOf<String>()

        assertFalse(FileEditor.readList(testFile1.path).joinToString("\n") != Constants.testText1)
        assertFalse(FileEditor.readList(testFile2.path).joinToString("\n") != Constants.testText2)

        if (perfTest) {
            var average: Long = 0

            for (i in 1..testsCount) {
                val currentTime = System.nanoTime()
                FileEditor.readList(testFile2.path)
                average += (System.nanoTime() - currentTime)
            }
            System.out.println("readList testFile2: " + average / testsCount)
        }
    }

    @Test
    fun existsTest() {
        assertFalse(!FileEditor.exists(testFile1.path, testFile2.path))
        assertFalse(!FileEditor.exists(testFile1.path))
        assertFalse(FileEditor.exists(testFile2.path, "Failing path"))
        assertFalse(FileEditor.exists("Failing path", testFile2.path))
        assertFalse(FileEditor.exists("Failing path", "Failing path"))
        assertFalse(FileEditor.exists("Failing path"))
    }

    @Test
    fun deleteTest() {
        assertFalse(!FileEditor.delete(testFile1.path, testFile2.path))
        createTempFiles()
        assertFalse(!FileEditor.delete(testFile1.path))
        assertFalse(FileEditor.delete(testFile2.path, "Failing path"))
        createTempFiles()
        assertFalse(FileEditor.exists("Failing path", testFile2.path))
        assertFalse(FileEditor.delete("Failing path", "Failing path"))
        assertFalse(FileEditor.delete("Failing path"))
    }


    @After
    @Throws(Exception::class)
    fun clear() {
        testFile1.delete()
        testFile2.delete()
    }
}



























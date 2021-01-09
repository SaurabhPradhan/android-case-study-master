package com.target.targetcasestudy.util

import java.io.BufferedReader
import java.io.InputStreamReader

class FileReader(path: String) {
    val content: String

    init {
        val reader = InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(path))
        val bufferedReader = BufferedReader(reader)
        val stringBuilder = StringBuilder()
        bufferedReader.use { _bufferedReader ->
            var line = _bufferedReader.readLine()
            while (line != null) {
                stringBuilder.append(line)
                line = _bufferedReader.readLine()
            }
        }
        content = stringBuilder.toString()
        reader.close()
    }
}
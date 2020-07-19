package dev.throwouterror.util.data

import java.io.File
import java.io.InputStream

/**
 * @author Throw Out Error (https://throw-out-error.dev)
 */

object FileUtils {
    fun read(path: String): InputStream {
        return Thread.currentThread().contextClassLoader.getResourceAsStream(path)
                ?: throw Exception("Error reading resource at $path")
    }

    fun readStr(path: String): String {
        val stream = Thread.currentThread().contextClassLoader.getResourceAsStream(path)
                ?: throw Exception("Error reading resource at $path")
        return String(stream.readBytes())
    }
}

fun File.readFirstLine(): String = this.useLines { it.toList()[0] }
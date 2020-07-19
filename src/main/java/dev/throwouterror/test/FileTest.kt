package dev.throwouterror.test

import dev.throwouterror.util.data.readFirstLine
import java.io.File

/**
 * @author Throw Out Error (https://throw-out-error.dev)
 */

class FileTest {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val file = File("C:/Users/theoparis/Documents/test.txt")
            println(file.readFirstLine())
        }
    }
}

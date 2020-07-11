package dev.throwouterror.util.data

import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Path
import java.util.*

/**
 * @author Theo Paris https://theoparis.com/about
 * A confiuration utility class for reading and writing data to a json file.
 */
class JsonConfiguration {
    var configMap: Map<String, Any> = HashMap()
        protected set
    private var defaultsCallback: ConfigDefaultsCallback? = null

    fun withDefaults(callback: ConfigDefaultsCallback?): JsonConfiguration {
        defaultsCallback = callback
        return this
    }

    protected var file: File

    constructor(path: Path) {
        file = path.toFile()
    }

    constructor(f: File) {
        file = f
    }

    fun write(): JsonConfiguration {
        try {
            val json = JsonUtils.get().toJson(configMap, object : TypeToken<Map<String?, Any?>?>() {}.type)
            val writer = FileWriter(file)
            // Write to the file you passed
            writer.write(json)
            // Always close when done.
            writer.close()
        } catch (e: IOException) { // Print an error if something fails. Please use a real logger, not System.out.
            println("Error creating default configuration.")
        }
        return this
    }

    fun create(): JsonConfiguration {
        try { // Create the config if it doesn't already exist.
            if (!file.exists() && file.createNewFile()) { // Get a default map of blocks. You could just use a blank map, however.
                val defaultMap = defaults
                // Convert the map to JSON format. There is a built in (de)serializer for it
// already.
                val json = JsonUtils.get().toJson(defaultMap, object : TypeToken<Map<String?, Any?>?>() {}.type)
                val writer = FileWriter(file)
                // Write to the file you passed
                writer.write(json)
                // Always close when done.
                writer.close()
            }
            // If the file exists (or we just made one exist), convert it from JSON format
// to a populated Map object
            configMap = JsonUtils.get().fromJson(FileReader(file), object : TypeToken<Map<String?, Any?>?>() {}.type)
        } catch (e: IOException) { // Print an error if something fails. Please use a real logger, not System.out.
            println("Error creating default configuration.")
        }
        return this
    }

    val defaults: Map<String, Any>
        get() {
            val ret: Map<String, Any> = HashMap()
            defaultsCallback!!.addDefaults(ret)
            return ret
        }

    fun fileExists(): Boolean {
        return file.exists()
    }
}
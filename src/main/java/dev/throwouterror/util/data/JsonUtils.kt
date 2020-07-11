package dev.throwouterror.util.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * @author Creepinson https:/theoparis.com/about
 */
object JsonUtils {
    private val gson = GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create()
    fun get(): Gson {
        return gson
    }
}
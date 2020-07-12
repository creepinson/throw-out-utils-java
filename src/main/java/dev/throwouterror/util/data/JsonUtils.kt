package dev.throwouterror.util.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * @author Creepinson https:/theoparis.com/about
 */
object JsonUtils {
    private val gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
    fun get(): Gson {
        return gson
    }
}

fun Any.toJsonString(): String {
    return JsonUtils.get().toJson(this)
}

fun String.fromJsonString(type: Class<*>): Any {
    return JsonUtils.get().fromJson(this, type)
}
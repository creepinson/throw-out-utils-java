package dev.throwouterror.util

import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

/**
 * Some basic String utilities
 *
 * @author Creepinson
 * @author Sam
 */
object StringUtils {
    const val EMPTY_STRING = ""
    const val ELLIPSIS_STRING = "..."
    fun isNullOrEmpty(string: String?): Boolean {
        return string == null || EMPTY_STRING == string
    }

    fun removeTagFromTagValueBlock(tagValueBlock: String?, tag: Int): String? {
        var newTagValueBlock = ""
        if (tagValueBlock == null) return null
        val blockTok = StringTokenizer(tagValueBlock, "|")
        while (blockTok.hasMoreTokens()) {
            val tagValueField = blockTok.nextToken()
            val nvpTok = StringTokenizer(tagValueField, "=")
            // Get the fix tag name.
            if (!nvpTok.hasMoreTokens()) {
                continue
            }
            val fixTagName = nvpTok.nextToken()
            // Get the fix tag value.
            if (!nvpTok.hasMoreTokens()) {
                continue
            }
            newTagValueBlock += try {
                if (fixTagName.toInt() == tag) {
                    continue
                }
                "$tagValueField|"
            } catch (ex: Exception) {
                continue
            }
        }
        return newTagValueBlock
    }

    /**
     * List the contents of an array. Each entry is separated by a newline character
     *
     * @param array
     * @return
     */
    fun dumpArray(array: Array<String>): String? {
        val delimiter = System.getProperty("line.separator")
        return dumpArray(array, delimiter)
    }

    /**
     * List the contents of an array. Each entry is separated by specified delimiter
     *
     * @param array
     * @param delimiter delimiter to use to separate each array element
     * @return
     */
    fun dumpArray(array: Array<String>, delimiter: String?): String? {
        var dump: String? = ""
        for (i in array.indices) {
            if (dump!!.isNotEmpty()) {
                dump += delimiter
            }
            dump += array[i]
        }
        return dump
    }

    /**
     * List the contents of an array. Each entry is separated by a newline
     * character. Objects are converted to their toString methods
     *
     * @param array
     * @return
     */
    fun dumpArray(array: Array<Any>): String? {
        val newArray = convertObjectArrayToStringArray(array)
        return dumpArray(newArray)
    }

    private fun convertObjectArrayToStringArray(array: Array<Any>): Array<String> {
        val newArray = Array(array.size) { "" }
        for (i in newArray.indices) {
            newArray[i] = array[i].toString()
        }
        return newArray
    }

    /**
     * Check to see if source string contains the target string within it
     *
     * @param source string to search
     * @param target value to search for
     * @return true if found, false if not
     */
    fun stringContains(source: String?, target: String?): Boolean {
        return if (source == null || target == null) {
            false
        } else source.indexOf(target) > -1
    }

    /**
     * Check to see if source string contains the target string within it
     *
     * @param source string to search
     * @param target value to search for
     * @return true if found, false if not
     */
    fun stringContains(source: String?, target: Int): Boolean {
        return stringContains(source, target.toString())
    }

    /**
     * Check to see if source string contains the target string within it
     *
     * @param source string to search
     * @param target value to search for
     * @return true if found, false if not
     */
    fun stringContains(source: String?, target: Double): Boolean {
        return stringContains(source, target.toString())
    }

    /**
     * Check to see if source string contains the target string within it
     *
     * @param source string to search
     * @param target value to search for
     * @return true if found, false if not
     */
    fun stringContains(source: String?, target: Any?): Boolean {
        return stringContains(source, target.toString())
    }

    fun dumpArray(list: Collection<Any>?): String? {
        return if (list == null) {
            null
        } else dumpArray(list.toTypedArray())
    }

    fun dumpArray(list: Collection<Any>, delimiter: String): String? {
        return dumpArray(list.toTypedArray(), delimiter)
    }

    fun dumpArray(array: Array<Any>, delimiter: String?): String? {
        return dumpArray(convertObjectArrayToStringArray(array), delimiter)
    }

    /**
     * Concatenate all String elements using a "," delimiter
     *
     * @param strings
     * @return single string, with comma separated elements
     */
    fun concatStrings(strings: Array<String?>): String {
        val buffer = StringBuffer()
        for (string in strings) {
            if (buffer.isNotEmpty()) {
                buffer.append(",")
            }
            buffer.append(string)
        }
        return buffer.toString()
    }

    /**
     * Takes Collection of Strings and returns them as a single line of comma
     * separated strings. See [.concatStrings]
     *
     * @param strings
     * @return
     */
    fun concatStrings(strings: Collection<String?>): String {
        return concatStrings(strings.toTypedArray())
    }

    fun wrapStringForSQL(value: String): String {
        return "'$value'"
    }

    fun concatForSQLInClause(stringList: Array<String>?): String? {
        if (stringList == null) return null
        val buffer = StringBuffer()
        for (string in stringList) {
            if (buffer.isNotEmpty()) {
                buffer.append(",")
            }
            buffer.append(wrapStringForSQL(string))
        }
        return buffer.toString()
    }

    /**
     * If String exceeds maxLength, truncate it and add [.ELLIPSIS_STRING] to
     * end.
     *
     * @param msg
     * @param maxLength
     * @return
     */
    fun abbreviate(msg: String?, maxLength: Int): String? {
        if (msg == null) return null
        return if (msg.length > maxLength) {
            msg.substring(0, maxLength) + ELLIPSIS_STRING
        } else msg
    }

    /**
     * Abbreviates to a string to max length
     *
     * @param offset
     * @param maxLength
     * @return
     */
    fun abbreviate(bytes: ByteArray?, offset: Int, maxLength: Int): String {
        if (bytes == null || offset < 0 || maxLength < 1) return EMPTY_STRING
        val msgLength = bytes.size - offset
        val length = if (msgLength > maxLength) maxLength else msgLength
        var message = String(bytes, offset, length)
        if (msgLength > maxLength) message += ELLIPSIS_STRING
        return message
    }

    fun join(joiner: String?, strings: Collection<String?>?): String? {
        if (strings == null) return null
        val buffer = StringBuffer()
        for (string in strings) {
            if (buffer.isNotEmpty()) {
                buffer.append(joiner)
            }
            buffer.append(string)
        }
        return buffer.toString()
    }

    fun join(joiner: String?, vararg strings: String?): String? {
        if (strings == null) return null
        val buffer = StringBuffer()
        for (string in strings) {
            if (buffer.isNotEmpty()) {
                buffer.append(joiner)
            }
            buffer.append(string)
        }
        return buffer.toString()
    }

    fun join(joiner: String?, vararg strings: Double): String {
        return join(joiner, *Arrays.stream(strings).toArray())
    }

    fun join(joiner: String?, vararg strings: Float): String? {
        val buffer = StringBuffer()
        for (string in strings) {
            if (buffer.isNotEmpty()) {
                buffer.append(joiner)
            }
            buffer.append(string)
        }
        return buffer.toString()
    }

    fun wrapAll(delimiter: String?, strings: Collection<String?>): Collection<String> {
        val results: MutableCollection<String> = ArrayList()
        for (string in strings) {
            val buffer = StringBuffer()
            buffer.append(delimiter)
            buffer.append(string)
            buffer.append(delimiter)
            results.add(buffer.toString())
        }
        return results
    }

    /**
     * Returns the given `template` string with each occurrence of
     * `"%s"` replaced with the corresponding argument value from
     * `args`; or, if the placeholder and argument counts do not match,
     * returns a best-effort form of that string. Will not throw an exception under
     * normal conditions.
     *
     *
     *
     * **Note:** For most string-formatting needs, use [ String.format][String.format], [PrintWriter.format][java.io.PrintWriter.format], and
     * related methods. These support the full range of [format
 * specifiers](https://docs.oracle.com/javase/9/docs/api/java/util/Formatter.html#syntax), and alert you to usage errors by throwing
     * [java.util.IllegalFormatException].
     *
     *
     *
     * In certain cases, such as outputting debugging information or constructing a
     * message to be used for another unchecked exception, an exception during
     * string formatting would serve little purpose except to supplant the real
     * information you were trying to provide. These are the cases this method is
     * made for; it instead generates a best-effort string with all supplied
     * argument values present. This method is also useful in environments such as
     * GWT where `String.format` is not available. As an example, method implementations of the
     * class use this formatter, for both of the reasons just discussed.
     *
     *
     *
     * **Warning:** Only the exact two-character placeholder sequence
     * `"%s"` is recognized.
     *
     * @param template a string containing zero or more `"%s"` placeholder
     * sequences. `null` is treated as the four-character string `"null"`.
     * @param args     the arguments to be substituted into the message template.
     * The first argument specified is substituted for the first
     * occurrence of `"%s"` in the template, and so forth. A
     * `null` argument is converted to the four-character
     * string `"null"`; non-null values are converted to
     * strings using [Object.toString].
     */
    fun lenientFormat(template: String, vararg args: Any?): String {
        var args = args.map { it.toString() }.toMutableList()
        for (i in args.indices) {
            args[i] = lenientToString(args[i])
        }
        // start substituting the arguments into the '%s' placeholders
        val builder = StringBuilder(template.length + 16 * args.size)
        var templateStart = 0
        var i = 0
        while (i < args.size) {
            val placeholderStart = template.indexOf("%s", templateStart)
            if (placeholderStart == -1) {
                break
            }
            builder.append(template, templateStart, placeholderStart)
            builder.append(args[i++])
            templateStart = placeholderStart + 2
        }
        builder.append(template, templateStart, template.length)
        // if we run out of placeholders, append the extra args in square braces
        if (i < args.size) {
            builder.append(" [")
            builder.append(args[i++])
            while (i < args.size) {
                builder.append(", ")
                builder.append(args[i++])
            }
            builder.append(']')
        }
        return builder.toString()
    }

    private fun lenientToString(o: Any?): String {
        return if (o == null) {
            "null"
        } else try {
            o.toString()
        } catch (e: Exception) { // Default toString() behavior - see Object.toString()
            val objectToString = o.javaClass.name + '@' + Integer.toHexString(System.identityHashCode(o))
            // Logger is created inline with fixed name to avoid forcing Proguard to create
// another class.
            Logger.getLogger("com.google.common.base.Strings").log(Level.WARNING,
                    "Exception during lenientFormat for $objectToString", e)
            "<" + objectToString + " threw " + e.javaClass.name + ">"
        }
    }
}
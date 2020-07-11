package dev.throwouterror.util.math

object HashUtils {
    const val SEED = 23
    const val fODD_PRIME_NUMBER = 37
    fun hash(aSeed: Int, aInt: Int): Int {
        return firstTerm(aSeed) + aInt
    }

    fun hash(aSeed: Int, aString: String): Int {
        return firstTerm(aSeed) + aString.hashCode()
    }

    fun firstTerm(aSeed: Int): Int {
        return fODD_PRIME_NUMBER * aSeed
    }

    /**
     * Compares two version strings.
     *
     *
     * Use this instead of String.compareTo() for a non-lexicographical
     * comparison that works for version strings. e.g. "1.10".compareTo("1.6").
     *
     * @param str1 a string of ordinal numbers separated by decimal points.
     * @param str2 a string of ordinal numbers separated by decimal points.
     * @return The result is a negative integer if str1 is _numerically_ less than str2.
     * The result is a positive integer if str1 is _numerically_ greater than str2.
     * The result is zero if the strings are _numerically_ equal.
     * @note It does not work if "1.10" is supposed to be equal to "1.10.0".
     */
    fun versionCompare(str1: String, str2: String): Int {
        val vals1 = str1.split("\\.").toTypedArray()
        val vals2 = str2.split("\\.").toTypedArray()
        var i = 0
        // set index to first non-equal ordinal or length of shortest version string
        while (i < vals1.size && i < vals2.size && vals1[i] == vals2[i]) {
            i++
        }
        // compare first non-equal ordinal number
        return if (i < vals1.size && i < vals2.size) {
            val diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]))
            Integer.signum(diff)
        } else {
            Integer.signum(vals1.size - vals2.size)
        }
    }
}
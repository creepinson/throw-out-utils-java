package dev.throwouterror.util

class RomanNumberUtils {
    // Or
    fun numural(number: Int): String {
        val symbols = arrayOf("M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I")
        val numbers = intArrayOf(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1)
        for (i in numbers.indices) {
            if (number >= numbers[i]) {
                return symbols[i] + numural(number - numbers[i])
            }
        }
        return ""
    }

    fun unnumural(number: String): Int {
        val symbols = arrayOf("M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I")
        val numbers = intArrayOf(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1)
        for (i in symbols.indices) {
            if (number.startsWith(symbols[i])) {
                return numbers[i] + unnumural(number.replaceFirst(symbols[i].toRegex(), ""))
            }
        }
        return 0
    }

    companion object {
        fun unnumeral(number: String): Int {
            if (number.startsWith("M")) return 1000 + unnumeral(number.replaceFirst("M".toRegex(), ""))
            if (number.startsWith("CM")) return 900 + unnumeral(number.replaceFirst("CM".toRegex(), ""))
            if (number.startsWith("D")) return 500 + unnumeral(number.replaceFirst("D".toRegex(), ""))
            if (number.startsWith("CD")) return 400 + unnumeral(number.replaceFirst("CD".toRegex(), ""))
            if (number.startsWith("C")) return 100 + unnumeral(number.replaceFirst("C".toRegex(), ""))
            if (number.startsWith("XC")) return 90 + unnumeral(number.replaceFirst("XC".toRegex(), ""))
            if (number.startsWith("L")) return 50 + unnumeral(number.replaceFirst("L".toRegex(), ""))
            if (number.startsWith("XL")) return 40 + unnumeral(number.replaceFirst("XL".toRegex(), ""))
            if (number.startsWith("X")) return 10 + unnumeral(number.replaceFirst("X".toRegex(), ""))
            if (number.startsWith("IX")) return 9 + unnumeral(number.replaceFirst("IX".toRegex(), ""))
            if (number.startsWith("V")) return 5 + unnumeral(number.replaceFirst("V".toRegex(), ""))
            if (number.startsWith("IV")) return 4 + unnumeral(number.replaceFirst("IV".toRegex(), ""))
            return if (number.startsWith("I")) 1 + unnumeral(number.replaceFirst("I".toRegex(), "")) else 0
        }

        fun numeral(number: Int): String? {
            if (number <= 0) return ""
            if (number - 1000 >= 0) return "M" + numeral(number - 1000)
            if (number - 900 >= 0) return "CM" + numeral(number - 900)
            if (number - 500 >= 0) return "D" + numeral(number - 500)
            if (number - 400 >= 0) return "CD" + numeral(number - 400)
            if (number - 100 >= 0) return "C" + numeral(number - 100)
            if (number - 90 >= 0) return "XC" + numeral(number - 90)
            if (number - 50 >= 0) return "L" + numeral(number - 50)
            if (number - 40 >= 0) return "XL" + numeral(number - 40)
            if (number - 10 >= 0) return "X" + numeral(number - 10)
            if (number - 9 >= 0) return "IX" + numeral(number - 9)
            if (number - 5 >= 0) return "V" + numeral(number - 5)
            if (number - 4 >= 0) return "IV" + numeral(number - 4)
            return if (number - 1 >= 0) "I" + numeral(number - 1) else null
        }
    }
}
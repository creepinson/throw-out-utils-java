package dev.throwouterror.util


fun <P> P.nonNullCheck(message: String): P {
    if (this == null) throw NullPointerException("$message returned null even though it shouldn't have.")
    return this
}

fun <P> P.nonNullCheck(): P {
    if (this == null) throw NullPointerException()
    return this
}

fun <T> T.nonNegativeCheck(): T where T : Number {
    require(this.toDouble() >= 0) { "$this must be >= 0" }
    return this
}
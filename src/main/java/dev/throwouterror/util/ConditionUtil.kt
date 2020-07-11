package dev.throwouterror.util

import java.math.BigInteger

/**
 * @author Creepinson https:/theoparis.com/about
 */
object ConditionUtil {
    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference an object reference
     * @return the non-null reference that was validated
     * @throws NullPointerException if `reference` is null
     */
    fun <T : Any?> checkNotNull(reference: T?): T {
        if (reference == null) {
            throw NullPointerException()
        }
        return reference
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference    an object reference
     * @param errorMessage the exception message to use if the check fails; will be converted to a
     * string using [String.valueOf]
     * @return the non-null reference that was validated
     * @throws NullPointerException if `reference` is null
     */
    fun <T : Any?> checkNotNull(
            reference: T?, errorMessage: Any): T {
        if (reference == null) {
            throw NullPointerException(errorMessage.toString())
        }
        return reference
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * @param expression a boolean expression
     * @throws IllegalArgumentException if `expression` is false
     */
    fun checkArgument(expression: Boolean) {
        require(expression)
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * @param expression   a boolean expression
     * @param errorMessage the exception message to use if the check fails; will be converted to a
     * string using [String.valueOf]
     * @throws IllegalArgumentException if `expression` is false
     */
    fun checkArgument(expression: Boolean, errorMessage: Any) {
        require(expression) { errorMessage.toString() }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * @param expression           a boolean expression
     * @param errorMessageTemplate a template for the exception message should the check fail. The
     * message is formed by replacing each `%s` placeholder in the template with an
     * argument. These are matched by position - the first `%s` gets `errorMessageArgs[0]`, etc. Unmatched arguments will be appended to the formatted message in
     * square braces. Unmatched placeholders will be left as-is.
     * @param errorMessageArgs     the arguments to be substituted into the message template. Arguments
     * are converted to strings using [String.valueOf].
     * @throws IllegalArgumentException if `expression` is false
     */
    fun checkArgument(
            expression: Boolean,
            errorMessageTemplate: String,
            vararg errorMessageArgs: Any?) {
        require(expression) { StringUtils.lenientFormat(errorMessageTemplate, *errorMessageArgs) }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     *
     * See [.checkArgument] for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    fun checkArgument(b: Boolean, errorMessageTemplate: String, p1: Char) {
        require(b) { StringUtils.lenientFormat(errorMessageTemplate, p1) }
    }

    fun checkNonNegative(role: String, x: Int): Int {
        require(x >= 0) { "$role ($x) must be >= 0" }
        return x
    }

    fun checkNonNegative(role: String, x: Long): Long {
        require(x >= 0) { "$role ($x) must be >= 0" }
        return x
    }

    fun checkNonNegative(role: String, x: BigInteger): BigInteger {
        require(x.signum() >= 0) { "$role ($x) must be >= 0" }
        return x
    }

    fun checkNonNegative(role: String, x: Double): Double {
        require(x >= 0) {
            // not x < 0, to work with NaN.
            "$role ($x) must be >= 0"
        }
        return x
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     *
     * See [.checkArgument] for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    fun checkArgument(b: Boolean, errorMessageTemplate: String, p1: Int) {
        require(b) { StringUtils.lenientFormat(errorMessageTemplate, p1) }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     *
     * See [.checkArgument] for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    fun checkArgument(b: Boolean, errorMessageTemplate: String, p1: Long) {
        require(b) { StringUtils.lenientFormat(errorMessageTemplate, p1) }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     *
     * See [.checkArgument] for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    fun checkArgument(
            b: Boolean, errorMessageTemplate: String, p1: Any?) {
        require(b) { StringUtils.lenientFormat(errorMessageTemplate, p1) }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     *
     * See [.checkArgument] for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    fun checkArgument(
            b: Boolean, errorMessageTemplate: String, p1: Char, p2: Char) {
        require(b) { StringUtils.lenientFormat(errorMessageTemplate, p1, p2) }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     *
     * See [.checkArgument] for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    fun checkArgument(
            b: Boolean, errorMessageTemplate: String, p1: Char, p2: Int) {
        require(b) { StringUtils.lenientFormat(errorMessageTemplate, p1, p2) }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     *
     * See [.checkArgument] for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    fun checkArgument(
            b: Boolean, errorMessageTemplate: String, p1: Char, p2: Long) {
        require(b) { StringUtils.lenientFormat(errorMessageTemplate, p1, p2) }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     *
     * See [.checkArgument] for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    fun checkArgument(
            b: Boolean, errorMessageTemplate: String, p1: Char, p2: Any?) {
        require(b) { StringUtils.lenientFormat(errorMessageTemplate, p1, p2) }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     *
     * See [.checkArgument] for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    fun checkArgument(
            b: Boolean, errorMessageTemplate: String, p1: Int, p2: Char) {
        require(b) { StringUtils.lenientFormat(errorMessageTemplate, p1, p2) }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     *
     * See [.checkArgument] for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    fun checkArgument(
            b: Boolean, errorMessageTemplate: String, p1: Int, p2: Int) {
        require(b) { StringUtils.lenientFormat(errorMessageTemplate, p1, p2) }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     *
     * See [.checkArgument] for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    fun checkArgument(
            b: Boolean, errorMessageTemplate: String, p1: Int, p2: Long) {
        require(b) { StringUtils.lenientFormat(errorMessageTemplate, p1, p2) }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     *
     * See [.checkArgument] for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    fun checkArgument(
            b: Boolean, errorMessageTemplate: String, p1: Int, p2: Any?) {
        require(b) { StringUtils.lenientFormat(errorMessageTemplate, p1, p2) }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     *
     * See [.checkArgument] for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    fun checkArgument(
            b: Boolean, errorMessageTemplate: String, p1: Long, p2: Char) {
        require(b) { StringUtils.lenientFormat(errorMessageTemplate, p1, p2) }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     *
     * See [.checkArgument] for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    fun checkArgument(
            b: Boolean, errorMessageTemplate: String, p1: Long, p2: Int) {
        require(b) { StringUtils.lenientFormat(errorMessageTemplate, p1, p2) }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     *
     * See [.checkArgument] for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    fun checkArgument(
            b: Boolean, errorMessageTemplate: String, p1: Long, p2: Long) {
        require(b) { StringUtils.lenientFormat(errorMessageTemplate, p1, p2) }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     *
     * See [.checkArgument] for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    fun checkArgument(
            b: Boolean, errorMessageTemplate: String, p1: Long, p2: Any?) {
        require(b) { StringUtils.lenientFormat(errorMessageTemplate, p1, p2) }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     *
     * See [.checkArgument] for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    fun checkArgument(
            b: Boolean, errorMessageTemplate: String, p1: Any?, p2: Char) {
        require(b) { StringUtils.lenientFormat(errorMessageTemplate, p1, p2) }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     *
     * See [.checkArgument] for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    fun checkArgument(
            b: Boolean, errorMessageTemplate: String, p1: Any?, p2: Int) {
        require(b) { StringUtils.lenientFormat(errorMessageTemplate, p1, p2) }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     *
     * See [.checkArgument] for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    fun checkArgument(
            b: Boolean, errorMessageTemplate: String, p1: Any?, p2: Long) {
        require(b) { StringUtils.lenientFormat(errorMessageTemplate, p1, p2) }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     *
     * See [.checkArgument] for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    fun checkArgument(
            b: Boolean, errorMessageTemplate: String, p1: Any?, p2: Any?) {
        require(b) { StringUtils.lenientFormat(errorMessageTemplate, p1, p2) }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     *
     * See [.checkArgument] for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    fun checkArgument(
            b: Boolean,
            errorMessageTemplate: String,
            p1: Any?,
            p2: Any?,
            p3: Any?) {
        require(b) { StringUtils.lenientFormat(errorMessageTemplate, p1, p2, p3) }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     *
     * See [.checkArgument] for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    fun checkArgument(
            b: Boolean,
            errorMessageTemplate: String,
            p1: Any?,
            p2: Any?,
            p3: Any?,
            p4: Any?) {
        require(b) { StringUtils.lenientFormat(errorMessageTemplate, p1, p2, p3, p4) }
    }
}
package dev.throwouterror.util

import java.util.*
import java.util.function.Consumer
import java.util.function.Supplier

/**
 * @author Creepinson https:/theoparis.com/about
 */
object TransformationUtils {
    fun <T> asSet(array: Array<T>): Set<T> {
        return HashSet(listOf(*array))
    }

    fun <T> make(supplier: Supplier<T>): T {
        return supplier.get()
    }

    fun <T> make(`object`: T, consumer: Consumer<T>): T {
        consumer.accept(`object`)
        return `object`
    }
}
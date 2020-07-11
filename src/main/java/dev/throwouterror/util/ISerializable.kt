package dev.throwouterror.util

import java.io.Serializable

/**
 * @author Creepinson https:/theoparis.com/about
 */
interface ISerializable<T> : Serializable {
    override fun toString(): String
    fun fromString(s: String): T
}
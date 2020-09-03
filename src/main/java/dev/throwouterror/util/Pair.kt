package dev.throwouterror.util

class Pair<K, V>(override val key: K, override var value: V) : MutableMap.MutableEntry<K, V> {

    override fun setValue(newValue: V): V {
        this.value = newValue
        return newValue
    }

    override fun hashCode(): Int {
        return key.hashCode()
    }

    fun `is`(key: K): Boolean {
        return if (this.key != null) this.key == key else this.key === key
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Pair<*, *>) key == other.key else false
    }

    override fun toString(): String {
        return "[$key=$value]"
    }

}
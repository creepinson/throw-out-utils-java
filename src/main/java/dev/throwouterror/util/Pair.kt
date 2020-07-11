package dev.throwouterror.util

class Pair<K, V>(override val key: K, override var value: V) : MutableMap.MutableEntry<K, V> {

    override fun setValue(value: V): V {
        this.value = value
        return value
    }

    override fun hashCode(): Int {
        return key.hashCode()
    }

    fun `is`(key: K): Boolean {
        return if (this.key != null) this.key == key else this.key === key
    }

    override fun equals(obj: Any?): Boolean {
        return if (obj is Pair<*, *>) key == obj.key else false
    }

    override fun toString(): String {
        return "[$key=$value]"
    }

}
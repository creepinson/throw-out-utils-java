package dev.throwouterror.util

import java.util.*
import java.util.function.Predicate

class PairList<K, V> : ArrayList<Pair<K, V>?> {
    constructor() : super()
    constructor(list: List<Pair<K, V>>) : super(list) {
        updateEntireMap()
    }

    protected var keyIndex = HashMap<K?, Int?>()
    protected var values: MutableList<V?> = ArrayList()
    protected fun updateEntireMap() {
        keyIndex.clear()
        values.clear()
        for (i in 0 until size) {
            keyIndex[this[i]!!.key] = i
            values.add(this[i]!!.value)
        }
    }

    override fun add(e: Pair<K, V>?): Boolean {
        Objects.requireNonNull(e)
        require(!keyIndex.containsKey(e!!.key)) { "Duplicates are not allowed key: " + e.key }
        if (super.add(e)) {
            keyIndex[e.key] = size - 1
            values.add(e.value)
            return true
        }
        return false
    }

    override fun add(index: Int, element: Pair<K, V>?) {
        Objects.requireNonNull(element)
        require(!keyIndex.containsKey(element!!.key)) { "Duplicates are not allowed key: " + element.key }
        super.add(index, element)
        updateEntireMap()
    }

    override fun addAll(c: Collection<Pair<K, V>?>): Boolean {
        Objects.requireNonNull(c)
        for (pair in c) {
            Objects.requireNonNull(pair)
            require(!keyIndex.containsKey(pair!!.key)) { "Duplicates are not allowed key: " + pair.key }
        }
        val sizeBefore = size
        if (super.addAll(c)) {
            for (i in c.indices) {
                val index = sizeBefore + i
                keyIndex[this[index]!!.key] = index
                values.add(this[index]!!.value)
            }
            return true
        }
        return false
    }

    override fun addAll(index: Int, c: Collection<Pair<K, V>?>): Boolean {
        Objects.requireNonNull(c)
        for (pair in c) {
            Objects.requireNonNull(pair)
            require(!keyIndex.containsKey(pair!!.key)) { "Duplicates are not allowed key: " + pair.key }
        }
        if (super.addAll(index, c)) {
            updateEntireMap()
            return true
        }
        return false
    }

    fun add(key: K, value: V): Boolean {
        return add(Pair(key, value))
    }

    operator fun set(key: K, value: V) {
        val pair = getPair(key)
        if (pair != null) pair.value = value
    }

    override fun removeAt(index: Int): Pair<K, V>? {
        var pair: Pair<K, V>?
        if (super.removeAt(index).also { pair = it } != null) {
            updateEntireMap()
            return pair
        }
        return null
    }

    override fun remove(element: Pair<K, V>?): Boolean {
        if (super.remove(element)) {
            updateEntireMap()
            return true
        }
        return false
    }

    override fun removeAll(elements: Collection<Pair<K, V>?>): Boolean {
        if (super.removeAll(elements)) {
            updateEntireMap()
            return true
        }
        return false
    }

    override fun removeIf(filter: Predicate<in Pair<K, V>?>): Boolean {
        if (super.removeIf(filter)) {
            updateEntireMap()
            return true
        }
        return false
    }

    override fun retainAll(elements: Collection<Pair<K, V>?>): Boolean {
        if (super.retainAll(elements)) {
            updateEntireMap()
            return true
        }
        return false
    }

    override fun clear() {
        super.clear()
        keyIndex.clear()
        values.clear()
    }

    override fun set(index: Int, element: Pair<K, V>?): Pair<K, V>? {
        Objects.requireNonNull(element)
        val exisiting = keyIndex[element!!.key]
        require(!(exisiting != null && exisiting != index)) { "Duplicates are not allowed key: " + element.key }
        val old = super.set(index, element)
        if (old != null) {
            keyIndex.remove(old.key)
            keyIndex[element.key] = index
            values[index] = element.value
        }
        return old
    }

    override fun sort(c: Comparator<in Pair<K, V>?>) {
        super.sort(c)
        updateEntireMap()
    }

    fun containsKey(key: K): Boolean {
        return keyIndex.containsKey(key)
    }

    fun indexOfKey(key: K): Int {
        return keyIndex.getOrDefault(key, -1)!!
    }

    fun removeKey(key: K): Boolean {
        val index = keyIndex[key]
        return if (index != null) removeAt(index) != null else false
    }

    fun values(): List<V?> {
        return ArrayList(values)
    }

    fun keys(): Set<K?> {
        return keyIndex.keys
    }

    val first: Pair<K, V>?
        get() = if (isEmpty()) null else get(0)

    val last: Pair<K, V>?
        get() = if (isEmpty()) null else get(size - 1)

    fun getValue(key: K): V? {
        val index = keyIndex[key]
        return if (index != null) get(index)!!.value else null
    }

    fun getPair(key: K): Pair<K, V>? {
        val index = keyIndex[key]
        return index?.let { get(it) }
    }

    companion object {
        private const val serialVersionUID = -7857974025247567866L
    }
}
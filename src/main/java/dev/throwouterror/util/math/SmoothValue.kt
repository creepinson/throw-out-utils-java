package dev.throwouterror.util.math

class SmoothValue @JvmOverloads constructor(val time: Long, initalValue: Double = 0.0) {
    protected var aimed = 0.0
    protected var current = 0.0
    protected var before = 0.0
    protected var timestamp: Long = 0
    fun setStart(value: Double) {
        aimed = value
        current = value
        before = value
        timestamp = 0
    }

    fun set(value: Double) {
        timestamp = System.currentTimeMillis()
        aimed = value
        before = current
    }

    fun tick() {
        if (timestamp != 0L) {
            if (timestamp + time <= System.currentTimeMillis()) {
                current = aimed
                before = current
                timestamp = 0
            } else current = before + (aimed - before) * ((System.currentTimeMillis() - timestamp) / time.toDouble())
        }
    }

    fun current(): Double {
        return current
    }

    fun aimed(): Double {
        return aimed
    }

    init {
        setStart(initalValue)
    }
}
package dev.throwouterror.util.worker

import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

abstract class Worker : Thread() {
    private val shouldWait = AtomicBoolean()
    protected abstract fun processingIsComplete(): Boolean
    protected abstract fun process()
    protected abstract fun cleanUpResources()
    val lock: ReentrantLock = ReentrantLock()

    fun disable() {
        shouldWait.set(false)
    }

    fun enable() {
        shouldWait.set(true)
    }

    override fun run() {
        try {
            val condition = lock.newCondition()
            while (!processingIsComplete()) {
                while (!shouldWait.get()) {
                    lock.withLock {
                        condition.await()
                    }
                }
            }
            process()
        } catch (e: InterruptedException) {
            println("Worker thread stopped")
        } finally {
            cleanUpResources()
        }
    }
}
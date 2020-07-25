package dev.throwouterror.util.module

/**
 * A simple class that manages [Module]'s.
 * The [ModuleManager#enable], [ModuleManager#disable], & [ModuleManager#reload]
 * methods should be called only once by your application.
 * You can use them in any other applications,
 * just make sure that you don't call the methods a second time.
 */
object ModuleManager {
    private var modules: MutableMap<String, Module> = mutableMapOf()

    /**
     * Adds a module to the list of registered modules.
     */
    fun add(m: Module) {
        modules[m.toString()] = m
    }

    /**
     * Retreive a module by its name.
     */
    fun get(name: String): Module? {
        return modules[name]
    }

    /**
     * This should be called by your application only one time.
     */
    fun enable() {
        for (m in modules.values) m.onEnable()
    }

    /**
     * This should be called by your application only one time.
     */
    fun disable() {
        for (m in modules.values) m.onDisable()
    }

    /**
     * This should be called by your application only one time.
     */
    fun reload() {
        for (m in modules.values) m.onReload()
    }
}
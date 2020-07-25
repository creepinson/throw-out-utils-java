package dev.throwouterror.util.module

interface Module {
    fun onEnable()
    fun onReload()
    fun onDisable()
}
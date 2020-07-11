package dev.throwouterror.util

interface Module {
    fun onEnable()
    fun onReload()
    fun onDisable()
}
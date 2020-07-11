package dev.throwouterror.util.data.game

import dev.throwouterror.util.ISerializable

/**
 * @author Creepinson https:/theoparis.com/about
 */
interface IWorld : ISerializable<Any?> {
    val loadedChunks: Collection<IChunk?>?
    fun getChunk(x: Int, z: Int): IChunk?
    val dimension: Int
}
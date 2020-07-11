package dev.throwouterror.util.data.game

import dev.throwouterror.util.math.Tensor
import java.io.Serializable

/**
 * @author Creepinson https:/theoparis.com/about
 */
interface IChunk : Serializable {
    val world: IWorld?
    val x: Int
    val z: Int
    fun setTile(tile: ITile?, position: Tensor?)
    fun getTile(position: Tensor?): ITile?
}
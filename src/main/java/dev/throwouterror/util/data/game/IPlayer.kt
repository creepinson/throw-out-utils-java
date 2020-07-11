package dev.throwouterror.util.data.game

import dev.throwouterror.util.ISerializable
import dev.throwouterror.util.math.Tensor

/**
 * @author Creepinson https:/theoparis.com/about
 */
interface IPlayer : ISerializable<Any?> {
    val world: IWorld?
    var position: Tensor?
}
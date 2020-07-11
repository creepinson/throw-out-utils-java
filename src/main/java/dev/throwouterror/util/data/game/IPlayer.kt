package dev.throwouterror.util.data.game;

import dev.throwouterror.util.ISerializable;
import dev.throwouterror.util.math.Tensor;

/**
 * @author Creepinson https:/theoparis.com/about
 **/
public interface IPlayer extends ISerializable {
	IWorld getWorld();

	Tensor getPosition();

	void setPosition(Tensor position);
}

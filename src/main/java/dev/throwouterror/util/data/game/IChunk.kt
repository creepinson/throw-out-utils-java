package dev.throwouterror.util.data.game;

import java.io.Serializable;

import dev.throwouterror.util.math.Tensor;

/**
 * @author Creepinson https:/theoparis.com/about
 **/
public interface IChunk extends Serializable {
	IWorld getWorld();

	int getX();

	int getZ();

	void setTile(ITile tile, Tensor position);

	ITile getTile(Tensor position);
}

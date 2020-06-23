package me.creepinson.creepinoutils.api.util.data.game;

import java.io.Serializable;

import me.creepinson.creepinoutils.api.util.math.Tensor;

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

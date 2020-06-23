package me.creepinson.creepinoutils.api.util.data.game;

import me.creepinson.creepinoutils.api.util.ISerializable;
import me.creepinson.creepinoutils.api.util.math.Tensor;

/**
 * @author Creepinson https:/theoparis.com/about
 **/
public interface IPlayer extends ISerializable {
	IWorld getWorld();

	Tensor getPosition();

	void setPosition(Tensor position);
}

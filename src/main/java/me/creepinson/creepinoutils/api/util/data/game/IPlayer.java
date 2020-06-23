package me.creepinson.creepinoutils.api.util.data.game;

import me.creepinson.creepinoutils.api.util.SerializableString;
import me.creepinson.creepinoutils.api.util.math.Vector;

/**
 * @author Creepinson https:/theoparis.com/about
 **/
public interface IPlayer extends SerializableString {
    IWorld getWorld();

    Vector getPosition();

    void setPosition(Vector position);
}

package me.creepinson.creepinoutils.api.util.data.game;

import me.creepinson.creepinoutils.api.util.SerializableString;
import me.creepinson.creepinoutils.api.util.math.Vector;

/**
 * @author Creepinson http://gitlab.com/creepinson
 **/
public interface IPlayer extends SerializableString {
    IWorld getWorld();

    Vector getPosition();

    void setPosition(Vector position);
}

package me.creepinson.creepinoutils.api.util.data.game;

import me.creepinson.creepinoutils.api.util.SerializableString;
import me.creepinson.creepinoutils.api.util.math.Vector3;

/**
 * @author Creepinson http://gitlab.com/creepinson
 **/
public interface IPlayer extends SerializableString {
    IWorld getWorld();

    Vector3 getPosition();

    void setPosition(Vector3 position);
}

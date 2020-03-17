package me.creepinson.creepinoutils.api.util.data.game;

import me.creepinson.creepinoutils.api.util.math.Vector3;

import java.io.Serializable;

/**
 * @author Creepinson http://gitlab.com/creepinson
 **/
public interface IPlayer extends Serializable {
    IWorld getWorld();

    Vector3 getPosition();

    void setPosition(Vector3 position);
}

package me.creepinson.creepinoutils.api.util.data.game;

import me.creepinson.creepinoutils.api.util.math.Vector;

import java.io.Serializable;

/**
 * @author Creepinson http://gitlab.com/creepinson
 **/
public interface IChunk extends Serializable {
    IWorld getWorld();

    int getX();

    int getZ();

    void setTile(ITile tile, Vector position);

    ITile getTile(Vector position);
}

package me.creepinson.creepinoutils.api.util.data.game;

import me.creepinson.creepinoutils.api.util.math.Vector3;

/**
 * @author Creepinson http://gitlab.com/creepinson
 **/
public interface IChunk {
    IWorld getWorld();

    int getX();

    int getZ();

    void setTile(ITile tile, Vector3 position);

    ITile getTile(Vector3 position);
}

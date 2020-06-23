package me.creepinson.creepinoutils.api.util.data.game;

import me.creepinson.creepinoutils.api.util.ISerializable;

import java.util.Collection;

/**
 * @author Creepinson https:/theoparis.com/about
 **/
public interface IWorld extends ISerializable {
    Collection<IChunk> getLoadedChunks();

    IChunk getChunk(int x, int z);

    int getDimension();
}

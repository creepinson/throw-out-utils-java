package me.creepinson.creepinoutils.api.util.data.game;

import java.util.Collection;

/**
 * @author Creepinson http://gitlab.com/creepinson
 **/
public interface IWorld {
    Collection<IChunk> getLoadedChunks();

    IChunk getChunk(int x, int z);

    int getDimension();
}

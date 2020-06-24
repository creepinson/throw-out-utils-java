package dev.throwouterror.util.data.game;

import java.util.Collection;

import dev.throwouterror.util.ISerializable;

/**
 * @author Creepinson https:/theoparis.com/about
 **/
public interface IWorld extends ISerializable {
    Collection<IChunk> getLoadedChunks();

    IChunk getChunk(int x, int z);

    int getDimension();
}

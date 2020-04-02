package me.creepinson.creepinoutils.api.util.data.game;

import me.creepinson.creepinoutils.api.util.SerializableString;

import java.util.Collection;

/**
 * @author Creepinson http://gitlab.com/creepinson
 **/
public interface IWorld extends SerializableString {
    Collection<IChunk> getLoadedChunks();

    IChunk getChunk(int x, int z);

    int getDimension();
}

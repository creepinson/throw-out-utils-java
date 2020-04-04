package me.creepinson.creepinoutils.api.util;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author Creepinson http://gitlab.com/creepinson
 **/
public class IteratorUtil {

    public static <T> Iterator<T> forArray(T[] arr) {
        return Arrays.asList(arr).iterator();
    }

}

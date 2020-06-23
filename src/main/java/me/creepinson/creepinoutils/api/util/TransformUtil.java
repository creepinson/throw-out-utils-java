package me.creepinson.creepinoutils.api.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Creepinson https:/theoparis.com/about
 **/
public class TransformUtil {
    public static <T> Set<T> asSet(T[] array) {
        Set<T> set = new HashSet<>(Arrays.asList(array));
        return set;
    }

    public static <T> T make(Supplier<T> supplier) {
        return supplier.get();
    }

    public static <T> T make(T object, Consumer<T> consumer) {
        consumer.accept(object);
        return object;
    }

}

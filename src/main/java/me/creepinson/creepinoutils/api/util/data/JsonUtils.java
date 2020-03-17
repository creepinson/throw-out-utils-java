package me.creepinson.creepinoutils.api.util.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.creepinson.creepinoutils.api.util.math.Vector3;

/**
 * @author Creepinson http://gitlab.com/creepinson
 **/
public class JsonUtils {
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static Gson get() {
        return gson;
    }

    public static String fromVector(Vector3 vector) {
        return get().toJson(vector);
    }
}

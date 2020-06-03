package me.creepinson.creepinoutils.api.util.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Creepinson https:/theoparis.com/about
 **/
public class JsonUtils {
    private static Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();

    public static Gson get() {
        return gson;
    }
}

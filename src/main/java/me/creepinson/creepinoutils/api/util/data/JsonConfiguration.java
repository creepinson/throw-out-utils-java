
package me.creepinson.creepinoutils.api.util.data;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

/**
 * A confiuration utility class for reading and writing data to a json file.
 */
public class JsonConfiguration {

    protected Map<String, Object> configMap = new HashMap<String, Object>();
    private ConfigDefaultsCallback defaultsCallback;

    public Map<String, Object> getConfigMap() {
        return configMap;
    }

    public JsonConfiguration withDefaults(ConfigDefaultsCallback callback) {
        this.defaultsCallback = callback;
        return this;
    }

    protected File file;

    public JsonConfiguration(Path path) {
        this.file = path.toFile();
    }

    public JsonConfiguration(File f) {
        this.file = f;
    }

    public JsonConfiguration write() {
        try {
            String json = JsonUtils.get().toJson(configMap, new TypeToken<Map<String, Object>>() {
            }.getType());
            FileWriter writer = new FileWriter(file);
            // Write to the file you passed
            writer.write(json);
            // Always close when done.
            writer.close();
        } catch (IOException e) {
            // Print an error if something fails. Please use a real logger, not System.out.
            System.out.println("Error creating default configuration.");
        }
        return this;
    }

    public JsonConfiguration create() {
        try {
            // Create the config if it doesn't already exist.
            if (!file.exists() && file.createNewFile()) {
                // Get a default map of blocks. You could just use a blank map, however.
                Map<String, Object> defaultMap = getDefaults();
                // Convert the map to JSON format. There is a built in (de)serializer for it
                // already.
                String json = JsonUtils.get().toJson(defaultMap, new TypeToken<Map<String, Object>>() {
                }.getType());
                FileWriter writer = new FileWriter(file);
                // Write to the file you passed
                writer.write(json);
                // Always close when done.
                writer.close();
            }

            // If the file exists (or we just made one exist), convert it from JSON format
            // to a populated Map object
            configMap = JsonUtils.get().fromJson(new FileReader(file), new TypeToken<Map<String, Object>>() {
            }.getType());
        } catch (IOException e) {
            // Print an error if something fails. Please use a real logger, not System.out.
            System.out.println("Error creating default configuration.");
        }

        return this;
    }

    public Map<String, Object> getDefaults() {
        Map<String, Object> ret = new HashMap<String, Object>();
        defaultsCallback.addDefaults(ret);
        return ret;
    }
}
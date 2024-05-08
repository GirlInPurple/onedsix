package onedsix.core.util;

import com.google.gson.JsonObject;
import onedsix.core.Vars;
import onedsix.core.assets.data.Identifier;

import java.io.File;


/** I18N framework. Looks from files with the name of the specified locale in user settings inside the {@code ./temp/...} folder.<br>
 * An loader would be {@code ./temp/core/en-US.json} or {@code ./temp/jar/basemod-a1.0.0.jar/fr-CA.json} */
@SuppressWarnings("rawtypes")
public class Translation {
    private static final Logger L = new Logger(Translation.class);
    
    static class Metadata {
        public final File extractionDirectory = null;
        public static Metadata getModMetadata(String modid) {return null;}
    }
    
    private Identifier ident;
    private String modid;
    private Metadata metadata;
    private JsonObject jsonObject;
    private String key;
    
    public Translation(Identifier identifier, String key) {
        this.ident = identifier;
        this.key = key;
    }
    
    public Translation(String modid, String key) {
        this.modid = modid;
        this.key = key;
    }
    
    public Translation(Metadata metadata, String key) {
        this.metadata = metadata;
        this.key = key;
    }
    
    public Translation(JsonObject json, String key) {
        this.jsonObject = json;
        this.key = key;
    }
    
    
    public static String Translate(Identifier identifier, String key) {
        return Translate(identifier.modid, key);
    }
    
    public static String Translate(String modid, String key) {
        Metadata modData = Metadata.getModMetadata(modid);
        if (modData != null) {
            return Translate(modData, key);
        }
        else {
            return key;
        }
    }
    
    public static String Translate(Metadata metadata, String key) {
        File jsonFile = FileHandler.findInDirectory(Vars.locale+".json", metadata.extractionDirectory);
        JsonObject jsonObject = FileHandler.JSON.readJSON(jsonFile);
        return Translate(jsonObject, key);
    }
    
    public static String Translate(JsonObject json, String key) {
        String output;
        try {
            output = json.get(key).getAsString();
            if (output == null) output = key;
        }
        catch (Exception e) {
            L.error(e.getMessage(), e);
            output = key;
        }
        return output;
    }
    
    @Override public String toString() {
        if (this.jsonObject != null) {
            return Translate(jsonObject, key);
        } else
        if (this.metadata != null) {
            return Translate(metadata, key);
        } else
        if (this.modid != null) {
            return Translate(modid, key);
        } else
        if (this.ident != null) {
            return Translate(ident, key);
        } else {
            return this.key;
        }
    }
}

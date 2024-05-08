package onedsix.core.systems;

import onedsix.core.Vars;
import onedsix.core.event.SettingsChangeEventManager;
import onedsix.core.util.FileHandler;
import onedsix.core.util.Logger;
import onedsix.core.util.*;

import java.io.*;
import java.nio.file.Files;

public class GameSettings {
    
    private static final Logger L = new Logger(GameSettings.class);
    
    public static class SettingsJson {
        public boolean useSplashText;
        public String customAssetPath;
        public int targetFps;
        public boolean useVsync;
        
        public SettingsJson() {
            this.useSplashText = true;
            this.customAssetPath = "C:/Your/Path/Here/";
            this.targetFps = 60;
            this.useVsync = true;
        }
    }
    
    public static void readSettings() {
        try {
            File settingsFile = new File("./settings.json");
            if (!settingsFile.exists()) {
                settingsFile.createNewFile();
                SettingsJson defaultSettings = new SettingsJson();
                Files.write(settingsFile.toPath(), Vars.GSON.toJson(defaultSettings).getBytes());
            }
    
            Vars.settings = FileHandler.JSON.readJSON(settingsFile, SettingsJson.class);
        }
        catch (IOException ioe) {
            L.error(ioe.getMessage(), ioe);
        }
        finally {
            SettingsChangeEventManager.sendSettingsChangedEvent(Vars.settings);
        }
    }
    
    public static void writeSettings() {
        try {
            File settingsFile = new File("./settings.json");
            Files.write(settingsFile.toPath(), Vars.GSON.toJson(Vars.settings).getBytes());
        }
        catch (IOException ioe) {
            L.error(ioe.getMessage(), ioe);
        }
        finally {
            SettingsChangeEventManager.sendSettingsChangedEvent(Vars.settings);
        }
    }
}

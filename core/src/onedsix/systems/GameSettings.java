package onedsix.systems;

import onedsix.util.FileHandler;
import onedsix.Vars;
import onedsix.event.settings.SettingsChangeManager;

import java.io.*;
import java.nio.file.Files;

public class GameSettings {
    
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
            
            Vars.settings = FileHandler.JSON.readJSON(settingsFile.toPath(), SettingsJson.class);
            SettingsChangeManager.changeSettings(Vars.settings);
            
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}

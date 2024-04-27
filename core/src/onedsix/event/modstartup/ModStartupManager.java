package onedsix.event.modstartup;

import onedsix.util.Logger;

import java.util.ArrayList;
import java.util.List;

public class ModStartupManager {
    private static final Logger L = new Logger(ModStartupManager.class);
    private static final List<ModStartupListener> listeners = new ArrayList<>();
    
    public static void addSettingsChangeListener(ModStartupListener listener) {
        listeners.add(listener);
    }
    public static void removeSettingsChangeListener(ModStartupListener listener) {
        listeners.remove(listener);
    }
    
    public static void modStartup() {
        ModStartupEvent event = new ModStartupEvent(ModStartupManager.class);
        L.info("Mod Startup event tripped");
        for (ModStartupListener listener : listeners) {
            listener.onStartup(event);
        }
    }
}

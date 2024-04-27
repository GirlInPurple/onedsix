package onedsix.event.modstartup;

import java.util.ArrayList;
import java.util.List;

public class ModStartupManager {
    private static final List<ModStartupListener> listeners = new ArrayList<>();
    
    public static void addSettingsChangeListener(ModStartupListener listener) {
        listeners.add(listener);
    }
    
    public static void removeSettingsChangeListener(ModStartupListener listener) {
        listeners.remove(listener);
    }
    
    public static void modStartup() {
        ModStartupEvent event = new ModStartupEvent(ModStartupManager.class);
        for (ModStartupListener listener : listeners) {
            listener.onStartup(event);
        }
    }
}

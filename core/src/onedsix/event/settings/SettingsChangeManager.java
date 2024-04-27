package onedsix.event.settings;

import onedsix.systems.GameSettings.SettingsJson;

import java.util.ArrayList;
import java.util.List;

public class SettingsChangeManager {
    private static final List<SettingsChangeListener> listeners = new ArrayList<>();
    
    public static void addSettingsChangeListener(SettingsChangeListener listener) {
        listeners.add(listener);
    }
    
    public static void removeSettingsChangeListener(SettingsChangeListener listener) {
        listeners.remove(listener);
    }
    
    public static void changeSettings(SettingsJson sj) {
        SettingsChangeEvent event = new SettingsChangeEvent(SettingsChangeManager.class, sj);
        for (SettingsChangeListener listener : listeners) {
            listener.settingsChanged(event, sj);
        }
    }
}

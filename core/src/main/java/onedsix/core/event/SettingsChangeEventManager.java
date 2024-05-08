package onedsix.core.event;

import onedsix.core.systems.GameSettings.SettingsJson;
import onedsix.core.util.Logger;

import java.util.*;

public class SettingsChangeEventManager  {
    
    public static class SettingsChangeEvent extends EventObject {
        public SettingsChangeEvent(Object source) {
            super(source);
        }
    }
    
    public interface SettingsChangeListener extends EventListener {
        void settingsChanged(SettingsChangeEvent event, SettingsJson sj);
    }
    
    private static final Logger L = new Logger(SettingsChangeEventManager.class);
    private static final List<SettingsChangeListener> listeners = new ArrayList<>();
    
    public static void addSettingsChangeListener(SettingsChangeListener listener) {
        listeners.add(listener);
    }
    
    public static void sendSettingsChangedEvent(SettingsJson sj) {
        SettingsChangeEvent event = new SettingsChangeEvent(SettingsChangeEventManager.class);
        L.info("Settings Changed event tripped");
        for (SettingsChangeListener listener : listeners) {
            listener.settingsChanged(event, sj);
        }
    }
}

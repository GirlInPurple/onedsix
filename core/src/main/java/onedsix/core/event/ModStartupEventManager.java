package onedsix.core.event;

import com.moandjiezana.toml.Toml;
import onedsix.core.util.Logger;

import java.util.*;

public class ModStartupEventManager {
    
    public static class ModStartupEvent extends EventObject {
        public ModStartupEvent(Object source) {
            super(source);
        }
    }
    
    public interface ModStartupListener extends EventListener {
        void onStartup(ModStartupEvent event, Toml[] otherMods);
    }
    
    private static final Logger L = new Logger(ModStartupEventManager.class);
    private static final List<ModStartupListener> listeners = new ArrayList<>();
    
    public static void addModStartupListener(ModStartupListener listener) {
        listeners.add(listener);
    }
    
    public static void sendModInitEvent(Toml[] otherMods) {
        ModStartupEvent event = new ModStartupEvent(ModStartupEventManager.class);
        L.info("Mod Startup event tripped");
        for (ModStartupListener listener : listeners) {
            listener.onStartup(event, otherMods);
        }
    }
}

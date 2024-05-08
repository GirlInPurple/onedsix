package onedsix.core.event;

import com.moandjiezana.toml.Toml;
import onedsix.core.util.Logger;

import java.util.*;

public class AsmPhaseEventManager {
    
    public static class AsmPhaseEvent extends EventObject {
        public AsmPhaseEvent(Object source) {
            super(source);
        }
    }
    
    public interface AsmPhaseListener extends EventListener {
        void onAsm(AsmPhaseEvent event, Toml[] otherMods);
    }
    
    private static final Logger L = new Logger(AsmPhaseEventManager.class);
    private static final List<AsmPhaseListener> listeners = new ArrayList<>();
    
    public static void addAsmStartupListener(AsmPhaseListener listener) {
        listeners.add(listener);
    }
    
    public static void sendAsmInitEvent(Toml[] otherMods) {
        AsmPhaseEvent event = new AsmPhaseEvent(AsmPhaseEventManager.class);
        L.info("ASM event tripped");
        for (AsmPhaseListener listener : listeners) {
            listener.onAsm(event, otherMods);
        }
    }
}

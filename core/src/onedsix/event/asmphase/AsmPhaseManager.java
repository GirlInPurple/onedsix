package onedsix.event.asmphase;

import onedsix.util.Logger;

import java.util.ArrayList;
import java.util.List;

public class AsmPhaseManager {
    private static final Logger L = new Logger(AsmPhaseManager.class);
    private static final List<AsmPhaseListener> listeners = new ArrayList<>();
    
    public static void addSettingsChangeListener(AsmPhaseListener listener) {
        listeners.add(listener);
    }
    public static void removeSettingsChangeListener(AsmPhaseListener listener) {
        listeners.remove(listener);
    }
    
    public static void asmEvent() {
        AsmPhaseEvent event = new AsmPhaseEvent(AsmPhaseManager.class);
        L.info("ASM event tripped");
        for (AsmPhaseListener listener : listeners) {
            listener.onAsm(event);
        }
    }
}

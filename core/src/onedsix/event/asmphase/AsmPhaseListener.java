package onedsix.event.asmphase;

import java.util.EventListener;

public interface AsmPhaseListener extends EventListener {
    void onAsm(AsmPhaseEvent event);
}

package onedsix.event.modstartup;

import java.util.EventListener;

public interface ModStartupListener extends EventListener {
    void onStartup(ModStartupEvent event);
}

package onedsix;

import onedsix.event.modstartup.ModStartupEvent;
import onedsix.event.modstartup.ModStartupListener;
import org.slf4j.*;

public class BaseMod implements ModStartupListener {
    
    private static final Logger L = LoggerFactory.getLogger(BaseMod.class);
    
    @Override
    public void onStartup(ModStartupEvent event) {
    
    }
}

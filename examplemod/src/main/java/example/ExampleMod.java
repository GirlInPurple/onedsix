package onedsix;

import example.YourItem;
import onedsix.event.modstartup.ModStartupEvent;
import onedsix.event.modstartup.ModStartupListener;
import onedsix.gen.DatagenHandler;
import org.slf4j.*;

public class ExampleMod implements ModStartupListener {
    
    private static final Logger L = LoggerFactory.getLogger(ExampleMod.class);
    
    @Override
    public void onStartup(ModStartupEvent event) {
        addCustomItem(YourItem.class);
    }
}

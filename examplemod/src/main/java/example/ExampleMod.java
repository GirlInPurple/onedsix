package example;

import onedsix.event.modstartup.ModStartupEvent;
import onedsix.event.modstartup.ModStartupListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static onedsix.gen.DatagenHandler.addCustomItem;

@SuppressWarnings("unused")
public class ExampleMod implements ModStartupListener {
    
    private static final Logger L = LoggerFactory.getLogger(ExampleMod.class);
    
    @Override
    public void onStartup(ModStartupEvent event) {
        addCustomItem(YourItem.class);
    }
}

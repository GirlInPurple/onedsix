package example;

import onedsix.event.modstartup.ModStartupEvent;
import onedsix.event.modstartup.ModStartupListener;
import onedsix.util.Logger;

import static onedsix.gen.DatagenHandler.addCustomItem;

@SuppressWarnings("unused")
public class ExampleMod implements ModStartupListener {
    
    private static final Logger L = new Logger(ExampleMod.class);
    
    @Override
    public void onStartup(ModStartupEvent event) {
        addCustomItem(YourItem.class);
    }
}

package example;

import com.moandjiezana.toml.Toml;
import onedsix.core.event.ModStartupEventManager.*;
import onedsix.core.util.Logger;

@SuppressWarnings("unused")
public class ExampleMod implements ModStartupListener {
	public static final String MODID = "examplemod";
    private static final Logger L = new Logger(ExampleMod.class);

	@Override
	public void onStartup(ModStartupEvent event, Toml[] otherMods) {

	}
}

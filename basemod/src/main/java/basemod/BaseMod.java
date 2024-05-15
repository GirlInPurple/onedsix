package basemod;

import lombok.extern.slf4j.Slf4j;
import onedsix.loader.api.event.Initialization.*;
import onedsix.core.util.Logger;

@SuppressWarnings("unused")
@Slf4j
public class BaseMod implements InitializeListener {

    private static final Logger L = new Logger(BaseMod.class);
    public static final String MOD_ID = "basemod";

	@Override
	public void onInitialize(InitialzeEvent event) {
		log.info("Basemod Has Loaded!");
	}
}

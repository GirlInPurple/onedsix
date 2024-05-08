package onedsix.api;

import onedsix.core.util.Logger;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

//import static onedsix.mods.ModMetadata.getModList;

/** Public API for mod handling. Contains methods for mod manipulation and checking. */
@SuppressWarnings("unused")
public final class ModHelper {
    private static final Logger L = new Logger(ModHelper.class);
    
    /** Checks if a specified ModId is loaded. */
    /*
    public boolean isModLoaded(String modid) {
        AtomicBoolean returns = new AtomicBoolean(false);
        getModList().stream().iterator().forEachRemaining((m) -> {
            if (Objects.equals(m.modProp.modId, modid)) {
                returns.set(true);
            }
        });
        return returns.get();
    }*/
}

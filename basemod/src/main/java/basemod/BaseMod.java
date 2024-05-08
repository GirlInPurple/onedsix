package basemod;

import com.moandjiezana.toml.Toml;
import onedsix.core.util.Logger;

@SuppressWarnings("unused")
public class BaseMod implements ModStartupListener, AsmPhaseListener {
    
    private static final Logger L = new Logger(BaseMod.class);
    public static final String MOD_ID = "basemod";
    
    @Override
    public void onStartup(ModStartupEvent event, Toml[] otherMods) {
        L.info("Started!");
    }
    
    @Override
    public void onAsm(AsmPhaseEvent event, Toml[] otherMods) {
        L.info("ASM Phase!");
    }
}

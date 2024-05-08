package onedsix.loader;

import onedsix.core.util.Logger;

public class ModLoader {
    private static final Logger L = new Logger(ModLoader.class);
    
    /** Phases during startup and gameplay */
    public static final class Phases {
        /** Mod Discovery, Version Confirmations, Updates
         * @see ModDiscovery#jarDiscovery()
         * */
        public static final int DISCOVERY = -5;
        /** ASM Init
         * @see onedsix.core.event.AsmPhaseEventManager
         * */
        public static final int ASM_INIT = -4;
        /** Mod Init
         * @see onedsix.core.event.ModStartupEventManager
         * */
        public static final int MOD_INIT = -3;
        /** Data-gen and Compatibility */
        public static final int DATAGEN_COMPAT = -2;
        /** Final Preparations (Not used by Core or BaseMod) */
        public static final int FINAL_PREP = -1;
        /** Loading Complete */
        public static final int LOAD_COMPLETE = 0;
        /** OnedsixServer Connection Attempt / Begin */
        public static final int CONNECTION_BEGIN = 1;
        /** In-Game */
        public static final int COMPLETE = 2;
    }
    
    public static void loadMods() {
        
        // Get Mods
        ModDiscovery.jarDiscovery();
        ModDiscovery.zipDiscovery();
    
        // Phases
        Vars.currentPhase = Phases.ASM_INIT;
        AsmPhaseEventManager.sendAsmInitEvent(null);
        Vars.currentPhase = Phases.MOD_INIT;
        ModStartupEventManager.sendModInitEvent(null);
    }
}

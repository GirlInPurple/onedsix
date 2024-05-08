package onedsix.loader;

/** Holds all the possible errors that cna be encountered during mod loading. */
public class ModErrors {
    
    /** Occurs whenever there an is a cyclic dependency within two or more mods.
     * Is thrown during mod loading whenever one mod requests to be run after a mod wth requests the first mod to be run.<br>
     * Mod1 > Mod2 > Mod1 > Mod2 > CyclicDependencyError */
    public static class CyclicDependencyError extends Error {
        public CyclicDependencyError() {super();}
        public CyclicDependencyError(String error) {super(error);}
    }
}

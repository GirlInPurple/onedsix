package basemod;

/** The base of all modules in BaseMod. */
public abstract class AbstractModule {
    
    protected final String moduleId;
    
    protected AbstractModule(String moduleId) {
        this.moduleId = moduleId;
    }
    
    /** Overwrite this to add a new module to BaseMod. */
    public abstract void startModule();
    
}

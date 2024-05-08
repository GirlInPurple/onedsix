package onedsix.core.assets.data;

import onedsix.core.assets.abstracts.Asset;

import java.util.LinkedList;

@SuppressWarnings("rawtypes") // Identifier and Class make the compiler angry
public class Registry {
    
    private static final LinkedList<Identifier> registry = new LinkedList<>();
    public static void register(Identifier<? extends Asset> ident) {registry.add(ident);}
    public static LinkedList<Identifier> getRegistry() {return registry;}
    
    public static class BrokenRegistryException extends Exception {
    
    }
    
}

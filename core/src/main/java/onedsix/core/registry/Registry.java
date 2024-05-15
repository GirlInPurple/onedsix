package onedsix.core.registry;

import onedsix.core.assets.abstracts.Asset;
import onedsix.core.assets.data.Identifier;

import java.util.LinkedList;

public class Registry {

    private static final LinkedList<Identifier<? extends Asset>> registry = new LinkedList<>();
    public static void register(Identifier<? extends Asset> ident) {registry.add(ident);}
    public static LinkedList<Identifier<? extends Asset>> getRegistry() {return registry;}

    public static class BrokenRegistryException extends Exception {

    }

	public class RegistryEntry {

	}

}

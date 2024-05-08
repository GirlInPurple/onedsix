package onedsix.core.assets.abstracts;

import onedsix.core.assets.data.Identifier;

import java.util.Arrays;
import java.util.LinkedList;

public abstract class Attribute extends Asset {
    
    public final Identifier<? extends Attribute> Ident;
    public Object value;
    /**Limits what {@link Asset} abstractions can use it. The attribute will remain on the asset for compatibility reasons, but it will not be run.<br>
     * If this is {@code null} or empty, it is treated as if it's applicable to all.<br>
     * Other mods may add onto it at any time.*/
    public LinkedList<Class<? extends Asset>> applicable;
    
    
    public Attribute(Identifier<? extends Attribute> ident, LinkedList<Class<? extends Asset>> applicableTo, String name) {
        super(ident, null, name);
        this.Ident = ident;
        this.applicable = applicableTo;
    }
    
    public Attribute(Identifier<? extends Attribute> ident, Class<? extends Asset>[] applicableTo, String name) {
        super(ident, null, name);
        this.Ident = ident;
        this.applicable = (LinkedList<Class<? extends Asset>>) Arrays.asList(applicableTo);
    }
    
    public Attribute(Identifier<? extends Attribute> ident, LinkedList<Class<? extends Asset>> applicableTo) {
        super(ident);
        this.Ident = ident;
        this.applicable = applicableTo;
    }
    
    public Attribute(Identifier<? extends Attribute> ident, Class<? extends Asset>[] applicableTo) {
        super(ident);
        this.Ident = ident;
        this.applicable = (LinkedList<Class<? extends Asset>>) Arrays.asList(applicableTo);
    }
    
    public Attribute(Identifier<? extends Attribute> ident) {
        super(ident);
        this.Ident = ident;
    }
    
    
    protected final boolean isApplicable(Class<? extends Asset> clazz) {
        return applicable == null || applicable.isEmpty() || applicable.contains(clazz);
    }
    
    public Object getObject() {return null;}
    
    public int getInt() {return 0;}
    public long getLong() {return 0L;}
    public float getFloat() {return 0F;}
    public double getDouble() {return 0D;}
    
    public boolean getBool() {return false;}
    
    public String getString() {return null;}
}

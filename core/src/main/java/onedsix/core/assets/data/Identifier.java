package onedsix.core.assets.data;

import onedsix.core.assets.abstracts.Asset;

public class Identifier<T extends Asset> {
    
    public final Class<T> clazz;
    public final String modid;
    
    public Identifier(Class<T> clazz, String modid) {
        this.clazz = clazz;
        this.modid = modid;
    }
    
    @Override
    public String toString() {
        return modid+":"+ clazz.getName();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identifier<?> that = (Identifier<?>) o;
        return clazz.equals(that.clazz) && modid.equals(that.modid);
    }
}

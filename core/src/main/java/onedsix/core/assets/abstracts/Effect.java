package onedsix.core.assets.abstracts;

import onedsix.core.assets.data.Attributes;
import onedsix.core.assets.data.Identifier;

public abstract class Effect extends Asset {
    public int level;
    public boolean isPositive;
    
    public Effect(Identifier<? extends Effect> ident, Attributes attributes, String name, boolean isPositive) {
        super(ident, attributes, name);
        this.isPositive = isPositive;
    }

}

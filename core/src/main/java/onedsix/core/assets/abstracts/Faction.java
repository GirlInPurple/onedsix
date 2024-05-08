package onedsix.core.assets.abstracts;

import onedsix.core.assets.data.Attributes;
import onedsix.core.assets.data.Identifier;
import org.jetbrains.annotations.Nullable;

public abstract class Faction extends Asset {
    protected Faction(Identifier<? extends Perk> ident, Attributes attributes, @Nullable String name) {
        super(ident, attributes, name);
    }
}

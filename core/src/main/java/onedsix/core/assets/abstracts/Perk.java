package onedsix.core.assets.abstracts;

import onedsix.core.assets.data.Attributes;
import onedsix.core.assets.data.Identifier;
import org.jetbrains.annotations.Nullable;

public abstract class Perk extends Asset {
    protected Perk(Identifier<? extends Perk> ident, Attributes attributes, @Nullable String name) {
        super(ident, attributes, name);
    }
}

package basemod.util;

import onedsix.core.assets.abstracts.Attribute;
import onedsix.core.assets.data.Identifier;
import onedsix.core.util.Translation;

import java.util.LinkedList;

import static basemod.BaseMod.MOD_ID;
import static onedsix.core.registry.Registry.register;

public class Level extends Attribute {

    public int level;

    public Level(int level) {
        super(
                new Identifier<>(Level.class, MOD_ID),
                new LinkedList<>(),
                new Translation(MOD_ID, "item.basemod.level").toString()
        );
        this.level = level;

        register(new Identifier<>(Level.class, MOD_ID));
    }

    @Override public int getInt() {return level;}
}

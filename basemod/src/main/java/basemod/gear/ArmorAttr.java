package basemod.gear;

import onedsix.core.assets.abstracts.Attribute;
import onedsix.core.assets.data.Identifier;
import onedsix.core.util.Translation;

import java.util.LinkedList;

import static basemod.BaseMod.MOD_ID;
import static onedsix.core.registry.Registry.register;

public class ArmorAttr extends Attribute {

    public long armor;

    public ArmorAttr(int armor) {
        super(
                new Identifier<>(ArmorAttr.class, MOD_ID),
                new LinkedList<>(),
                new Translation(MOD_ID, "item.basemod.armor").toString()
        );
        this.armor = armor;

        register(new Identifier<>(ArmorAttr.class, MOD_ID));
    }

    @Override public long getLong() {return armor;}
}


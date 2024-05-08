package basemod.gear;

import main.java.onedsix.core.assets.abstracts.Attribute;
import main.java.onedsix.core.assets.data.Identifier;
import main.java.onedsix.core.util.Translation;

import java.util.LinkedList;

import static basemod.BaseMod.MOD_ID;

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


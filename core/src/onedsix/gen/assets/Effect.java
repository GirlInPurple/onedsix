package onedsix.gen.assets;

import lombok.Getter;
import lombok.Setter;

public class Effect extends Equipable {
    @Getter boolean isPositive;
    @Getter @Setter private Attributes attributes;
    
    public Effect(boolean isPositive, Attributes attributes) {
        this.isPositive = isPositive;
        this.attributes = attributes;
    }

}

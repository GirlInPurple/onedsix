package onedsix.gen.assets;

import com.badlogic.gdx.graphics.Texture;

public class Effect {
    //public String name;
    public Attributes attributes;
    public Texture img;
    public int level;
    public boolean isPositive;
    
    public Effect(boolean isPositive, Attributes attributes) {
        this.isPositive = isPositive;
        this.attributes = attributes;
    }

}

package basemod.gear;

import basemod.Level;
import basemod.consumables.Nutrition;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import main.java.onedsix.core.assets.data.Attributes;
import main.java.onedsix.core.assets.data.Identifier;
import main.java.onedsix.core.assets.abstracts.Item;
import main.java.onedsix.core.assets.data.Recipe;

import java.util.Arrays;
import java.util.LinkedList;

public abstract class ArmorItem extends Item {
    
    private static final Attributes defaultAttr = new Attributes(new LinkedList<>(Arrays.asList(
        new ArmorAttr(0),
        new Level(0)
    )));
    
    public ArmorItem(Identifier<? extends ArmorItem> ident, Attributes attributes, String name, Recipe recipe, long roughCost, Nutrition nutrition) {
        super(ident, attributes, name, recipe, roughCost);
    }
    
    public ArmorItem(Identifier<? extends ArmorItem> ident, Attributes attributes, String name, String[] flavorText, Texture img, Model model, int level, Recipe recipe, long roughCost, Nutrition nutrition) {
        super(ident, attributes, name, recipe, roughCost);
    }
}

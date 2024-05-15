package basemod.consumables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import onedsix.core.assets.data.Attributes;
import onedsix.core.assets.data.Identifier;
import onedsix.core.assets.abstracts.Item;
import onedsix.core.assets.data.Recipe;

public abstract class Food extends Item {

    public Nutrition nutrition;

    public Food(Identifier<? extends Item> ident, Attributes attributes, String name, Recipe recipe, long roughCost, Nutrition nutrition) {
        super(ident, attributes, name, recipe, roughCost);
        this.nutrition = nutrition;
    }

    public Food(Identifier<? extends Item> ident, Attributes attributes, String name, String[] flavorText, Texture img, Model model, int level, Recipe recipe, long roughCost, Nutrition nutrition) {
        super(ident, attributes, name, recipe, roughCost);
        this.nutrition = nutrition;
    }
}

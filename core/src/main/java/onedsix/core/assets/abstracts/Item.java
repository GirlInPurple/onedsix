package onedsix.core.assets.abstracts;

import com.badlogic.gdx.graphics.g3d.Model;
import onedsix.core.assets.data.Attributes;
import onedsix.core.assets.data.Identifier;
import onedsix.client.ClientPlayer;
import onedsix.core.assets.data.Recipe;
import org.jetbrains.annotations.Nullable;

public abstract class Item extends Asset {
    public String[] flavorText;
    public Model model;
    public int level;
    public Recipe recipe;
    public long roughCost;

    public Item(Identifier<? extends Item> ident, Attributes attributes, @Nullable String name, Recipe recipe, long roughCost) {
        super(ident, attributes, name);
        this.recipe = recipe;
        this.roughCost = roughCost;
    }

    public Item(Identifier<? extends Item> ident, Attributes attributes, @Nullable String name, String[] flavorText, Model model, int level, Recipe recipe, long roughCost) {
        super(ident, attributes, name);
        this.flavorText = flavorText;
        this.model = model;
        this.level = level;
        this.recipe = recipe;
        this.roughCost = roughCost;
    }

    public long roughCost() {
        long amount = 0;

        amount += recipe.levelNeeded * 0.12;
        amount += recipe.perksNeeded.size() * 0.16;
        amount += recipe.factionsNeeded.size() * 0.18;
        for (Item i : recipe.itemsNeeded.keySet()) {
            amount += i.roughCost * 1.05;
        }

        return amount;
    }
    /** Called whenever the item is on the ground in a cell and the player interacts with it. */
    public abstract void onInteract(ClientPlayer player);
    /** Called whenever the item is on the ground in a cell and the player interacts with it during battle. */
    public abstract void onInteractBattle(ClientPlayer player);
    /** Called whenever the item is used when inside the player's inventory. */
    public abstract void onUse(ClientPlayer player);
    /** Called whenever the item is used when inside the player's inventory during battle. */
    public abstract void onUseBattle(ClientPlayer player);
}

package onedsix.gen.assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import onedsix.Player;

public abstract class Item {
    public String name;
    public String[] flavorText;
    public Attributes attributes;
    public Texture img;
    public Model model;
    public int level;
    public Recipe recipe;
    public long roughCost;

    public Item(Attributes attributes, Recipe recipe, long roughCost) {
        this.attributes = attributes;
        this.recipe = recipe;
        this.roughCost = roughCost;
    }
    
    public Item(String name, String[] flavorText, Attributes attributes, Texture img, Model model, int level, Recipe recipe, long roughCost) {
        this.name = name;
        this.flavorText = flavorText;
        this.img = img;
        this.model = model;
        this.level = level;
        this.attributes = attributes;
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
    public abstract void onInteract(Player player);
    /** Called whenever the item is on the ground in a cell and the player interacts with it during battle. */
    public abstract void onInteractBattle(Player player);
    /** Called whenever the item is used when inside the player's inventory. */
    public abstract void onUse(Player player);
    /** Called whenever the item is used when inside the player's inventory during battle. */
    public abstract void onUseBattle(Player player);
}

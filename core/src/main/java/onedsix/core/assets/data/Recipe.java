package onedsix.core.assets.data;

import onedsix.core.assets.abstracts.Faction;
import onedsix.core.assets.abstracts.Item;
import onedsix.core.assets.abstracts.Perk;

import java.util.List;
import java.util.Map;

public class Recipe {

    public final Map<Item, Integer> itemsNeeded;
    public final Map<Faction, Integer> factionsNeeded;
    public final List<Perk> perksNeeded;
    public final Long levelNeeded;

    public Recipe(Map<Item, Integer> recipe, Map<Faction, Integer> factionsNeeded, List<Perk> perksNeeded, Long levelNeeded) {
        this.itemsNeeded = recipe;
        this.factionsNeeded = factionsNeeded;
        this.perksNeeded = perksNeeded;
        this.levelNeeded = levelNeeded;
    }
}

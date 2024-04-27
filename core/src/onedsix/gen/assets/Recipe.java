package onedsix.gen.assets;

import lombok.Getter;

import java.util.List;
import java.util.Map;

public class Recipe {

    @Getter private final Map<Item, Integer> recipe;
    @Getter private final Map<Faction, Integer> factionsNeeded;
    @Getter private final List<Perk> perksNeeded;
    @Getter private final Long levelNeeded;

    public Recipe(Map<Item, Integer> recipe, Map<Faction, Integer> factionsNeeded, List<Perk> perksNeeded, Long levelNeeded) {
        this.recipe = recipe;
        this.factionsNeeded = factionsNeeded;
        this.perksNeeded = perksNeeded;
        this.levelNeeded = levelNeeded;
    }
}

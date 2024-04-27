package onedsix.gen.assets;

import lombok.Getter;
import lombok.Setter;

public class Item extends Equipable {
    
    @Getter @Setter private Attributes attributes;
    @Getter @Setter private Recipe recipe;
    @Getter private final long roughCost;

    public Item(Attributes attributes, Recipe recipe, long roughCost) {
        this.attributes = attributes;
        this.recipe = recipe;
        this.roughCost = roughCost;
    }

    public long roughCost() {
        long amount = 0;

        amount += recipe.getLevelNeeded() * 0.12;
        amount += recipe.getPerksNeeded().size() * 0.16;
        amount += recipe.getFactionsNeeded().size() * 0.18;
        for (Item i : recipe.getRecipe().keySet()) {
            amount += i.getRoughCost() * 1.05;
        }

        return amount;
    }
    
    public class Food extends Item {
        
        @Getter @Setter private long calories;
        @Getter @Setter private Nutrition nutrition;
        @Getter @Setter private long thirst;
        
        public Food(Attributes attributes, Recipe recipe, long roughCost, long calories, long thirst) {
            super(attributes, recipe, roughCost);
            this.calories = calories;
            this.thirst = thirst;
        }
        
        class Nutrition {
            @Getter @Setter private long fruits = 50;
            @Getter @Setter private long[] fruitBounds = {25, 75};
            @Getter @Setter private long grains = 50;
            @Getter @Setter private long[] grainBounds = {0, 100};
            @Getter @Setter private long vegetables = 50;
            @Getter @Setter private long[] vegetableBounds = {Long.MIN_VALUE, Long.MAX_VALUE};
            @Getter @Setter private long protein = 50;
            @Getter @Setter private long[] proteinBounds = {20, 90};
            @Getter @Setter private long dairy = 50;
            @Getter @Setter private long[] dairyBounds = {0, 50};
            
            protected Nutrition() {}
            
            public boolean[] checkNutrition() {
                boolean[] out = {true, true, true, true, true};
                if (notWithinBounds(this.fruits, fruitBounds)) {out[0] = false;}
                if (notWithinBounds(this.grains, grainBounds)) {out[1] = false;}
                if (notWithinBounds(this.vegetables, vegetableBounds)) {out[2] = false;}
                if (notWithinBounds(this.protein, proteinBounds)) {out[3] = false;}
                if (notWithinBounds(this.dairy, dairyBounds)) {out[4] = false;}
                return out;
            }
            
            private boolean notWithinBounds(long value, long[] bounds) {
                return value < bounds[0] || value > bounds[1];
            }
        }
    }
    
    public class Weapon extends Item {
        protected Weapon(Attributes attributes, Recipe recipe, long roughCost) {
            super(attributes, recipe, roughCost);
        }
    }
}

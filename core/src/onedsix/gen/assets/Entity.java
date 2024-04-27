package onedsix.gen.assets;

import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

public class Entity {
    @Getter @Setter private Decal img;
    @Getter @Setter private HashMap<Decal, Long> queuedFrames;
    @Getter @Setter private long sinceLastFrame;
    @Getter @Setter private String name;
    @Getter @Setter private int level;
    @Getter @Setter private float experience;
    @Getter @Setter private int karma;
    @Getter @Setter private int cash;
    @Getter @Setter private HashMap<Faction, Integer> factionStanding;
    @Getter @Setter private boolean factionLocked;
    @Getter @Setter private int armor;
    @Getter @Setter private float healthCurrent;
    @Getter @Setter private float healthMaximum;
    @Getter @Setter private int staminaCurrent;
    @Getter @Setter private int staminaMaximum;
    @Getter @Setter private int calories;
    @Getter @Setter private int nutrition;
    @Getter @Setter private int thirst;
    @Getter @Setter private double temperature;
    @Getter @Setter private float sicknessChance;
    @Getter @Setter private int absorbedRadiation;
    @Getter @Setter private int outputRadiation;
    @Getter @Setter private List<Effect> statusEffects;
    @Getter @Setter private List<Item> inventory;
    @Getter @Setter private List<Perk> perks;
    @Getter @Setter private Vector3 position;
    @Getter @Setter private float speed;

    public Entity(
            Decal img,
            String name,
            int cash,
            HashMap<Faction, Integer> factionStanding,
            List<Effect> statusEffects,
            List<Item> inventory,
            List<Perk> perks,
            Vector3 position
    ){
        this.img = img;
        this.queuedFrames = new HashMap<>();
        this.sinceLastFrame = 0;
        this.name = name;
        this.level = 1;
        this.experience = 0;
        this.karma = 0;
        this.cash = cash;
        this.factionStanding = factionStanding;
        this.factionLocked = false;
        this.armor = 0;
        this.healthCurrent = this.level;
        this.healthMaximum = 1;
        this.staminaCurrent = 1;
        this.staminaMaximum = 1;
        this.calories = 2000;
        this.nutrition = 90;
        this.thirst = 75;
        this.temperature = 37.5;
        this.sicknessChance = 2;
        this.absorbedRadiation = 5;
        this.outputRadiation = 0;
        this.statusEffects = statusEffects;
        this.inventory = inventory;
        this.perks = perks;
        this.position = position;
        this.speed = 5f;
    }

    public Object perFrame() {
        return null;
    }
    
    public void cycleFrames() {
    }
}

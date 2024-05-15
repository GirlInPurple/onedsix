package onedsix.core.assets.abstracts;

import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import onedsix.core.assets.data.Attributes;
import onedsix.core.assets.data.Identifier;

public abstract class Entity extends Asset {
    /*
    Currently migrating this over to the attribute system and removing Lombok.
    Do not remove!

    @Getter @Setter private HashMap<Decal, Long> queuedFrames;
    @Getter @Setter private long sinceLastFrame;
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
    */
    public Decal img;
    public float speed;
    public Vector3 position;

    public Entity(
            Identifier<? extends Entity> ident,
            Attributes attr,
            Decal img,
            String name,
            Vector3 position
    ){
        super(ident, attr, name);
        this.img = img;
        /*
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
        */
        this.speed = 5f;
        this.position = position;
    }

    /** Cycles between a set of frames, used for animations. */
    public void cycleFrames() {}

    /** Is called for every data, every frame/server tick. Be careful what you put here! */
    public abstract Object perFrame();
}

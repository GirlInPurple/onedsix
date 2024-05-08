package onedsix.core.assets.data;

import onedsix.core.assets.abstracts.Attribute;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.function.Consumer;

public class Attributes {
    
    /*
    @Getter @Setter private String name;
    @Getter @Setter private int level;
    @Getter @Setter private float experience;
    @Getter @Setter private int karma;
    @Getter @Setter private int cash;
    @Getter @Setter private HashMap<Faction, Integer> factionStanding;
    @Getter @Setter private boolean factionLocked;
    @Getter @Setter private int armor;
    @Getter @Setter private int staminaCurrent;
    @Getter @Setter private int staminaMaximum;
    @Getter @Setter private double temperature;
    @Getter @Setter private float sicknessChance;
    @Getter @Setter private int absorbedRadiation;
    @Getter @Setter private int outputRadiation;
    */
    public final LinkedList<Attribute> attributes;
    
    /**
     * Creates a {@link Attributes} instance with the specified {@link Attribute} list.
     * */
    public Attributes(LinkedList<Attribute> attributes) {
        this.attributes = attributes;
    }
    
    /**
     * Creates a {@link Attributes} instance with the specified {@link Attribute} list.
     * */
    public Attributes(Attribute[] attr) {
        this.attributes = new LinkedList<>(Arrays.asList(attr));
    }
    
    /**
     * Creates a {@link Attributes} instance with an empty {@link Attribute} list. Used mostly for debugging.
     * */
    public Attributes() {
        this.attributes = new LinkedList<>();
    }
    
    /** Loops over all the attributes on this instance */
    public void forEachAttribute(Consumer<Attribute> processor) {
        for (Attribute item : this.attributes) {
            processor.accept(item);
        }
    }
    
    /** Loops over all the attributes of the input attributes instance */
    public static void forEachAttribute(Attributes attrInstance, Consumer<Attribute> processor) {
        for (Attribute item : attrInstance.attributes) {
            processor.accept(item);
        }
    }
    
    /**
     * Takes in a list of {@link Attributes} and merges all the values into one larger {@link Attributes} instance.
     * */
    public static Attributes mergeAttributes(Attributes... attrs) {
        LinkedList<Attribute> outputAttr = new LinkedList<>();
        for (Attributes attributes : attrs) {
            forEachAttribute(attributes, outputAttr::add);
        }
        return new Attributes(outputAttr);
    }
}

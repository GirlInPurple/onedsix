package onedsix.gen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import onedsix.Player;
import onedsix.gen.assets.*;
import onedsix.graphics.GeometryHandler;
import onedsix.util.FileHandler;
import onedsix.util.Logger;
import onedsix.util.Logger.*;

import java.util.HashMap;
import java.util.LinkedList;

import static onedsix.Vars.*;
import static onedsix.graphics.SvgHandler.loadSvgFromFile;
import static onedsix.graphics.SvgHandler.svgToPng;

public class DatagenHandler {
    private static final Logger L = new Logger(DatagenHandler.class);
    
    private static final LinkedList<Class<? extends Item>> customItems = new LinkedList<>();
    public static LinkedList<Class<? extends Item>> getCustomItems() {return customItems;}
    public static void addCustomItem(Class<? extends Item> I) {customItems.add(I);}
    
    public final String name;
    public final JsonObject jo;
    public final JsonArray geometry;
    public final JsonArray npcs;
    public final JsonObject stats;
    public final JsonObject misc;

    public DatagenHandler(JsonObject jo, String name) {
        this.name = name;
        this.jo = jo;
        this.geometry = jo.getAsJsonArray("geom");
        this.npcs = jo.getAsJsonArray("npc");
        this.stats = jo.getAsJsonObject("stats");
        this.misc = jo.getAsJsonObject("misc");
        
        long startTime = System.nanoTime();
        
        try {
            buildGeom(this);
            buildEntities(this);
            buildCellStats(this);
        } catch (Exception e) {
            L.error(e.getMessage(), e);
        } finally {
            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;
            double seconds = (double) elapsedTime / 1_000_000_000.0;
            L.info("Built Datagen! Elapsed Time: " + seconds + " seconds");
        }
    }
    
    public static void read() {
        // slow? yes.
        // can it use runnable? gl crashes if it does.
        // do we have any other options? not really.
        for (String s : loadList) {
            JsonObject js;
            
            L.loadingLogger("Reading "+s, Level.INFO);
            js = FileHandler.RESOURCES.getJson(s);
            
            if (js != null) {
                L.loadingLogger("Datagen started for " + s, Level.INFO);
                if (s.equals("playerdata.json")) {
                    buildPlayer(js);
                } else {
                    new DatagenHandler(js, s);
                }
            } else {
                L.loadingLogger("Could not read "+s, Level.ERROR);
            }
        }
        
        if (!loadList.isEmpty()) {
            L.loadingLogger("Finished loading datagen!", Level.INFO);
        }
        loadList.clear();
        currentPhase = Phases.INIT;
    }
    
    public static void buildGeom(DatagenHandler dgh) {
        for (JsonElement je : dgh.geometry.asList()) {
            switch (je.getAsJsonObject().get("model_type").getAsString()) {
                case "box": modelInstances.add(GeometryHandler.transformShorthand(
                        // Set the needed position
                        je.getAsJsonObject().get("x").getAsFloat(),
                        je.getAsJsonObject().get("y").getAsFloat(),
                        je.getAsJsonObject().get("z").getAsFloat(),
                        // Create the box
                        new ModelInstance(
                                modelBuilder.createBox(
                                        je.getAsJsonObject().get("width").getAsFloat(),
                                        je.getAsJsonObject().get("height").getAsFloat(),
                                        je.getAsJsonObject().get("depth").getAsFloat(),
                                        new Material(),
                                        VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal
                                ))
                ));
            }
        }
    }
    
    public static void buildEntities(DatagenHandler dgh) {
        for (JsonElement je : dgh.npcs.asList()) {
            L.info("Would be creating an entity right now!");
            je.getAsJsonObject().get("model_type").getAsString();
        }
    }
    
    public static void buildCellStats(DatagenHandler dgh) {
        L.info("Would be handling cell stats right now!");
    }
    
    public static void buildEntityStats(JsonObject JO) {
        L.info("Would be handling entity stats right now!");
    }
    
    public static void buildPlayer(JsonObject playerdata) {
        L.loadingLogger("Loading Playerdata...", Level.INFO);
        //Texture t = new Texture(svgToPng(loadSvgFromString(playerdata.get("svg").getAsString()), "player"));
        Texture t = new Texture(svgToPng(loadSvgFromFile(Gdx.files.internal("test.svg").path()), "player"));
        JsonArray pos = playerdata.get("position").getAsJsonArray();
        
        player = new Player(
                Decal.newDecal(2, 2, new TextureRegion(t), true),
                playerdata.get("name").getAsString(),
                playerdata.get("cash").getAsInt(),
                new HashMap<>(), //playerdata.get("standing").getAsJsonObject()
                new LinkedList<>(), //playerdata.get("effects").getAsJsonArray()
                new LinkedList<>(), //playerdata.get("inventory").getAsJsonArray()
                new LinkedList<>(), //playerdata.get("perks").getAsJsonArray()
                new Vector3(pos.get(0).getAsFloat(),pos.get(1).getAsFloat(),pos.get(2).getAsFloat())
        );
        L.loadingLogger("Loaded Playerdata!", Level.INFO);
    }
}

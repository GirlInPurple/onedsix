package onedsix.gen.handling;

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
import lombok.Getter;
import onedsix.Player;
import onedsix.Vars;
import onedsix.graphics.GeometryHandler;
import onedsix.util.FileHandler;
import onedsix.util.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;

import static onedsix.Vars.loadingLogs;
import static onedsix.graphics.SvgHandler.loadSvgFromFile;
import static onedsix.graphics.SvgHandler.svgToPng;

public class DatagenHandler {
    private static final Logger L = LoggerFactory.getLogger(DatagenHandler.class);
    @Getter private final String name;
    @Getter private final JsonObject jo;
    @Getter private final JsonArray geometry;
    @Getter private final JsonArray npcs;
    @Getter private final JsonObject stats;
    @Getter private final JsonObject misc;

    public DatagenHandler(JsonObject jo, String name) {
        this.name = name;
        this.jo = jo;
        this.geometry = jo.getAsJsonArray("geom");
        this.npcs = jo.getAsJsonArray("npc");
        this.stats = jo.getAsJsonObject("stats");
        this.misc = jo.getAsJsonObject("misc");
        
        long startTime = System.nanoTime();
        
        try {
            buildGeom();
            buildEntities();
            buildStats();
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
        for (String s : Vars.loadList) {
            JsonObject js;
            
            L.info("Reading "+s);
            loadingLogs.add(new LoggerUtils.LoadingLogs("Reading "+s));
            js = FileHandler.RESOURCES.getJson(s);
            
            if (js != null) {
                L.info("Datagen started for " + s);
                if (s.equals("playerdata.json")) {
                    buildPlayer(js);
                } else {
                    new DatagenHandler(js, s);
                }
            }
        }
        Vars.loadList.clear();
    }
    
    public void buildGeom() {
        for (JsonElement je : this.geometry.asList()) {
            switch (je.getAsJsonObject().get("model_type").getAsString()) {
                case "box": Vars.modelInstances.add(GeometryHandler.transformShorthand(
                        // Set the needed position
                        je.getAsJsonObject().get("x").getAsFloat(),
                        je.getAsJsonObject().get("y").getAsFloat(),
                        je.getAsJsonObject().get("z").getAsFloat(),
                        // Create the box
                        new ModelInstance(
                                Vars.modelBuilder.createBox(
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
    
    public void buildEntities() {
        for (JsonElement je : this.npcs.asList()) {
            L.info("Would be creating an entity right now!");
            je.getAsJsonObject().get("model_type").getAsString();
        }
    }
    
    public void buildStats() {
        L.info("Would be handling stats right now!");
    }
    
    public static void buildPlayer(JsonObject playerdata) {
        loadingLogs.add(new LoggerUtils.LoadingLogs("Loading Playerdata..."));
        //Texture t = new Texture(svgToPng(loadSvgFromString(playerdata.get("svg").getAsString()), "player"));
        Texture t = new Texture(svgToPng(loadSvgFromFile(Gdx.files.internal("test.svg").path()), "player"));
        JsonArray pos = playerdata.get("position").getAsJsonArray();
        
        Vars.player = new Player(
                Decal.newDecal(2, 2, new TextureRegion(t), true),
                playerdata.get("name").getAsString(),
                playerdata.get("cash").getAsInt(),
                new HashMap<>(), //playerdata.get("standing").getAsJsonObject()
                new LinkedList<>(), //playerdata.get("effects").getAsJsonArray()
                new LinkedList<>(), //playerdata.get("inventory").getAsJsonArray()
                new LinkedList<>(), //playerdata.get("perks").getAsJsonArray()
                new Vector3(pos.get(0).getAsFloat(),pos.get(1).getAsFloat(),pos.get(2).getAsFloat())
        );
        loadingLogs.add(new LoggerUtils.LoadingLogs("Loaded Playerdata!"));
    }
}
